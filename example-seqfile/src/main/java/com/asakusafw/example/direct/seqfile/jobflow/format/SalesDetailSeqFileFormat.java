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
import java.util.Calendar;

import org.apache.hadoop.io.NullWritable;

import com.asakusafw.example.direct.seqfile.modelgen.dmdl.model.SalesDetail;
import com.asakusafw.example.direct.seqfile.writable.SalesDetailWritable;
import com.asakusafw.runtime.directio.hadoop.SequenceFileFormat;
import com.asakusafw.runtime.value.DateTime;
import com.asakusafw.runtime.value.DateUtil;

/**
 * SequenceFile format for {@link SalesDetail}.
 */
public class SalesDetailSeqFileFormat extends SequenceFileFormat<NullWritable, SalesDetailWritable, SalesDetail> {

    @Override
    public Class<SalesDetail> getSupportedType() {
        return SalesDetail.class;
    }

    @Override
    protected NullWritable createKeyObject() {
        return NullWritable.get();
    }

    @Override
    protected SalesDetailWritable createValueObject() {
        return new SalesDetailWritable();
    }

    @Override
    protected void copyToModel(NullWritable key, SalesDetailWritable value, SalesDetail model) throws IOException {
        model.setSalesDateTime(toDateTime(value.getSalesDateTime()));
        model.setStoreCodeAsString(value.getStoreCode());
        model.setItemCodeAsString(value.getItemCode());
        model.setAmount(value.getAmount());
        model.setUnitSellingPrice(value.getUnitSellingPrice());
        model.setSellingPrice(value.getSellingPrice());
    }

    DateTime toDateTime(java.util.Date from) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        long sec = DateUtil.getSecondFromCalendar(calendar);
        return new DateTime(sec);
    }

    @Override
    protected void copyFromModel(SalesDetail model, NullWritable key, SalesDetailWritable value) throws IOException {
        value.setSalesDateTime(toUtilDate(model.getSalesDateTime()));
        value.setStoreCode(model.getStoreCodeAsString());
        value.setItemCode(model.getItemCodeAsString());
        value.setAmount(model.getAmount());
        value.setUnitSellingPrice(model.getUnitSellingPriceOption().or(0));
        value.setSellingPrice(model.getSellingPrice());
    }

    java.util.Date toUtilDate(DateTime from) {
        Calendar calendar = Calendar.getInstance();
        DateUtil.setSecondToCalendar(from.getElapsedSeconds(), calendar);
        return calendar.getTime();
    }
}
