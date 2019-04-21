package com.gmail.chibitopoochan.soqlexec.model;

import java.util.HashMap;
import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper;

/**
 * SObject設定値の内、使用する値を保持する
 */
public class SObjectMetaInfo {
	// 外部に提供するメタ情報
	private String name;
	private String label;
	private String keyPrefix;
	private Map<String,String> metaMap;

	/**
	 * 最低限の設定値を持ったメタ情報
	 * @param name オブジェクト名
	 * @param label ラベル
	 * @param keyPrefix プレフィックス
	 */
	public SObjectMetaInfo(String name, String label, String keyPrefix) {
		setName(name);
		setLabel(label);
		setKeyPrefix(keyPrefix);
	}

	/**
	 * 最大限の設定値を持ったメタ情報
	 * @param result オブジェクト情報
	 */
	public SObjectMetaInfo(DescribeGlobalSObjectResultWrapper result) {
		this(result.getName(), result.getLabel(), result.getKeyPrefix());
		metaMap = new HashMap<>();
		metaMap.put("Name", result.getName());
		metaMap.put("Label", result.getLabel());
		metaMap.put("KeyPrefix", result.getKeyPrefix());
		metaMap.put("LabelPlural", result.getLabelPlural());
		metaMap.put("Activateable", Boolean.toString(result.getActivateable()));
		metaMap.put("Custom", Boolean.toString(result.getCustom()));
		metaMap.put("CustomSetting", Boolean.toString(result.getCustomSetting()));
		metaMap.put("Deletable", Boolean.toString(result.getDeletable()));
		metaMap.put("DeprecatedAndHidden", Boolean.toString(result.getDeprecatedAndHidden()));
		metaMap.put("FeedEnabled", Boolean.toString(result.getFeedEnabled()));
		metaMap.put("HasSubtypes", Boolean.toString(result.getHasSubtypes()));
		metaMap.put("IdEnabled", Boolean.toString(result.getIdEnabled()));
		metaMap.put("IsSubtype", Boolean.toString(result.getIsSubtype()));
		metaMap.put("Layoutable", Boolean.toString(result.getLayoutable()));
		metaMap.put("Mergeable", Boolean.toString(result.getMergeable()));
		metaMap.put("MruEnabled", Boolean.toString(result.getMruEnabled()));
		metaMap.put("Queryable", Boolean.toString(result.getQueryable()));
		metaMap.put("Replicateable", Boolean.toString(result.getReplicateable()));
		metaMap.put("Retrieveable", Boolean.toString(result.getRetrieveable()));
		metaMap.put("Searchable", Boolean.toString(result.getSearchable()));
		metaMap.put("Triggerable", Boolean.toString(result.getTriggerable()));
		metaMap.put("Undeletable", Boolean.toString(result.getUndeletable()));
		metaMap.put("Updateable", Boolean.toString(result.getUpdateable()));
	}

	/**
	 * オブジェクト名の取得
	 * @return オブジェクト名
	 */
	public String getName() {
		return name;
	}

	/**
	 * オブジェクト名の設定
	 * @param name オブジェクト名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ラベルの取得
	 * @return ラベル
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * ラベルの設定
	 * @param label ラベル
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * プレフィックスの取得
	 * @return プレフィックス
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * プレフィックスの設定
	 * @param keyPrefix プレフィックス
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	/**
	 * メタ情報の取得
	 * @return メタ情報
	 */
	public Map<String, String> getMetaInfo() {
		return metaMap;
	}


}
