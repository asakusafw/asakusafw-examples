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
package com.asakusafw.example.clob.operator;

import com.asakusafw.example.clob.modelgen.table.model.Document;
import com.asakusafw.example.clob.modelgen.table.model.Word;
import com.asakusafw.runtime.core.Result;
import com.asakusafw.vocabulary.flow.processor.PartialAggregation;
import com.asakusafw.vocabulary.model.Key;
import com.asakusafw.vocabulary.operator.Extract;
import com.asakusafw.vocabulary.operator.Fold;

/**
 * Operators for word count.
 */
public abstract class WordCountOperator {

    /**
     * Extract a document into words.
     * @param document a document
     * @param words the words in the document
     */
    @Extract
    public void extractDocument(Document document, Result<Word> words) {
        Word word = new Word();
        for (String s : document.getContentAsString().split("\\W+")) {
            if (s.isEmpty() == false) {
                word.setStringAsString(s.toLowerCase());
                word.setFrequency(1);
                words.add(word);
            }
        }
    }

    /**
     * Fold each words.
     * @param acc a word summing up
     * @param op a word to be sum up
     */
    @Fold(partialAggregation = PartialAggregation.PARTIAL)
    public void foldWords(@Key(group = "string") Word acc, Word op) {
        acc.setFrequency(acc.getFrequency() + op.getFrequency());
    }
}
