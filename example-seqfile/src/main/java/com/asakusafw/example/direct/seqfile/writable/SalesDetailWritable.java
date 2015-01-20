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
package com.asakusafw.example.direct.seqfile.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * 売上明細を表すWritableクラス。
 */
public class SalesDetailWritable implements Writable {

    /** 売上日時 */
    protected Date salesDateTime;

    /** 店舗コード */
    protected String storeCode;

    /** 商品コード */
    protected String itemCode;

    /** 数量 */
    protected int amount;

    /** 販売単価 */
    protected int unitSellingPrice;

    /** 販売金額 */
    protected int sellingPrice;

    /**
     * 売上日時を返す。
     * @return 売上日時
     */
    public Date getSalesDateTime() {
        return salesDateTime;
    }

    /**
     * 売上日時を設定する。
     * @param salesDateTime 設定する値
     */
    public void setSalesDateTime(Date salesDateTime) {
        this.salesDateTime = salesDateTime;
    }

    /**
     * 店舗コードを返す。
     * @return 店舗コード
     */
    public String getStoreCode() {
        return storeCode;
    }

    /**
     * 店舗コードを設定する。
     * @param storeCode 設定する値
     */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    /**
     * 商品コードを返す。
     * @return 商品コード
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * 商品コードを設定する。
     * @param itemCode 設定する値
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * 数量を返す。
     * @return 数量
     */
    public int getAmount() {
        return amount;
    }

    /**
     * 数量を設定する。
     * @param amount 設定する値
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * 販売単価を返す。
     * @return 販売単価
     */
    public int getUnitSellingPrice() {
        return unitSellingPrice;
    }

    /**
     * 販売単価を設定する。
     * @param unitSellingPrice 設定する値
     */
    public void setUnitSellingPrice(int unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    /**
     * 販売金額を返す。
     * @return 販売金額
     */
    public int getSellingPrice() {
        return sellingPrice;
    }

    /**
     * 販売金額を設定する。
     * @param sellingPrice 設定する値
     */
    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(salesDateTime.getTime());
        Text.writeString(out, storeCode);
        Text.writeString(out, itemCode);
        out.writeInt(amount);
        out.writeInt(unitSellingPrice);
        out.writeInt(sellingPrice);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        salesDateTime = new Date(in.readLong());
        storeCode = Text.readString(in);
        itemCode = Text.readString(in);
        amount = in.readInt();
        unitSellingPrice = in.readInt();
        sellingPrice = in.readInt();
    }
}
