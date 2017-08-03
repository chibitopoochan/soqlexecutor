package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
	public Object getField(String name) {
		return result.getField(name);
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getChildren()}をラップ
	 * @return 子要素一覧
	 */
	public Iterator<XmlObjectWrapper> getChildren() {
		List<XmlObjectWrapper> children = new LinkedList<>();
		result.getChildren().forEachRemaining(c -> children.add(new XmlObjectWrapper(c)));
		return children.iterator();
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getValue()}をラップ
	 * @return 値
	 */
	public Object getValue() {
		return result.getValue();
	}

	/**
	 * {@link com.sforce.ws.bind.XmlObject#getName()}をラップ
	 * @return 名前
	 */
	public QNameWrapper getName() {
		return new QNameWrapper(result.getName());
	}

}
