package com.gmail.chibitopoochan.soqlexec.soap.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper;
import com.sforce.ws.ConnectionException;

public class SOQLExecutorMock extends SOQLExecutor {
	private PartnerConnectionWrapper connection;
	private int size;
	private boolean more;
	private boolean all;
	private boolean error;
	private Map<String, List<Map<String, String>>> resultMap = new HashMap<>();

	@Override
	public void setPartnerConnection(PartnerConnectionWrapper connection) {
		this.connection = connection;
	}

	public PartnerConnectionWrapper getPartnerConnection() {
		return connection;
	}

	@Override
	public void setBatchSize(int batchSize) {
		this.size = batchSize;
	}

	public int getBatchSize() {
		return size;
	}

	@Override
	public void setMoreOption(boolean more) {
		this.more = more;
	}

	public boolean getMoreOption() {
		return more;
	}

	@Override
	public void setAllOption(boolean all) {
		this.all = all;
	}

	public boolean getAllOption() {
		return all;
	}

	public void putResult(String query, List<Map<String,String>> result) {
		this.resultMap.put(query, result);
	}

	public void setError(boolean error) {
		this.error = error;
	}

	@Override
	public List<Map<String, String>> execute(String soql) throws ConnectionException {
		if(error) {
			throw new ConnectionException();
		}

		if(resultMap.containsKey(soql)) {
			return resultMap.get(soql);
		} else {
			return new ArrayList<>();
		}
	}

}
