package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import com.sforce.soap.partner.sobject.SObject;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class SObjectWrapper {
	private SObject result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public SObjectWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject}をラップ
	 * @param result ラップ対象
	 */
	public SObjectWrapper(SObject result) {
		setSObjectWrapper(result);
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject}をラップ
	 * @param result ラップ対象
	 */
	public void setSObjectWrapper(SObject result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getChild(String)}をラップ
	 * @param name 項目名
	 * @return 参照先オブジェクト
	 */
	public XmlObjectWrapper getChild(String name) {
		return new XmlObjectWrapper(result.getChild(name));
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getField(String)}をラップ
	 * @param name 項目名
	 * @return 値
	 */
	public String getField(String name) {
		return (String)result.getField(name);
	}

}
