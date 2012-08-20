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
package com.example.business.operator;

import java.util.Iterator;
import java.util.List;

import com.asakusafw.runtime.core.Result;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.Branch;
import com.asakusafw.vocabulary.operator.CoGroup;
import com.example.business.modelgen.table.model.Shipment;
import com.example.business.modelgen.table.model.Stock;

/**
 * co-groupingを利用した単純な在庫引当の演算子例。
 */
public abstract class StockOp {

    /**
     * 出荷の状態を返す。
     * (普通はこれくらいはSQLでやった方が楽)
     * @param shipment 対象の出荷
     * @return 処理状態
     */
    @Branch
    public ShipmentStatus checkShipment(Shipment shipment) {
        // 出荷時刻が設定されていない
        if (shipment.getShippedDateOption().isNull()) {
            return ShipmentStatus.NOT_SHIPPED;
        }
        // 原価が設定されていない
        if (shipment.getCostOption().isNull()) {
            return ShipmentStatus.COST_UNKNOWN;
        }
        // 計算完了
        return ShipmentStatus.COMPLETED;
    }
    
    /**
     * 出荷状態。
     */
    public enum ShipmentStatus {
        
        /**
         * 未出荷。
         */
        NOT_SHIPPED,
        
        /**
         * 出荷済みだが原価不明。
         */
        COST_UNKNOWN,
        
        /**
         * 処理完了。
         */
        COMPLETED,
    }
    
    /**
     * 在庫引当を行う。
     * @param stocks 商品ごとの在庫一覧 
     * @param shipments 商品ごとの注文一覧 (出荷済みのみ)
     * @param newStocks 計算後の在庫一覧
     * @param newShipments 計算後の注文一覧
     */
    @CoGroup
    public void cutoff(
            // 在庫は商品ごとにグルーピングして、さらに入荷順に並べる
            @Key(group = "ITEM_CODE", order = "PURCHASED_DATE ASC") List<Stock> stocks,
            // 注文も商品ごとにグルーピングして、さらに出荷順に並べる
            @Key(group = "ITEM_CODE", order = "SHIPPED_DATE ASC") List<Shipment> shipments,
            Result<Stock> newStocks,
            Result<Shipment> newShipments) {
        Iterator<Stock> eachStock = stocks.iterator();
        Iterator<Shipment> eachShipment = shipments.iterator();
        
        // 在庫が空になるまで
        while (eachStock.hasNext()) {
            Stock stock = eachStock.next();
            
            // 注文を処理しきるまで
            while (eachShipment.hasNext()) {
                // この在庫レコードが終わったら次へ
                if (stock.getQuantity() == 0) {
                    break;
                }
                // 注文をひとつ取り出して処理
                Shipment shipment = eachShipment.next();
                shipment.setCost(stock.getCost());
                newShipments.add(shipment);
                
                // 在庫を減らす
                stock.getQuantityOption().add(-1);
            }
            
            // 現在の在庫情報を出力
            newStocks.add(stock);
        }
        
        // 出荷したはずが在庫が足りてないですよ...
        // (書き戻さなくてもよく、その場合は更新されないだけ)
        while (eachShipment.hasNext()) {
            Shipment shipment = eachShipment.next();
            newShipments.add(shipment);
        }
    }
}
