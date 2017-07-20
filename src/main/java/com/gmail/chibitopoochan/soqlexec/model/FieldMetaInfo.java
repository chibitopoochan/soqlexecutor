package com.gmail.chibitopoochan.soqlexec.model;

import java.util.List;

/**
 * SObject項目の設定値の内、使用する値を保持する
 */
public class FieldMetaInfo {
	// 外部に提供するメタ情報
	private String name;
	private String label;
	private int length;
	private String type;
	private List<String> picklist;
	private List<String> referenceToList;

	/**
	 * 初期値を持つメタ情報を生成
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

}
