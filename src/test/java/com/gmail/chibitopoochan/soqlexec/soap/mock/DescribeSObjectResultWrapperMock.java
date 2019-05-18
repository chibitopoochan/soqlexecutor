package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerDescribeSObjectResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper;

public class DescribeSObjectResultWrapperMock extends PartnerDescribeSObjectResultWrapper {
	private PartnerFieldsWrapper[] fields;

	/**
	 *
	 * @param fields
	 */
	public void setFieldsWrapper(PartnerFieldsWrapper[] fields) {
		this.fields = fields;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeSObjectResultWrapper#getFields()
	 */
	@Override
	public PartnerFieldsWrapper[] getFields() {
		return fields;
	}

}
