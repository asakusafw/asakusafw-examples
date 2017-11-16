/**
 * Copyright 2011-2017 Asakusa Framework Team.
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
package com.asakusafw.example.summarization.batch;

import com.asakusafw.example.summarization.jobflow.SummarizeReceiptJobFlow;
import com.asakusafw.vocabulary.batch.Batch;
import com.asakusafw.vocabulary.batch.Batch.Parameter;
import com.asakusafw.vocabulary.batch.BatchDescription;

/**
 * POSレシートデータを商品別・カテゴリ別・店舗別に集計する。
 */
@Batch(
    name = "example.summarization",
    comment = "Asakusa Framework example batch application",
    parameters = {
        @Parameter(key = "FROM_ISSUE_DATE", comment = "The issue start date", pattern = "\\d{8}"),
        @Parameter(key = "TO_ISSUE_DATE", comment = "The issue end date", pattern = "\\d{8}"),
    },
    strict = true
)
public class SummarizeReceiptBatch extends BatchDescription {

	@Override
	protected void describe() {
		run(SummarizeReceiptJobFlow.class).soon();
	}
}
