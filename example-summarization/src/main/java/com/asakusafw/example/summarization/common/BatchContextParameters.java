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
package com.asakusafw.example.summarization.common;

import com.asakusafw.runtime.core.BatchContext;
import com.asakusafw.runtime.value.DateTime;
import com.asakusafw.runtime.value.DateTime.Format;

/**
 * 処理対象発行日を対象とした{@link BatchContext}のwrapper.
 */
public class BatchContextParameters {

	/**
	 * 発行開始日のパラメータ名
	 */
	public static final String FROM_ISSUE_DATE = "FROM_ISSUE_DATE";

	/**
	 * 発行終了日のパラメータ名
	 */
	public static final String TO_ISSUE_DATE = "TO_ISSUE_DATE";

	/**
	 * バッチパラメータより発行開始日を取得する.
	 * <p>
	 * 時刻は00時00分00秒が設定される
	 * </p>
	 *
	 * @return 発行開始日
	 */
	public DateTime getFromIssueDate() {
		String fromIssueDate = getIssueDate(FROM_ISSUE_DATE);
		return DateTime.valueOf(fromIssueDate + "000000", Format.SIMPLE);
	}

	/**
	 * バッチパラメータより発行終了日を取得する.
	 * <p>
	 * 時刻は23時59分59秒が設定される
	 * </p>
	 *
	 * @return 発行終了日
	 */
	public DateTime getToIssueDate() {
		String toIssueDate = getIssueDate(TO_ISSUE_DATE);
		return DateTime.valueOf(toIssueDate + "235959", Format.SIMPLE);
	}

	/**
	 * {@link BatchContext} から日付の文字列表現を取得する.
	 *
	 * @param parameterName
	 *            {@link BatchContext} の変数名
	 * @return 日付の文字列表現
	 * @exception IllegalArgumentException
	 *                パラメータの値が8桁の数値以外
	 */
	private String getIssueDate(String parameterName) {
		String dateAsString = BatchContext.get(parameterName);
		if (dateAsString == null || dateAsString.length() != 8
				|| "[0-9]*".matches(dateAsString)) {
			throw new IllegalArgumentException(dateAsString
					+ " is not valid parameter for " + parameterName);
		}
		return dateAsString;
	}

}
