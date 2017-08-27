package com.gmail.chibitopoochan.soqlexec.soap;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
	private QueryMore more = new QueryMore();
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
	 * Allオプションを指定
	 * @param all 削除も取得するならtrue
	 */
	public void setAllOption(boolean all) {
		this.all = all;
	}

	/**
	 * SOQLの実行
	 * @param soql 実行するSOQL
	 * @return レコード一覧
	 * @throws ConnectionException 接続エラー
	 */
	public List<Map<String, String>> execute(String soql) throws ConnectionException {
		// パラメータをログ出力
		if(logger.isInfoEnabled()) {
			logger.info(resources.getString(Constants.Message.Information.MSG_005), soql, connection.getQueryOption(), all);
		}

		// SELECT文から項目を抽出
		Matcher match = Pattern
				.compile(Constants.SOQL.Pattern.SELECT_FIELDS, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL)
				.matcher(soql);

		// 項目が抽出できたか確認
		String selectField = "";
		if(match.matches()) {
			selectField = match.group(1);
		} else {
			logger.warn(resources.getString(Constants.Message.Error.ERR_004), soql);
			throw new IllegalArgumentException(resources.getString(Constants.Message.Error.ERR_004).replace("{}", soql));
		}

		// サブクエリ、集計関数は対象外（解析が複雑になるので）
		if(selectField.contains(Constants.SOQL.SUBQUERY_LEFT_SIGN)) {
			logger.warn(resources.getString(Constants.Message.Error.ERR_003));
			throw new IllegalArgumentException(resources.getString(Constants.Message.Error.ERR_003));
		}

		// 項目に分割
		List<String> fields = Arrays
				.stream(selectField.split(Constants.SOQL.FIELD_SEPARATE_SIGN))
				.map(f -> f.trim())
				.collect(Collectors.toList());

		// SOQLを発行
		QueryResultWrapper result = all ? connection.queryAll(soql) : connection.query(soql);

		// レコードを取得
		List<Map<String, String>> fieldList = Arrays.stream(result.getRecords())
				.map(r -> toMapRecord(fields, r))
				.collect(Collectors.toList());
		logger.info(resources.getString(Constants.Message.Information.MSG_009), fieldList.size());

		// さらに取得する場合、ループで呼び出す
		if(!result.isDone()) {
			more = new QueryMore(fields, result.getQueryLocator());
		} else {
			more = new QueryMore();
		}

		return fieldList;
	}

	/**
	 * 追加レコード取得用のインスタンスを取得
	 * @return 追加レコードの取得を行うQueryMoreインスタンス
	 */
	public QueryMore getQueryMore() {
		return more;
	}

	/**
	 * 追加レコードの取得
	 */
	public class QueryMore {
		private String queryLocator;
		private List<String> fields;
		private boolean done;

		/**
		 * 追加レコード無しの状態でQueryMoreを作成
		 */
		public QueryMore() {
			this.done = true;
		}

		/**
		 * 追加レコードを取得できるQueryMoreを作成
		 * @param fields 項目定義
		 * @param queryLocator クエリロケータ
		 */
		public QueryMore(List<String> fields, String queryLocator) {
			this.fields = fields;
			this.queryLocator = queryLocator;
		}

		/**
		 * 残りのレコード有無
		 * @return 残りのレコードがなければtrue
		 */
		public boolean isDone() {
			return done;
		}

		/**
		 * 残りのレコードを取得
		 * @return 残りのレコード
		 * @throws ConnectionException Salesforceのエラー
		 */
		public List<Map<String, String>> execute() throws ConnectionException {
			// 終了しているなら空リストを返す
			if(done) {
				return new LinkedList<>();
			}

			// 残りのレコードを取得
			QueryResultWrapper result = connection.queryMore(queryLocator);
			List<Map<String, String>> fieldList = Stream.of(result.getRecords())
					.map(r -> toMapRecord(fields, r))
					.collect(Collectors.toList());
			logger.info(resources.getString(Constants.Message.Information.MSG_009), fieldList.size());

			// 残りのレコードがあるか判定
			if(result.isDone()) {
				done = true;
			} else {
				queryLocator = result.getQueryLocator();
			}

			return fieldList;

		}

	}

	/**
	 * レコードを項目と値のペアに変換
	 * @param fields 項目一覧
	 * @param record 検索結果
	 * @return 項目と値のペア
	 */
	private static Map<String, String> toMapRecord(List<String> fields, SObjectWrapper record) {
		Map<String, String> fieldMap = new LinkedHashMap<>(fields.size());

		// 項目名をもとに値とのペアを作成
		for(String field : fields) {
			List<String> keys = Arrays.asList(field.split(Constants.SOQL.FIELD_RELATION_SIGN));
			String lastKey = keys.get(keys.size()-1);

			// SOQLで参照を経由している場合、経由先を辿る
			XmlObjectWrapper obj = null;
			for(String key : keys) {
				// API名を取得
				Iterator<XmlObjectWrapper> children = obj == null ? record.getChildren() : obj.getChildren();
				String apiKey = toAPIName(children, key).orElse(key);

				// 項目の値か参照先を取得
				if(lastKey.equals(key)) {
					String value = (String) (obj == null ? record.getField(apiKey) : obj.getField(apiKey));
					fieldMap.put(field, value);
					logger.debug(resources.getString(Constants.Message.Information.MSG_008), field, value);
				} else {
					obj = obj == null ? record.getChild(apiKey) : obj.getChild(apiKey);
				}
			}

		}

		return fieldMap;

	}

	/**
	 * 項目名をAPI名に変換
	 * @param objects 要素一覧
	 * @param queryName クエリでの名前
	 * @return API名
	 */
	private static Optional<String> toAPIName(Iterator<XmlObjectWrapper> objects, String queryName) {
		Optional<String> apiName =
				StreamSupport.stream(Spliterators.spliteratorUnknownSize(objects, Spliterator.ORDERED),false)
				.map(i -> i.getName().getLocalPart()) // ローカル名（API名）に変換
				.filter(i -> i.toLowerCase().equals(queryName.toLowerCase())) // クエリ名と一致する場合
				.findFirst(); // 最初に一致したAPI名を返す

		logger.debug(resources.getString(Constants.Message.Information.MSG_007), queryName, apiName);

		return apiName;

	}

}
