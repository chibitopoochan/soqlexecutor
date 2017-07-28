package com.gmail.chibitopoochan.soqlexec.soap.mock;

import java.util.HashMap;
import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.XmlObjectWrapper;

public class SObjectWrapperMock extends SObjectWrapper {
	private Map<String, XmlObjectWrapper> childMap = new HashMap<>();
	private Map<String, String> fieldMap = new HashMap<>();

	public void putChild(String key, XmlObjectWrapper value) {
		childMap.put(key, value);
	}

	public void putField(String key, String value) {
		fieldMap.put(key, value);
	}

	public Map<String, XmlObjectWrapper> getChild() {
		return childMap;
	}

	public Map<String, String> getField() {
		return fieldMap;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getChild(java.lang.String)
	 */
	@Override
	public XmlObjectWrapper getChild(String name) {
		return childMap.get(name);
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getField(java.lang.String)
	 */
	@Override
	public String getField(String name) {
		return fieldMap.get(name);
	}

}
