package com.gmail.chibitopoochan.soqlexec.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper;

/**
 * SObject項目の設定値の内、使用する値を保持する
 */
public class FieldMetaInfo {
	// 外部に提供するメタ情報
	private String name;
	private String label;
	private int length;
	private String type;
	private List<String> picklist = new LinkedList<>();
	private List<String> referenceToList = new LinkedList<>();
	private Map<String,String> metaMap = new HashMap<>();

	/**
	 * 最低限の設定値を持つメタ情報を生成
	 * @param name 項目名
	 * @param label ラベル名
	 * @param length サイズ
	 * @param type 項目タイプ
	 */
	public FieldMetaInfo(String name, String label, int length, String type) {
		this.setName(name);
		this.setLabel(label);
		this.setLength(length);
		this.setType(type);
	}

	/**
	 * 最大限の設定値を持ったメタ情報
	 * @param result フィールド情報
	 */
	public FieldMetaInfo(FieldsWrapper f) {
		this(f.getName(), f.getLabel(), f.getLength(), f.getType());
		metaMap.put("Name", name);
		metaMap.put("Label", label);
		metaMap.put("Length", Integer.toString(length));
		metaMap.put("Type", type);
		metaMap.put("CalculatedFormula", f.getCalculatedFormula());
		metaMap.put("CompoundFieldName", f.getCompoundFieldName());
		metaMap.put("ControllerName", f.getControllerName());
		metaMap.put("DefaultValueFormula", f.getDefaultValueFormula());
		metaMap.put("ExtraTypeInfo", f.getExtraTypeInfo());
		metaMap.put("InlineHelpText", f.getInlineHelpText());
		metaMap.put("Label", f.getLabel());
		metaMap.put("Mask", f.getMask());
		metaMap.put("MaskType", f.getMaskType());
		metaMap.put("ReferenceTargetField", f.getReferenceTargetField());
		metaMap.put("RelationshipName", f.getRelationshipName());
		metaMap.put("ReferenceTo", Stream.of(f.getReferenceTo()).collect(Collectors.joining(",")));
		metaMap.put("Aggregatable", Boolean.toString(f.getAggregatable()));
		metaMap.put("AutoNumber", Boolean.toString(f.getAutoNumber()));
		metaMap.put("ByteLength", Integer.toString(f.getByteLength()));
		metaMap.put("Calculated", Boolean.toString(f.getCalculated()));
		metaMap.put("CascadeDelete", Boolean.toString(f.getCascadeDelete()));
		metaMap.put("CaseSensitive", Boolean.toString(f.getCaseSensitive()));
		metaMap.put("Createable", Boolean.toString(f.getCreateable()));
		metaMap.put("Custom", Boolean.toString(f.getCustom()));
		metaMap.put("DefaultedOnCreate", Boolean.toString(f.getDefaultedOnCreate()));
		metaMap.put("DefaultValue", Optional.ofNullable(f.getDefaultValue()).orElse("").toString());
		metaMap.put("DependentPicklist", Boolean.toString(f.getDependentPicklist()));
		metaMap.put("DeprecatedAndHidden", Boolean.toString(f.getDeprecatedAndHidden()));
		metaMap.put("Digits", Integer.toString(f.getDigits()));
		metaMap.put("DisplayLocationInDecimal", Boolean.toString(f.getDisplayLocationInDecimal()));
		metaMap.put("Encrypted", Boolean.toString(f.getEncrypted()));
		metaMap.put("ExternalId", Boolean.toString(f.getExternalId()));
		metaMap.put("Filterable", Boolean.toString(f.getFilterable()));

		if(f.getFilteredLookupInfo() != null) {
			metaMap.put("FilteredLookupInfo.OptionalFilter", Boolean.toString(f.getFilteredLookupInfo().isOptionalFilter()));
			metaMap.put("FilteredLookupInfo.Dependent", Boolean.toString(f.getFilteredLookupInfo().isDependent()));
			metaMap.put("FilteredLookupInfo.ControllingFields", f.getFilteredLookupInfo().getControllingFields() != null ?
					Stream.of(f.getFilteredLookupInfo().getControllingFields()).collect(Collectors.joining(",")) : "");
		} else {
			metaMap.put("FilteredLookupInfo.OptionalFilter", Boolean.toString(false));
			metaMap.put("FilteredLookupInfo.Dependent", Boolean.toString(false));
			metaMap.put("FilteredLookupInfo.ControllingFields", "");
		}

		metaMap.put("Groupable", Boolean.toString(f.getGroupable()));
		metaMap.put("HighScaleNumber", Boolean.toString(f.getHighScaleNumber()));
		metaMap.put("HtmlFormatted", Boolean.toString(f.getHtmlFormatted()));
		metaMap.put("IdLookup", Boolean.toString(f.getIdLookup()));
		metaMap.put("NameField", Boolean.toString(f.getNameField()));
		metaMap.put("NamePointing", Boolean.toString(f.getNamePointing()));
		metaMap.put("Nillable", Boolean.toString(f.getNillable()));
		metaMap.put("Permissionable", Boolean.toString(f.getPermissionable()));

		if(f.getPicklistValues() != null) {
			metaMap.put("PicklistValues.Active", Arrays.asList(
					f.getPicklistValues()).stream()
					.filter(e -> e.isActive())
					.map(e -> e.getValue())
					.collect(Collectors.joining(",")));

			metaMap.put("PicklistValues.InActive", Arrays.asList(
					f.getPicklistValues()).stream()
					.filter(e -> !e.isActive())
					.map(e -> e.getValue())
					.collect(Collectors.joining(",")));
		} else {
			metaMap.put("PicklistValues.Active", "");
			metaMap.put("PicklistValues.InActive", "");
		}

		metaMap.put("Precision", Integer.toString(f.getPrecision()));
		metaMap.put("QueryByDistance", Boolean.toString(f.getQueryByDistance()));
		metaMap.put("RelationshipOrder", Integer.toString(f.getRelationshipOrder()));
		metaMap.put("RestrictedDelete", Boolean.toString(f.getRestrictedDelete()));
		metaMap.put("RestrictedPicklist", Boolean.toString(f.getRestrictedPicklist()));
		metaMap.put("Scale", Integer.toString(f.getScale()));
		metaMap.put("SoapType", f.getSoapType());
		metaMap.put("Sortable", Boolean.toString(f.getSortable()));
		metaMap.put("Unique", Boolean.toString(f.getUnique()));
		metaMap.put("Updateable", Boolean.toString(f.getUpdateable()));
		metaMap.put("WriteRequiresMasterRead", Boolean.toString(f.getWriteRequiresMasterRead()));

	}

