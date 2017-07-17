package com.gmail.chibitopoochan.soqlexec.soap.wrapper;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 * テストを考慮し、インスタンス化は{@link #createNewInstance(ConnectorConfig)}で行うため、
 * 使用前に呼び出しが必要です。他のメソッドは元のAPIと同じシグネチャとします。
 */
public class PartnerConnectionWrapper {
	private PartnerConnection connection;

	/**
	 * PartnerConnectionのインスタンス化
	 * @param config インスタンス化のパラメータ
	 * @throws ConnectionException 接続情報のエラー
	 */
	public void createNewInstance(ConnectorConfig config) throws ConnectionException {
		connection = new PartnerConnection(config);
	}

	/**
	 * {@see com.sforce.soap.partner.PartnerConnection#login(String, String)}のラップ.
	 * 詳細は呼び出し先のAPIを参照のこと
	 * @param username
	 * @param password
	 * @return
	 * @throws ConnectionException
	 */
	public LoginResult login(String username, String password) throws ConnectionException {
		return connection.login(username, password);
	}

	/**
	 * {@see com.sforce.soap.partner.PartnerConnection#logout()}のラップ
	 * 詳細は呼び出し先のAPIを参照のこと
	 * @throws ConnectionException
	 */
	public void logout() throws ConnectionException {
		connection.logout();
	}

}
