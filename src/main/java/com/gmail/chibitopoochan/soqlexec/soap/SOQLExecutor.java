package com.gmail.chibitopoochan.soqlexec.soap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.PartnerConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.SObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.XmlObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.sforce.ws.ConnectionException;

/**
 * SOQLの実行.
 * SFDCへの接続を使用してSOQLの発行と結果の提供を行います。
 */
public class SOQLExecutor {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(SOQLExecutor.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	public static final int DEFAULT_BATCH_SIZE = 1000;
	private PartnerConnectionWrapper connection;
	private boolean more;
	private boolean all;

	/**
	 * 接続を持たないインスタンスを生成
	 */
	public SOQLExecutor() {}

	/**
	 * 接続を持つインスタンスを生成
	 * @param connection
	 */
	public SOQLExecutor(PartnerConnectionWrapper connection) {
		setPartnerConnection(connection);
	}

	/**
	 *  接続を設定
	 * @param connection
	 */
	public void setPartnerConnection(PartnerConnectionWrapper connection) {
		this.connection = connection;
		this.connection.setQueryOption(DEFAULT_BATCH_SIZE);
		this.more = false;
		this.all = false;
	}

	/**
	 * バッチサイズを指定
	 * @param batchSize バッチサイズ
	 */
	public void setBatchSize(int batchSize){
		connection.setQueryOption(batchSize);
	}

	/**
	 * Moreオプションを指定
	 * @param more 全てのレコードを取得するならtrue
	 */
	public void setMoreOption(boolean more) {
		this.more = more;
	}

	/**
	 * Allオプションを指定
	 * @param all 削除も取得するならtrue
	 */
	public void setAllOption(boolean all) {
		this.all = all;
	}

	/**
	 * SOQLの実行
	 * @param soql 実行するSOQL
	 * @param batchSize バッチサイズ（nullならデフォルト値）
	 * @return レコード一覧
	 * @throws ConnectionException 接続エラー
	 */
	public List<Map<String, String>> execute(String soql) throws ConnectionException {
		// パラメータをログ出力
		if(logger.isInfoEnabled()) {
			logger.info(resources.getString(Constants.Message.Information.MSG_005), soql, connection.getQueryOption(), more, all);
		}

		// SOQLを発行
		QueryResultWrapper result = all ? connection.queryAll(soql) : connection.query(soql);

		// SELECT文から項目を抽出
		Matcher match = Pattern
				.compile(Constants.SOQL.Pattern.SELECT_FIELDS, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
				.matcher(soql);

		// 項目が抽出できたか確認
		String selectField = "";
		if(match.matches()) {
			selectField = match.group(1);
		} else {
			logger.warn(resources.getString(Constants.Message.Error.ERR_004), soql);
			throw new IllegalArgumentException(resources.getString(Constants.Message.Error.ERR_004).replace("{0}", soql));
		}

		// サブクエリは対象外（解析が複雑になるので）
		if(selectField.contains(Constants.SOQL.SUBQUERY_LEFT_SIGN)) {
			logger.warn(resources.getString(Constants.Message.Error.ERR_003));
			throw new IllegalArgumentException(resources.getString(Constants.Message.Error.ERR_003));
		}

		// 項目に分割
		String[] fields = selectField.split(Constants.SOQL.FIELD_SEPARATE_SIGN);

		// レコードを取得
		List<Map<String, String>> fieldList = Arrays.stream(result.getRecords())
				.map(r -> toMapRecord(fields, r))
				.collect(Collectors.toList());

		// さらに取得する場合、ループで呼び出す
		if(more) {
			while(!result.isDone()) {
				result = connection.queryMore(result.getQueryLocator());
				fieldList.addAll(Arrays.stream(result.getRecords())
						.map(r -> toMapRecord(fields, r))
						.collect(Collectors.toList()));
			}
		}

		return fieldList;
	}

	/**
	 * レコードを項目と値のペアに変換
	 * @param fields 項目一覧
	 * @param record 検索結果
	 * @return 項目と値のペア
	 */
	private static Map<String, String> toMapRecord(String[] fields, SObjectWrapper record) {
		Map<String, String> fieldMap = new HashMap<>();

		// 項目名をもとに値とのペアを作成
		for(String field : fields) {
			List<String> keys = Arrays.asList(field.split(Constants.SOQL.FIELD_RELATION_SIGN));
			String lastKey = keys.get(keys.size()-1);

			// SOQLで参照を経由している場合、経由先を辿る
			XmlObjectWrapper obj = null;
			for(String key : keys) {
				if(lastKey.equals(key)) {
					// 項目の値を取得
					String value = obj == null ? record.getField(lastKey) : obj.getField(lastKey);
					fieldMap.put(field, value);
				} else {
					obj = obj == null ? record.getChild(key) : obj.getChild(key);
				}
			}

		}

		return fieldMap;

	}

}
