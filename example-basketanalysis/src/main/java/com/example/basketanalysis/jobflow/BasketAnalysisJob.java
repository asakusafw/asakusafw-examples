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
package com.example.basketanalysis.jobflow;

import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;
import com.example.basketanalysis.jobflow.gateway.CollocationToHdfs;
import com.example.basketanalysis.jobflow.gateway.LogLineFromHdfs;
import com.example.basketanalysis.modelgen.dmdl.model.Collocation;
import com.example.basketanalysis.modelgen.dmdl.model.LogLine;
import com.example.basketanalysis.operator.BasketAnalysisOperatorFactory;

/**
 * JobFlow of Basket Analysis.
 */
@JobFlow(name = "BasketAnalysisJob")
public class BasketAnalysisJob extends FlowDescription {

	BasketAnalysisOperatorFactory opeartor = new BasketAnalysisOperatorFactory();

	In<LogLine> in;
	Out<Collocation> out;

	public BasketAnalysisJob(@Import(name = "in", description = LogLineFromHdfs.class) In<LogLine> in,
			@Export(name = "out", description = CollocationToHdfs.class) Out<Collocation> out) {
		this.in = in;
		this.out = out;
	}

	@Override
	protected void describe() {
		out.add(opeartor.sortCollocation(opeartor.countCollocation(opeartor.buildCollocation(in).result).result).result);
	}
}
