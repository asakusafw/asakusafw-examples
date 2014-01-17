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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import com.asakusafw.runtime.value.Date;
import com.asakusafw.runtime.value.DateOption;

/**
 * {@link ItemInfoSeqFileFormat}のテスト。
 */
public class ItemInfoSeqFileFormatTest {

    /**
     * {@link ItemInfoSeqFileFormat#toAsakusaDate(java.util.Date)}のテスト。
     */
    @Test
    public void testToAsakusaDate() {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(2012, 5 - 1, 8, 23, 59, 49);
        java.util.Date from = cal.getTime();

        ItemInfoSeqFileFormat f = new ItemInfoSeqFileFormat();
        Date r = f.toAsakusaDate(from);

        assertThat(r.toString(), is("2012-05-08"));
    }

    /**
     * {@link ItemInfoSeqFileFormat#toUtilDate(DateOption)}のテスト。
     */
    @Test
    public void testToUtilDate() {
        ItemInfoSeqFileFormat f = new ItemInfoSeqFileFormat();
        {
            DateOption from = new DateOption(null);
            java.util.Date r = f.toUtilDate(from);
            assertThat(r.getTime(), is(0L));
        }
        {
            DateOption from = new DateOption(new Date(2012, 5, 8));
            java.util.Date r = f.toUtilDate(from);

            Calendar cal = Calendar.getInstance();
            cal.setTime(r);
            assertThat(cal.get(Calendar.YEAR), is(2012));
            assertThat(cal.get(Calendar.MONTH), is(5 - 1));
            assertThat(cal.get(Calendar.DAY_OF_MONTH), is(8));
            assertThat(cal.get(Calendar.HOUR_OF_DAY), is(0));
            assertThat(cal.get(Calendar.MINUTE), is(0));
            assertThat(cal.get(Calendar.SECOND), is(0));
            assertThat(cal.get(Calendar.MILLISECOND), is(0));
        }
    }
}
