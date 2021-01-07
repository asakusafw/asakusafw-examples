/**
 * Copyright 2011-2021 Asakusa Framework Team.
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
package com.example.flowpart;

import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.FlowPart;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.Out;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory;
import com.example.modelgen.dmdl.model.CategorySummary;
import com.example.modelgen.dmdl.model.ErrorRecord;
import com.example.modelgen.dmdl.model.ItemInfo;
import com.example.modelgen.dmdl.model.SalesDetail;
import com.example.modelgen.dmdl.model.StoreInfo;
import com.example.operator.CategorySummaryOperatorFactory;
import com.example.operator.CategorySummaryOperatorFactory.CheckStore;
import com.example.operator.CategorySummaryOperatorFactory.JoinItemInfo;
import com.example.operator.CategorySummaryOperatorFactory.SetErrorMessage;
import com.example.operator.CategorySummaryOperatorFactory.SummarizeByCategory;

/**
 * カテゴリ別に売上の集計を計算する。
 */
@FlowPart
public class CategorySummaryFlowPart extends FlowDescription {

    final In<SalesDetail> salesDetail;

    final In<StoreInfo> storeInfo;

    final In<ItemInfo> itemInfo;

    final Out<CategorySummary> categorySummary;

    final Out<ErrorRecord> errorRecord;


    /**
     * フロー部品インスタンスを生成する。
     * @param salesDetail 売上明細
     * @param storeInfo 店舗マスタ
     * @param itemInfo 商品マスタ
     * @param categorySummary カテゴリ別集計結果
     * @param errorRecord エラーレコード
     */
    public CategorySummaryFlowPart(
            In<SalesDetail> salesDetail,
            In<StoreInfo> storeInfo,
            In<ItemInfo> itemInfo,
            Out<CategorySummary> categorySummary,
            Out<ErrorRecord> errorRecord
            ) {
        this.salesDetail = salesDetail;
        this.storeInfo = storeInfo;
        this.itemInfo = itemInfo;
        this.categorySummary = categorySummary;
        this.errorRecord = errorRecord;
    }

    @Override
    protected void describe() {
        CoreOperatorFactory core = new CoreOperatorFactory();
        CategorySummaryOperatorFactory operators = new CategorySummaryOperatorFactory();

        // 店舗コードが妥当かどうか調べる
        CheckStore checkStore = operators.checkStore(storeInfo, salesDetail);

        // 売上に商品情報を載せる
        JoinItemInfo joinItemInfo = operators.joinItemInfo(itemInfo, checkStore.found);

        // 売上をカテゴリ別に集計
        SummarizeByCategory summarize = operators.summarizeByCategory(joinItemInfo.joined);

        // 集計結果を出力
        categorySummary.add(summarize.out);

        // 存在しない店舗コードでの売上はエラー
        SetErrorMessage unknownStore = operators.setErrorMessage(
                core.restructure(checkStore.missed, ErrorRecord.class),
                "店舗不明");
        errorRecord.add(unknownStore.out);

        // 商品情報が存在しない売上はエラー
        SetErrorMessage unknownItem = operators.setErrorMessage(
                core.restructure(joinItemInfo.missed, ErrorRecord.class),
                "商品不明");
        errorRecord.add(unknownItem.out);
    }
}
