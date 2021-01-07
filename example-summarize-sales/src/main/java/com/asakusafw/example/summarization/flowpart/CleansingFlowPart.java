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
package com.asakusafw.example.summarization.flowpart;

import com.asakusafw.example.summarization.modelgen.dmdl.model.AllValues;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ErrorReceipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Item;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Receipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Shop;
import com.asakusafw.example.summarization.operator.CleansingOperatorFactory;
import com.asakusafw.example.summarization.operator.CleansingOperatorFactory.DataCleansing;
import com.asakusafw.example.summarization.operator.CleansingOperatorFactory.Exists;
import com.asakusafw.example.summarization.operator.CleansingOperatorFactory.SetErrorMessage;
import com.asakusafw.example.summarization.operator.JoinOperatorFactory;
import com.asakusafw.example.summarization.operator.JoinOperatorFactory.JoinItem;
import com.asakusafw.example.summarization.operator.JoinOperatorFactory.SetCustomers;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.FlowPart;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.Out;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory.Extend;

/**
 * データクレンジングを実行するフロー部品
 */
@FlowPart
public class CleansingFlowPart extends FlowDescription {

	private final In<Receipt> receipt;
	private final In<Shop> shop;
	private final In<Item> item;
	private final Out<AllValues> out;
	private final Out<ErrorReceipt> error;

	/**
	 * データクレンジングを実行するフロー部品のインスタンスを生成する。
	 *
	 * @param receipt
	 *            レシートデータ
	 * @param shop
	 *            店舗マスタ
	 * @param item
	 *            商品マスタ
	 * @param out
	 *            クレンジング後のレシートデータ
	 * @param error
	 *            エラーレシートデータ
	 */
	public CleansingFlowPart(In<Receipt> receipt, In<Shop> shop, In<Item> item,
			Out<AllValues> out, Out<ErrorReceipt> error) {
		this.receipt = receipt;
		this.shop = shop;
		this.item = item;
		this.out = out;
		this.error = error;
	}

	@Override
	protected void describe() {
		CoreOperatorFactory core = new CoreOperatorFactory();
		CleansingOperatorFactory cleansing = new CleansingOperatorFactory();

		// データクレンジング
		DataCleansing dataCleansing = cleansing.dataCleansing(receipt);
		core.stop(dataCleansing.exclude);

		// 店舗コードの存在チェックを行う
		Exists exists = cleansing.exists(shop, dataCleansing.valid);

		// 商品マスタと結合する
		JoinOperatorFactory join = new JoinOperatorFactory();
		JoinItem joinItem = join.joinItem(item, exists.found);

		// 集計用項目を追加する
		Extend<AllValues> allValues = core.extend(joinItem.joined,
				AllValues.class);
		SetCustomers result = join.setCustomers(allValues);
		out.add(result.out);

		// 不正なレシートデータはエラー
		SetErrorMessage invalidReceipt = cleansing.setErrorMessage(
				core.extend(dataCleansing.invalid, ErrorReceipt.class),
				"妥当性エラー");
		error.add(invalidReceipt.out);

		// 登録されていない店舗コードのレシートはエラー
		SetErrorMessage shopUnregistered = cleansing.setErrorMessage(
				core.extend(exists.missed, ErrorReceipt.class), "店舗マスタ未登録");
		error.add(shopUnregistered.out);

		// 登録されていない商品コードのレシートはエラー
		SetErrorMessage itemUnregistered = cleansing.setErrorMessage(
				core.extend(joinItem.missed, ErrorReceipt.class), "商品マスタ未登録");
		error.add(itemUnregistered.out);
	}
}
