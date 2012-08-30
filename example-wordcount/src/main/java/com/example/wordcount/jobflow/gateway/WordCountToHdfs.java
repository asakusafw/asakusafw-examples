package com.example.wordcount.jobflow.gateway;

import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.asakusafw.vocabulary.external.FileExporterDescription;
import com.example.wordcount.modelgen.dmdl.model.WordCount;

/**
 * Export Word Count as Sequence File.
 * 
 * @author shingo.furuyama
 *
 */
public class WordCountToHdfs extends FileExporterDescription {

	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends FileOutputFormat> getOutputFormat() {
		return TextOutputFormat.class;
	}

	@Override
	public Class<?> getModelType() {
		return WordCount.class;
	}

	@Override
	public String getPathPrefix() {
		return "result/result-*";
	}
}
