/**
 * Copyright 2011-2017 Asakusa Framework Team.
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
package com.asakusafw.example.summarization.jobflow;

import com.asakusafw.example.summarization.flowpart.CleansingFlowPartFactory;
import com.asakusafw.example.summarization.flowpart.CleansingFlowPartFactory.CleansingFlowPart;
import com.asakusafw.example.summarization.flowpart.SummarizeReceiptFlowPartFactory;
import com.asakusafw.example.summarization.flowpart.SummarizeReceiptFlowPartFactory.SummarizeReceiptFlowPart;
import com.asakusafw.example.summarization.gateway.CategorySummaryToCSV;
import com.asakusafw.example.summarization.gateway.ErrorReceiptToCSV;
import com.asakusafw.example.summarization.gateway.ItemFromCSV;
import com.asakusafw.example.summarization.gateway.ItemSummaryToCSV;
import com.asakusafw.example.summarization.gateway.ReceiptFromCSV;
import com.asakusafw.example.summarization.gateway.ShopFromCSV;
import com.asakusafw.example.summarization.gateway.ShopSummaryToCSV;
import com.asakusafw.example.summarization.modelgen.dmdl.model.CategorySummary;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ErrorReceipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Item;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ItemSummary;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Receipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Shop;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ShopSummary;
import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;

/**
 * レシートデータから単品別、カテゴリ別、店舗別の集計を出力する。
 *
 */
@JobFlow(name = "summarizeReceiptJobFlow")
public class SummarizeReceiptJobFlow extends FlowDescription {

	private final In<Receipt> receipt;
	private final In<Shop> shop;
	private final In<Item> item;
	private final Out<ItemSummary> itemSummary;
	private final Out<CategorySummary> categorySummary;
	private final Out<ShopSummary> shopSummary;
	private final Out<ErrorReceipt> error;

	/**
	 * ジョブフローインスタンスを生成する。
	 * @param receipt レシートデータ
	 * @param shop 店舗マスタ
	 * @param item 商品マスタ
	 * @param itemSummary 単品別集計結果
	 * @param categorySummary カテゴリ別集計結果
	 * @param shopSummary 店舗別集計結果
	 * @param error エラーレシートデータ
	 */
	public SummarizeReceiptJobFlow(
			@Import(name = "receipt", description = ReceiptFromCSV.class)
			In<Receipt> receipt,
			@Import(name = "shop", description = ShopFromCSV.class)
			In<Shop> shop,
			@Import(name = "item", description = ItemFromCSV.class)
			In<Item> item,
			@Export(name = "itemSummary", description = ItemSummaryToCSV.class)
			Out<ItemSummary> itemSummary,
			@Export(name = "categorySummary", description = CategorySummaryToCSV.class)
			Out<CategorySummary> categorySummary,
			@Export(name = "shopSummary", description = ShopSummaryToCSV.class)
			Out<ShopSummary> shopSummary,
			@Export(name = "error", description = ErrorReceiptToCSV.class)
			Out<ErrorReceipt> error) {
		this.receipt = receipt;
		this.shop = shop;
		this.item = item;
		this.itemSummary = itemSummary;
		this.categorySummary = categorySummary;
		this.shopSummary = shopSummary;
		this.error = error;
	}

	@Override
	public void describe() {

		// データクレンジング
		CleansingFlowPartFactory cleansing = new CleansingFlowPartFactory();
		CleansingFlowPart cleaned = cleansing.create(receipt, shop, item);
		error.add(cleaned.error);

		// 集計
		SummarizeReceiptFlowPartFactory summarize = new SummarizeReceiptFlowPartFactory();
		SummarizeReceiptFlowPart summarized = summarize.create(cleaned.out);
		itemSummary.add(summarized.itemSummary);
		categorySummary.add(summarized.categorySummary);
		shopSummary.add(summarized.shopSummary);
	}
}
