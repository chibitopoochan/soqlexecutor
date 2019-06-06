package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ConnectionWrapper;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 * テストを考慮し、インスタンス化は{@link #createNewInstance(ConnectorConfig)}で行うため、
 * 使用前に呼び出しが必要です。他のメソッドは元のAPIと同じシグネチャとします。
 */
public class PartnerConnectionWrapper implements ConnectionWrapper {
	private PartnerConnection connection;

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection}のインスタンス化
	 * @param config インスタンス化のパラメータ
	 * @throws ConnectionException 接続情報のエラー
	 */
	public void createNewInstance(ConnectorConfig config) throws ConnectionException {
		connection = new PartnerConnection(config);
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#queryAll(String)}のラップ
	 * @param soql SOQL
	 * @return 実行結果
	 * @throws ConnectionException 接続エラー
	 */
	public PartnerQueryResultWrapper queryAll(String soql) throws ConnectionException {
		return new PartnerQueryResultWrapper(connection.queryAll(soql));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#login(String, String)}のラップ.
	 * 詳細は呼び出し先のAPIを参照のこと
	 * @param username ユーザ名
	 * @param password パスワード
	 * @return ログイン結果
	 * @throws ConnectionException 接続エラー
	 */
	public PartnerLoginResultWrapper login(String username, String password) throws ConnectionException {
		return new PartnerLoginResultWrapper(connection.login(username, password));
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
	public PartnerDescribeGlobalResultWrapper describeGlobal() throws ConnectionException {
		return new PartnerDescribeGlobalResultWrapper(connection.describeGlobal());
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#describeSObject(String)}のラップ
	 * @param name SObjectタイプ
	 * @return 項目情報
	 * @throws ConnectionException 接続エラー
	 */
	public PartnerDescribeSObjectResultWrapper describeSObject(String name) throws ConnectionException {
		return new PartnerDescribeSObjectResultWrapper(connection.describeSObject(name));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#setQueryOptions(int)}のラップ
	 * @param batchSize 取得件数
	 */
	public void setQueryOption(int batchSize) {
		connection.setQueryOptions(batchSize);
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#setLocaleOptions(String, boolean)}のラップ
	 * @param language 言語
	 * @param localizeErrors
	 */
	public void setLocaleOptions(String language, boolean localizeErrors) {
		connection.setLocaleOptions(language, localizeErrors);
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#getQueryOptions()}のラップ
	 * バッチサイズ取得のため、内部でgetBatchSize()を呼び出している。
	 * @return 取得件数
	 */
	public int getQueryOption() {
		return connection.getQueryOptions().getBatchSize();
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#query(String)}}
	 * @param soql SOQL
	 * @return 実行結果
	 * @throws ConnectionException 接続エラー
	 */
	public PartnerQueryResultWrapper query(String soql) throws ConnectionException {
		return new PartnerQueryResultWrapper(connection.query(soql));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#queryMore(String)}のラップ
	 * @param queryLocator クエリ位置
	 * @return 実行結果
	 * @throws ConnectionException 接続エラー
	 */
	public PartnerQueryResultWrapper queryMore(String queryLocator) throws ConnectionException {
		return new PartnerQueryResultWrapper(connection.queryMore(queryLocator));
	}

	/**
	 * {@link com.sforce.soap.partner.PartnerConnection#getUserInfo()}のラップ
	 * @return ユーザ情報
	 * @throws ConnectionException 接続エラー
	 */
	public PartnerGetUserInfoResultWrapper getUserInfo() throws ConnectionException {
		return new PartnerGetUserInfoResultWrapper(connection.getUserInfo());
	}

}
