package com.gmail.chibitopoochan.soqlexec.soap.mock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QNameWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.XmlObjectWrapper;

public class XmlObjectWrapperMock extends XmlObjectWrapper {
	private Map<String, XmlObjectWrapper> childMap = new HashMap<>();
	private QNameWrapper name;
	private Object value;

	public void setValue(Object value) {
		this.value = value;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.XmlObjectWrapper#getValue()
	 */
	@Override
	public Optional<Object> getValue() {
		return Optional.ofNullable(value);
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getField(java.lang.String)
	 */
	@Override
	public Optional<Object> getField(String name) {
		return childMap.get(name).getValue();
	}

	public void addChild(XmlObjectWrapper value) {
		childMap.put(value.getName().getLocalPart(), value);
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.XmlObjectWrapper#getChildren()
	 */
	@Override
	public Iterator<XmlObjectWrapper> getChildren() {
		return childMap.values().iterator();
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper#getChild(java.lang.String)
	 */
	@Override
	public Optional<ObjectWrapper> getChild(String name) {
		return Optional.ofNullable(childMap.get(name));
	}

	public void setName(QNameWrapper name) {
		this.name = name;
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.XmlObjectWrapper#getName()
	 */
	@Override
	public QNameWrapper getName() {
		return name;
	}

}
