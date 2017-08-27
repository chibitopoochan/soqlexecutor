package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper;

public class SalesforceConnectionFactoryMock extends SalesforceConnectionFactory {
	private PartnerConnectionWrapper partner;
	private boolean login;
	private boolean logout;
	private boolean error;

	private String authEndPoint;
	private String username;
	private String password;

	public void setLoginError(boolean error) {
		this.error = error;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#setParameter(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void setParameter(String authEndPoint, String username, String password) {
		this.authEndPoint = authEndPoint;
		this.username = username;
		this.password = password;
	}

	public String getAuthEndPoint() {
		return authEndPoint;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#login()
	 */
	@Override
	public boolean login() {
		if(error) {
			login = false;
		} else {
			login = true;
		}
		return login;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#isLogin()
	 */
	@Override
	public boolean isLogin() {
		return login;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#logout()
	 */
	@Override
	public boolean logout() {
		if(error) {
			logout = false;
		} else {
			logout = true;
		}
		return logout;
	}

	public boolean isLogout() {
		return logout;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#getPartnerConnection()
	 */
	@Override
	public PartnerConnectionWrapper getPartnerConnection() {
		return partner;
	}

	public void setPartnerConnection(PartnerConnectionWrapper partner) {
		this.partner = partner;
	}

}
