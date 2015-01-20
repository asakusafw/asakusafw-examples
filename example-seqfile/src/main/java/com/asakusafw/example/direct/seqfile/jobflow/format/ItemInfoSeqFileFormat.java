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
package com.asakusafw.example.direct.seqfile.jobflow.format;

import java.io.IOException;
import java.util.Calendar;

import org.apache.hadoop.io.Text;

import com.asakusafw.example.direct.seqfile.modelgen.dmdl.model.ItemInfo;
import com.asakusafw.example.direct.seqfile.writable.ItemInfoWritable;
import com.asakusafw.runtime.directio.hadoop.SequenceFileFormat;
import com.asakusafw.runtime.value.Date;
import com.asakusafw.runtime.value.DateOption;
import com.asakusafw.runtime.value.DateUtil;

/**
 * SequenceFile format for {@link ItemInfo}.
 */
public class ItemInfoSeqFileFormat extends SequenceFileFormat<Text, ItemInfoWritable, ItemInfo> {

    @Override
    public Class<ItemInfo> getSupportedType() {
        return ItemInfo.class;
    }

    @Override
    protected Text createKeyObject() {
        return new Text();
    }

    @Override
    protected ItemInfoWritable createValueObject() {
        return new ItemInfoWritable();
    }

    @Override
    protected void copyToModel(Text key, ItemInfoWritable value, ItemInfo model) throws IOException {
        model.setItemCode(key);
        model.setItemNameAsString(value.getItemName());
        model.setDepartmentCodeAsString(value.getDepartmentCode());
        model.setDepartmentNameAsString(value.getDepartmentName());
        model.setCategoryCodeAsString(value.getCategoryCode());
        model.setCategoryNameAsString(value.getCategoryName());
        model.setUnitSellingPrice(value.getUnitSellingPrice());
        model.setRegisteredDate(toAsakusaDate(value.getRegisteredDate()));
        model.setBeginDate(toAsakusaDate(value.getBeginDate()));
        model.setEndDate(toAsakusaDate(value.getEndDate()));
    }

    Date toAsakusaDate(java.util.Date from) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        return new Date(DateUtil.getDayFromCalendar(calendar));
    }

    @Override
    protected void copyFromModel(ItemInfo model, Text key, ItemInfoWritable value) throws IOException {
        key.set(model.getItemCode());
        value.setItemName(model.getItemNameAsString());
        value.setDepartmentCode(model.getDepartmentCodeOption().or(""));
        value.setDepartmentName(model.getDepartmentNameOption().or(""));
        value.setCategoryCode(model.getCategoryCodeOption().or(""));
        value.setCategoryName(model.getCategoryNameOption().or(""));
        value.setUnitSellingPrice(model.getUnitSellingPriceOption().or(0));
        value.setRegisteredDate(toUtilDate(model.getRegisteredDateOption()));
        value.setBeginDate(toUtilDate(model.getBeginDateOption()));
        value.setEndDate(toUtilDate(model.getEndDateOption()));
    }

    java.util.Date toUtilDate(DateOption from) {
        if (from.isNull()) {
            return new java.util.Date(0L);
        }
        Calendar calendar = Calendar.getInstance();
        DateUtil.setDayToCalendar(from.get().getElapsedDays(), calendar);
        return calendar.getTime();
    }
}
