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
package com.asakusafw.example.csv.jobflow;

import com.asakusafw.example.csv.modelgen.dmdl.csv.AbstractStoreInfoCsvImporterDescription;

/**
 * 店舗マスタをWindGate/CSVからインポートする。
 * インポート対象ファイルは {@code master/store_info.csv}。
 */
public class StoreInfoFromCsv extends AbstractStoreInfoCsvImporterDescription {

    @Override
    public String getProfileName() {
        return "asakusa";
    }

    @Override
    public String getPath() {
        return "master/store_info.csv";
    }

    @Override
    public DataSize getDataSize() {
        // 店舗マスタは小さい前提
        return DataSize.TINY;
    }
}
