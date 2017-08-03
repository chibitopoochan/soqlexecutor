package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import javax.xml.namespace.QName;

public class QNameWrapper {
	private QName name;

	public QNameWrapper(){}

	public QNameWrapper(QName name) {
		setQNameWrapper(name);
	}

	public void setQNameWrapper(QName name) {
		this.name = name;
	}

	public String getLocalPart() {
		return name.getLocalPart();
	}

}
