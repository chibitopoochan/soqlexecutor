package com.gmail.chibitopoochan.soqlexec.soap.mock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QNameWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.XmlObjectWrapper;

public class SObjectWrapperMock extends SObjectWrapper {
	private Map<String, XmlObjectWrapper> childMap = new HashMap<>();
	private QNameWrapper name;
	private Object value;

	public void setValue(Object value) {
		this.value = value;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getField(java.lang.String)
	 */
	@Override
	public Object getField(String name) {
		if(childMap.containsKey(name)) {
			return childMap.get(name).getValue();
		} else {
			return null;
		}
	}

	public void addChild(XmlObjectWrapper value) {
		childMap.put(value.getName().getLocalPart(), value);
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getChildren()
	 */
	@Override
	public Iterator<XmlObjectWrapper> getChildren() {
		return childMap.values().iterator();
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getChild(java.lang.String)
	 */
	@Override
	public XmlObjectWrapper getChild(String name) {
		return childMap.get(name);
	}

	public void setName(QNameWrapper name){
		this.name = name;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getName()
	 */
	@Override
	public QNameWrapper getName() {
		return name;
	}

}
