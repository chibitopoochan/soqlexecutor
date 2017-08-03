package com.gmail.chibitopoochan.soqlexec.ui;

import java.util.Map;

public interface Processor {

	void execute();

	void setParameter(Map<String, String> parameter);

}
