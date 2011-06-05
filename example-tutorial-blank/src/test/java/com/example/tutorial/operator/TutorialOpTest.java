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

import org.junit.Test;

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
	 * Test method for {@link com.example.tutorial.operator.TutorialOp#toAmount(com.example.tutorial.modelgen.view.model.SumOrder)}.
	 */
	@Test
	public void testToAmount() {
		// XXX 単体テストを記述する。演算子クラスはAPTで生成される演算子の実装クラス（TutorialOpImpl）を使用することにより、普通のJUnitのテストと同じ書き味で記述することができる。
	}

	/**
	 * Test method for {@link com.example.tutorial.operator.TutorialOp#setStatus(com.example.tutorial.modelgen.table.model.OrderDetail, java.lang.String)} .
	 */
	@Test
	public void testSetStatus() {
		// XXX 単体テストを記述する
	}
}
