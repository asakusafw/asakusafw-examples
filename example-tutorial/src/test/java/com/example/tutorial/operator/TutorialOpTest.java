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
package com.example.tutorial.operator;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.example.tutorial.modelgen.table.model.ItemInfo;
import com.example.tutorial.modelgen.table.model.OrderAmount;
import com.example.tutorial.modelgen.table.model.OrderDetail;
import com.example.tutorial.modelgen.view.model.JoinOrder;
import com.example.tutorial.modelgen.view.model.SumOrder;

/**
 * サンプル：演算子のテストケース
 * 
 * test cases for operator.
 * 
 * @author shingo.furuyama
 * 
 */
public class TutorialOpTest {

	/**
	 * Test method for {@link com.example.tutorial.operator.TutorialOp#join(com.example.tutorial.modelgen.table.model.ItemInfo, com.example.tutorial.modelgen.table.model.OrderDetail)} .
	 */
	@Test
	public void testJoin() {
		TutorialOpImpl operator = new TutorialOpImpl();

		OrderDetail order = new OrderDetail();
		ItemInfo info = new ItemInfo();

		JoinOrder actual = operator.join(info, order);

		assertThat(actual, is(not(nullValue())));
	}

	/**
	 * Test method for {@link com.example.tutorial.operator.TutorialOp#sum(com.example.tutorial.modelgen.view.model.JoinOrder)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testSum() {
		TutorialOpImpl operator = new TutorialOpImpl();

		JoinOrder each = null;
		SumOrder actual = operator.sum(each);

		assertThat(actual, is(not(nullValue())));
	}

	/**
	 * Test method for {@link com.example.tutorial.operator.TutorialOp#toAmount(com.example.tutorial.modelgen.view.model.SumOrder)}.
	 */
	@Test
	public void testToAmount() {
		TutorialOpImpl operator = new TutorialOpImpl();
		
		SumOrder total = new SumOrder();
		total.setAmount(100);
		total.setOrderId(10);
		
		OrderAmount actual = operator.toAmount(total);
		
		assertThat(actual, is(not(nullValue())));
		assertThat(actual.getAmount(), is(equalTo(100L)));
		assertThat(actual.getOrderId(), is(equalTo(10L)));
	}

	/**
	 * Test method for {@link com.example.tutorial.operator.TutorialOp#setStatus(com.example.tutorial.modelgen.table.model.OrderDetail, java.lang.String)} .
	 */
	@Test
	public void testSetStatus() {
		TutorialOpImpl operator = new TutorialOpImpl();
		
		OrderDetail order = new OrderDetail();
		String status = "STATUS";
		
		operator.setStatus(order, status);
		
		assertThat(order.getStatusAsString(), is(not(nullValue())));
		assertThat(order.getStatusAsString(), is(equalTo("STATUS")));
	}
}
