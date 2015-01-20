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

import java.util.HashSet;
import java.util.Set;

import com.asakusafw.example.direct.modelgen.dmdl.model.Document;
import com.asakusafw.vocabulary.external.FileImporterDescription;


/**
 * Import documents from file.
 */
public class DocumentFromFile extends FileImporterDescription {

    @Override
    public Class<?> getModelType() {
        return Document.class;
    }

    @Override
    public Set<String> getPaths() {
        Set<String> paths = new HashSet<String>();
        paths.add("target/testing/document/file-*");
        return paths;
    }
}
