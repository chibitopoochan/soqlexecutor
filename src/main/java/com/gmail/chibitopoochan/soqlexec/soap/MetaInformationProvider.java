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
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
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

	private ConnectionWrapper connection;

	/**
	 * Salesforceへの接続を持たないインスタンスを生成
	 */
	public MetaInformationProvider() {}

	/**
	 * Salesforceへの接続を持ったインスタンスを生成
	 * @param connectionWrapper Salesforceへの接続
	 */
	public MetaInformationProvider(ConnectionWrapper connectionWrapper) {
		setPartnerConnection(connectionWrapper);
	}

	/**
	 * Salesforceへの接続を設定
	 * @param partnerConnection Salesforceへの接続
	 */
	public void setPartnerConnection(ConnectionWrapper partnerConnection) {
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
				.map(SObjectMetaInfo::new)
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
				.map(FieldMetaInfo::new)
				.collect(Collectors.toList());

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
