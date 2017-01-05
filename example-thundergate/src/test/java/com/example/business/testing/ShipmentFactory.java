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
package com.example.business.testing;

import com.asakusafw.runtime.value.DateTime;
import com.example.business.modelgen.table.model.Shipment;

/**
 * 出荷明細のファクトリ
 * @author shingo.furuyama
 */
public class ShipmentFactory {

    private ShipmentFactory() {
        return;
    }

    /**
     * {@link Shipment}のインスタンスを生成して返します
     *
     * @param shippedDate
     *            出荷日付
     * @param itemCode
     *            　商品コード
     * @param cost
     *            コスト
     * @return {@link Shipment}のインスタンス
     */
    public static Shipment create(DateTime shippedDate, long itemCode, int cost) {
        Shipment result = new Shipment();
        result.setShippedDate(shippedDate);
        result.setItemCode(itemCode);
        result.setCost(cost);
        return result;
    }
}
