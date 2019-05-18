package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper;

public class DescribeGlobalResultWrapperMock extends PartnerDescribeGlobalResultWrapper {
	private PartnerDescribeGlobalSObjectResultWrapper[] sObjects;

	public void setSobjects(PartnerDescribeGlobalSObjectResultWrapper[] sObjects) {
		this.sObjects = sObjects;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalResultWrapper#getSobjects()
	 */
	@Override
	public PartnerDescribeGlobalSObjectResultWrapper[] getSobjects() {
		return sObjects;
	}

}
