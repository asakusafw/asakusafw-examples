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
package com.asakusafw.example.summarization.common;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.asakusafw.example.summarization.common.BatchContextParameters;
import com.asakusafw.runtime.value.DateTime;
import com.asakusafw.testdriver.OperatorTestEnvironment;

/**
 * {@link BatchContextParameters}のテスト
 */
public class BatchContextParametersTest {

	private BatchContextParameters parameters = new BatchContextParameters();
	private OperatorTestEnvironment environment = new OperatorTestEnvironment();

	@Test
	public void testFromIssueDate() {
		environment.setBatchArg(BatchContextParameters.FROM_ISSUE_DATE, "20111101");
		environment.reload();
		DateTime actual = parameters.getFromIssueDate();
		assertThat(actual.getYear(), is(equalTo(2011)));
		assertThat(actual.getMonth(), is(equalTo(11)));
		assertThat(actual.getDay(), is(equalTo(01)));
		assertThat(actual.getHour(), is(equalTo(00)));
		assertThat(actual.getMinute(), is(equalTo(00)));
		assertThat(actual.getSecond(), is(equalTo(00)));
	}

	@Test
	public void testToIssueDate() {
		environment.setBatchArg(BatchContextParameters.TO_ISSUE_DATE, "20111130");
		environment.reload();
		DateTime actual = parameters.getToIssueDate();
		assertThat(actual.getYear(), is(equalTo(2011)));
		assertThat(actual.getMonth(), is(equalTo(11)));
		assertThat(actual.getDay(), is(equalTo(30)));
		assertThat(actual.getHour(), is(equalTo(23)));
		assertThat(actual.getMinute(), is(equalTo(59)));
		assertThat(actual.getSecond(), is(equalTo(59)));
	}

}
