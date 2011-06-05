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
package com.example.tutorial.jobflow;

import com.asakusafw.testdriver.JobFlowTestDriver;

import org.junit.Test;

/**
 * サンプル：ジョブフローのテストクラス
 */
public class TutorialJobFlowTest {

    /**
     * サンプル：ジョブフローの実行
     * 
     * @throws Throwable 
     */    
	@Test
	public void testExample() throws Throwable {
        // XXX src/test/data/TutorialJobFlowTest/testExample配下にテストデータを配置してテストを実行する
		JobFlowTestDriver driver = new JobFlowTestDriver();

		driver.runTest(TutorialJob.class);
	}

}
