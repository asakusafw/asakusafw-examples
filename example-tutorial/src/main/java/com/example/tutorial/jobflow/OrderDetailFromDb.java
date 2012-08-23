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

import com.example.tutorial.modelgen.table.model.OrderDetail;

/**
 * {@link OrderDetail}をデータベースからロードする。
 */
public class OrderDetailFromDb extends DefaultDbImporterDescription {
	
    @Override
    public Class<?> getModelType() {
        return OrderDetail.class;
    }
 
    @Override
    public LockType getLockType() {
        // 件数によっては行ロックでもよい
        return LockType.TABLE;
    }
}
