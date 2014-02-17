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
package com.example.tutorial.jobflow;

import com.example.tutorial.modelgen.table.model.ItemInfo;

/**
 * {@link ItemInfo}をデータベースからロードする。
 */
public class ItemInfoFromDb extends DefaultDbImporterDescription {

    @Override
    public Class<?> getModelType() {
        return ItemInfo.class;
    }

    @Override
    public LockType getLockType() {
        // マスタはロックをとらない
        return LockType.CHECK;
    }

    @Override
    public DataSize getDataSize() {
        // 今回は10MB未満程度とする
        return DataSize.TINY;
    }
}
