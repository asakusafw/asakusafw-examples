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
package com.example.basketanalysis.jobflow.gateway;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.asakusafw.vocabulary.external.FileExporterDescription;
import com.example.basketanalysis.modelgen.dmdl.model.Collocation;

/**
 * Export Collocation as Sequence File.
 * 
 * @author shingo.furuyama
 *
 */
public class CollocationToHdfs extends FileExporterDescription {

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends FileOutputFormat> getOutputFormat() {
		return TextOutputFormat.class;
	}

	@Override
	public Class<?> getModelType() {
		return Collocation.class;
	}

	@Override
	public String getPathPrefix() {
		return "result/result-*";
	}
}
