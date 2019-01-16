/**
 * Copyright 2011-2019 Asakusa Framework Team.
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
package com.asakusafw.example.summarization.batch;

import org.junit.Before;
import org.junit.Test;

import com.asakusafw.example.summarization.common.BatchContextParameters;
import com.asakusafw.example.summarization.jobflow.SummarizeReceiptJobFlow;
import com.asakusafw.example.summarization.modelgen.dmdl.model.CategorySummary;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ErrorReceipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Item;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ItemSummary;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Receipt;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Shop;
import com.asakusafw.example.summarization.modelgen.dmdl.model.ShopSummary;
import com.asakusafw.testdriver.BatchTester;
import com.asakusafw.testdriver.excel.ExcelSheetSinkFactory;
import com.asakusafw.testdriver.html.HtmlDifferenceSinkFactory;

/**
 * {@link SummarizeReceiptJobFlow}のテスト
 */
public class SummarizeReceiptBatchTest {

	static final String INPUT_PATH = "/com/asakusafw/example/summarization/input";
	static final String OUTPUT_PATH = "/com/asakusafw/example/summarization/output";
	static final String DUMP_PATH = "/dump";
	static final String JOBFLOW_NAME = "summarizeReceiptJobFlow";

	BatchTester tester;

	@Before
	public void setup() {
		tester = new BatchTester(getClass());
		tester.setBatchArg(BatchContextParameters.FROM_ISSUE_DATE, "20120101");
		tester.setBatchArg(BatchContextParameters.TO_ISSUE_DATE, "20120131");
	}

	@Test
	public void testSummarizeReceiptBatch() {

		tester.jobflow(JOBFLOW_NAME).input("receipt", Receipt.class)
			.prepare(INPUT_PATH + "/receipt.xls#input");
		tester.jobflow(JOBFLOW_NAME).input("shop", Shop.class)
			.prepare(INPUT_PATH + "/shop.xls#input");
		tester.jobflow(JOBFLOW_NAME).input("item", Item.class)
			.prepare(INPUT_PATH + "/item.xls#input");

		tester.jobflow(JOBFLOW_NAME).output("error", ErrorReceipt.class)
			.verify(OUTPUT_PATH + "/expected_error_receipt.xls#output",
					OUTPUT_PATH + "/expected_error_receipt.xls#rule")
			.dumpActual(new ExcelSheetSinkFactory("target" +
					DUMP_PATH + "/error_receipt_actual.xls"))
			.dumpDifference(new HtmlDifferenceSinkFactory("target" +
					DUMP_PATH + "/error_receipt_difference.html"));

		tester.jobflow(JOBFLOW_NAME).output("itemSummary", ItemSummary.class)
			.verify(OUTPUT_PATH + "/expected_item_summary.xls#output",
					OUTPUT_PATH + "/expected_item_summary.xls#rule")
			.dumpActual(new ExcelSheetSinkFactory("target" +
					DUMP_PATH + "/item_summary_actual.xls"))
			.dumpDifference(new HtmlDifferenceSinkFactory("target" +
					DUMP_PATH + "/item_summary_difference.html"));

		tester.jobflow(JOBFLOW_NAME).output("categorySummary", CategorySummary.class)
			.verify(OUTPUT_PATH + "/expected_category_summary.xls#output",
					OUTPUT_PATH + "/expected_category_summary.xls#rule")
			.dumpActual(new ExcelSheetSinkFactory("target" +
					DUMP_PATH + "/category_summary_actual.xls"))
			.dumpDifference(new HtmlDifferenceSinkFactory("target" +
					DUMP_PATH + "/category_summary_difference.html"));

		tester.jobflow(JOBFLOW_NAME).output("shopSummary", ShopSummary.class)
			.verify(OUTPUT_PATH + "/expected_shop_summary.xls#output",
					OUTPUT_PATH + "/expected_shop_summary.xls#rule")
			.dumpActual(new ExcelSheetSinkFactory("target" +
					DUMP_PATH + "/shop_summary_actual.xls"))
			.dumpDifference(new HtmlDifferenceSinkFactory("target" +
					DUMP_PATH + "/shop_summary_difference.html"));

		tester.runTest(SummarizeReceiptBatch.class);
	}
}
