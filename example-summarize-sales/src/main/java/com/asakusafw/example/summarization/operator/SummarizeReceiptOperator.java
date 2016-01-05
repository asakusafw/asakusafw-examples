/**
 * Copyright 2011-2016 Asakusa Framework Team.
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
package com.asakusafw.example.summarization.operator;

import com.asakusafw.example.summarization.modelgen.dmdl.model.AllValues;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.Fold;

/**
 * 単品別、カテゴリ別、店舗別にレシートデータを集計する演算子
 */
public abstract class SummarizeReceiptOperator {

	/**
	 * 商品コード別にレシートデータを集計する
	 * @param sum 集計結果レコード
	 * @param each 集計対象レコード
	 */
	@Fold
	public void summarizeItem(@Key(group = "jan_code", order = "receipt_id ASC") AllValues sum, AllValues each) {
		addValues(sum, each);
	}

	/**
	 * 店舗コード別にレシートデータを集計する
	 * @param sum 集計結果レコード
	 * @param each 集計対象レコード
	 */
	@Fold
	public void summarizeShop(@Key(group = "shop_code", order = "receipt_id ASC") AllValues sum, AllValues each) {
		addValues(sum, each);
	}

	/**
	 * カテゴリコード別にレシートデータを集計する
	 * @param sum 集計結果レコード
	 * @param each 集計対象レコード
	 */
	@Fold
	public void summarizeCategory(@Key(group = "category_code", order = "receipt_id ASC") AllValues sum, AllValues each) {
		addValues(sum, each);
	}

	/**
	 * 値を集計結果に追加する
	 * @param sum 集計結果レコード
	 * @param each 集計対象レコード
	 */
	private void addValues(AllValues sum, AllValues each) {
		if (!sum.getReceiptIdOption().equals(each.getReceiptIdOption())) {
			sum.setCustomers(sum.getCustomers() + 1);
			sum.setReceiptId(each.getReceiptId());
		}
		sum.setUnitSales(sum.getUnitSalesOption().or(0) + each.getUnitSalesOption().or(0));
		sum.setSellingPrice(sum.getSellingPriceOption().or(0) + each.getSellingPriceOption().or(0));
	}

}
