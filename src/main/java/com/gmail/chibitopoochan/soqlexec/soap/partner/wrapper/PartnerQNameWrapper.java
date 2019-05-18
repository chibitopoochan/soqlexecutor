package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;

import javax.xml.namespace.QName;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QNameWrapper;

public class PartnerQNameWrapper implements QNameWrapper {
	private QName name;

	public PartnerQNameWrapper(){}

	public PartnerQNameWrapper(QName name) {
		setQNameWrapper(name);
	}

	public void setQNameWrapper(QName name) {
		this.name = name;
	}

	public String getLocalPart() {
		return name.getLocalPart();
	}

}
