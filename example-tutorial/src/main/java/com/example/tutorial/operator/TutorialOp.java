/**
 * Copyright 2011-2014 Asakusa Framework Team.
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
package com.example.tutorial.operator;

import com.asakusafw.vocabulary.operator.MasterJoin;
import com.asakusafw.vocabulary.operator.Summarize;
import com.asakusafw.vocabulary.operator.Update;
import com.example.tutorial.modelgen.table.model.ItemInfo;
import com.example.tutorial.modelgen.table.model.OrderDetail;
import com.example.tutorial.modelgen.view.model.JoinOrder;
import com.example.tutorial.modelgen.view.model.SumOrder;

/**
 * チュートリアルで利用する演算子。
 */
public abstract class TutorialOp {
    
    /**
     * 注文商品の情報と明細を結合する。
     * @param info 注文商品の情報 
     * @param order 明細
     * @return 結合した結果
     */
    @MasterJoin
    public abstract JoinOrder join(ItemInfo info, OrderDetail order);
    
    /**
     * 明細の価格を、注文ごとに集計する。
     * @param each それぞれの明細
     * @return 集計した結果
     */
    @Summarize
    public abstract SumOrder sum(JoinOrder each);
    
    /**
     * 指定の注文明細に状態を設定する。
     * @param order 対象の明細
     * @param status 設定する状態文字列
     */
    @Update
    public void setStatus(OrderDetail order, String status) {
        order.setStatusAsString(status);
    }
}
