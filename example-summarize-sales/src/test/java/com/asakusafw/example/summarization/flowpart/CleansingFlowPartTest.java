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

import org.junit.Before;
import org.junit.Test;

import com.asakusafw.example.summarization.common.BatchContextParameters;
import com.asakusafw.example.summarization.modelgen.dmdl.model.AllValues;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ErrorReceipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Item;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Receipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Shop;
import com.asakusafw.testdriver.FlowPartTester;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.Out;

/**
 * {@link CleansingFlowPart}のテスト
 */
public class CleansingFlowPartTest {

	static final String INPUT_PATH = "/com/asakusafw/example/summarization/input/";
	static final String PATH = "/com/asakusafw/example/summarization/flowpart/cleansing/";

	FlowPartTester tester;

	@Before
	public void setup() {
		tester = new FlowPartTester(getClass());
		tester.setBatchArg(BatchContextParameters.FROM_ISSUE_DATE, "20120101");
		tester.setBatchArg(BatchContextParameters.TO_ISSUE_DATE, "20120131");
	}

	/**
	 * 処理対象外のレシートデータ
	 */
	@Test
	public void testExcludeReceipt() {
		run(PATH + "exclude_receipt.xls");
	}

	/**
	 * 妥当性エラーのテスト
	 */
	@Test
	public void testInvalidReceipt() {
		run(PATH + "invalid_receipt.xls");
	}

	/**
	 * マスタ未登録のレシートデータ
	 */
	@Test
	public void testInvalidMaster() {
		run(PATH + "invalid_master.xls");
	}

	/**
	 * クレンジング正常処理のテスト
	 */
	@Test
	public void testCleansing() {
		run(PATH + "valid_receipt.xls");
	}

	private void run(String dataSet) {
		In<Receipt> receipt = tester.input("receipt", Receipt.class)
				.prepare(dataSet + "#receipt");
		In<Shop> shop = tester.input("shop", Shop.class)
				.prepare(INPUT_PATH + "shop.xls#input");
		In<Item> item = tester.input("item", Item.class)
				.prepare(INPUT_PATH + "item.xls#input");
		Out<AllValues> allValues = tester.output("result", AllValues.class)
				.verify(dataSet + "#all_values",
						PATH + "verify_rule.xls#all_values");
		Out<ErrorReceipt> error = tester.output("error", ErrorReceipt.class)
				.verify(dataSet + "#error_receipt",
						PATH + "verify_rule.xls#error_receipt");
		tester.runTest(new CleansingFlowPart(receipt, shop, item, allValues,
				error));
	}

}
