package com.gmail.chibitopoochan.soqlexec.soap;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.sforce.soap.partner.LoginResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * Salesforceへの接続を提供.
 * SalesforceへのI/Fを提供するコネクタを生成します。
 */
public class SalesforceConnectionFactory {
	private static final Logger logger = LoggerFactory.getLogger(SalesforceConnectionFactory.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle("Message");

	private PartnerConnectionWrapper connection;
	private LoginResult loginResult;
	private ConnectorConfig config;
	private String username;
	private String password;

	/**
	 * 接続情報を持つコネクタを作成します
	 * @param authEndPoint 認証先URL
	 * @param username ユーザ名
	 * @param password パスワード
	 */
	public SalesforceConnectionFactory(String authEndPoint, String username, String password) {
		connection = new PartnerConnectionWrapper();
        config = new ConnectorConfig();
        config.setAuthEndpoint(authEndPoint);
        config.setManualLogin(true);
	}

	/**
	 * SalesforceAPIのラッパーを取得
	 * @param wrapper SalesforceAPIのラッパー
	 */
	public void setConnectionWrapper(PartnerConnectionWrapper wrapper) {
		this.connection = wrapper;
	}

	/**
	 * Salesforceへ接続
	 * @return 成功ならtrue
	 */
	public boolean login(){
		boolean isSuccess = false;

		try {
			// ログイン
			connection.createNewInstance(config);
			loginResult = connection.login(username, password);
			isSuccess = true;

			// ログ出力
			logger.info(resources.getString(Constants.MSG_001.getValue()));

		} catch (ConnectionException e) {
			e.printStackTrace();
			logger.warn(
					 resources.getString(Constants.ERR_001.getValue())
					,username
					,password
					,config.getAuthEndpoint());

		}

		return isSuccess;
	}

	/**
	 * Salesforceへの接続を解除
	 * @return 成功ならtrue
	 */
	public boolean logout() {
		boolean isSuccess = false;

		try {
			// ログアウト
			connection.logout();
			isSuccess = true;

			// ログ出力
			logger.info(resources.getString(Constants.MSG_002.getValue()));

			// 変数の初期化
			connection = null;
			loginResult = null;

		} catch (ConnectionException e) {
			e.printStackTrace();
			logger.warn(resources.getString(Constants.ERR_002.getValue()));

		}

		return isSuccess;
	}

}
