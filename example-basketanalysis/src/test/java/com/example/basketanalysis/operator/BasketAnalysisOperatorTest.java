/**
 * Copyright 2011-2014 Asakusa Framework Team.
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
package com.example.basketanalysis.operator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.asakusafw.runtime.testing.MockResult;
import com.asakusafw.testdriver.OperatorTestEnvironment;
import com.example.basketanalysis.modelgen.dmdl.model.Collocation;
import com.example.basketanalysis.modelgen.dmdl.model.LogLine;
import com.example.basketanalysis.testing.LogLineFactory;

public class BasketAnalysisOperatorTest {

	@Rule
	public OperatorTestEnvironment environment = new OperatorTestEnvironment();

	private BasketAnalysisOperator operator;

	@Before
	public void setup() {
		environment.setBatchArg("WINDOW", "1000");
		environment.reload();

		operator = new BasketAnalysisOperatorImpl();
	}

	@Test
	public void testBlank() {
		List<LogLine> lines = new ArrayList<LogLine>();
		MockResult<Collocation> result = new MockResult<Collocation>();
		operator.buildCollocation(lines, result);

		List<Collocation> results = result.getResults();
		assertThat(results.size(), is(0));
	}

	@Test
	public void testSingleInput() {
		List<LogLine> lines = new ArrayList<LogLine>();
		lines.add(LogLineFactory.create("user1", "10000000", "test"));

		MockResult<Collocation> result = new MockResult<Collocation>();
		operator.buildCollocation(lines, result);

		List<Collocation> results = result.getResults();
		assertThat(results.size(), is(0));
	}

	@Test
	public void testDualInputWindowIncluded() {
		List<LogLine> lines = new ArrayList<LogLine>();
		lines.add(LogLineFactory.create("user1", "10000000", "first"));
		lines.add(LogLineFactory.create("user1", "10001000", "second"));

		MockResult<Collocation> result = new MockResult<Collocation>();

		operator.buildCollocation(lines, result);

		List<Collocation> results = result.getResults();
		assertThat(results.size(), is(1));

		Collocation collocation = result.getResults().get(0);
		assertThat(collocation.getFirstAsString(), is("first"));
		assertThat(collocation.getSecondAsString(), is("second"));
		assertThat(collocation.getCount(), is(1));
	}

	@Test
	public void testDualInputWindowSeparated() {
		List<LogLine> lines = new ArrayList<LogLine>();
		lines.add(LogLineFactory.create("user1", "10000000", "first"));
		lines.add(LogLineFactory.create("user1", "10001001", "second"));

		MockResult<Collocation> result = new MockResult<Collocation>();
		operator.buildCollocation(lines, result);

		List<Collocation> results = result.getResults();
		assertThat(results.size(), is(0));
	}

	@Test
	public void testMultiInputWindowIncluded() {
		List<LogLine> lines = new ArrayList<LogLine>();
		lines.add(LogLineFactory.create("user1", "10000000", "one"));
		lines.add(LogLineFactory.create("user1", "10000500", "two"));
		lines.add(LogLineFactory.create("user1", "10001000", "three"));

		MockResult<Collocation> result = new MockResult<Collocation>();
		operator.buildCollocation(lines, result);

		List<Collocation> results = result.getResults();
		assertThat(results.size(), is(3));
		
		Collocation collocation = null;

		collocation = result.getResults().get(0);
		assertThat(collocation.getFirstAsString(), is("one"));
		assertThat(collocation.getSecondAsString(), is("two"));
		assertThat(collocation.getCount(), is(1));

		collocation = result.getResults().get(1);
		assertThat(collocation.getFirstAsString(), is("one"));
		assertThat(collocation.getSecondAsString(), is("three"));
		assertThat(collocation.getCount(), is(1));

		collocation = result.getResults().get(2);
		assertThat(collocation.getFirstAsString(), is("two"));
		assertThat(collocation.getSecondAsString(), is("three"));
		assertThat(collocation.getCount(), is(1));
	}

	@Test
	public void testMultiInputWindowSeparated() {
		List<LogLine> lines = new ArrayList<LogLine>();
		lines.add(LogLineFactory.create("user1", "10000000", "one"));
		lines.add(LogLineFactory.create("user1", "10001001", "two"));
		lines.add(LogLineFactory.create("user1", "10002002", "three"));

		MockResult<Collocation> result = new MockResult<Collocation>();
		operator.buildCollocation(lines, result);

		List<Collocation> results = result.getResults();
		assertThat(results.size(), is(0));
	}
}
