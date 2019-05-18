package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeSObjectResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.GetUserInfoResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.LoginResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper;
import com.sforce.soap.tooling.ToolingConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 * テストを考慮し、インスタンス化は{@link #createNewInstance(ConnectorConfig)}で行うため、
 * 使用前に呼び出しが必要です。他のメソッドは元のAPIと同じシグネチャとします。
 */
public class ToolingConnectionWrapper implements ConnectionWrapper {
	private ToolingConnection connection;

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection}のインスタンス化
	 * @param config インスタンス化のパラメータ
	 * @throws ConnectionException 接続情報のエラー
	 */
	public void createNewInstance(ConnectorConfig config) throws ConnectionException {
		connection = new ToolingConnection(config);
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#login(String, String)}のラップ.
	 * 詳細は呼び出し先のAPIを参照のこと
	 * @param username ユーザ名
	 * @param password パスワード
	 * @return ログイン結果
	 * @throws ConnectionException 接続エラー
	 */
	public LoginResultWrapper login(String username, String password) throws ConnectionException {
		return new ToolingLoginResultWrapper(connection.login(username, password));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#logout()}のラップ
	 * 詳細は呼び出し先のAPIを参照のこと
	 * @throws ConnectionException 接続エラー
	 */
	public void logout() throws ConnectionException {
		connection.logout();
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#describeGlobal()}のラップ
	 * @return グローバル記述結果
	 * @throws ConnectionException 接続エラー
	 */
	public DescribeGlobalResultWrapper describeGlobal() throws ConnectionException {
		return new ToolingDescribeGlobalResultWrapper(connection.describeGlobal());
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#describeSObject(String)}のラップ
	 * @param name SObjectタイプ
	 * @return 項目情報
	 * @throws ConnectionException 接続エラー
	 */
	public DescribeSObjectResultWrapper describeSObject(String name) throws ConnectionException {
		return new ToolingDescribeSObjectResultWrapper(connection.describeSObject(name));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#setQueryOptions(int)}のラップ
	 * @param batchSize 取得件数
	 */
	public void setQueryOption(int batchSize) {
		// 対応する機能なし
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#getQueryOptions()}のラップ
	 * バッチサイズ取得のため、内部でgetBatchSize()を呼び出している。
	 * @return 取得件数
	 */
	public int getQueryOption() {
		return 0;
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#query(String)}}
	 * @param soql SOQL
	 * @return 実行結果
	 * @throws ConnectionException 接続エラー
	 */
	public QueryResultWrapper query(String soql) throws ConnectionException {
		return new ToolingQueryResultWrapper(connection.query(soql));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#queryAll(String)}のラップ
	 * @param soql SOQL
	 * @return 実行結果
	 * @throws ConnectionException 接続エラー
	 */
	public QueryResultWrapper queryAll(String soql) throws ConnectionException {
		return new ToolingQueryResultWrapper(connection.queryAll(soql));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#queryMore(String)}のラップ
	 * @param queryLocator クエリ位置
	 * @return 実行結果
	 * @throws ConnectionException 接続エラー
	 */
	public QueryResultWrapper queryMore(String queryLocator) throws ConnectionException {
		return new ToolingQueryResultWrapper(connection.queryMore(queryLocator));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#getUserInfo()}のラップ
	 * @return ユーザ情報
	 * @throws ConnectionException 接続エラー
	 */
	public GetUserInfoResultWrapper getUserInfo() throws ConnectionException {
		return new ToolingGetUserInfoResultWrapper(connection.getUserInfo());
	}

}
