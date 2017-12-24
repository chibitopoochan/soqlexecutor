package com.gmail.chibitopoochan.soqlexec.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.model.FieldMetaInfo;
import com.gmail.chibitopoochan.soqlexec.model.SObjectMetaInfo;
import com.gmail.chibitopoochan.soqlexec.soap.MetaInformationProvider;
import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;
import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Constants.Message.Error;
import com.gmail.chibitopoochan.soqlexec.util.Message;
import com.sforce.ws.ConnectionException;

public class Connector {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(Connector.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);
	private static final ResourceBundle properties = ResourceBundle.getBundle(Constants.Properties.RESOURCE);

	// Proxy情報の保持
	private static String proxyHost;
	private static int proxyPort;
	private static String proxyId;
	private static String proxyPassword;
	private static boolean useProxy;

	// 接続状態の保持
	private Optional<SOQLExecutor.QueryMore> queryMore;
	private SalesforceConnectionFactory factory;
	private SOQLExecutor executor;
	private MetaInformationProvider provider;
	private boolean close = false;
	private int size;

	/**
	 * Salesforceへの接続
	 * @param factory ログイン後の接続情報
	 */
	private Connector(SalesforceConnectionFactory factory) {
		this.factory = factory;
		this.executor = new SOQLExecutor(factory.getPartnerConnection());
		this.provider = new MetaInformationProvider(factory.getPartnerConnection());
	}

	/**
	 * メタ情報提供クラスを指定
	 * @param provider メタ情報提供クラス
	 */
	public void setMetaInfoProvieder(MetaInformationProvider provider) {
		this.provider = provider;
		this.provider.setPartnerConnection(factory.getPartnerConnection());
	}

	/**
	 * SOQL実行クラスを指定
	 * @param executor SOQL実行クラス
	 */
	public void setSOQLExecutor(SOQLExecutor executor) {
		this.executor = executor;
		this.executor.setPartnerConnection(factory.getPartnerConnection());
	}

	/**
	 * Salesforceへのログイン
	 * @param username ユーザ名
	 * @param password パスワード
	 * @param env authEndPoint
	 * @return Salesforceへの接続
	 * @throws ConnectionException ログインエラー
	 */
	public static Connector login(String username, String password, String env) throws Exception {
		SalesforceConnectionFactory factory;

		if(Connector.useProxy) {
			factory = SalesforceConnectionFactory.newInstance(env, username, password
					,Connector.proxyHost, Connector.proxyPort, Connector.proxyId, Connector.proxyPassword);
		} else {
			factory = SalesforceConnectionFactory.newInstance(env, username, password);
		}

		if(!factory.login()) {
			throw new ConnectionException(Message.get(Error.ERR_001, username, password, env));
		}

		return new Connector(factory);

	}

	/**
	 * Proxy接続の設定
	 * @param proxyHost ホスト名
	 * @param proxyPort ポート番号
	 * @param proxyId ユーザID
	 * @param proxyPassword パスワード
	 */
	public static void setProxySetting(String proxyHost, int proxyPort, String proxyId, String proxyPassword) {
		Connector.useProxy = true;
		Connector.proxyHost = proxyHost;
		Connector.proxyPort = proxyPort;
		Connector.proxyId = proxyId;
		Connector.proxyPassword = proxyPassword;
	}

	/**
	 * Salesforceへのログイン
	 * @param username ユーザ名
	 * @param password パスワード
	 * @return Salesforceへの接続
	 * @throws ConnectionException ログインエラー
	 */
	public static Connector login(String username, String password) throws Exception {
		return Connector.login(username, password, properties.getString(Constants.Properties.AUTH_END_POINT));
	}

	/**
	 * SOQLの実行
	 * @param query SOQL
	 * @param all ALLオプション
	 * @param batchSize 取得最大件数
	 * @return 実行結果
	 * @throws ConnectionException クエリの実行エラー
	 */
	public List<Map<String, String>> execute(String query, boolean all, int batchSize) throws ConnectionException {
		if(close) {
			logger.error(resources.getString(Error.ERR_013));
			throw new ConnectionException(resources.getString(Error.ERR_013));
		}

		// パラメータ設定
		executor.setAllOption(all);
		executor.setBatchSize(batchSize);

		// SOQL実行
		List<Map<String, String>> result = executor.execute(query);
		queryMore = Optional.of(executor.getQueryMore());
		size = executor.getSize();

		return result;

	}

	/**
	 * SOQLのレコード件数
	 * @return 件数
	 */
	public int getSize(){
		return size;
	}

	/**
	 * SOQL実行結果の追加取得
	 * @return 実行結果。全て取得済みなら０件
	 * @throws ConnectionException クエリの実行エラー
	 */
	public List<Map<String, String>> executeMore() throws ConnectionException {
		if(close) {
			logger.error(resources.getString(Error.ERR_013));
			throw new ConnectionException(resources.getString(Error.ERR_013));
		}

		// SOQL実行
		List<Map<String, String>> result = new LinkedList<>();
		if(queryMore.isPresent()) {
			result = queryMore.get().execute();
		}

		return result;

	}

	/**
	 * Salesforceへの接続を切断
	 */
	public void logout() {
		if(!close) {
			factory.logout();
			close = true;
		}

	}

	/**
	 * オブジェクト情報の取得
	 * @return オブジェクト一覧
	 * @throws ConnectionException 接続エラー
	 */
	public List<SObjectMetaInfo> getDescribeSObjects() throws ConnectionException {
		return provider.getSObjectList();
	}

	/**
	 * フィールド情報の取得
	 * @param name SObject名
	 * @return フィールド情報一覧
	 * @throws ConnectionException 接続エラー
	 */
	public List<FieldMetaInfo> getDescribeFields(String name) throws ConnectionException {
		return provider.getFieldList(name);
	}

}