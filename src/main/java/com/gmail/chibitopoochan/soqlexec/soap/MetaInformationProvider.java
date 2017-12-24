package com.gmail.chibitopoochan.soqlexec.soap;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.model.FieldMetaInfo;
import com.gmail.chibitopoochan.soqlexec.model.SObjectMetaInfo;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalSObjectResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.FieldsWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.sforce.soap.partner.FieldType;
import com.sforce.ws.ConnectionException;

/**
 * メタ情報の提供.
 * Salesforceへの接続（ログイン済み）を使い、メタ情報の提供を行う。
 * 提供するメタ情報はオブジェクトと項目のみ。
 */
public class MetaInformationProvider {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(MetaInformationProvider.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	private PartnerConnectionWrapper connection;

	/**
	 * Salesforceへの接続を持たないインスタンスを生成
	 */
	public MetaInformationProvider() {}

	/**
	 * Salesforceへの接続を持ったインスタンスを生成
	 * @param connectionWrapper Salesforceへの接続
	 */
	public MetaInformationProvider(PartnerConnectionWrapper connectionWrapper) {
		setPartnerConnection(connectionWrapper);
	}

	/**
	 * Salesforceへの接続を設定
	 * @param partnerConnection Salesforceへの接続
	 */
	public void setPartnerConnection(PartnerConnectionWrapper partnerConnection) {
		this.connection = partnerConnection;
	}

	/**
	 * SObjectの一覧を取得
	 * @return SObject一覧
	 * @throws ConnectionException 接続エラー
	 */
	public List<SObjectMetaInfo> getSObjectList() throws ConnectionException {
		return Arrays.stream(connection.describeGlobal().getSobjects())
				.map(MetaInformationProvider::loggingSObject)
				.map(t -> new SObjectMetaInfo(t.getName(), t.getLabel(), t.getKeyPrefix()))
				.collect(Collectors.toList());

	}

	/**
	 * 項目の一覧を取得
	 * @param name オブジェクト名
	 * @return 項目一覧
	 * @throws ConnectionException 接続エラー
	 */
	public List<FieldMetaInfo> getFieldList(String name) throws ConnectionException {
		return Arrays.stream(connection.describeSObject(name).getFields())
				.map(MetaInformationProvider::loggingField)
				.map(MetaInformationProvider::toFieldMetaInfo)
				.collect(Collectors.toList());

	}

	/**
	 * Salesforceから取得したメタ情報をFieldMetaInfoへ設定
	 * @param metaInfo 取得したメタ情報
	 * @return 引数のメタ情報をもとに作成したFieldMetaInfo
	 */
	private static FieldMetaInfo toFieldMetaInfo(FieldsWrapper metaInfo) {
		// メタ情報を設定
		FieldMetaInfo info = new FieldMetaInfo(
				 metaInfo.getName()
				,metaInfo.getLabel()
				,metaInfo.getLength()
				,metaInfo.getType());

		// Picklistなら選択肢も設定
		if (info.getType() == FieldType.picklist.name()) {
			List<String> picklist = Arrays
					.stream(metaInfo.getPicklistValues())
					.filter(p -> p.isActive())
					.map(p -> p.getValue())
					.collect(Collectors.toList());

			info.setPicklist(picklist);

		// 参照なら参照先情報も設定
		} else if (info.getType() == FieldType.reference.name()) {
			List<String> referList = Arrays
					.stream(metaInfo.getReferenceTo())
					.collect(Collectors.toList());

			info.setReferenceToList(referList);

		}

		return info;

	}

	/**
	 * SObjectのログ出力
	 * @param metaInfo 取得したメタ情報
	 * @return 取得したメタ情報
	 */
	private static DescribeGlobalSObjectResultWrapper loggingSObject(DescribeGlobalSObjectResultWrapper metaInfo) {
		if(logger.isDebugEnabled()) {
			logger.debug(
					resources.getString(Constants.Message.Information.MSG_003)
					,metaInfo.getName()
					,metaInfo.getLabel()
					,metaInfo.getKeyPrefix());
		}

		return metaInfo;

	}

	/**
	 * Fieldのログ出力
	 * @param metaInfo 取得したメタ情報
	 * @return 取得したメタ情報
	 */
	private static FieldsWrapper loggingField(FieldsWrapper metaInfo) {
		if(logger.isDebugEnabled()) {
			logger.debug(
					resources.getString(Constants.Message.Information.MSG_004)
					,metaInfo.getName()
					,metaInfo.getLabel()
					,metaInfo.getLength()
					,metaInfo.getType());
		}
		return metaInfo;
	}

}
