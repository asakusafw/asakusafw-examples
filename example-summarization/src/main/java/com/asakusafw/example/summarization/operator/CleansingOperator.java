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
package com.asakusafw.example.summarization.operator;

import com.asakusafw.example.summarization.common.BatchContextParameters;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ErrorReceipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Receipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Shop;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.Branch;
import com.asakusafw.vocabulary.operator.MasterCheck;
import com.asakusafw.vocabulary.operator.Update;

/**
 * データのクレンジングを処理する演算子
 */
public abstract class CleansingOperator {

	/**
	 * 処理の分岐先を表す列挙型
	 */
	public enum CleansingResultType {
		/** 処理対象 **/
		VALID,
		/** 妥当性エラー **/
		INVALID,
		/** 処理対象外 **/
		EXCLUDE;
	}

	BatchContextParameters parameters = new BatchContextParameters();

	/**
	 * レシートデータの妥当性確認と発行日より処理対象レコードの判定をする。
	 *
	 * @param receipt
	 *            レシートデータ
	 * @return 処理の分岐先
	 */
	@Branch
	public CleansingResultType dataCleansing(final Receipt receipt) {

		if (receipt.getReceiptIdOption().isNull()
				|| receipt.getUnitSalesOption().isNull()
				|| receipt.getUnitSales() < 0
				|| receipt.getSellingPriceOption().isNull()
				|| receipt.getSellingPrice() < 0) {
			return CleansingResultType.INVALID;
		}
		if (receipt.getIssueDate().getElapsedSeconds() >= parameters
				.getFromIssueDate().getElapsedSeconds()
				&& receipt.getIssueDate().getElapsedSeconds() <= parameters
						.getToIssueDate().getElapsedSeconds()) {
			return CleansingResultType.VALID;
		} else {
			return CleansingResultType.EXCLUDE;
		}

	}

	/**
	 * レシートデータの店舗コードに対応する店舗マスタが存在するかチェックする。
	 *
	 * @param shop
	 *            店舗マスタ
	 * @param receipt
	 *            レシートデータ
	 * @return 存在すれば{@code true}
	 */
	@MasterCheck
	public abstract boolean exists(@Key(group = "shopCode") Shop shop,
			@Key(group = "shopCode") Receipt receipt);

	/**
	 * エラーレシートデータにエラーメッセージを設定する。
	 *
	 * @param error
	 *            エラーレシートデータ
	 * @param message
	 *            エラーメッセージ
	 */
	@Update
	public void setErrorMessage(ErrorReceipt error, String message) {
		error.setErrorMessageAsString(message);
	}

}
