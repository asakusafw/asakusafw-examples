/**
 * Copyright 2012 Asakusa Framework Team.
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
import com.asakusafw.example.summarization.modelgen.dmdl.model.Item;
import com.asakusafw.example.summarization.modelgen.dmdl.model.JoinedReceipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Receipt;
import com.asakusafw.vocabulary.operator.MasterJoin;
import com.asakusafw.vocabulary.operator.Update;



/**
 * レシートデータと商品マスタを結合する演算子
 */
public abstract class JoinOperator {

	/**
	 * レシートデータと商品マスタを商品コードで結合する
	 *
	 * @param master
	 *            商品マスタ
	 * @param tx
	 *            レシートデータ
	 * @return 結合結果
	 */
	@MasterJoin
	public abstract JoinedReceipt joinItem(Item master, Receipt tx);

	/**
	 * 客数を初期化する。
	 *
	 * @param values
	 *            商品マスタと結合したレシートデータ
	 */
	@Update
	public void setCustomers(AllValues values) {
		values.setCustomers(1);
	}

}
