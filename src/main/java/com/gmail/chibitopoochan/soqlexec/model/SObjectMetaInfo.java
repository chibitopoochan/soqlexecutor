package com.gmail.chibitopoochan.soqlexec.model;

/**
 * SObject設定値の内、使用する値を保持する
 */
public class SObjectMetaInfo {
	// 外部に提供するメタ情報
	private String name;
	private String label;
	private String keyPrefix;

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

}
