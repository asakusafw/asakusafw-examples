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
package com.asakusafw.example.jdbc.jobflow;

import com.asakusafw.example.jdbc.modelgen.dmdl.jdbc.AbstractCategorySummaryJdbcExporterDescription;

/**
 * カテゴリ別集計結果をWindGate/JDBCでエクスポートする。
 * エクスポート対象テーブルは {@code CATEGORY_SUMMARY}。
 */
public class CategorySummaryToJdbc extends AbstractCategorySummaryJdbcExporterDescription {

    @Override
    public String getProfileName() {
        return "asakusa";
    }

}
