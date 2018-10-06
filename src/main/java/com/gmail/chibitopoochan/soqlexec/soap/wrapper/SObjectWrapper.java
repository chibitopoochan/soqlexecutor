package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.sforce.soap.partner.sobject.SObject;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 * Nullの可能性がある項目はOptionalで返します。
 */
public class SObjectWrapper implements ObjectWrapper {
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
	public Optional<ObjectWrapper> getChild(String name) {
		return Optional.of(result.getChild(name)).map(c -> (ObjectWrapper)new XmlObjectWrapper(c));
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getField(String)}をラップ
	 * @param name 項目名
	 * @return 値
	 */
	public Optional<Object> getField(String name) {
		return Optional.ofNullable(result.getField(name));
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
	 * {@link com.sforce.soap.partner.sobject.SObject#getName()}をラップ
	 * @return 名前
	 */
	public QNameWrapper getName() {
		return new QNameWrapper(result.getName());
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getValue()}をラップ
	 * @return 値
	 */
	public Optional<Object> getValue() {
		return Optional.ofNullable(result.getValue());
	}
}
