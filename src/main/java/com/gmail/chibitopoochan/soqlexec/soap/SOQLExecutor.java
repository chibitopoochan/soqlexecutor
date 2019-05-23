package com.gmail.chibitopoochan.soqlexec.soap;

import java.util.Arrays;
import java.util.HashMap;
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

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper;
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
	private ConnectionWrapper connection;
	private QueryMore more = new QueryMore();
	private boolean all;
	private boolean join;
	private int size;
	private Map<String, String> subqueryMap = new HashMap<>();
	private Pattern selectPattern = Pattern.compile(Constants.SOQL.Pattern.SELECT_FIELDS, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);
	private Pattern labelPattern = Pattern.compile(Constants.SOQL.Pattern.LABEL_FIELDS, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);
	private Pattern countPattern = Pattern.compile(Constants.SOQL.Pattern.COUNT_FIELDS, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);
	private Pattern subqueryPattern = Pattern.compile(Constants.SOQL.Pattern.QUERY_FIELDS, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);
	private Pattern formPattern = Pattern.compile(Constants.SOQL.Pattern.FROM_FIELD, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.DOTALL);

	/**
	 * 接続を持たないインスタンスを生成
	 */
	public SOQLExecutor() {}

	/**
	 * 接続を持つインスタンスを生成
	 * @param connection
	 */
	public SOQLExecutor(ConnectionWrapper connection) {
		setPartnerConnection(connection);
	}

	/**
	 *  接続を設定
	 * @param connection
	 */
	public void setPartnerConnection(ConnectionWrapper connection) {
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

	public void setJoinOption(boolean join) {
		this.join = join;
	}

	/**
	 * SOQLの実行
	 * @param soql 実行するSOQL
	 * @return レコード一覧
	 * @throws ConnectionException 接続エラー
	 */
	public List<Map<String, String>> execute(String soql) throws ConnectionException {
		// パラメータをログ出力
		logger.info(resources.getString(Constants.Message.Information.MSG_005), soql, connection.getQueryOption(), all);

		// 項目を抽出
		List<String> fields = extractFields(soql);

		// SOQLを発行
		QueryResultWrapper result = all ? connection.queryAll(soql) : connection.query(soql);

		// レコードを取得
		List<Map<String, String>> fieldList = Arrays.stream(result.getRecords())
				.flatMap(r -> toMapRecord(fields, r).stream())
				.collect(Collectors.toList());
		logger.info(resources.getString(Constants.Message.Information.MSG_009), fieldList.size());

		size = result.getSize();

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
	 * レコードの最大件数を取得
	 * @return 件数
	 */
	public int getSize() {
		return size;
	}

	/**
	 * SELECT文から項目名を抽出
	 * @param soql SELECT文のSOQL
	 * @return 項目一覧
	 */
	private List<String> extractFields(String soql) {
		// 表示ラベル項目の置き換え
		soql = replaceToLabel(soql);
		// 集計項目の置き換え
		soql = replaceCount(soql);
		// サブクエリの置き換え
		soql = replaceSubquery(soql);

		// SELECT文から項目を抽出
		Matcher selectMatch = selectPattern.matcher(soql);

		// 項目が抽出できたか確認
		String selectField = "";
		if(selectMatch.matches()) {
			selectField = selectMatch.group(1);
		} else {
			logger.warn(resources.getString(Constants.Message.Error.ERR_004), soql);
			throw new IllegalArgumentException(resources.getString(Constants.Message.Error.ERR_004).replace("{}", soql));
		}

		// 項目に分割
		return Arrays
				.stream(selectField.split(Constants.SOQL.FIELD_SEPARATE_SIGN))
				.map(f -> f.trim())
				.collect(Collectors.toList());

	}

	/**
	 * サブクエリを仮項目名に置換<br>
	 * 実行結果の取得用にクエリをリレーション名に仮置きする。
	 * @param selectField 取得項目の文字列
	 * @return 置換後の取得項目
	 */
	private String replaceSubquery(String selectField) {
		// 項目からサブクエリを抽出
		Matcher subqueryMatch = subqueryPattern.matcher(selectField);

		// サブクエリをリレーション名に置換
		StringBuffer workSelectField = new StringBuffer();
		while(subqueryMatch.find()) {
			// リレーション名に置き換え
			String subquery = subqueryMatch.group(1);
			Matcher formMatch = formPattern.matcher(subquery);
			if(!formMatch.find()) {
				logger.warn(resources.getString(Constants.Message.Error.ERR_004), subquery);
				throw new IllegalArgumentException(resources.getString(Constants.Message.Error.ERR_004).replace("{}", subquery));
			}
			String relationName = formMatch.group(1);
			subqueryMatch.appendReplacement(workSelectField, relationName);

			// リレーション名とサブクエリの項目を記録
			Matcher selectMatch = selectPattern.matcher(subquery);
			if(!selectMatch.find()) {
				logger.warn(resources.getString(Constants.Message.Error.ERR_004), subquery);
				throw new IllegalArgumentException(resources.getString(Constants.Message.Error.ERR_004).replace("{}", subquery));
			}
			subqueryMap.put(relationName, selectMatch.group());

		}
		subqueryMatch.appendTail(workSelectField);

		return workSelectField.toString();

	}

	/**
	 * 表示ラベル項目を項目名に置換
	 * @param selectField 取得項目の文字列
	 * @return 置換後の取得項目
	 */
	private String replaceToLabel(String selectField) {
		// 項目からtoLabelを抽出
		Matcher labelMatch = labelPattern.matcher(selectField);

		// 列名を差し替え
		StringBuffer workSelectField = new StringBuffer();
		while(labelMatch.find()) {
			labelMatch.appendReplacement(workSelectField, labelMatch.group(1));
		}
		labelMatch.appendTail(workSelectField);

		return workSelectField.toString();
	}

	/**
	 * 集計項目を仮項目名に置換
	 * @param selectField 取得項目の文字列
	 * @return 置換後の取得項目
	 */
	private String replaceCount(String selectField) {
		// 項目から集計項目を抽出
		Matcher countMatch = countPattern.matcher(selectField);

		// 集計項目用の列名に置き換え
		StringBuffer workSelectField = new StringBuffer();
		int counter = 0;
		while(countMatch.find()) {
			countMatch.appendReplacement(workSelectField, Constants.SOQL.GROUPING_ANOTATION + counter++);
		}
		countMatch.appendTail(workSelectField);

		return workSelectField.toString();

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
					.flatMap(r -> toMapRecord(fields, r).stream())
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
	 * @param join サブクエリの表現（trueなら結合）
	 * @return 項目と値のペア
	 */
	private List<Map<String, String>> toMapRecord(List<String> fields, ObjectWrapper record) {
		List<Map<String,String>> fieldMapList = new LinkedList<>();
		List<Map<String,String>> subqueryMapList = new LinkedList<>();
		Map<String, String> fieldMap = new LinkedHashMap<>(fields.size());

		// 項目名をもとに値とのペアを作成
		for(String field : fields) {
			List<String> keys = Arrays.asList(field.split(Constants.SOQL.FIELD_RELATION_SIGN));
			String lastKey = keys.get(keys.size()-1);

			// SOQLで参照を経由している場合、経由先を辿る
			Optional<ObjectWrapper> obj = Optional.empty();
			for(String key : keys) {
				// API名を取得
				Iterator<ObjectWrapper> children = obj.orElse(record).getChildren();
				String apiKey = toAPIName(children, key).orElse(key);

				// 項目の値か参照先を取得
				if(lastKey.equals(key)) {
					Optional<Object> value = obj.orElse(record).getField(apiKey);
					if(!subqueryMap.containsKey(field) || join) {
						fieldMap.put(field, value.orElse("").toString());
					}
					logger.debug(resources.getString(Constants.Message.Information.MSG_008), field, value);
				} else {
					obj = obj.orElse(record).getChild(apiKey);
				}
			}

			// SOQLでサブクエリを使用している場合、サブクエリの結果を取得する
			if(subqueryMap.containsKey(field) && !join) {
				// API名を取得
				Iterator<ObjectWrapper> children = obj.orElse(record).getChildren();
				String apiKey = toAPIName(children, field).orElse(field);

				// 項目を抽出
				List<String> subqueryFieldList = extractFields(subqueryMap.get(field));

				// レコードを取得
				Optional<ObjectWrapper> subquery = record.getChild(apiKey);
				if(subquery.isPresent()){
					subquery.get().getChildren().forEachRemaining(r -> {
						if(r.getName().getLocalPart().equals("records")) {
							for(Map<String,String> m : toMapRecord(subqueryFieldList, r).stream().collect(Collectors.toList())) {
								Map<String,String> map = new LinkedHashMap<>();
								for(String key : m.keySet()) {
									map.put(field + "." + key, m.get(key));
									fieldMap.put(field + "." + key, "");
								}
								subqueryMapList.add(map);
							}
						}
						logger.info(resources.getString(Constants.Message.Information.MSG_009), subqueryMapList.size());
					});
				} else {
					Map<String,String> dummyField = new LinkedHashMap<>();
					for(String key : subqueryFieldList) {
						dummyField.put(field + "." + key, "");
						fieldMap.put(field + "." + key, "");
					}
					subqueryMapList.add(dummyField);
				}
			}
		}

		if(subqueryMapList.isEmpty()) {
			fieldMapList.add(fieldMap);
		} else {
			for(Map<String,String> map : subqueryMapList) {
				Map<String,String> work = new LinkedHashMap<>(fieldMap);
				work.putAll(map);
				fieldMapList.add(work);
			}
		}

		return fieldMapList;

	}

	/**
	 * 項目名をAPI名に変換
	 * @param objects 要素一覧
	 * @param queryName クエリでの名前
	 * @return API名
	 */
	private Optional<String> toAPIName(Iterator<ObjectWrapper> objects, String queryName) {
		Optional<String> apiName =
				StreamSupport.stream(Spliterators.spliteratorUnknownSize(objects, Spliterator.ORDERED),false)
				.map(i -> i.getName().getLocalPart()) // ローカル名（API名）に変換
				.filter(i -> i.toLowerCase().equals(queryName.toLowerCase())) // クエリ名と一致する場合
				.findFirst(); // 最初に一致したAPI名を返す

		logger.debug(resources.getString(Constants.Message.Information.MSG_007), queryName, apiName);

		return apiName;

	}

}
