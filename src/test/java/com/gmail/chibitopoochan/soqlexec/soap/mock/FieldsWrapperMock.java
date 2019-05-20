package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerPicklistEntryWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FilteredLookupInfoWrapper;

public class FieldsWrapperMock extends PartnerFieldsWrapper {
	private String name;
	private String label;
	private int length;
	private String type;
	private String[] referenceTo;
	private PartnerPicklistEntryWrapper[] picklistValues;
	private boolean aggregatable;
	private boolean autoNumber;
	private int byteLength;
	private String controllerName;
	private boolean createable;
	private String compoundFieldName;
	private boolean calculated;
	private String calculatedFormula;
	private boolean cascadeDelete;
	private boolean caseSensitive;
	private boolean custom;
	private boolean deffaultedOnCreate;
	private Object defaultValue;
	private String defaultValueFormula;
	private boolean deprecatedAndHidden;
	private boolean dependentPicklist;
	private int digits;
	private boolean displayLocationInDecimal;
	private boolean encrypted;
	private boolean externalId;
	private String extraTypeInfo;
	private boolean filterable;
	private FilteredLookupInfoWrapper ilteredLookupInfo;
	private boolean groupable;
	private boolean highScaleNumber;
	private boolean htmlFormatted;
	private boolean idLookup;
	private String inlineHelpText;
	private String mask;
	private String maskType;
	private boolean nameField;
	private boolean namePointing;
	private boolean nillable;
	private boolean permissionable;
	private int precision;
	private boolean queryByDistance;
	private String referenceTargetField;
	private String relationshipName;
	private int relationshipOrder;
	private boolean restrictedDelete;
	private boolean restrictedPicklist;
	private int scale;
	private String soapType;
	private boolean sortable;
	private boolean unique;
	private boolean updateable;
	private boolean writeRequiresMasterRead;;

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

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getAggregatable()
	 */
	@Override
	public boolean getAggregatable() {
		return aggregatable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getAutoNumber()
	 */
	@Override
	public boolean getAutoNumber() {
		return autoNumber;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getByteLength()
	 */
	@Override
	public int getByteLength() {
		return byteLength;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getCalculated()
	 */
	@Override
	public boolean getCalculated() {
		return calculated;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getCalculatedFormula()
	 */
	@Override
	public String getCalculatedFormula() {
		return calculatedFormula;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getCascadeDelete()
	 */
	@Override
	public boolean getCascadeDelete() {
		return cascadeDelete;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getCaseSensitive()
	 */
	@Override
	public boolean getCaseSensitive() {
		return caseSensitive;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getCompoundFieldName()
	 */
	@Override
	public String getCompoundFieldName() {
		return compoundFieldName;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getControllerName()
	 */
	@Override
	public String getControllerName() {
		return controllerName;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getCreateable()
	 */
	@Override
	public boolean getCreateable() {
		return createable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getCustom()
	 */
	@Override
	public boolean getCustom() {
		return custom;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getDefaultedOnCreate()
	 */
	@Override
	public boolean getDefaultedOnCreate() {
		return deffaultedOnCreate;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getDefaultValue()
	 */
	@Override
	public Object getDefaultValue() {
		return defaultValue;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getDefaultValueFormula()
	 */
	@Override
	public String getDefaultValueFormula() {
		return defaultValueFormula;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getDependentPicklist()
	 */
	@Override
	public boolean getDependentPicklist() {
		return dependentPicklist;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getDeprecatedAndHidden()
	 */
	@Override
	public boolean getDeprecatedAndHidden() {
		return deprecatedAndHidden;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getDigits()
	 */
	@Override
	public int getDigits() {
		return digits;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getDisplayLocationInDecimal()
	 */
	@Override
	public boolean getDisplayLocationInDecimal() {
		return displayLocationInDecimal;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getEncrypted()
	 */
	@Override
	public boolean getEncrypted() {
		return encrypted;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getExternalId()
	 */
	@Override
	public boolean getExternalId() {
		return externalId;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getExtraTypeInfo()
	 */
	@Override
	public String getExtraTypeInfo() {
		return extraTypeInfo;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getFilterable()
	 */
	@Override
	public boolean getFilterable() {
		return filterable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getFilteredLookupInfo()
	 */
	@Override
	public FilteredLookupInfoWrapper getFilteredLookupInfo() {
		return ilteredLookupInfo;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getGroupable()
	 */
	@Override
	public boolean getGroupable() {
		return groupable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getHighScaleNumber()
	 */
	@Override
	public boolean getHighScaleNumber() {
		return highScaleNumber;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getHtmlFormatted()
	 */
	@Override
	public boolean getHtmlFormatted() {
		return htmlFormatted;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getIdLookup()
	 */
	@Override
	public boolean getIdLookup() {
		return idLookup;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getInlineHelpText()
	 */
	@Override
	public String getInlineHelpText() {
		return inlineHelpText;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getMask()
	 */
	@Override
	public String getMask() {
		return mask;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getMaskType()
	 */
	@Override
	public String getMaskType() {
		return maskType;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getNameField()
	 */
	@Override
	public boolean getNameField() {
		return nameField;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getNamePointing()
	 */
	@Override
	public boolean getNamePointing() {
		return namePointing;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getNillable()
	 */
	@Override
	public boolean getNillable() {
		return nillable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getPermissionable()
	 */
	@Override
	public boolean getPermissionable() {
		return permissionable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getPrecision()
	 */
	@Override
	public int getPrecision() {
		return precision;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getQueryByDistance()
	 */
	@Override
	public boolean getQueryByDistance() {
		return queryByDistance;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getReferenceTargetField()
	 */
	@Override
	public String getReferenceTargetField() {
		return referenceTargetField;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getRelationshipName()
	 */
	@Override
	public String getRelationshipName() {
		return relationshipName;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getRelationshipOrder()
	 */
	@Override
	public int getRelationshipOrder() {
		return relationshipOrder;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getRestrictedDelete()
	 */
	@Override
	public boolean getRestrictedDelete() {
		return restrictedDelete;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getRestrictedPicklist()
	 */
	@Override
	public boolean getRestrictedPicklist() {
		return restrictedPicklist;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getScale()
	 */
	@Override
	public int getScale() {
		return scale;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getSoapType()
	 */
	@Override
	public String getSoapType() {
		return soapType;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getSortable()
	 */
	@Override
	public boolean getSortable() {
		return sortable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getUnique()
	 */
	@Override
	public boolean getUnique() {
		return unique;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getUpdateable()
	 */
	@Override
	public boolean getUpdateable() {
		return updateable;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper#getWriteRequiresMasterRead()
	 */
	@Override
	public boolean getWriteRequiresMasterRead() {
		return writeRequiresMasterRead;
	}

	/**
	 * @param aggregatable セットする aggregatable
	 */
	public void setAggregatable(boolean aggregatable) {
		this.aggregatable = aggregatable;
	}

	/**
	 * @param autoNumber セットする autoNumber
	 */
	public void setAutoNumber(boolean autoNumber) {
		this.autoNumber = autoNumber;
	}

	/**
	 * @param byteLength セットする byteLength
	 */
	public void setByteLength(int byteLength) {
		this.byteLength = byteLength;
	}

	/**
	 * @param controllerName セットする controllerName
	 */
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	/**
	 * @param createable セットする createable
	 */
	public void setCreateable(boolean createable) {
		this.createable = createable;
	}

	/**
	 * @param compoundFieldName セットする compoundFieldName
	 */
	public void setCompoundFieldName(String compoundFieldName) {
		this.compoundFieldName = compoundFieldName;
	}

	/**
	 * @param calculated セットする calculated
	 */
	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
	}

	/**
	 * @param calculatedFormula セットする calculatedFormula
	 */
	public void setCalculatedFormula(String calculatedFormula) {
		this.calculatedFormula = calculatedFormula;
	}

	/**
	 * @param cascadeDelete セットする cascadeDelete
	 */
	public void setCascadeDelete(boolean cascadeDelete) {
		this.cascadeDelete = cascadeDelete;
	}

	/**
	 * @param caseSensitive セットする caseSensitive
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * @param custom セットする custom
	 */
	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	/**
	 * @param deffaultedOnCreate セットする deffaultedOnCreate
	 */
	public void setDeffaultedOnCreate(boolean deffaultedOnCreate) {
		this.deffaultedOnCreate = deffaultedOnCreate;
	}

	/**
	 * @param defaultValue セットする defaultValue
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @param defaultValueFormula セットする defaultValueFormula
	 */
	public void setDefaultValueFormula(String defaultValueFormula) {
		this.defaultValueFormula = defaultValueFormula;
	}

	/**
	 * @param deprecatedAndHidden セットする deprecatedAndHidden
	 */
	public void setDeprecatedAndHidden(boolean deprecatedAndHidden) {
		this.deprecatedAndHidden = deprecatedAndHidden;
	}

	/**
	 * @param dependentPicklist セットする dependentPicklist
	 */
	public void setDependentPicklist(boolean dependentPicklist) {
		this.dependentPicklist = dependentPicklist;
	}

	/**
	 * @param digits セットする digits
	 */
	public void setDigits(int digits) {
		this.digits = digits;
	}

	/**
	 * @param displayLocationInDecimal セットする displayLocationInDecimal
	 */
	public void setDisplayLocationInDecimal(boolean displayLocationInDecimal) {
		this.displayLocationInDecimal = displayLocationInDecimal;
	}

	/**
	 * @param encrypted セットする encrypted
	 */
	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	/**
	 * @param externalId セットする externalId
	 */
	public void setExternalId(boolean externalId) {
		this.externalId = externalId;
	}

	/**
	 * @param extraTypeInfo セットする extraTypeInfo
	 */
	public void setExtraTypeInfo(String extraTypeInfo) {
		this.extraTypeInfo = extraTypeInfo;
	}

	/**
	 * @param filterable セットする filterable
	 */
	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}

	/**
	 * @param ilteredLookupInfo セットする ilteredLookupInfo
	 */
	public void setIlteredLookupInfo(FilteredLookupInfoWrapper ilteredLookupInfo) {
		this.ilteredLookupInfo = ilteredLookupInfo;
	}

	/**
	 * @param groupable セットする groupable
	 */
	public void setGroupable(boolean groupable) {
		this.groupable = groupable;
	}

	/**
	 * @param highScaleNumber セットする highScaleNumber
	 */
	public void setHighScaleNumber(boolean highScaleNumber) {
		this.highScaleNumber = highScaleNumber;
	}

	/**
	 * @param htmlFormatted セットする htmlFormatted
	 */
	public void setHtmlFormatted(boolean htmlFormatted) {
		this.htmlFormatted = htmlFormatted;
	}

	/**
	 * @param idLookup セットする idLookup
	 */
	public void setIdLookup(boolean idLookup) {
		this.idLookup = idLookup;
	}

	/**
	 * @param inlineHelpText セットする inlineHelpText
	 */
	public void setInlineHelpText(String inlineHelpText) {
		this.inlineHelpText = inlineHelpText;
	}

	/**
	 * @param mask セットする mask
	 */
	public void setMask(String mask) {
		this.mask = mask;
	}

	/**
	 * @param maskType セットする maskType
	 */
	public void setMaskType(String maskType) {
		this.maskType = maskType;
	}

	/**
	 * @param nameField セットする nameField
	 */
	public void setNameField(boolean nameField) {
		this.nameField = nameField;
	}

	/**
	 * @param namePointing セットする namePointing
	 */
	public void setNamePointing(boolean namePointing) {
		this.namePointing = namePointing;
	}

	/**
	 * @param nillable セットする nillable
	 */
	public void setNillable(boolean nillable) {
		this.nillable = nillable;
	}

	/**
	 * @param permissionable セットする permissionable
	 */
	public void setPermissionable(boolean permissionable) {
		this.permissionable = permissionable;
	}

	/**
	 * @param precision セットする precision
	 */
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	/**
	 * @param queryByDistance セットする queryByDistance
	 */
	public void setQueryByDistance(boolean queryByDistance) {
		this.queryByDistance = queryByDistance;
	}

	/**
	 * @param referenceTargetField セットする referenceTargetField
	 */
	public void setReferenceTargetField(String referenceTargetField) {
		this.referenceTargetField = referenceTargetField;
	}

	/**
	 * @param relationshipName セットする relationshipName
	 */
	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}

	/**
	 * @param relationshipOrder セットする relationshipOrder
	 */
	public void setRelationshipOrder(int relationshipOrder) {
		this.relationshipOrder = relationshipOrder;
	}

	/**
	 * @param restrictedDelete セットする restrictedDelete
	 */
	public void setRestrictedDelete(boolean restrictedDelete) {
		this.restrictedDelete = restrictedDelete;
	}

	/**
	 * @param restrictedPicklist セットする restrictedPicklist
	 */
	public void setRestrictedPicklist(boolean restrictedPicklist) {
		this.restrictedPicklist = restrictedPicklist;
	}

	/**
	 * @param scale セットする scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * @param soapType セットする soapType
	 */
	public void setSoapType(String soapType) {
		this.soapType = soapType;
	}

	/**
	 * @param sortable セットする sortable
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/**
	 * @param unique セットする unique
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * @param updateable セットする updateable
	 */
	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	/**
	 * @param writeRequiresMasterRead セットする writeRequiresMasterRead
	 */
	public void setWriteRequiresMasterRead(boolean writeRequiresMasterRead) {
		this.writeRequiresMasterRead = writeRequiresMasterRead;
	}

}
