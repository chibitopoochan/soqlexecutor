package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public interface FieldsWrapper {
	/**
	 * {@link com.sforce.soap.partner.Field#getName()}をラップ
	 * @return 項目名
	 */
	public String getName();


	/**
	 * {@link com.sforce.soap.partner.Field#getLabel()}をラップ
	 * @return ラベル名
	 */
	public String getLabel();


	/**
	 * {@link com.sforce.soap.partner.Field#getLength()}をラップ
	 * @return サイズ
	 */
	public int getLength();


	/**
	 * {@link com.sforce.soap.partner.Field#getType()}をラップ
	 * @return 項目タイプ
	 */
	public String getType();

	/**
	 * {@link com.sforce.soap.partner.Field#getReferenceTo()}をラップ
	 * @return 参照先
	 */
	public String[] getReferenceTo();

	public boolean getAggregatable();

	public boolean getAutoNumber();

	public int getByteLength();

	public boolean getCalculated();

	public String getCalculatedFormula();

	public boolean getCascadeDelete();

	public boolean getCaseSensitive();

	public String getCompoundFieldName();

	public String getControllerName();

	public boolean getCreateable();

	public boolean getCustom();

	public boolean getDefaultedOnCreate();

	public Object getDefaultValue();

	public String getDefaultValueFormula();

	public boolean getDependentPicklist();

	public boolean getDeprecatedAndHidden();

	public int getDigits();

	public boolean getDisplayLocationInDecimal();

	public boolean getEncrypted();

	public boolean getExternalId();

	public String getExtraTypeInfo();

	public boolean getFilterable();

	public FilteredLookupInfoWrapper getFilteredLookupInfo();

	public boolean getGroupable();

	public boolean getHighScaleNumber();

	public boolean getHtmlFormatted();

	public boolean getIdLookup();

	public String getInlineHelpText();

	public String getMask();

	public String getMaskType();

	public boolean getNameField();

	public boolean getNamePointing();

	public boolean getNillable();

	public boolean getPermissionable();

	public int getPrecision();

	public boolean getQueryByDistance();

	public String getReferenceTargetField();

	public String getRelationshipName();

	public int getRelationshipOrder();

	public boolean getRestrictedDelete();

	public boolean getRestrictedPicklist();

	public int getScale();

	public String getSoapType();

	public boolean getSortable();

	public boolean getUnique();

	public boolean getUpdateable();

	public boolean getWriteRequiresMasterRead();

	/**
	 * {@link com.sforce.soap.partner.Field#getPicklistValues()}をラップ
	 * @return 選択肢一覧
	 */
	public PicklistEntryWrapper[] getPicklistValues();

}
