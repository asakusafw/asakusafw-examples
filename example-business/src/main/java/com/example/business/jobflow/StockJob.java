/**
 * Copyright 2011 Asakusa Framework Team.
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
package com.example.business.jobflow;

import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory;
import com.example.business.modelgen.table.model.Shipment;
import com.example.business.modelgen.table.model.Stock;
import com.example.business.operator.StockOpFactory;
import com.example.business.operator.StockOpFactory.CheckShipment;
import com.example.business.operator.StockOpFactory.Cutoff;

/**
 * co-groupingを利用した単純な在庫引当のジョブフロー例。
 * (読みやすさのため、直接ジョブフローに演算子を書いている)
 */
@JobFlow(name = "stock")
public class StockJob extends FlowDescription {

    private In<Shipment> shipmentIn;
    
    private In<Stock> stockIn;
    
    private Out<Shipment> shipmentOut;
    
    private Out<Stock> stockOut;

    /**
     * コンストラクタ。
     * @param shipmentIn 処理対象の注文情報
     * @param stockIn 処理対象の在庫情報
     * @param shipmentOut 処理結果の注文情報
     * @param stockOut 処理結果の在庫情報
     */
    public StockJob(
            @Import(name = "shipment", description = ShipmentFromDb.class)
            In<Shipment> shipmentIn,
            @Import(name = "stock", description = StockFromDb.class)
            In<Stock> stockIn,
            @Export(name = "shipment", description = ShipmentToDb.class)
            Out<Shipment> shipmentOut,
            @Export(name = "stock", description = StockToDb.class)
            Out<Stock> stockOut) {
        this.shipmentIn = shipmentIn;
        this.stockIn = stockIn;
        this.shipmentOut = shipmentOut;
        this.stockOut = stockOut;
    }

    @Override
    protected void describe() {
        CoreOperatorFactory core = new CoreOperatorFactory();
        StockOpFactory op = new StockOpFactory();
        
        // 処理できない注文をあらかじめフィルタリング
        CheckShipment check = op.checkShipment(shipmentIn);
        core.stop(check.notShipmentped);
        core.stop(check.completed);
        
        // 在庫引当を行う
        Cutoff cutoff = op.cutoff(stockIn, check.costUnknown);
        
        // 結果を書き出す
        shipmentOut.add(cutoff.newShipments);
        stockOut.add(cutoff.newStocks);
    }
}
