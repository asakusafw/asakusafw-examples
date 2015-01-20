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
package com.asakusafw.example.direct.jobflow;

import com.asakusafw.example.direct.operator.WordCountOperatorFactory;
import com.asakusafw.example.direct.operator.WordCountOperatorFactory.ExtractDocument;
import com.asakusafw.example.direct.operator.WordCountOperatorFactory.FoldWords;
import com.asakusafw.example.direct.modelgen.dmdl.model.Document;
import com.asakusafw.example.direct.modelgen.dmdl.model.Word;
import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;

/**
 * Word count.
 */
@JobFlow(name = "wordcount")
public class WordCountJob extends FlowDescription {

    In<Document> documents;

    Out<Word> words;

    /**
     * Creates a new instance.
     * @param documents source documents
     * @param words resulting words
     */
    public WordCountJob(
            @Import(name = "documents", description = DocumentFromFile.class)
            In<Document> documents,
            @Export(name = "words", description = WordIntoFile.class)
            Out<Word> words) {
        this.documents = documents;
        this.words = words;
    }

    @Override
    protected void describe() {
        WordCountOperatorFactory factory = new WordCountOperatorFactory();
        ExtractDocument extracted = factory.extractDocument(documents);
        FoldWords fold = factory.foldWords(extracted.words);
        words.add(fold.out);
    }
}
