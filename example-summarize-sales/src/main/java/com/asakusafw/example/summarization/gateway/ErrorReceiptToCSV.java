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
package com.asakusafw.example.summarization.gateway;

import java.util.Arrays;
import java.util.List;

import com.asakusafw.example.summarization.modelgen.dmdl.csv.AbstractErrorReceiptCsvOutputDescription;

/**
 * エラーレシートデータをDirect I/Oで出力する。  出力ファイルは
 * {@code result/error-receipt-<date:発行開始日>_<date:発行終了日>.csv}。
 */
public class ErrorReceiptToCSV extends AbstractErrorReceiptCsvOutputDescription {

	@Override
	public String getBasePath() {
        return "result/error";
	}

	@Override
	public String getResourcePattern() {
		return "error-receipt-${FROM_ISSUE_DATE}_${TO_ISSUE_DATE}.csv";
	}

	@Override
	public List<String> getOrder() {
		return Arrays.asList("+receipt_id", "+line_number");
	}

}
