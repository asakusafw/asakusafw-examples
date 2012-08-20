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
package com.example.business.jobflow;

import com.example.business.modelgen.table.model.Stock;

/**
 * {@link Stock}をデータベースからロードする。
 */
public class StockFromDb extends DefaultDbImporterDescription {
	
    @Override
    public Class<?> getModelType() {
        return Stock.class;
    }

    @Override
    public String getWhere() {
        // 在庫が1個以上ないと計算しても無駄
        return "QUANTITY > 0";
    }
    
}
