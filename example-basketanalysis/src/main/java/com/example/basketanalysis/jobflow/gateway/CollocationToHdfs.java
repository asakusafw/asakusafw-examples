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
