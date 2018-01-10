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
package com.asakusafw.example.summarization.flowpart;

import org.junit.Before;
import org.junit.Test;

import com.asakusafw.example.summarization.modelgen.dmdl.model.AllValues;
import com.asakusafw.example.summarization.modelgen.dmdl.model.CategorySummary;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ItemSummary;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ShopSummary;
import com.asakusafw.testdriver.FlowPartTester;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.Out;

/**
 * {@link SummarizeReceiptFlowPart}のテスト
 */
public class SummarizeReceiptFlowPartTest {

	static final String INPUT_PATH = "/com/asakusafw/example/summarization/input";
	static final String OUTPUT_PATH = "/com/asakusafw/example/summarization/output";

	FlowPartTester tester;

	@Before
	public void setup() {
		tester = new FlowPartTester(getClass());
	}

	@Test
	public void testSummarizeReceiptFlowPart() {

		In<AllValues> allValues = tester.input("allvalues", AllValues.class)
				.prepare(INPUT_PATH + "/all_values.xls#input");

		Out<ItemSummary> item = tester.output("item", ItemSummary.class)
				.verify(OUTPUT_PATH + "/expected_item_summary.xls#output",
						OUTPUT_PATH + "/expected_item_summary.xls#rule");

		Out<CategorySummary> category = tester.output("category", CategorySummary.class)
				.verify(OUTPUT_PATH + "/expected_category_summary.xls#output",
						OUTPUT_PATH + "/expected_category_summary.xls#rule");

		Out<ShopSummary> shop = tester.output("shop", ShopSummary.class)
				.verify(OUTPUT_PATH + "/expected_shop_summary.xls#output",
						OUTPUT_PATH + "/expected_shop_summary.xls#rule");

		tester.runTest(new SummarizeReceiptFlowPart(allValues, item, category, shop));
	}

}
