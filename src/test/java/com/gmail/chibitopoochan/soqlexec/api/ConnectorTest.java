package com.gmail.chibitopoochan.soqlexec.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.gmail.chibitopoochan.soqlexec.api.mock.MetaInformationProviderMock;
import com.gmail.chibitopoochan.soqlexec.model.FieldMetaInfo;
import com.gmail.chibitopoochan.soqlexec.model.SObjectMetaInfo;
import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.soap.mock.PartnerConnectionWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.SOQLExecutorMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.SalesforceConnectionFactoryMock;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Constants.Properties;
import com.sforce.ws.ConnectionException;

/**
 * Connectorのテストクラス
 */
public class ConnectorTest {
	// リソースの取得
	private static final ResourceBundle properties = ResourceBundle.getBundle(Constants.Properties.RESOURCE);

	// 検証に使用するインスタンス
	private PartnerConnectionWrapperMock wrapper;
	private SalesforceConnectionFactoryMock factory;
	private SOQLExecutorMock executor;

	// 検証に使用する共通パラメータ
	private String authEndPoint = properties.getString(Properties.AUTH_END_POINT);
	private String username = "username";
	private String password = "password";

	/**
	 * 例外検証用のルールを用意
	 */
	@Rule public ExpectedException thrown = ExpectedException.none();

	/**
	 * テスト前の共通設定
	 */
	@Before public void setup() {
		wrapper = new PartnerConnectionWrapperMock();

		factory = new SalesforceConnectionFactoryMock();
		factory.setLoginError(false);
		factory.setPartnerConnection(wrapper);
		factory.setParameter(authEndPoint, username, password,false,"ja_JP");

		SalesforceConnectionFactory.setSalesforceConnectionFactory(factory);

	}

	/**
	 * SOQLの実行
	 * @throws ConnectionException 接続エラー
	 */
	@Test public void testSOQLExecute() throws Exception {
		// SOQLの結果を設定
		Map<String, String> record = new HashMap<>();
		record.put("id", "result");

		List<Map<String, String>> result = new LinkedList<>();
		result.add(record);

		executor = new SOQLExecutorMock();
		executor.putResult("select id from user", result);

		SOQLExecutorMock.QueryMoreMock more = executor.new QueryMoreMock();
		more.setMore(result);
		executor.setQueryMore(more);

		// ログイン
		Connector connect = Connector.login(username, password, false,"ja_JP");
		assertThat(factory.getUsername(), is(username));
		assertThat(factory.getPassword(), is(password));
		assertThat(factory.getAuthEndPoint(), is(authEndPoint));

		// クエリの実行
		connect.setSOQLExecutor(executor);
		assertThat(connect.execute("select id from user", true, 5, true), is(result));
		assertTrue(executor.getAllOption());
		assertThat(executor.getBatchSize(), is(5));
		assertThat(connect.executeMore(), is(result));

		// ログアウト
		connect.logout();
		assertTrue(factory.isLogout());

	}

	/**
	 * メタ情報を取得.
	 * @throws Exception 接続エラー
	 */
	@Test public void testDescribeSObject() throws Exception {
		List<SObjectMetaInfo> obj = new LinkedList<>();
		obj.add(new SObjectMetaInfo("name", "lable", "prefix"));

		List<FieldMetaInfo> field = new LinkedList<>();
		field.add(new FieldMetaInfo("name", "label", 100, "type"));

		MetaInformationProviderMock provider = new MetaInformationProviderMock();
		provider.setSObjectList(obj);
		provider.setFieldList(field);

		// ログイン
		Connector connect = Connector.login(username, password, false,"ja_JP");
		connect.setMetaInfoProvieder(provider);

		assertThat(connect.getDescribeSObjects(), is(obj));
		assertThat(connect.getDescribeFields(""), is(field));
	}

	/**
	 * ログアウト後の操作.
	 * ログアウトした後はエラーが発生する。
	 * 使用したい場合、再ログインする必要がある。
	 * @throws ConnectionException 接続エラー
	 */
	@Test public void testOperateWithLogout() throws Exception {

		// ログイン
		Connector connect = Connector.login(username, password, false,"ja_JP");
		assertThat(factory.getUsername(), is(username));
		assertThat(factory.getPassword(), is(password));
		assertThat(factory.getAuthEndPoint(), is(authEndPoint));

		// ログアウト
		connect.logout();
		assertTrue(factory.isLogout());

		thrown.expect(Exception.class);

		connect.execute("", false, 10, true);

	}

	/**
	 * プロキシを用いた接続.
	 * プロキシ設定がパラメターとして受け渡されることを確認する。
	 * @throws Exception 接続エラー
	 */
	@Test public void testProxySetting() throws Exception {
		String proxyHost = "host";
		int proxyPort = 8080;
		String proxyId = "user";
		String proxyPassword = "password";

		// ログイン
		Connector.setProxySetting(proxyHost, proxyPort, proxyId, proxyPassword);
		Connector.login(username, password, false,"ja_JP");

		assertThat(factory.getProxyHost(), is(proxyHost));
		assertThat(factory.getProxyPort(), is(proxyPort));
		assertThat(factory.getProxyUser(), is(proxyId));
		assertThat(factory.getProxyPass(), is(proxyPassword));

		// 再ログイン（プロキシ設定の指定が無ければ通常ログイン）
		setup(); // モックを使いまわしてしまうので再設定
		Connector.login(username, password, false,"ja_JP");
		assertThat(factory.getProxyHost(), nullValue());
		assertThat(factory.getProxyPort(), is(0));
		assertThat(factory.getProxyUser(), nullValue());
		assertThat(factory.getProxyPass(), nullValue());

		// 再ログイン（プロキシ設定の指定があればプロキシ接続）
		setup(); // モックを使いまわしてしまうので再設定
		Connector.setProxySetting(proxyHost, proxyPort, proxyId, proxyPassword);
		Connector.login(username, password, false,"ja_JP");

		assertThat(factory.getProxyHost(), is(proxyHost));
		assertThat(factory.getProxyPort(), is(proxyPort));
		assertThat(factory.getProxyUser(), is(proxyId));
		assertThat(factory.getProxyPass(), is(proxyPassword));

	}

}
