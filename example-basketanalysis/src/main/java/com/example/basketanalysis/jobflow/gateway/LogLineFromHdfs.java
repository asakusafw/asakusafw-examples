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
package com.example.basketanalysis.jobflow.gateway;

import java.util.HashSet;
import java.util.Set;

import com.asakusafw.vocabulary.external.FileImporterDescription;
import com.example.basketanalysis.modelgen.dmdl.model.LogLine;

/**
 * Import Seqence File as LogLine Instance.
 * 
 * @author shingo.furuyama
 *
 */
public class LogLineFromHdfs extends FileImporterDescription {

	@Override
	public Class<?> getModelType() {
		return LogLine.class;
	}
	
	@Override
	public Set<String> getPaths() {
		Set<String> result = new HashSet<String>();
		result.add("hdfs:///user/asakusa/example/excite-small.sar");
		return result;
	}
}
