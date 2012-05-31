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
package com.asakusafw.example.direct.seqfile.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 売上集計を表すWritableクラス。
 */
public class SummaryWritable implements Writable {

    /** 販売数量 */
    protected long amountTotal;

    /** 売上合計 */
    protected long sellingPriceTotal;

    /**
     * 販売数量を返す。
     * @return 販売数量
     */
    public long getAmountTotal() {
        return amountTotal;
    }

    /**
     * 販売数量を設定する。
     * @param amountTotal 設定する値
     */
    public void setAmountTotal(long amountTotal) {
        this.amountTotal = amountTotal;
    }

    /**
     * 売上合計を返す。
     * @return 売上合計
     */
    public long getSellingPriceTotal() {
        return sellingPriceTotal;
    }

    /**
     * 売上合計を設定する。
     * @param sellingPriceTotal 設定する値
     */
    public void setSellingPriceTotal(long sellingPriceTotal) {
        this.sellingPriceTotal = sellingPriceTotal;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(amountTotal);
        out.writeLong(sellingPriceTotal);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        amountTotal = in.readLong();
        sellingPriceTotal = in.readLong();
    }
}
