package com.gmail.chibitopoochan.soqlexec.soap.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ConnectionWrapper;
import com.sforce.ws.ConnectionException;

public class SOQLExecutorMock extends SOQLExecutor {
	private ConnectionWrapper connection;
	private int size;
	private boolean all;
	private boolean error;
	private Map<String, List<Map<String, String>>> resultMap = new HashMap<>();
	private QueryMore more;

	public void setQueryMore(QueryMore more) {
		this.more = more;
	}

	@Override
	public QueryMore getQueryMore() {
		return more;
	}

	@Override
	public void setPartnerConnection(ConnectionWrapper connection) {
		this.connection = connection;
	}

	public ConnectionWrapper getPartnerConnection() {
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

	public class QueryMoreMock extends QueryMore {
		private List<Map<String, String>> more;
		private boolean done = true;

		public void setMore(List<Map<String,String>> more) {
			this.more = more;
			done = false;
		}

		@Override
		public boolean isDone() {
			return done;
		}

		@Override
		public List<Map<String, String>> execute() throws ConnectionException {
			done = true;
			return more;
		}

	}

}
