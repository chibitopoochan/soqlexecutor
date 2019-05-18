package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeGlobalSObjectResultWrapper;

public class DescribeGlobalSObjectResultWrapperMock extends PartnerDescribeGlobalSObjectResultWrapper {
	private String name;
	private String label;
	private String keyPrefix;

	public DescribeGlobalSObjectResultWrapperMock(String name, String label, String keyPrefix) {
		this.name = name;
		this.label = label;
		this.keyPrefix = keyPrefix;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper#getKeyPrefix()
	 */
	@Override
	public String getKeyPrefix() {
		return keyPrefix;
	}

}
