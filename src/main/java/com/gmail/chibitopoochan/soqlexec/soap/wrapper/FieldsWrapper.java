package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.sforce.soap.partner.Field;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class FieldsWrapper {
	private Field result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public FieldsWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.Field}をラップ
	 * @param result ラップ対象
	 */
	public FieldsWrapper(Field result) {
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

	/**
	 * {@link com.sforce.soap.partner.Field#getPicklistValues()}をラップ
	 * @return 選択肢一覧
	 */
	public PicklistEntryWrapper[] getPicklistValues() {
		return Arrays.stream(result.getPicklistValues())
				.map(p -> new PicklistEntryWrapper(p))
				.collect(Collectors.toList())
				.toArray(new PicklistEntryWrapper[0]);
	}

}
