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
package com.example.jobflow;

import com.example.modelgen.dmdl.hive.orc.AbstractSalesDetailOrcFileInputDescription;

/**
 * 売上明細をDirect I/Oで入力する。
 * 入力ファイルは {@code sales} 上のすべてのファイル。
 */
public class SalesDetailFromOrc extends AbstractSalesDetailOrcFileInputDescription {

    @Override
    public String getBasePath() {
        return "tables/sales_detail";
    }

    @Override
    public String getResourcePattern() {
        return "**/*";
    }

    @Override
    public DataSize getDataSize() {
        return DataSize.LARGE;
    }
}
