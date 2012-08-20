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
