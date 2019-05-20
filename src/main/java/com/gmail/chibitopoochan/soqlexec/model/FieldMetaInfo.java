package com.gmail.chibitopoochan.soqlexec.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper;

/**
 * SObject項目の設定値の内、使用する値を保持する
 */
public class FieldMetaInfo {
	public enum Type {
		Name,Label,Length,Type,CalculatedFormula,CompoundFieldName,ControllerName,DefaultValueFormula,ExtraTypeInfo,InlineHelpText,Mask,MaskType,ReferenceTargetField,RelationshipName,ReferenceTo,Aggregatable,AutoNumber,ByteLength,Calculated,CascadeDelete,CaseSensitive,Createable,Custom,DefaultedOnCreate,DefaultValue,DependentPicklist,DeprecatedAndHidden,Digits,DisplayLocationInDecimal,Encrypted,ExternalId,Filterable,FilteredLookupInfo_OptionalFilter,FilteredLookupInfo_Dependent,FilteredLookupInfo_ControllingFields,Groupable,HighScaleNumber,HtmlFormatted,IdLookup,NameField,NamePointing,Nillable,Permissionable,PicklistValues_Active,PicklistValues_InActive,Precision,QueryByDistance,RelationshipOrder,RestrictedDelete,RestrictedPicklist,Scale,SoapType,Sortable,Unique,Updateable,WriteRequiresMasterRead
	}
	// 外部に提供するメタ情報
	private String name;
	private String label;
	private int length;
	private String type;
	private Map<Type,String> metaMap = new HashMap<>();

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
		metaMap.put(Type.Name, name);
		metaMap.put(Type.Label, label);
		metaMap.put(Type.Length, Integer.toString(length));
		metaMap.put(Type.Type, type);
		metaMap.put(Type.CalculatedFormula, f.getCalculatedFormula());
		metaMap.put(Type.CompoundFieldName, f.getCompoundFieldName());
		metaMap.put(Type.ControllerName, f.getControllerName());
		metaMap.put(Type.DefaultValueFormula, f.getDefaultValueFormula());
		metaMap.put(Type.ExtraTypeInfo, f.getExtraTypeInfo());
		metaMap.put(Type.InlineHelpText, f.getInlineHelpText());
		metaMap.put(Type.Label, f.getLabel());
		metaMap.put(Type.Mask, f.getMask());
		metaMap.put(Type.MaskType, f.getMaskType());
		metaMap.put(Type.ReferenceTargetField, f.getReferenceTargetField());
		metaMap.put(Type.RelationshipName, f.getRelationshipName());
		metaMap.put(Type.ReferenceTo, Stream.of(f.getReferenceTo()).collect(Collectors.joining(",")));
		metaMap.put(Type.Aggregatable, Boolean.toString(f.getAggregatable()));
		metaMap.put(Type.AutoNumber, Boolean.toString(f.getAutoNumber()));
		metaMap.put(Type.ByteLength, Integer.toString(f.getByteLength()));
		metaMap.put(Type.Calculated, Boolean.toString(f.getCalculated()));
		metaMap.put(Type.CascadeDelete, Boolean.toString(f.getCascadeDelete()));
		metaMap.put(Type.CaseSensitive, Boolean.toString(f.getCaseSensitive()));
		metaMap.put(Type.Createable, Boolean.toString(f.getCreateable()));
		metaMap.put(Type.Custom, Boolean.toString(f.getCustom()));
		metaMap.put(Type.DefaultedOnCreate, Boolean.toString(f.getDefaultedOnCreate()));
		metaMap.put(Type.DefaultValue, Optional.ofNullable(f.getDefaultValue()).orElse("").toString());
		metaMap.put(Type.DependentPicklist, Boolean.toString(f.getDependentPicklist()));
		metaMap.put(Type.DeprecatedAndHidden, Boolean.toString(f.getDeprecatedAndHidden()));
		metaMap.put(Type.Digits, Integer.toString(f.getDigits()));
		metaMap.put(Type.DisplayLocationInDecimal, Boolean.toString(f.getDisplayLocationInDecimal()));
		metaMap.put(Type.Encrypted, Boolean.toString(f.getEncrypted()));
		metaMap.put(Type.ExternalId, Boolean.toString(f.getExternalId()));
		metaMap.put(Type.Filterable, Boolean.toString(f.getFilterable()));

