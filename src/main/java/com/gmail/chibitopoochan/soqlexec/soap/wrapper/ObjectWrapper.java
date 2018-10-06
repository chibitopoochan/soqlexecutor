package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import java.util.Iterator;
import java.util.Optional;

public interface ObjectWrapper {

	/**
	 * {@link com.sforce.ws.bind.XmlObject#getChild(String)}をラップ
	 * @param name 項目名
	 * @return 参照先のオブジェクト
	 */
	Optional<ObjectWrapper> getChild(String name);

	/**
	 * {@link com.sforce.ws.bind.XmlObject#getField(String)}をラップ
	 * @param name 項目名
	 * @return 値
	 */
	Optional<Object> getField(String name);

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getChildren()}をラップ
	 * @return 子要素一覧
	 */
	Iterator<XmlObjectWrapper> getChildren();

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getValue()}をラップ
	 * @return 値
	 */
	Optional<Object> getValue();

	/**
	 * {@link com.sforce.ws.bind.XmlObject#getName()}をラップ
	 * @return 名前
	 */
	QNameWrapper getName();

}