package com.gmail.chibitopoochan.soqlexec.soap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.gmail.chibitopoochan.soqlexec.soap.mock.PartnerConnectionWrapperMock;

/**
 * SalesforceConnectionFactoryのテストクラス
 */
public class SalesforceConnectionFactoryTest {
	// 検証に使用する共通パラメータ
	private String username;
	private String password;
	private String url;

	// 検証に使用するインスタンス
	private PartnerConnectionWrapperMock mock;
	private SalesforceConnectionFactory factory;

	/**
	 * テスト前の共通設定
	 */
	@Before public void setup() {
    	// ダミークラスを作成
    	mock = new PartnerConnectionWrapperMock();

    	// Login情報を設定
    	url = "url";
    	username = "name";
    	password = "pwd";

    	// Loginを実行
    	factory = new SalesforceConnectionFactory(url, username, password);
    	factory.setConnectionWrapper(mock);

	}

	/**
     * ログイン成功のケース
     */
    @Test public void testLoginSuccess() {
    	mock.setSuccess(true);

    	assertTrue(factory.login());
    	assertThat(mock.getUrl(), is(url));
    	assertThat(mock.getUsername(), is(username));
    	assertThat(mock.getPassword(), is(password));

    }

    /**
     * ログイン失敗のケース
     */
    @Test public void testLoginFailed() {
    	mock.setSuccess(false);
    	assertFalse(factory.login());

    }

    /**
     * ログアウト成功のケース
     */
    @Test public void testLogoutSuccess() {
    	mock.setSuccess(true);
    	assertTrue(factory.logout());
    }

    /**
     * ログアウト失敗のケース
     */
    @Test public void testLogoutFailed() {
    	mock.setSuccess(false);
    	assertFalse(factory.logout());
    }

}
