/**
 * Copyright 2011-2018 Asakusa Framework Team.
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
package com.example.jobflow;

import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;
import com.example.flowpart.CategorySummaryFlowPartFactory;
import com.example.flowpart.CategorySummaryFlowPartFactory.CategorySummaryFlowPart;
import com.example.modelgen.dmdl.model.CategorySummary;
import com.example.modelgen.dmdl.model.ErrorRecord;
import com.example.modelgen.dmdl.model.ItemInfo;
import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;

/**
 * カテゴリ別に売上の集計を計算する。
 */
@JobFlow(name = "byCategory")
public class CategorySummaryJob extends FlowDescription {

    final In<SalesDetail> salesDetail;

    final In<StoreInfo> storeInfo;

    final In<ItemInfo> itemInfo;

    final Out<CategorySummary> categorySummary;

    final Out<ErrorRecord> errorRecord;

    /**
     * ジョブフローインスタンスを生成する。
     * @param salesDetail 売上明細
     * @param storeInfo 店舗マスタ
     * @param itemInfo 商品マスタ
     * @param categorySummary カテゴリ別集計結果
     * @param errorRecord エラーレコード
     */
    public CategorySummaryJob(
            @Import(name = "salesDetail", description = SalesDetailFromCsv.class)
            In<SalesDetail> salesDetail,
            @Import(name = "storeInfo", description = StoreInfoFromCsv.class)
            In<StoreInfo> storeInfo,
            @Import(name = "itemInfo", description = ItemInfoFromCsv.class)
            In<ItemInfo> itemInfo,
            @Export(name = "categorySummary", description = CategorySummaryToCsv.class)
            Out<CategorySummary> categorySummary,
            @Export(name = "errorRecord", description = ErrorRecordToCsv.class)
            Out<ErrorRecord> errorRecord) {
        this.salesDetail = salesDetail;
        this.storeInfo = storeInfo;
        this.itemInfo = itemInfo;
        this.categorySummary = categorySummary;
        this.errorRecord = errorRecord;
    }

    @Override
    protected void describe() {
        CategorySummaryFlowPartFactory flowPartFactory = new CategorySummaryFlowPartFactory();

        CategorySummaryFlowPart flowPart = flowPartFactory.create(salesDetail, storeInfo, itemInfo);
        categorySummary.add(flowPart.categorySummary);
        errorRecord.add(flowPart.errorRecord);
    }
}
