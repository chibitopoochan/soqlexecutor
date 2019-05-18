package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerPicklistEntryWrapper;

public class FieldsWrapperMock extends PartnerFieldsWrapper {
	private String name;
	private String label;
	private int length;
	private String type;
	private String[] referenceTo;
	private PartnerPicklistEntryWrapper[] picklistValues;

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param label セットする label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param length セットする length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @param referenceTo セットする referenceTo
	 */
	public void setReferenceTo(String[] referenceTo) {
		this.referenceTo = referenceTo;
	}

	/**
	 * @param picklistValues セットする picklistValues
	 */
	public void setPicklistValues(PartnerPicklistEntryWrapper[] picklistValues) {
		this.picklistValues = picklistValues;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper#getLength()
	 */
	@Override
	public int getLength() {
		return length;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper#getReferenceTo()
	 */
	@Override
	public String[] getReferenceTo() {
		return referenceTo;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper#getPicklistValues()
	 */
	@Override
	public PartnerPicklistEntryWrapper[] getPicklistValues() {
		return picklistValues;
	}

}
