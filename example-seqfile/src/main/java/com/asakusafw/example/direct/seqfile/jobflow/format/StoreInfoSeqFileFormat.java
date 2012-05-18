/**
 * Copyright 2012 Asakusa Framework Team.
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
package com.asakusafw.example.direct.seqfile.jobflow.format;

import java.io.IOException;

import org.apache.hadoop.io.Text;

import com.asakusafw.example.direct.seqfile.modelgen.dmdl.model.StoreInfo;
import com.asakusafw.runtime.directio.hadoop.SequenceFileFormat;

/**
 * SequenceFile format for {@link StoreInfo}.
 */
public class StoreInfoSeqFileFormat extends SequenceFileFormat<Text, Text, StoreInfo> {

    @Override
    public Class<StoreInfo> getSupportedType() {
        return StoreInfo.class;
    }

    @Override
    protected Text createKeyObject() {
        return new Text();
    }

    @Override
    protected Text createValueObject() {
        return new Text();
    }

    @Override
    protected void copyToModel(Text key, Text value, StoreInfo model) throws IOException {
        model.setStoreCode(key);
        model.setStoreName(value);
    }

    @Override
    protected void copyFromModel(StoreInfo model, Text key, Text value) throws IOException {
        key.set(model.getStoreCode());
        value.set(model.getStoreName());
    }
}
