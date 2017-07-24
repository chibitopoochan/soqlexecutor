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
 * 接続情報を初期化時に決定するため、変更する場合は別のインスタンスを作成してください。
 */
public class SalesforceConnectionFactory {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(SalesforceConnectionFactory.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	// ログイン関連の情報
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

        this.username = username;
        this.password = password;

	}

	/**
	 * SalesforceAPIのラッパーを設定
	 * @param wrapper SalesforceAPIのラッパー
	 */
	public void setConnectionWrapper(PartnerConnectionWrapper wrapper) {
		this.connection = wrapper;
	}

	/**
	 * SalesforceAPIのラッパーを取得
	 * @return SalesforceAPIのラッパー
	 */
	public PartnerConnectionWrapper getConnectionWrapper() {
		return connection;
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
			logger.info(resources.getString(Constants.Message.Information.MSG_001));

		} catch (ConnectionException e) {
			logger.error(
					 resources.getString(Constants.Message.Error.ERR_001)
					,username
					,password
					,config.getAuthEndpoint()
					,e);

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
			logger.info(resources.getString(Constants.Message.Error.ERR_002));

			// 変数の初期化
			connection = null;
			loginResult = null;

		} catch (ConnectionException e) {
			loginResult = null;
			logger.error(resources.getString(Constants.Message.Error.ERR_002), e);

		}

		return isSuccess;
	}

	/**
	 * ログイン状態の取得
	 * @return ログインならtrue
	 */
	public boolean isLogin() {
		return loginResult != null;
	}

}
