package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;

public class SalesforceConnectionFactoryMock extends SalesforceConnectionFactory {

	public SalesforceConnectionFactoryMock(String authEndPoint, String username, String password) {
		super(authEndPoint, username, password);
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#login()
	 */
	@Override
	public boolean login() {
		// TODO 自動生成されたメソッド・スタブ
		return super.login();
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory#logout()
	 */
	@Override
	public boolean logout() {
		// TODO 自動生成されたメソッド・スタブ
		return super.logout();
	}



}
