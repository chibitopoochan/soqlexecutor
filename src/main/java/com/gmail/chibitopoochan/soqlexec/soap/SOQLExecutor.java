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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ConnectionWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper;
import com.gmail.chibitopoochan.soqlexec.soql.QueryAnalyzeUtils;
import com.gmail.chibitopoochan.soqlexec.soql.SOQL;
import com.gmail.chibitopoochan.soqlexec.soql.SOQLField;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Message;
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

	/**
	 * サブクエリの展開有無
	 * @param join 展開しないならtrue
	 */
	public void setJoinOption(boolean join) {
		this.join = join;
	}

	/**
	 * SOQLの実行
	 * @param query 実行するSOQL
	 * @return レコード一覧
	 * @throws ConnectionException 接続エラー
	 */
	public List<Map<String, String>> execute(String soql) throws ConnectionException {
		// パラメータをログ出力
		logger.info(resources.getString(Constants.Message.Information.MSG_005), soql, connection.getQueryOption(), all);

		// 項目を抽出
		SOQL query = extractFields(soql);

		// SOQLを発行
		QueryResultWrapper result = all ? connection.queryAll(soql) : connection.query(soql);

		// レコードを取得
		List<Map<String, String>> fieldList = Arrays.stream(result.getRecords())
				.flatMap(r -> toMapRecord(query, r).stream())
				.collect(Collectors.toList());
		logger.info(resources.getString(Constants.Message.Information.MSG_009), fieldList.size());

		size = result.getSize();

		// さらに取得する場合、ループで呼び出す
		if(!result.isDone()) {
			more = new QueryMore(query, result.getQueryLocator());
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
	private SOQL extractFields(String soql) {
		SOQL query;
		try {
			query = QueryAnalyzeUtils.analyze(soql);
		} catch (QueryAnalyzeUtils.TokenException ex) {
			logger.warn(Message.get(Constants.Message.Error.ERR_004, soql, ex.getMessage()));
			throw new IllegalArgumentException(Message.get(Constants.Message.Error.ERR_004, soql, ex.getMessage()), ex);
		}

		return query;

	}

	/**
	 * 追加レコードの取得
	 */
	public class QueryMore {
		private String queryLocator;
		private SOQL query;
		private boolean done;

		/**
		 * 追加レコード無しの状態でQueryMoreを作成
		 */
		public QueryMore() {
			this.done = true;
		}

		/**
		 * 追加レコードを取得できるQueryMoreを作成
		 * @param query クエリ
		 * @param queryLocator クエリロケータ
		 */
		public QueryMore(SOQL query, String queryLocator) {
			this.query = query;
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
					.flatMap(r -> toMapRecord(query, r).stream())
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
	 * @param query クエリ
	 * @param record 検索結果
	 * @param join サブクエリの表現（trueなら結合）
	 * @return 項目と値のペア
	 */
	private List<Map<String, String>> toMapRecord(SOQL query, ObjectWrapper record) {
		List<Map<String,String>> fieldMapList = new LinkedList<>();
		List<Map<String,String>> subqueryMapList = new LinkedList<>();
		Map<String, String> fieldMap = new LinkedHashMap<>(query.getSelectFields().size());

		// 項目名をもとに値とのペアを作成
		for(SOQLField field : query.getSelectFields()) {
			// SOQLで参照を経由している場合、経由先を辿る
			Optional<ObjectWrapper> obj = Optional.empty();
			Optional<SOQLField> relation = Optional.of(field);
			while(relation.isPresent()) {
				// API名を取得
				Iterator<ObjectWrapper> children = obj.orElse(record).getChildren();
				String apiKey = toAPIName(children, relation.get().getName()).orElse(relation.get().getName());

				// 項目の値か参照先を取得
				if(relation.get().getRelation().isPresent()) {
					obj = obj.orElse(record).getChild(apiKey);
				} else {
					Optional<Object> value = obj.orElse(record).getField(apiKey);
					fieldMap.put(field.toString(), value.orElse("").toString());
					logger.debug(resources.getString(Constants.Message.Information.MSG_008), field, value);
				}
				relation = relation.get().getRelation();
			}

			// SOQLでサブクエリを使用している場合、サブクエリの結果を取得する
			if(field.getSubQuery().isPresent()) {
				// API名を取得
				Iterator<ObjectWrapper> children = record.getChildren();
				String apiKey = toAPIName(children, field.getName()).orElse(field.getName());

				if(join) {
					Optional<Object> value = record.getField(apiKey);
					fieldMap.put(field.getLabel(), value.orElse("").toString());
				} else {
					// レコードを取得
					Optional<ObjectWrapper> subquery = record.getChild(apiKey);
					if(subquery.isPresent()){
						subquery.get().getChildren().forEachRemaining(r -> {
							if(r.getName().getLocalPart().equals("records")) {
								for(Map<String,String> m : toMapRecord(field.getSubQuery().get(), r).stream().collect(Collectors.toList())) {
									Map<String,String> map = new LinkedHashMap<>();
									for(String key : m.keySet()) {
										map.put(field.getLabel() + "." + key, m.get(key));
										fieldMap.put(field.getLabel() + "." + key, "");
									}
									subqueryMapList.add(map);
								}
							}
							logger.info(resources.getString(Constants.Message.Information.MSG_009), subqueryMapList.size());
						});
					} else {
						Map<String,String> dummyField = new LinkedHashMap<>();
						for(SOQLField key : field.getSubQuery().get().getSelectFields()) {
							dummyField.put(field.getLabel() + "." + key.getLabel(), "");
							fieldMap.put(field.getLabel() + "." + key.getLabel(), "");
						}
						subqueryMapList.add(dummyField);
					}

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

		// Typeの場合、大文字小文字を意識するので読み替えを行う
		if(apiName.isPresent()) {
			if("type".equals(apiName.get())) {
				apiName = Optional.of("Type");
			}
		}

		logger.debug(resources.getString(Constants.Message.Information.MSG_007), queryName, apiName);

		return apiName;

	}

}
