package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import javax.xml.namespace.QName;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QNameWrapper;

public class ToolingQNameWrapper implements QNameWrapper {
	private QName name;

	public ToolingQNameWrapper(){}

	public ToolingQNameWrapper(QName name) {
		setQNameWrapper(name);
	}

	public void setQNameWrapper(QName name) {
		this.name = name;
	}

	public String getLocalPart() {
		return name.getLocalPart();
	}

}
