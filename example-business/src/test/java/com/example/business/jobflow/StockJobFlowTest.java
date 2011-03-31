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

import org.junit.Test;

import com.asakusafw.testdriver.FlowPartTestDriver;
import com.asakusafw.testdriver.JobFlowTestDriver;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.Out;
import com.example.business.modelgen.table.model.Shipment;
import com.example.business.modelgen.table.model.Stock;

/**
 * サンプル：ジョブフローのテストクラス
 */
public class StockJobFlowTest {

    /**
     * サンプル：ジョブフローの実行
     * 
     * @throws Throwable 
     */    
	@Test
	public void testExample() throws Throwable {

		JobFlowTestDriver driver = new JobFlowTestDriver();

		driver.runTest(StockJob.class);
	}
	
    /**
     * サンプル：フロー部品としてジョブフローのテストを実行
     * 
     * @throws Throwable 
     */        
    @Test
    public void testExampleAsFlowPart() throws Throwable {

    	FlowPartTestDriver driver = new FlowPartTestDriver();
        In<Shipment> shipmentIn = driver.createIn(Shipment.class);
        In<Stock> stockIn = driver.createIn(Stock.class);
        
        Out<Shipment> shipmentOut = driver.createOut(Shipment.class);
        Out<Stock> stockOut = driver.createOut(Stock.class);

        FlowDescription flowDesc = new StockJob(shipmentIn, stockIn, shipmentOut, stockOut);

        driver.runTest(flowDesc);
    }
}
