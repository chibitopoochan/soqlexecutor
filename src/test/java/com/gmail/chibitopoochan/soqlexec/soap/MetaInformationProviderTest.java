package com.gmail.chibitopoochan.soqlexec.soap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.gmail.chibitopoochan.soqlexec.model.FieldMetaInfo;
import com.gmail.chibitopoochan.soqlexec.model.SObjectMetaInfo;
import com.gmail.chibitopoochan.soqlexec.soap.mock.DescribeGlobalResultWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.DescribeGlobalSObjectResultWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.DescribeSObjectResultWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.FieldsWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.PartnerConnectionWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.PicklistEntryWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerFieldsWrapper;
import com.sforce.soap.partner.FieldType;
import com.sforce.ws.ConnectionException;

/**
 * MetaInformationProviderのテストクラス
 */
public class MetaInformationProviderTest {
	// 検証に使用するインスタンス
	private DescribeGlobalSObjectResultWrapperMock sObject;
	private DescribeGlobalSObjectResultWrapperMock[] sObjects;
	private FieldsWrapperMock field;

	private DescribeGlobalResultWrapperMock globalResult;
	private PartnerConnectionWrapperMock wrapper;
	private DescribeSObjectResultWrapperMock sobjectResult;

	/**
	 * 例外検証用のルールを用意
	 */
	@Rule public ExpectedException thrown = ExpectedException.none();

	/**
	 * テスト前の共通設定
	 */
	@Before public void setup() {
		// 検証に使用するモックを用意
		sObject = new DescribeGlobalSObjectResultWrapperMock("name","label","prefix");
		sObjects = new DescribeGlobalSObjectResultWrapperMock[]{sObject};

		globalResult = new DescribeGlobalResultWrapperMock();
		globalResult.setSobjects(sObjects);

		field = new FieldsWrapperMock();
		field.setName("name");
		field.setLabel("label");
		field.setLength(5);

		sobjectResult = new DescribeSObjectResultWrapperMock();
		sobjectResult.setFieldsWrapper(new PartnerFieldsWrapper[]{field});

		wrapper = new PartnerConnectionWrapperMock();
		wrapper.setDescribeGlobalResultWrapper(globalResult);
		wrapper.setDescribeSObjectResultWrapper(sobjectResult);

	}

	/**
	 * SObject一覧取得に成功するケース
	 */
	@Test public void testGetListSuccess() {
		// 実行結果を設定
		wrapper.setSuccess(true);

		// メタ情報提供クラスの検証
		MetaInformationProvider provider = new MetaInformationProvider(wrapper);
		try {
			List<SObjectMetaInfo> sobjectList = provider.getSObjectList();
			SObjectMetaInfo actual = sobjectList.get(0);
			assertThat(actual.getName(), is(sObject.getName()));
			assertThat(actual.getLabel(), is(sObject.getLabel()));
			assertThat(actual.getKeyPrefix(), is(sObject.getKeyPrefix()));
		} catch (ConnectionException e) {
			fail();
		}

	}

	/**
	 * SObject一覧取得に失敗するケース（例外発生）
	 * @throws ConnectionException 接続エラー
	 */
	@Test public void testGetListFailed() throws ConnectionException {
		// 実行結果を設定
		wrapper.setSuccess(false);

		// メタ情報提供クラスの検証
		MetaInformationProvider provider = new MetaInformationProvider(wrapper);
		thrown.expect(ConnectionException.class);
		provider.getSObjectList();

	}

	// 項目一覧取得に成功するケース（テキストタイプ）
	@Test public void testGetFieldListForStringSuccess() {
		// 実行結果を設定
		wrapper.setSuccess(true);
		field.setType(FieldType.string.name());

		// 実行結果を設定
		MetaInformationProvider provider = new MetaInformationProvider(wrapper);
		try {
			// 通常項目
			List<FieldMetaInfo> fieldList = provider.getFieldList(sObject.getName());
			FieldMetaInfo actual = fieldList.get(0);

			assertThat(actual.getName(), is(field.getName()));
			assertThat(actual.getLabel(), is(field.getLabel()));
			assertThat(actual.getType(), is(field.getType()));
			assertThat(actual.getLength(), is(field.getLength()));

		} catch (ConnectionException e) {
			fail();
		}

	}

	// 項目一覧取得に成功するケース（選択肢タイプ）
	@Test public void testGetFieldListForPicklistSuccess() {
		// 実行結果を設定
		wrapper.setSuccess(true);

		// 有効な選択肢
		PicklistEntryWrapperMock activeItem = new PicklistEntryWrapperMock();
		activeItem.setActive(true);
		activeItem.setValue("activeItem");

		// 無効な選択肢
		PicklistEntryWrapperMock inactiveItem = new PicklistEntryWrapperMock();
		inactiveItem.setActive(false);
		inactiveItem.setValue("inactiveItem");

		field.setType(FieldType.picklist.name());
		field.setPicklistValues(new PicklistEntryWrapperMock[]{inactiveItem, activeItem});

		// 実行結果を設定
		MetaInformationProvider provider = new MetaInformationProvider(wrapper);
		try {
			// 選択肢項目
			List<FieldMetaInfo> fieldList = provider.getFieldList(sObject.getName());
			FieldMetaInfo actual = fieldList.get(0);

			assertThat(actual.getPicklist(), is(Arrays.asList(activeItem.getValue())));

		} catch (ConnectionException e) {
			fail();
		}

	}

	// 項目一覧取得に成功するケース（参照タイプ）
	@Test public void testGetFieldListForReferenceSuccess() {
		// 実行結果を設定
		wrapper.setSuccess(true);
		field.setType(FieldType.reference.name());
		field.setReferenceTo(new String[]{"Refer1"});

		// 実行結果を設定
		MetaInformationProvider provider = new MetaInformationProvider(wrapper);
		try {
			// 通常項目
			List<FieldMetaInfo> fieldList = provider.getFieldList(sObject.getName());
			FieldMetaInfo actual = fieldList.get(0);

			assertThat(actual.getReferenceToList(), is(Arrays.asList(field.getReferenceTo())));

		} catch (ConnectionException e) {
			fail();
		}

	}

	// 項目一覧取得に失敗するケース（例外発生）
	@Test public void testGetFieldListFailed() throws ConnectionException {
		// 実行結果を設定
		wrapper.setSuccess(false);

		// メタ情報提供クラスの検証
		MetaInformationProvider provider = new MetaInformationProvider(wrapper);
		thrown.expect(ConnectionException.class);
		provider.getFieldList("");

	}

}
