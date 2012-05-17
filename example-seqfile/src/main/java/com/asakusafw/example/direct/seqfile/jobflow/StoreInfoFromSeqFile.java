/**
 * Copyright 2011-2012 Asakusa Framework Team.
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
package com.asakusafw.example.direct.seqfile.jobflow;

import com.asakusafw.example.direct.seqfile.jobflow.format.StoreInfoSeqFileFormat;
import com.asakusafw.example.direct.seqfile.modelgen.dmdl.model.StoreInfo;
import com.asakusafw.runtime.directio.DataFormat;
import com.asakusafw.vocabulary.directio.DirectFileInputDescription;

/**
 * 店舗マスタをDirect I/Oで入力する。
 * 入力ファイルは {@code master} 上の {@code store_info.dat}。
 */
public class StoreInfoFromSeqFile extends DirectFileInputDescription {

    @Override
    public Class<?> getModelType() {
        return StoreInfo.class;
    }

    @Override
    public Class<? extends DataFormat<?>> getFormat() {
        return StoreInfoSeqFileFormat.class;
    }

    @Override
    public String getBasePath() {
        return "master";
    }

    @Override
    public String getResourcePattern() {
        return "store_info.dat";
    }

    @Override
    public DataSize getDataSize() {
        // 店舗マスタは小さい前提
        return DataSize.TINY;
    }
}
