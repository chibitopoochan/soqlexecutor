package com.gmail.chibitopoochan.soqlexec.soap.mock;

import java.util.HashMap;
import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeSObjectResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerLoginResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerQueryResultWrapper;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class PartnerConnectionWrapperMock extends PartnerConnectionWrapper {
	private boolean success;
	private String username;
	private String password;
	private String url;
	private PartnerDescribeGlobalResultWrapper globalResult;
	private PartnerDescribeSObjectResultWrapper fieldResult;
	private int queryOption;
	private Map<String, PartnerQueryResultWrapper> resultMap = new HashMap<>();
	private Map<String, PartnerQueryResultWrapper> resultAllMap = new HashMap<>();
	private Map<String, PartnerQueryResultWrapper> resultMoreMap = new HashMap<>();

	/**
	 *
	 * @param wrapper
	 */
	public void setDescribeSObjectResultWrapper(PartnerDescribeSObjectResultWrapper wrapper) {
		this.fieldResult = wrapper;
	}

	/**
	 *
	 * @param wrapper
	 */
	public void setDescribeGlobalResultWrapper(PartnerDescribeGlobalResultWrapper wrapper) {
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

	/**
	 *
	 * @param soql
	 * @param result
	 */
	public void putSOQL(String soql, PartnerQueryResultWrapper result) {
		resultMap.put(soql, result);
	}

	/**
	 *
	 * @param soql
	 * @param result
	 */
	public void putSOQLAll(String soql, PartnerQueryResultWrapper result) {
		resultAllMap.put(soql, result);
	}

	/**
	 *
	 * @param queryLocation
	 * @param result
	 */
	public void putSOQLMore(String queryLocation, PartnerQueryResultWrapper result) {
		resultMoreMap.put(queryLocation, result);
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
	public PartnerLoginResultWrapper login(String username, String password) throws ConnectionException {
		this.username = username;
		this.password = password;
		if(success) {
			return new PartnerLoginResultWrapper();
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
	public PartnerDescribeGlobalResultWrapper describeGlobal() throws ConnectionException {
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
	public PartnerDescribeSObjectResultWrapper describeSObject(String name) throws ConnectionException {
		if(success) {
			return fieldResult;
		} else {
			throw new ConnectionException();
		}
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper#setQueryOption(int)
	 */
	@Override
	public void setQueryOption(int batchSize) {
		queryOption = batchSize;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper#getQueryOption()
	 */
	@Override
	public int getQueryOption() {
		return queryOption;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper#query(java.lang.String)
	 */
	@Override
	public PartnerQueryResultWrapper query(String soql) throws ConnectionException {
		if(success) {
			return resultMap.get(soql);
		} else {
			throw new ConnectionException();
		}
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper#queryAll(java.lang.String)
	 */
	@Override
	public PartnerQueryResultWrapper queryAll(String soql) throws ConnectionException {
		if(success) {
			return resultAllMap.get(soql);
		} else {
			throw new ConnectionException();
		}
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper#queryMore(java.lang.String)
	 */
	@Override
	public PartnerQueryResultWrapper queryMore(String queryLocator) throws ConnectionException {
		if(success) {
			return resultMoreMap.get(queryLocator);
		} else {
			throw new ConnectionException();
		}
	}

}

