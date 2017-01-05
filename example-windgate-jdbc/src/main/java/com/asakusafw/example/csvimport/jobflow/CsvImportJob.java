/**
 * Copyright 2011-2017 Asakusa Framework Team.
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
package com.asakusafw.example.csvimport.jobflow;

import com.asakusafw.example.jdbc.modelgen.dmdl.model.ItemInfo;
import com.asakusafw.example.jdbc.modelgen.dmdl.model.SalesDetail;
import com.asakusafw.example.jdbc.modelgen.dmdl.model.StoreInfo;
import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;

/**
 * CSVファイルをテーブルにインポートする。
 */
@JobFlow(name = "csvImport")
public class CsvImportJob extends FlowDescription {

    final In<SalesDetail> salesDetailIn;

    final In<StoreInfo> storeInfoIn;

    final In<ItemInfo> itemInfoIn;

    final Out<SalesDetail> salesDetailOut;

    final Out<StoreInfo> storeInfoOut;

    final Out<ItemInfo> itemInfoOut;

    /**
     * ジョブフローインスタンスを生成する。
     * 
     * @param salesDetailIn
     *            売上明細
     * @param storeInfoIn
     *            店舗マスタ
     * @param itemInfoIn
     *            商品マスタ
     * @param salesDetailOut
     *            売上明細
     * @param storeInfoOut
     *            店舗マスタ
     * @param itemInfoOut
     *            商品マスタ
     */
    public CsvImportJob(
            @Import(name = "salesDetailIn", description = SalesDetailFromCsv.class) In<SalesDetail> salesDetailIn,
            @Import(name = "storeInfoIn", description = StoreInfoFromCsv.class) In<StoreInfo> storeInfoIn,
            @Import(name = "itemInfoIn", description = ItemInfoFromCsv.class) In<ItemInfo> itemInfoIn,
            @Export(name = "salesDetailOut", description = SalesDetailToJdbc.class) Out<SalesDetail> salesDetailOut,
            @Export(name = "storeInfoOut", description = StoreInfoToJdbc.class) Out<StoreInfo> storeInfoOut,
            @Export(name = "itemInfoOut", description = ItemInfoToJdbc.class) Out<ItemInfo> itemInfoOut) {
        this.salesDetailIn = salesDetailIn;
        this.storeInfoIn = storeInfoIn;
        this.itemInfoIn = itemInfoIn;
        this.salesDetailOut = salesDetailOut;
        this.storeInfoOut = storeInfoOut;
        this.itemInfoOut = itemInfoOut;
    }

    @Override
    protected void describe() {
        salesDetailOut.add(salesDetailIn);
        storeInfoOut.add(storeInfoIn);
        itemInfoOut.add(itemInfoIn);
    }
}