	/**
	 * 項目名の取得
	 * @return 項目名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 項目名の設定
	 * @param name 項目名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ラベル名の取得
	 * @return ラベル名
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * ラベル名の設定
	 * @param label ラベル名
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * サイズの取得
	 * @return length サイズ（単位は項目の種類に依存）
	 */
	public int getLength() {
		return length;
	}

	/**
	 * サイズの設定
	 * @param length サイズ（単位は項目の種類に依存）
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * 選択肢の取得
	 * @return picklist 選択肢一覧
	 */
	public List<String> getPicklist() {
		return picklist;
	}

	/**
	 * 選択肢の設定
	 * @param picklist 選択肢一覧
	 */
	public void setPicklist(List<String> picklist) {
		this.picklist = picklist;
	}

	/**
	 * 参照先の取得
	 * @return referenceToList 参照先一覧
	 */
	public List<String> getReferenceToList() {
		return referenceToList;
	}

	/**
	 * 参照先の設定
	 * @param referenceToList 参照先一覧
	 */
	public void setReferenceToList(List<String> referenceToList) {
		this.referenceToList = referenceToList;
	}

	/**
	 * 項目タイプの取得
	 * @return 項目タイプ
	 */
	public String getType() {
		return type;
	}

	/**
	 * 項目タイプの設定
	 * @param type 項目タイプ
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * メタ情報の取得
	 * @return メタ情報
	 */
	public Map<String, String> getMetaInfo() {
		return metaMap;
	}

}
