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
package com.asakusafw.example.direct.jobflow;

import org.junit.Test;

import com.asakusafw.example.direct.modelgen.dmdl.model.Document;
import com.asakusafw.example.direct.modelgen.dmdl.model.Word;
import com.asakusafw.testdriver.JobFlowTester;

/**
 * Test for {@link WordCountJob}.
 */
public class WordCountJobTest {

    /**
     * A simple testing.
     */
    @Test
    public void simple() {
        JobFlowTester tester = new JobFlowTester(getClass());
        tester.input("documents", Document.class)
            .prepare("simple-document.json");
        tester.output("words", Word.class)
            .verify("simple-word.xls#output", "simple-word.xls#rule");
        tester.runTest(WordCountJob.class);
    }
}
