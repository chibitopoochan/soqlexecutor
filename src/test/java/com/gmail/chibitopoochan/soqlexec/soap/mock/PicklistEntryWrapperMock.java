package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerPicklistEntryWrapper;

public class PicklistEntryWrapperMock extends PartnerPicklistEntryWrapper {
	private boolean active;
	private String value;

	/**
	 *
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 *
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PicklistEntryWrapper#isActive()
	 */
	@Override
	public boolean isActive() {
		return active;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.PicklistEntryWrapper#getValue()
	 */
	@Override
	public String getValue() {
		return value;
	}

}
