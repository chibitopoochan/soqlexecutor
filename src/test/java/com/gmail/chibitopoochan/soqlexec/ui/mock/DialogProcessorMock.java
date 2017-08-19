package com.gmail.chibitopoochan.soqlexec.ui.mock;

import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.ui.DialogProcessor;

public class DialogProcessorMock extends DialogProcessor {
	private Map<String, String> parameter;

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.ui.AbstractProcessor#setParameter(java.util.Map)
	 */
	@Override
	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	public Map<String, String> getParameter() {
		return parameter;
	}

}
