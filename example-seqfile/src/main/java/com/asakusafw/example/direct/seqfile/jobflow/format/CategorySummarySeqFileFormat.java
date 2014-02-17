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
package com.asakusafw.example.direct.seqfile.jobflow.format;

import java.io.IOException;

import org.apache.hadoop.io.Text;

import com.asakusafw.example.direct.seqfile.modelgen.dmdl.model.CategorySummary;
import com.asakusafw.example.direct.seqfile.writable.SummaryWritable;
import com.asakusafw.runtime.directio.hadoop.SequenceFileFormat;

/**
 * SequenceFile format for {@link CategorySummary}.
 */
public class CategorySummarySeqFileFormat extends SequenceFileFormat<Text, SummaryWritable, CategorySummary> {

    @Override
    public Class<CategorySummary> getSupportedType() {
        return CategorySummary.class;
    }

    @Override
    protected Text createKeyObject() {
        return new Text();
    }

    @Override
    protected SummaryWritable createValueObject() {
        return new SummaryWritable();
    }

    @Override
    protected void copyToModel(Text key, SummaryWritable value, CategorySummary model) throws IOException {
        model.setCategoryCode(key);
        model.setAmountTotal(value.getAmountTotal());
        model.setSellingPriceTotal(value.getSellingPriceTotal());
    }

    @Override
    protected void copyFromModel(CategorySummary model, Text key, SummaryWritable value) throws IOException {
        key.set(model.getCategoryCode());
        value.setAmountTotal(model.getAmountTotal());
        value.setSellingPriceTotal(model.getSellingPriceTotal());
    }
}
