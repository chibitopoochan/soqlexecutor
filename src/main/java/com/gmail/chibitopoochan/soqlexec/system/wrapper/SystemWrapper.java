package com.gmail.chibitopoochan.soqlexec.system.wrapper;

import java.io.InputStream;

/**
 * JavaAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class SystemWrapper {
	private static SystemWrapper instance;
	private InputStream in;

	/**
	 * インスタンスの取得.
	 * &lt;Singletonパターン&gt;
	 * @return インスタンス
	 */
	public static SystemWrapper getInstance() {
		if(instance == null) instance = new SystemWrapper();
		return instance;
	}

	/**
	 * 初期化.
	 */
	private SystemWrapper() {
		in = System.in;
	}

	/**
	 * 標準入力の提供
	 * @return 標準入力
	 */
	public InputStream in() {
		return in;
	}

}
