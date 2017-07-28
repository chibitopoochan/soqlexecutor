package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import com.sforce.ws.bind.XmlObject;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class XmlObjectWrapper {
	private XmlObject result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public XmlObjectWrapper(){}

	/**
	 * {@link com.sforce.ws.bind.XmlObject}をラップ
	 * @param result ラップ対象
	 */
	public XmlObjectWrapper(XmlObject result) {
		setXmlObjectWrapper(result);
	}

	/**
	 * {@link com.sforce.ws.bind.XmlObject}をラップ
	 * @param result ラップ対象
	 */
	public void setXmlObjectWrapper(XmlObject result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.ws.bind.XmlObject#getChild(String)}をラップ
	 * @param name 項目名
	 * @return 参照先のオブジェクト
	 */
	public XmlObjectWrapper getChild(String name) {
		return new XmlObjectWrapper(result.getChild(name));
	}

	/**
	 * {@link com.sforce.ws.bind.XmlObject#getField(String)}をラップ
	 * @param name 項目名
	 * @return 値
	 */
	public String getField(String name) {
		return (String) result.getField(name);
	}

}
