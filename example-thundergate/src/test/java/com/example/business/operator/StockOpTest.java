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
package com.example.business.operator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.asakusafw.runtime.core.Result;
import com.asakusafw.runtime.testing.MockResult;
import com.asakusafw.runtime.value.DateTime;
import com.example.business.modelgen.table.model.Shipment;
import com.example.business.modelgen.table.model.Stock;
import com.example.business.operator.StockOp.ShipmentStatus;
import com.example.business.testing.ShipmentFactory;
import com.example.business.testing.StockFactory;

/**
 * サンプル：在庫引当演算子のテストケース。
 * @author shingo.furuyama
 */
public class StockOpTest {

    /**
     * {@link StockOp#checkShipment(Shipment)} returns NOT_SHIPPED.
     */
    @Test
    public void testCheckShipment_notShipped() {
        StockOpImpl operator = new StockOpImpl();
        Shipment shipment = new Shipment();

        ShipmentStatus actual = operator.checkShipment(shipment);

        assertThat(actual, is(ShipmentStatus.NOT_SHIPPED));
    }

    /**
     * {@link StockOp#checkShipment(Shipment)} returns COST_UNKNOWN.
     */
    @Test
    public void testCheckShipment_costUnKnown() {
        StockOpImpl operator = new StockOpImpl();
        Shipment shipment = new Shipment();
        shipment.setShippedDate(new DateTime());

        ShipmentStatus actual = operator.checkShipment(shipment);

        assertThat(actual, is(ShipmentStatus.COST_UNKNOWN));
    }

    /**
     * {@link StockOp#checkShipment(Shipment)} returns COMPLETED.
     */
    @Test
    public void testCheckShipment_shipped() {
        StockOpImpl operator = new StockOpImpl();
        Shipment shipment = new Shipment();
        shipment.setShippedDate(new DateTime());
        shipment.setCost(100);

        ShipmentStatus actual = operator.checkShipment(shipment);

        assertThat(actual, is(ShipmentStatus.COMPLETED));
    }

    /**
     * {@link StockOp#cutoff(List, List, Result, Result)}.
     */
    @Test
    public void testCutoff() {
        StockOpImpl operator = new StockOpImpl();

        List<Stock> stocks = new ArrayList<Stock>();
        stocks.add(StockFactory.create(new DateTime(), 0, 100, 10));

        List<Shipment> shipments = new ArrayList<Shipment>();
        shipments.add(ShipmentFactory.create(new DateTime(), 10, 100));

        MockResult<Stock> newStocks = new MockResult<Stock>();
        MockResult<Shipment> newShipments = new MockResult<Shipment>();

        operator.cutoff(stocks, shipments, newStocks, newShipments);

        assertThat(newStocks.getResults().size(), is(1));
        assertThat(newShipments.getResults().size(), is(1));
    }

    /**
     * {@link StockOp#cutoff(List, List, Result, Result)}, stocks are shortage.
     */
    @Test
    public void testCutoff_shortage() {
        StockOpImpl operator = new StockOpImpl();

        List<Stock> stocks = Arrays.asList(StockFactory.create(new DateTime(), 0, 100, 10));
        List<Shipment> shipments = Arrays.asList();
        MockResult<Stock> newStocks = new MockResult<Stock>();
        MockResult<Shipment> newShipments = new MockResult<Shipment>();

        operator.cutoff(stocks, shipments, newStocks, newShipments);

        assertThat(newStocks.getResults().size(), is(1));
        assertThat(newShipments.getResults().size(), is(0));
    }

    /**
     * {@link StockOp#cutoff(List, List, Result, Result)}, shipments are raced.
     */
    @Test
    public void testCutoff_race() {
        StockOpImpl operator = new StockOpImpl();

        List<Stock> stocks = new ArrayList<Stock>();
        List<Shipment> shipments = new ArrayList<Shipment>();

        MockResult<Stock> newStocks = new MockResult<Stock>();
        MockResult<Shipment> newShipments = new MockResult<Shipment>();

        operator.cutoff(stocks, shipments, newStocks, newShipments);

        assertThat(newStocks.getResults().size(), is(0));
        assertThat(newShipments.getResults().size(), is(0));
    }
}
