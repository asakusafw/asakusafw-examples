/**
 * Copyright 2011-2019 Asakusa Framework Team.
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
import com.example.business.modelgen.table.model.Stock;

/**
 * 在庫のファクトリ
 * @author shingo.furuyama
 */
public class StockFactory {

    private StockFactory() {
        return;
    }

    /**
     * {@link Stock}のインスタンスを生成して返します
     *
     * @param purchasedDate
     *            購買日付
     * @param itemCode
     *            明細コード
     * @param cost
     *            コスト
     * @param quantity
     *            　数量
     * @return {@link Stock}のインスタンス
     */
    public static Stock create(DateTime purchasedDate, long itemCode, int cost, int quantity) {
        Stock result = new Stock();
        result.setPurchasedDate(purchasedDate);
        result.setItemCode(itemCode);
        result.setCost(cost);
        result.setQuantity(quantity);
        return result;
    }
}
