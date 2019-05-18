package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FilteredLookupInfoWrapper;
import com.sforce.soap.partner.FilteredLookupInfo;

public class PartnerFilteredLookupInfoWrapper implements FilteredLookupInfoWrapper {
	private FilteredLookupInfo result;

	public PartnerFilteredLookupInfoWrapper() {

	}

	public PartnerFilteredLookupInfoWrapper(FilteredLookupInfo filteredLookupInfo) {
		this.result = filteredLookupInfo;
	}

	@Override
	public boolean isOptionalFilter() {
		return result.isOptionalFilter();
	}

	@Override
	public boolean isDependent() {
		return result.isDependent();
	}

	@Override
	public String[] getControllingFields() {
		return result.getControllingFields();
	}

}
