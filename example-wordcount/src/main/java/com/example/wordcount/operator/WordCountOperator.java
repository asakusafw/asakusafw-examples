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
package com.example.wordcount.operator;

import java.util.List;

import org.apache.hadoop.io.Text;

import com.asakusafw.runtime.core.Result;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.CoGroup;
import com.example.wordcount.modelgen.dmdl.model.LogLine;
import com.example.wordcount.modelgen.dmdl.model.WordCount;

/**
 * Operator of Word Count.
 * 
 * @author marblejenka
 *
 */
public abstract class WordCountOperator {

	private static final String SPACE = " ";

	@CoGroup
	public void split(@Key(group = {}) List<LogLine> lines,
			Result<WordCount> result) {
		for (LogLine line : lines) {
			Text query = line.getQuery();
			if (query.toString().length() == 0) {
				continue;
			}

			for (String word : query.toString().split(SPACE)) {
				WordCount count = new WordCount();
				count.setWordAsString(word);
				count.setCount(1);
				result.add(count);
			}
		}
	}

	@CoGroup
	public void summlize(@Key(group = { "word" }) List<WordCount> counts,
			Result<WordCount> result) {
		WordCount count = new WordCount();
		count.setWord(counts.get(0).getWord());
		count.setCount(counts.size());
		result.add(count);
	}

}
