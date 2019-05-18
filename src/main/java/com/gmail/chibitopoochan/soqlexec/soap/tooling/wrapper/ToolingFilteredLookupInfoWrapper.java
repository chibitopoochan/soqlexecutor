package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FilteredLookupInfoWrapper;
import com.sforce.soap.tooling.FilteredLookupInfo;

public class ToolingFilteredLookupInfoWrapper implements FilteredLookupInfoWrapper {

	private FilteredLookupInfo info;

	public ToolingFilteredLookupInfoWrapper() {

	}

	public ToolingFilteredLookupInfoWrapper(FilteredLookupInfo info) {
		this.info = info;
	}

	@Override
	public boolean isOptionalFilter() {
		return info.isOptionalFilter();
	}

	@Override
	public boolean isDependent() {
		return info.isDependent();
	}

	@Override
	public String[] getControllingFields() {
		return info.getControllingFields();
	}

}
