package com.gmail.chibitopoochan.soqlexec.soap;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper.ToolingConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.GetUserInfoResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.LoginResultWrapper;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

/**
 * Salesforceへの接続を提供.
 * SalesforceへのI/Fを提供するコネクタを生成します。
 * 接続情報を初期化時に決定するため、変更する場合は別のインスタンスを作成が必要です。
 */
public class SalesforceConnectionFactory {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(SalesforceConnectionFactory.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	// ログイン関連の情報
	private ConnectionWrapper connection;
	private LoginResultWrapper loginResult;
	private GetUserInfoResultWrapper userInfo;
	private ConnectorConfig config;
	private String username;
	private String password;
	private String proxyHost;
	private int proxyPort;
	private String proxyUsername;
	private String proxyPassword;

	// 自身のインスタンス
	private static Optional<SalesforceConnectionFactory> factory = Optional.empty();

	/**
	 * 自身のインスタンスの設定
	 * @param factory Singletonパターンで提供するインスタンス
	 */
	public static void setSalesforceConnectionFactory(SalesforceConnectionFactory factory) {
		SalesforceConnectionFactory.factory = Optional.ofNullable(factory);
	}

	/**
	 * インスタンスの取得
	 * @param authEndPoint 認証先（URL）
	 * @param username ユーザ名
	 * @param password パスワード
	 * @param useTooling ツールAPI使用
	 * @return インスタンス
	 */
	public static SalesforceConnectionFactory newInstance(String authEndPoint, String username, String password, boolean useTooling) {
		return factory.orElse(new SalesforceConnectionFactory(authEndPoint, username, password, useTooling));
	}

	/**
	 * インスタンスの取得
	 * @param authEndPoint 認証先（URL）
	 * @param username ユーザ名
	 * @param password パスワード
	 * @param proxyServer プロキシサーバのホスト名
	 * @param proxyPort プロキシサーバのポート
	 * @param proxyUser プロキシサーバのユーザ名
	 * @param proxyPassword プロキシサーバのパスワード
	 * @return インスタンス
	 */
	public static SalesforceConnectionFactory newInstance(String authEndPoint, String username, String password, boolean useTooling
			,String proxyServer, int proxyPort, String proxyUser, String proxyPassword) {
		SalesforceConnectionFactory value = factory.orElse(
				new SalesforceConnectionFactory(authEndPoint, username, password, useTooling));
		value.setProxyParameter(proxyServer, proxyPort, proxyUser, proxyPassword);
		return value;
	}

	/**
	 * 接続情報を持たないコネクタを作成
	 */
	public SalesforceConnectionFactory() {}

	/**
	 * 接続情報を持つコネクタを作成します
	 * @param authEndPoint 認証先URL
	 * @param username ユーザ名
	 * @param password パスワード
	 */
	private SalesforceConnectionFactory(String authEndPoint, String username, String password,boolean useTooling) {
		setParameter(authEndPoint, username, password, useTooling);
	}

	/**
	 * 接続情報を設定
	 * @param authEndPoint 接続先
	 * @param username ユーザ名
	 * @param password パスワード
	 */
	public void setParameter(String authEndPoint, String username, String password, boolean useTooling) {
		connection = useTooling ? new ToolingConnectionWrapper() : new PartnerConnectionWrapper();
        config = new ConnectorConfig();
        config.setAuthEndpoint(authEndPoint);
        config.setServiceEndpoint(authEndPoint);
        config.setManualLogin(true);

        this.username = username;
        this.password = password;

	}

	/**
	 * プロキシ情報を設定
	 * @param host プロキシサーバのホスト
	 * @param port プロキシサーバのポート
	 * @param username ユーザ名
	 * @param password パスワード
	 */
	public void setProxyParameter(String host, int port, String username, String password) {
        config.setProxy(host, port);
        config.setProxyUsername(username);
        config.setProxyPassword(password);

        proxyHost = host;
        proxyPort = port;
        proxyUsername = username;
        proxyPassword = password;

	}

	/**
	 * SalesforceAPIのラッパーを設定
	 * @param wrapper SalesforceAPIのラッパー
	 */
	public void setPartnerConnection(ConnectionWrapper wrapper) {
		this.connection = wrapper;
	}

	/**
	 * SalesforceAPIのラッパーを取得.
	 * 取得時に都度接続を生成
	 * @return SalesforceAPIのラッパー
	 */
	public ConnectionWrapper getPartnerConnection() {
		// 接続設定を再作成
        ConnectorConfig config = new ConnectorConfig();
        config.setAuthEndpoint(loginResult.getServerUrl());
        config.setServiceEndpoint(loginResult.getServerUrl());
        config.setManualLogin(true);
        config.setSessionId(loginResult.getSessionId());

        if(proxyHost != null) {
        	config.setProxy(proxyHost, proxyPort);
        	config.setProxyUsername(proxyUsername);
        	config.setProxyPassword(proxyPassword);
        }

        // 接続を再作成
        try {
			connection.createNewInstance(config);
			userInfo = connection.getUserInfo();
			logger.info(resources.getString(Constants.Message.Information.MSG_006),loginResult.getServerUrl(), loginResult.getSessionId());
		} catch (ConnectionException e) {
			logger.error(resources.getString(Constants.Message.Error.ERR_005),loginResult.getServerUrl(), loginResult.getSessionId(), e);
			logger.error(e.toString(), e);
		}

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
			logger.info(resources.getString(Constants.Message.Information.MSG_001), username, password, config.getAuthEndpoint());

		} catch (ConnectionException e) {
			loginResult = null;
			logger.error(
					 resources.getString(Constants.Message.Error.ERR_001)
					,username
					,password
					,config.getAuthEndpoint());
			logger.error(e.toString(), e);

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
			getPartnerConnection().logout();
			isSuccess = true;

			// ログ出力
			logger.info(resources.getString(Constants.Message.Information.MSG_002));

			// 変数の初期化
			connection = null;
			loginResult = null;
			userInfo = null;

		} catch (ConnectionException e) {
			loginResult = null;
			logger.error(e.toString());
			logger.error(resources.getString(Constants.Message.Error.ERR_002), e);

		}

		return isSuccess;
	}

	/**
	 * インスタンス参照の削除
	 */
	public void removeInstance() {
		factory = Optional.empty();
	}

	/**
	 * ログイン状態の取得
	 * @return ログインならtrue
	 */
	public boolean isLogin() {
		return loginResult != null;
	}

	/**
	 * ログイン結果の設定
	 * @param result ログイン結果
	 */
	public void setLoginResult(LoginResultWrapper result) {
		this.loginResult = result;
	}

	/**
	 * ユーザ情報の取得
	 * @return ユーザ情報
	 */
	public GetUserInfoResultWrapper getUserInfo() {
		return userInfo;
	}
}
