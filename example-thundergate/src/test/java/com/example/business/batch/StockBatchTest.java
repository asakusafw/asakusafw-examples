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
package com.example.business.batch;

import org.junit.Test;

import com.asakusafw.testdriver.BatchTester;
import com.example.business.modelgen.table.model.Shipment;
import com.example.business.modelgen.table.model.Stock;

/**
 * サンプル：バッチのテストクラス
 */
public class StockBatchTest {

    /**
     * サンプル：バッチの実行
     * @throws Throwable テストに失敗した場合
     */
    @Test
    public void testExample() throws Throwable {
        BatchTester tester = new BatchTester(getClass());
        tester.jobflow("stock").input("shipment", Shipment.class)
            .prepare("shipment.xls#input");
        tester.jobflow("stock").input("stock", Stock.class)
            .prepare("stock.xls#input");
        tester.jobflow("stock").output("shipment", Shipment.class)
            .verify("shipment.xls#output", "shipment.xls#rule");
        tester.jobflow("stock").output("stock", Stock.class)
            .verify("stock.xls#output", "stock.xls#rule");

        tester.runTest(StockBatch.class);
    }
}
