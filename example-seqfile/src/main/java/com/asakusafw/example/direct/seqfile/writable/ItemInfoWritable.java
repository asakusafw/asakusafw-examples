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
package com.asakusafw.example.direct.seqfile.writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * 商品マスタを表すWritableクラス。
 */
public class ItemInfoWritable implements Writable {

    /** 商品名 */
    protected String itemName;

    /** 商品部門コード */
    protected String departmentCode;

    /** 商品部門名 */
    protected String departmentName;

    /** 商品カテゴリコード */
    protected String categoryCode;

    /** 商品カテゴリ名 */
    protected String categoryName;

    /** 商品単価 */
    protected int unitSellingPrice;

    /** マスタ登録日 */
    protected Date registeredDate;

    /** マスタ適用開始日 */
    protected Date beginDate;

    /** マスタ適用終了日 */
    protected Date endDate;

    /**
     * 商品名を返す。
     * @return 商品名
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 商品名を設定する。
     * @param itemName 設定する値
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 商品部門コードを返す。
     * @return 商品部門コード
     */
    public String getDepartmentCode() {
        return departmentCode;
    }

    /**
     * 商品部門コードを設定する。
     * @param departmentCode 設定する値
     */
    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    /**
     * 商品部門名を返す。
     * @return 商品部門コード
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * 商品部門名を設定する。
     * @param departmentName 設定する値
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * 商品カテゴリコードを返す。
     * @return 商品カテゴリコード
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * 商品カテゴリコードを設定する。
     * @param categoryCode 設定する値
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * 商品カテゴリ名を返す。
     * @return 商品カテゴリ名
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 商品カテゴリ名を設定する。
     * @param categoryName 設定する値
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 商品単価を返す。
     * @return 商品単価
     */
    public int getUnitSellingPrice() {
        return unitSellingPrice;
    }

    /**
     * 商品単価を設定する。
     * @param unitSellingPrice 設定する値
     */
    public void setUnitSellingPrice(int unitSellingPrice) {
        this.unitSellingPrice = unitSellingPrice;
    }

    /**
     * マスタ登録日を返す。
     * @return マスタ登録日
     */
    public Date getRegisteredDate() {
        return registeredDate;
    }

    /**
     * マスタ登録日を設定する。
     * @param registeredDate 設定する値
     */
    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    /**
     * マスタ適用開始日を返す。
     * @return マスタ適用開始日
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * マスタ適用開始日を設定する。
     * @param beginDate 設定する値
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * マスタ適用終了日を返す。
     * @return マスタ適用終了日
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * マスタ適用終了日を設定する。
     * @param endDate 設定する値
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        Text.writeString(out, itemName);
        Text.writeString(out, departmentCode);
        Text.writeString(out, departmentName);
        Text.writeString(out, categoryCode);
        Text.writeString(out, categoryName);
        out.writeInt(unitSellingPrice);
        out.writeLong(registeredDate.getTime());
        out.writeLong(beginDate.getTime());
        out.writeLong(endDate.getTime());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        itemName = Text.readString(in);
        departmentCode = Text.readString(in);
        departmentName = Text.readString(in);
        categoryCode = Text.readString(in);
        categoryName = Text.readString(in);
        unitSellingPrice = in.readInt();
        registeredDate = new Date(in.readLong());
        beginDate = new Date(in.readLong());
        endDate = new Date(in.readLong());
    }
}
