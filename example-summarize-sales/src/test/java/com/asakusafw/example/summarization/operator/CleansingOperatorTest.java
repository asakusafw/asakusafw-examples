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
package com.asakusafw.example.summarization.operator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.asakusafw.example.summarization.common.BatchContextParameters;
import com.asakusafw.example.summarization.modelgen.dmdl.model.Receipt;
import com.asakusafw.example.summarization.operator.CleansingOperator.CleansingResultType;
import com.asakusafw.runtime.value.DateTime;
import com.asakusafw.runtime.value.DateTime.Format;
import com.asakusafw.testdriver.OperatorTestEnvironment;

/**
 * {@link CleansingOperator}のテスト
 */
public class CleansingOperatorTest {

	private OperatorTestEnvironment environment = new OperatorTestEnvironment();
	private Receipt receipt = new Receipt();
	private CleansingOperatorImpl operator = new CleansingOperatorImpl();
	private CleansingResultType actual;

	@Before
	public void setup() {
		environment.setBatchArg(BatchContextParameters.FROM_ISSUE_DATE, "20111101");
		environment.setBatchArg(BatchContextParameters.TO_ISSUE_DATE, "20111130");
		environment.reload();
		receipt.setReceiptId(0);
		receipt.setUnitSales(0);
		receipt.setSellingPrice(0);
	}

	@Test
	public void testResultTypeIsInvalid() {
		receipt.setReceiptIdOption(null);
		actual = operator.dataCleansing(receipt);
		assertThat("レシートIDがNullならINVALID", actual, is(CleansingResultType.INVALID));
	}

	@Test
	public void testResultTypeIsValid() {
		receipt.setIssueDate(DateTime.valueOf("20111101000000", Format.SIMPLE));
		actual = operator.dataCleansing(receipt);
		assertThat("対象データ(2011/11/01)ならVALID", actual, is(CleansingResultType.VALID));

		receipt.setIssueDate(DateTime.valueOf("20111130000000", Format.SIMPLE));
		actual = operator.dataCleansing(receipt);
		assertThat("対象データ(2011/11/30)ならVALID", actual, is(CleansingResultType.VALID));
	}

	@Test
	public void testResultTypeIsExclude() {
		receipt.setIssueDate(DateTime.valueOf("20111031000000", Format.SIMPLE));
		actual = operator.dataCleansing(receipt);
		assertThat("対象外データ(2011/10/31)ならEXCLUDE", actual, is(CleansingResultType.EXCLUDE));

		receipt.setIssueDate(DateTime.valueOf("20111201000000", Format.SIMPLE));
		actual = operator.dataCleansing(receipt);
		assertThat("対象外データ(2011/12/01)ならEXCLUDE", actual, is(CleansingResultType.EXCLUDE));
	}
}
