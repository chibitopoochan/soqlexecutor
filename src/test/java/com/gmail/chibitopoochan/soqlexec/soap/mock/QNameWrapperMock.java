package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QNameWrapper;

public class QNameWrapperMock extends QNameWrapper {

	private String localPart;

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.QNameWrapper#getLocalPart()
	 */
	@Override
	public String getLocalPart() {
		return localPart;
	}

	public void setLocalPart(String localPart) {
		this.localPart = localPart;
	}

}
