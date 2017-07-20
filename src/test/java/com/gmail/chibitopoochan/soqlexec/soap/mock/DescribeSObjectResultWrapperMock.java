package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeSObjectResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper;

public class DescribeSObjectResultWrapperMock extends DescribeSObjectResultWrapper {
	private FieldsWrapper[] fields;

	/**
	 *
	 * @param fields
	 */
	public void setFieldsWrapper(FieldsWrapper[] fields) {
		this.fields = fields;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeSObjectResultWrapper#getFields()
	 */
	@Override
	public FieldsWrapper[] getFields() {
		return fields;
	}

}
