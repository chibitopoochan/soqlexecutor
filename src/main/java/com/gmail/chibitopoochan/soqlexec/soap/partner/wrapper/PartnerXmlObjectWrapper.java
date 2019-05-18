package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper;
import com.sforce.ws.bind.XmlObject;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 * Nullの可能性がある項目はOptionalで返します。
 */
public class PartnerXmlObjectWrapper implements ObjectWrapper {
	private XmlObject result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public PartnerXmlObjectWrapper(){}

	/**
	 * {@link com.sforce.ws.bind.XmlObject}をラップ
	 * @param result ラップ対象
	 */
	public PartnerXmlObjectWrapper(XmlObject result) {
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
		return Optional.ofNullable(result.getChild(name)).map(c -> new PartnerXmlObjectWrapper(c));
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
	public Iterator<ObjectWrapper> getChildren() {
		List<ObjectWrapper> children = new LinkedList<>();
		result.getChildren().forEachRemaining(c -> children.add(new PartnerXmlObjectWrapper(c)));
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
	public PartnerQNameWrapper getName() {
		return new PartnerQNameWrapper(result.getName());
	}

}
