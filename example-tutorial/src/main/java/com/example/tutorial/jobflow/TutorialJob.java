/**
 * Copyright 2011 Asakusa Framework Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tutorial.jobflow;

import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;
import com.example.tutorial.flowpart.TutorialFlowFactory;
import com.example.tutorial.flowpart.TutorialFlowFactory.TutorialFlow;
import com.example.tutorial.modelgen.table.model.ItemInfo;
import com.example.tutorial.modelgen.table.model.OrderAmount;
import com.example.tutorial.modelgen.table.model.OrderDetail;

/**
 * チュートリアル用のジョブフロー。
 */
@JobFlow(name = "order")
public class TutorialJob extends FlowDescription {

    private In<OrderDetail> orderIn;

    private In<ItemInfo> itemIn;

    private Out<OrderDetail> orderOut;

    private Out<OrderAmount> resultOut;
    
    /**
     * コンストラクタ。
     * @param orderIn 処理対象の注文明細
     * @param itemIn 商品マスタ
     * @param orderOut 処理結果の注文明細
     * @param resultOut 集計結果
     */
    public TutorialJob(
            @Import(name = "order", description = OrderDetailFromDb.class)
            In<OrderDetail> orderIn,
            @Import(name = "item", description = ItemInfoFromDb.class)
            In<ItemInfo> itemIn,
            @Export(name = "order", description = OrderDetailToDb.class)
            Out<OrderDetail> orderOut,
            @Export(name = "amount", description = OrderAmountToDb.class)
            Out<OrderAmount> resultOut) {
        this.orderIn = orderIn;
        this.itemIn = itemIn;
        this.orderOut = orderOut;
        this.resultOut = resultOut;
    }

    @Override
    protected void describe() {
        TutorialFlowFactory flow = new TutorialFlowFactory();
        
        // 今回は作成したフロー部品をそのまま接続する
        TutorialFlow result = flow.create(orderIn, itemIn);
        orderOut.add(result.orderOut);
        resultOut.add(result.resultOut);
    }
}
