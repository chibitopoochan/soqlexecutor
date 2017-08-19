package com.gmail.chibitopoochan.soqlexec.ui;

/**
 * コマンドラインから要求を受け付けるUIクラス.
 * SOQLを一回実行して終わるコマンド形式とユーザ入力でSOQLを実行する対話形式がある。
 *
 * <h3>コマンド形式</h3>
 * SOQLExecutor -id [username] -pwd [password] -query [SOQL] (-env URL | -set OPTION=VALUE;OPTION=VALUE...)
 *
 * <h3>対話形式</h3>
 * SOQLExecutor -id [username] -pwd [password] (-env URL | -set OPTION=VALUE;OPTION=VALUE...)
 *
 * <h3>パラメータ</h3>
 * id:ユーザ名
 * pwd:パスワード
 * query:実行するSOQL
 * env:ログイン先のURL
 * set:MORE,ALLの指定。デフォルトはfalse
 *
 * <h3>対話形式のコマンド群</h3>
 * "quit" 終了
 * SOQL";" SOQLの実行
 *
 * @author mamet
 *
 */
public class SOQLExecutor {
	/**
	 * メインメソッド.
	 *
	 * @param args パラメータ
	 */
	public static void main(String[] args) {
		// 形式によって担当クラスに処理を移譲
		Processor processor = ProcessorFactoryImpl.newProcessor(args);
		processor.execute();

	}

}
