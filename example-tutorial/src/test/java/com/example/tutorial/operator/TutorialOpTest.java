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
package com.example.tutorial.operator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.example.tutorial.modelgen.table.model.ItemInfo;
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
	 * 
	 * abstractな演算子はテスト不要だが、あえてテストしようとすると下記のようになる。
	 * 
	 * Needless to test abstract operator method, but you can do as following.
	 * 
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testJoin() {
		TutorialOpImpl operator = new TutorialOpImpl();

		OrderDetail order = new OrderDetail();
		ItemInfo info = new ItemInfo();

		JoinOrder actual = operator.join(info, order);

		assertThat(actual, is(not(nullValue())));
	}

	/**
	 * Test method for {@link com.example.tutorial.operator.TutorialOp#sum(com.example.tutorial.modelgen.view.model.JoinOrder)}.
	 * 
	 * abstractな演算子はテスト不要だが、あえてテストしようとすると下記のようになる。
	 * 
	 * Needless to test abstract operator method, but you can do as following.
	 * 
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testSum() {
		TutorialOpImpl operator = new TutorialOpImpl();

		JoinOrder each = null;
		SumOrder actual = operator.sum(each);

		assertThat(actual, is(not(nullValue())));
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
