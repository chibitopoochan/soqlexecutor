package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerQueryResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerSObjectWrapper;

public class QueryResultWrapperMock extends PartnerQueryResultWrapper {
	private int size;
	private boolean done;
	private String queryLocator;
	private PartnerSObjectWrapper[] records;

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper#getRecords()
	 */
	@Override
	public PartnerSObjectWrapper[] getRecords() {
		return records;
	}

	public void setRecords(PartnerSObjectWrapper[] records) {
		this.records = records;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper#getQueryLocator()
	 */
	@Override
	public String getQueryLocator() {
		return queryLocator;
	}

	public void setQueryLocator(String queryLocator) {
		this.queryLocator = queryLocator;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper#isDone()
	 */
	@Override
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper#getSize()
	 */
	@Override
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
