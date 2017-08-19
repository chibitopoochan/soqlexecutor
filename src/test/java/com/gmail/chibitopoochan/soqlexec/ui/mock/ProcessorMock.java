package com.gmail.chibitopoochan.soqlexec.ui.mock;

import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;
import com.gmail.chibitopoochan.soqlexec.ui.Processor;

public class ProcessorMock implements Processor {
	private Map<String, String> parameter;
	private boolean execute;

	@Override
	public void execute() {
		execute = true;
	}

	public boolean isExecute() {
		return execute;
	}

	@Override
	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	public Map<String, String> getParameter() {
		return parameter;
	}

	public void setSOQLExecutor(SOQLExecutor executor) {
	}

}
