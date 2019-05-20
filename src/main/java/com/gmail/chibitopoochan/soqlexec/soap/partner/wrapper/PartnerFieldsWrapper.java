package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FilteredLookupInfoWrapper;
import com.sforce.soap.partner.Field;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class PartnerFieldsWrapper implements FieldsWrapper {
	private Field result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public PartnerFieldsWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.Field}をラップ
	 * @param result ラップ対象
	 */
	public PartnerFieldsWrapper(Field result) {
		setFieldsWrapper(result);
	}

	/**
	 * {@link com.sforce.soap.partner.Field}をラップ
	 * @param result ラップ対象
	 */
	public void setFieldsWrapper(Field result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.soap.partner.Field#getName()}をラップ
	 * @return 項目名
	 */
	public String getName() {
		return result.getName();
	}


	/**
	 * {@link com.sforce.soap.partner.Field#getLabel()}をラップ
	 * @return ラベル名
	 */
	public String getLabel() {
		return result.getLabel();
	}


	/**
	 * {@link com.sforce.soap.partner.Field#getLength()}をラップ
	 * @return サイズ
	 */
	public int getLength() {
		return result.getLength();
	}


	/**
	 * {@link com.sforce.soap.partner.Field#getType()}をラップ
	 * @return 項目タイプ
	 */
	public String getType() {
		return result.getType().name();
	}

	/**
	 * {@link com.sforce.soap.partner.Field#getReferenceTo()}をラップ
	 * @return 参照先
	 */
	public String[] getReferenceTo() {
		return result.getReferenceTo();
	}

	public boolean getAggregatable() {
		return result.getAggregatable();
	}

	public boolean getAutoNumber() {
		return result.getAutoNumber();
	}

	public int getByteLength() {
		return result.getByteLength();
	}

	public boolean getCalculated() {
		return result.getCalculated();
	}

	public String getCalculatedFormula() {
		return result.getCalculatedFormula();
	}

	public boolean getCascadeDelete() {
		return result.getCascadeDelete();
	}

	public boolean getCaseSensitive() {
		return result.getCaseSensitive();
	}

	public String getCompoundFieldName() {
		return result.getCompoundFieldName();
	}

	public String getControllerName() {
		return result.getControllerName();
	}

	public boolean getCreateable() {
		return result.getCreateable();
	}

	public boolean getCustom() {
		return result.getCustom();
	}

	public boolean getDefaultedOnCreate() {
		return result.getDefaultedOnCreate();
	}

	public Object getDefaultValue() {
		return result.getDefaultValue();
	}

	public String getDefaultValueFormula() {
		return result.getDefaultValueFormula();
	}

	public boolean getDependentPicklist() {
		return result.getDependentPicklist();
	}

	public boolean getDeprecatedAndHidden() {
		return result.getDeprecatedAndHidden();
	}

	public int getDigits() {
		return result.getDigits();
	}

	public boolean getDisplayLocationInDecimal() {
		return result.getDisplayLocationInDecimal();
	}

	public boolean getEncrypted() {
		return result.getEncrypted();
	}

	public boolean getExternalId() {
		return result.getExternalId();
	}

	public String getExtraTypeInfo() {
		return result.getExtraTypeInfo();
	}

	public boolean getFilterable() {
		return result.getFilterable();
	}

	public FilteredLookupInfoWrapper getFilteredLookupInfo() {
		if(result.getFilteredLookupInfo() == null) {
			return null;
		} else {
			return new PartnerFilteredLookupInfoWrapper(result.getFilteredLookupInfo());
		}
	}

	public boolean getGroupable() {
		return result.getGroupable();
	}

	public boolean getHighScaleNumber() {
		return result.getHighScaleNumber();
	}

	public boolean getHtmlFormatted() {
		return result.getHtmlFormatted();
	}

	public boolean getIdLookup() {
		return result.getIdLookup();
	}

	public String getInlineHelpText() {
		return result.getInlineHelpText();
	}

	public String getMask() {
		return result.getMask();
	}

	public String getMaskType() {
		return result.getMaskType();
	}

	public boolean getNameField() {
		return result.getNameField();
	}

	public boolean getNamePointing() {
		return result.getNamePointing();
	}

	public boolean getNillable() {
		return result.getNillable();
	}

	public boolean getPermissionable() {
		return result.getPermissionable();
	}

	public int getPrecision() {
		return result.getPrecision();
	}

	public boolean getQueryByDistance() {
		return result.getQueryByDistance();
	}

	public String getReferenceTargetField() {
		return result.getReferenceTargetField();
	}

	public String getRelationshipName() {
		return result.getRelationshipName();
	}

	public int getRelationshipOrder() {
		return result.getRelationshipOrder();
	}

	public boolean getRestrictedDelete() {
		return result.getRestrictedDelete();
	}

	public boolean getRestrictedPicklist() {
		return result.getRestrictedPicklist();
	}

	public int getScale() {
		return result.getScale();
	}

	public String getSoapType() {
		return result.getSoapType().name();
	}

	public boolean getSortable() {
		return result.getSortable();
	}

	public boolean getUnique() {
		return result.getUnique();
	}

	public boolean getUpdateable() {
		return result.getUpdateable();
	}

	public boolean getWriteRequiresMasterRead() {
		return result.getWriteRequiresMasterRead();
	}

	/**
	 * {@link com.sforce.soap.partner.Field#getPicklistValues()}をラップ
	 * @return 選択肢一覧
	 */
	public PartnerPicklistEntryWrapper[] getPicklistValues() {
		return Arrays.stream(result.getPicklistValues())
				.map(p -> new PartnerPicklistEntryWrapper(p))
				.collect(Collectors.toList())
				.toArray(new PartnerPicklistEntryWrapper[0]);
	}

}
