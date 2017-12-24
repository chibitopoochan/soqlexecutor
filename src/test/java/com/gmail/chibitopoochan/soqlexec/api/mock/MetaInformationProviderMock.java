package com.gmail.chibitopoochan.soqlexec.api.mock;

import java.util.List;

import com.gmail.chibitopoochan.soqlexec.model.FieldMetaInfo;
import com.gmail.chibitopoochan.soqlexec.model.SObjectMetaInfo;
import com.gmail.chibitopoochan.soqlexec.soap.MetaInformationProvider;
import com.sforce.ws.ConnectionException;

public class MetaInformationProviderMock extends MetaInformationProvider {

	private List<SObjectMetaInfo> objList;
	private List<FieldMetaInfo> fieldList;

	public void setSObjectList(List<SObjectMetaInfo> info) {
		this.objList = info;
	}

	public void setFieldList(List<FieldMetaInfo> info) {
		this.fieldList = info;
	}

	@Override
	public List<SObjectMetaInfo> getSObjectList() throws ConnectionException {
		return objList;
	}

	@Override
	public List<FieldMetaInfo> getFieldList(String name) throws ConnectionException {
		return fieldList;
	}

}
