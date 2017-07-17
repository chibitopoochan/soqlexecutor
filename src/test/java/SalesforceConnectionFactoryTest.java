import org.junit.Test;

import com.sforce.soap.partner.LoginResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;

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
    	String url = "url";
    	String username = "name";
    	String password = "pwd";

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

    class PartnerConnectionWrapperMock extends PartnerConnectionWrapper {
    	private boolean success;
    	private String username;
    	private String password;
    	private String url;

    	/**
    	 *
    	 * @param success
    	 */
    	public void setSuccess(boolean success) {
    		this.success = success;
    	}

		/**
		 * @return username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @return password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @return url
		 */
		public String getUrl() {
			return url;
		}

		/* (非 Javadoc)
		 * @see PartnerConnectionWrapper#createNewInstance(com.sforce.ws.ConnectorConfig)
		 */
		@Override
		public void createNewInstance(ConnectorConfig config) throws ConnectionException {
			this.url = config.getAuthEndpoint();
		}

		/* (非 Javadoc)
		 * @see PartnerConnectionWrapper#login(java.lang.String, java.lang.String)
		 */
		@Override
		public LoginResult login(String username, String password) throws ConnectionException {
			this.username = username;
			this.password = password;
			if(success) {
				return new LoginResult();
			} else {
				throw new ConnectionException();
			}
		}

		/* (非 Javadoc)
		 * @see PartnerConnectionWrapper#logout()
		 */
		@Override
		public void logout() throws ConnectionException {
			if(!success) {
				throw new ConnectionException();
			}
		}


    }
}
