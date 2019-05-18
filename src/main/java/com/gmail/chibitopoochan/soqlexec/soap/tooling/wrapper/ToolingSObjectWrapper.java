package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper;
import com.sforce.soap.tooling.sobject.SObject;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 * Nullの可能性がある項目はOptionalで返します。
 */
public class ToolingSObjectWrapper implements ObjectWrapper {
	private SObject result;
	private Method[] methods;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public ToolingSObjectWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject}をラップ
	 * @param result ラップ対象
	 */
	public ToolingSObjectWrapper(SObject result) {
		setSObjectWrapper(result);
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject}をラップ
	 * @param result ラップ対象
	 */
	public void setSObjectWrapper(SObject result) {
		this.result = result;
		methods = result.getClass().getDeclaredMethods();
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getChild(String)}をラップ
	 * @param name 項目名
	 * @return 参照先オブジェクト
	 */
	public Optional<ObjectWrapper> getChild(String name) {
		return Optional.empty();
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getField(String)}をラップ
	 * @param name 項目名
	 * @return 値
	 */
	public Optional<Object> getField(String name) {
		for(Method m : methods){
			if(m.getName().toLowerCase().equals("get"+name.toLowerCase())) {
				try {
					return Optional.ofNullable(m.invoke(result));
				} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getChildren()}をラップ
	 * @return 子要素一覧
	 */
	public Iterator<ObjectWrapper> getChildren() {
		List<ObjectWrapper> children = new LinkedList<>();
		return children.iterator();
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getName()}をラップ
	 * @return 名前
	 */
	public ToolingQNameWrapper getName() {
		return null;
	}

	/**
	 * {@link com.sforce.soap.partner.sobject.SObject#getValue()}をラップ
	 * @return 値
	 */
	public Optional<Object> getValue() {
		return null;
	}
}
