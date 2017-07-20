package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeSObjectResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper;
import com.sforce.soap.partner.LoginResult;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class PartnerConnectionWrapperMock extends PartnerConnectionWrapper {
	private boolean success;
	private String username;
	private String password;
	private String url;
	private DescribeGlobalResultWrapper globalResult;
	private DescribeSObjectResultWrapper fieldResult;

	/**
	 *
	 * @param wrapper
	 */
	public void setDescribeSObjectResultWrapper(DescribeSObjectResultWrapper wrapper) {
		this.fieldResult = wrapper;
	}

	/**
	 *
	 * @param wrapper
	 */
	public void setDescribeGlobalResultWrapper(DescribeGlobalResultWrapper wrapper) {
		this.globalResult = wrapper;
	}

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

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper#describeGlobal()
	 */
	@Override
	public DescribeGlobalResultWrapper describeGlobal() throws ConnectionException {
		if(success) {
			return globalResult;
		} else {
			throw new ConnectionException();
		}
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper#describeSObject(java.lang.String)
	 */
	@Override
	public DescribeSObjectResultWrapper describeSObject(String name) throws ConnectionException {
		if(success) {
			return fieldResult;
		} else {
			throw new ConnectionException();
		}
	}

}

