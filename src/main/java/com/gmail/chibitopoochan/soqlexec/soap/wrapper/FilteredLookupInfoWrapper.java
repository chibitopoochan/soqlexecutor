package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

public interface FilteredLookupInfoWrapper {

	boolean isOptionalFilter();

	boolean isDependent();

	String[] getControllingFields();

}
