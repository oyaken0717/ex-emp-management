package com.example;

public class memo {

}

//Javadoc
/**
 * ■①クラス、インターフェース 車を表す部品用のクラス. (2行目にタイトルと「.ピリオド」) 現実世界に走っている車を表現している (詳細) <br>
 * クラスです。
 * 
 * @author kenji.oyamada (ラクスは名前と名字)
 *
 */
public class Car {
	/**
	 * ■②変数 スピード(①変数の書き方)
	 */
	private int speed;

	/** 車体の色(②変数の書き方) */
	private String bodyColor;

	/**
	 * ■③メソッド 走ります.(↓は必要なものだけ追加する)
	 * 
	 * @param speed 追加するスピード
	 * @return 追加されたスピード
	 * @throws Exception スピードが決められた以上になった時に発生
	 */
	public int run(int speed) throws Exception {
		this.speed += speed;

		if (speed > 200) {
			throw new Exception("スピードの出し過ぎです");
		}
		return speed;
	}
}
