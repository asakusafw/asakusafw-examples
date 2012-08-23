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
package com.example.tutorial.flowpart;

import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.FlowPart;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.Out;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory;
import com.asakusafw.vocabulary.flow.util.CoreOperatorFactory.Extend;
import com.example.tutorial.modelgen.table.model.ItemInfo;
import com.example.tutorial.modelgen.table.model.OrderAmount;
import com.example.tutorial.modelgen.table.model.OrderDetail;
import com.example.tutorial.operator.TutorialOpFactory;
import com.example.tutorial.operator.TutorialOpFactory.Join;
import com.example.tutorial.operator.TutorialOpFactory.SetStatus;
import com.example.tutorial.operator.TutorialOpFactory.Sum;

/**
 * チュートリアルで利用するフロー部品。
 */
@FlowPart
public class TutorialFlow extends FlowDescription {

	private In<OrderDetail> orderIn;

	private In<ItemInfo> itemIn;

	private Out<OrderDetail> orderOut;

	private Out<OrderAmount> resultOut;

	// チュートリアル用の演算子ファクトリと組込みのファクトリを作成
	private TutorialOpFactory op = new TutorialOpFactory();
	private CoreOperatorFactory core = new CoreOperatorFactory();

	/**
	 * コンストラクタ。
	 * 
	 * @param orderIn
	 *            処理対象の注文明細
	 * @param itemIn
	 *            商品マスタ
	 * @param orderOut
	 *            処理結果の注文明細
	 * @param resultOut
	 *            集計結果
	 */
	public TutorialFlow(In<OrderDetail> orderIn, In<ItemInfo> itemIn,
			Out<OrderDetail> orderOut, Out<OrderAmount> resultOut) {
		this.orderIn = orderIn;
		this.itemIn = itemIn;
		this.orderOut = orderOut;
		this.resultOut = resultOut;
	}

	@Override
	protected void describe() {
		// まずは明細と商品マスタを結合
		Join join = op.join(itemIn, orderIn);

		// 結合に失敗したものはエラーフラグを立てて終了させておく
		SetStatus missing = op.setStatus(join.missed, "商品不明");
		orderOut.add(missing.out);

		// 結合に成功したものは注文ごとに集計
		Sum sum = op.sum(join.joined);

		// 出力先のテーブルに対応するモデルに変換
		Extend<OrderAmount> result = core.extend(sum.out, OrderAmount.class);
		resultOut.add(result.out);
	}
}
