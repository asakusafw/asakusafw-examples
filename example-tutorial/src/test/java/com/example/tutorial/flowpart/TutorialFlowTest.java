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
package com.example.tutorial.flowpart;

import org.junit.Test;

import com.asakusafw.testdriver.FlowPartTestDriver;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.Out;
import com.example.tutorial.modelgen.table.model.ItemInfo;
import com.example.tutorial.modelgen.table.model.OrderAmount;
import com.example.tutorial.modelgen.table.model.OrderDetail;

/**
 * サンプル：フロー部品のテストクラス
 */
public class TutorialFlowTest {

    /**
     * サンプル：フロー部品の実行
     * 
     * @throws Throwable 
     */        
    @Test
    public void testExample() throws Throwable {

    	FlowPartTestDriver driver = new FlowPartTestDriver();
        In<OrderDetail> orderIn = driver.createIn(OrderDetail.class);
        In<ItemInfo> itemIn = driver.createIn(ItemInfo.class);
        
        Out<OrderDetail> orderOut = driver.createOut(OrderDetail.class);
        Out<OrderAmount> resultOut = driver.createOut(OrderAmount.class);

        FlowDescription flowDesc = new TutorialFlow(orderIn, itemIn, orderOut, resultOut);

        driver.runTest(flowDesc);
    }
    
}
