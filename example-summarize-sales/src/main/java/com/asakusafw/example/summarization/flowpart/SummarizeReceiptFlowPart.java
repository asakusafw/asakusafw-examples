/**
 * Copyright 2011-2015 Asakusa Framework Team.
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
package com.asakusafw.example.summarization.flowpart;

import com.asakusafw.example.summarization.modelgen.dmdl.model.AllValues;
import com.asakusafw.example.summarization.modelgen.dmdl.model.CategorySummary;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ItemSummary;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ShopSummary;
import com.asakusafw.example.summarization.operator.SummarizeReceiptOperatorFactory;
import com.asakusafw.example.summarization.operator.SummarizeReceiptOperatorFactory.SummarizeCategory;
import com.asakusafw.example.summarization.operator.SummarizeReceiptOperatorFactory.SummarizeItem;
import com.asakusafw.example.summarization.operator.SummarizeReceiptOperatorFactory.SummarizeShop;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.FlowPart;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.Out;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory.Project;

/**
 * 単品、カテゴリ、商品別に集計するフロー部品
 */
@FlowPart
public class SummarizeReceiptFlowPart extends FlowDescription {

	private final In<AllValues> allValues;
	private final Out<ItemSummary> itemSummary;
	private final Out<CategorySummary> categorySummary;
	private final Out<ShopSummary> shopSummary;

	/**
	 * 集計を実行するフロー部品のインスタンスを生成する。
	 *
	 * @param allValues
	 *            クレンジング後レシートデータ
	 * @param itemSummary
	 *            単品別集計結果
	 * @param categorySummary
	 *            カテゴリ別集計結果
	 * @param shopSummary
	 *            店舗別集計結果
	 */
	public SummarizeReceiptFlowPart(In<AllValues> allValues,
			Out<ItemSummary> itemSummary, Out<CategorySummary> categorySummary,
			Out<ShopSummary> shopSummary) {
		this.allValues = allValues;
		this.itemSummary = itemSummary;
		this.categorySummary = categorySummary;
		this.shopSummary = shopSummary;
	}

	@Override
	protected void describe() {
		CoreOperatorFactory core = new CoreOperatorFactory();
		SummarizeReceiptOperatorFactory sum = new SummarizeReceiptOperatorFactory();

		// 単品毎に集計する
		SummarizeItem summarizedItem = sum.summarizeItem(allValues);
		Project<ItemSummary> item = core.project(summarizedItem.out,
				ItemSummary.class);
		itemSummary.add(item);

		// 商品分類毎に集計する
		SummarizeCategory summarizedCategory = sum.summarizeCategory(allValues);
		Project<CategorySummary> category = core.project(
				summarizedCategory.out, CategorySummary.class);
		categorySummary.add(category);

		// 店舗毎に集計する
		SummarizeShop summarizedShop = sum.summarizeShop(allValues);
		Project<ShopSummary> shop = core.project(summarizedShop.out,
				ShopSummary.class);
		shopSummary.add(shop);
	}
}
