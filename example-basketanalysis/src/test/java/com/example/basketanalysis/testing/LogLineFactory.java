package com.example.basketanalysis.testing;

import java.math.BigDecimal;

import com.example.basketanalysis.modelgen.dmdl.model.LogLine;

public class LogLineFactory {
	private LogLineFactory() {
	}

	public static LogLine create(String userId, String accessDateTime, String query) {
		LogLine result = new LogLine();
		result.setUserIdAsString(userId);
		result.setAccessDateTime(new BigDecimal(accessDateTime));
		result.setQueryAsString(query);
		return result;
	}
}