		if(f.getFilteredLookupInfo() != null) {
			metaMap.put(Type.FilteredLookupInfo_OptionalFilter, Boolean.toString(f.getFilteredLookupInfo().isOptionalFilter()));
			metaMap.put(Type.FilteredLookupInfo_Dependent, Boolean.toString(f.getFilteredLookupInfo().isDependent()));
			metaMap.put(Type.FilteredLookupInfo_ControllingFields, f.getFilteredLookupInfo().getControllingFields() != null ?
					Stream.of(f.getFilteredLookupInfo().getControllingFields()).collect(Collectors.joining(",")) : "");
		} else {
			metaMap.put(Type.FilteredLookupInfo_OptionalFilter, Boolean.toString(false));
			metaMap.put(Type.FilteredLookupInfo_Dependent, Boolean.toString(false));
			metaMap.put(Type.FilteredLookupInfo_ControllingFields, "");
		}

		metaMap.put(Type.Groupable, Boolean.toString(f.getGroupable()));
		metaMap.put(Type.HighScaleNumber, Boolean.toString(f.getHighScaleNumber()));
		metaMap.put(Type.HtmlFormatted, Boolean.toString(f.getHtmlFormatted()));
		metaMap.put(Type.IdLookup, Boolean.toString(f.getIdLookup()));
		metaMap.put(Type.NameField, Boolean.toString(f.getNameField()));
		metaMap.put(Type.NamePointing, Boolean.toString(f.getNamePointing()));
		metaMap.put(Type.Nillable, Boolean.toString(f.getNillable()));
		metaMap.put(Type.Permissionable, Boolean.toString(f.getPermissionable()));

		if(f.getPicklistValues() != null) {
			metaMap.put(Type.PicklistValues_Active, Arrays.asList(
					f.getPicklistValues()).stream()
					.filter(e -> e.isActive())
					.map(e -> e.getValue())
					.collect(Collectors.joining(",")));

			metaMap.put(Type.PicklistValues_InActive, Arrays.asList(
					f.getPicklistValues()).stream()
					.filter(e -> !e.isActive())
					.map(e -> e.getValue())
					.collect(Collectors.joining(",")));
		} else {
			metaMap.put(Type.PicklistValues_Active, "");
			metaMap.put(Type.PicklistValues_InActive, "");
		}

		metaMap.put(Type.Precision, Integer.toString(f.getPrecision()));
		metaMap.put(Type.QueryByDistance, Boolean.toString(f.getQueryByDistance()));
		metaMap.put(Type.RelationshipOrder, Integer.toString(f.getRelationshipOrder()));
		metaMap.put(Type.RestrictedDelete, Boolean.toString(f.getRestrictedDelete()));
		metaMap.put(Type.RestrictedPicklist, Boolean.toString(f.getRestrictedPicklist()));
		metaMap.put(Type.Scale, Integer.toString(f.getScale()));
		metaMap.put(Type.SoapType, f.getSoapType());
		metaMap.put(Type.Sortable, Boolean.toString(f.getSortable()));
		metaMap.put(Type.Unique, Boolean.toString(f.getUnique()));
		metaMap.put(Type.Updateable, Boolean.toString(f.getUpdateable()));
		metaMap.put(Type.WriteRequiresMasterRead, Boolean.toString(f.getWriteRequiresMasterRead()));

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
	public Map<Type, String> getMetaInfo() {
		return metaMap;
	}

}
