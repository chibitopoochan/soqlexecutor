package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.sforce.ws.bind.XmlObject;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 * Nullの可能性がある項目はOptionalで返します。
 */
public class XmlObjectWrapper implements ObjectWrapper {
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

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper#getChild(java.lang.String)
	 */
	@Override
	public Optional<ObjectWrapper> getChild(String name) {
		return Optional.of(result.getChild(name)).map(c -> new XmlObjectWrapper(c));
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper#getField(java.lang.String)
	 */
	@Override
	public Optional<Object> getField(String name) {
		return Optional.ofNullable(result.getField(name));
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper#getChildren()
	 */
	@Override
	public Iterator<XmlObjectWrapper> getChildren() {
		List<XmlObjectWrapper> children = new LinkedList<>();
		result.getChildren().forEachRemaining(c -> children.add(new XmlObjectWrapper(c)));
		return children.iterator();
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper#getValue()
	 */
	@Override
	public Optional<Object> getValue() {
		return Optional.ofNullable(result.getValue());
	}

	/* (非 Javadoc)
	 * @see com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper#getName()
	 */
	@Override
	public QNameWrapper getName() {
		return new QNameWrapper(result.getName());
	}

}
