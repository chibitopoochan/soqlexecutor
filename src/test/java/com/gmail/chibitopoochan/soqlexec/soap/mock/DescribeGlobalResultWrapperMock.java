package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper;

public class DescribeGlobalResultWrapperMock extends DescribeGlobalResultWrapper {
	private DescribeGlobalSObjectResultWrapper[] sObjects;

	public void setSobjects(DescribeGlobalSObjectResultWrapper[] sObjects) {
		this.sObjects = sObjects;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalResultWrapper#getSobjects()
	 */
	@Override
	public DescribeGlobalSObjectResultWrapper[] getSobjects() {
		return sObjects;
	}

}
