package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ConnectionWrapper;

public class SalesforceConnectionFactoryMock extends SalesforceConnectionFactory {
	private ConnectionWrapper partner;
	private boolean login;
	private boolean logout;
	private boolean error;

	private String authEndPoint;
	private String username;
	private String password;
	private String proxyHost;
	private int proxyPort;
	private String proxyUser;
	private String proxyPass;

	public void setLoginError(boolean error) {
		this.error = error;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#setParameter(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void setParameter(String authEndPoint, String username, String password, boolean tool, String local) {
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
	public ConnectionWrapper getPartnerConnection() {
		return partner;
	}

	public void setPartnerConnection(ConnectionWrapper partner) {
		this.partner = partner;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#setProxyParameter(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	@Override
	public void setProxyParameter(String host, int port, String username, String password) {
		proxyHost = host;
		proxyPort = port;
		proxyUser = username;
		proxyPass = password;
	}

	/**
	 * @return proxyHost
	 */
	public String getProxyHost() {
		return proxyHost;
	}

	/**
	 * @return proxyPort
	 */
	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * @return proxyUser
	 */
	public String getProxyUser() {
		return proxyUser;
	}

	/**
	 * @return proxyPass
	 */
	public String getProxyPass() {
		return proxyPass;
	}

}
