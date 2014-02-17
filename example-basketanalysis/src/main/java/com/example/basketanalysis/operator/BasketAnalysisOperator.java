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

import java.util.List;

import com.asakusafw.runtime.core.BatchContext;
import com.asakusafw.runtime.core.Result;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.CoGroup;
import com.asakusafw.vocabulary.operator.GroupSort;
import com.example.basketanalysis.modelgen.dmdl.model.Collocation;
import com.example.basketanalysis.modelgen.dmdl.model.LogLine;

/**
 * Operator of Basket Analysis.
 */
public abstract class BasketAnalysisOperator {
	
	/**
	 * Term window for recognize collocation.
	 */
	private static final String WINDOW = "WINDOW";
	
	/**
	 * build collocation from log lines.
	 * 
	 * @param lines
	 *            log lines
	 * @param result
	 *            collocation
	 */
	@CoGroup
	public void buildCollocation(@Key(group = { "userId" }, order = { "accessDateTime" }) List<LogLine> lines,
			Result<Collocation> result) {
		if (lines.size() == 0) {
			return;
		}

		for (LogLine baseLine : lines) {
			long base = baseLine.getAccessDateTime().longValue();

			for (LogLine logLine : lines) {
				if (baseLine.equals(logLine)) {
					continue;
				}
				long diff = logLine.getAccessDateTime().longValue() - base;
				if (diff < 0) {
					continue;
				}
				if (!(diff <= Long.parseLong(BatchContext.get(WINDOW)))) {
					break;
				}
				Collocation one = new Collocation();
				one.setFirst(baseLine.getQuery());
				one.setSecond(logLine.getQuery());
				one.setCount(1);
				result.add(one);
			}
		}
	}

	/**
	 * count collocations.
	 * 
	 * @param collocations
	 *            collocations
	 * @param result
	 *            counted collocation
	 */
	@CoGroup
	public void countCollocation(@Key(group = { "first", "second" }) List<Collocation> collocations,
			Result<Collocation> result) {
		Collocation collocation = new Collocation();
		collocation.setFirst(collocations.get(0).getFirst());
		collocation.setSecond(collocations.get(0).getSecond());
		collocation.setCount(collocations.size());
		result.add(collocation);
	}

	/**
	 * sort collocations.
	 * 
	 * @param collocations
	 *            collocations
	 * @param result
	 *            counted collocations
	 */
	@GroupSort
	public void sortCollocation(@Key(group = {}, order = { "count" }) List<Collocation> collocations,
			Result<Collocation> result) {
		for (Collocation collocation : collocations) {
			result.add(collocation);
		}
	}
}
