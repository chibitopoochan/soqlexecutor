package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.sforce.soap.partner.QueryResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class QueryResultWrapper {
	private QueryResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public QueryResultWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.QueryResult}をラップ
	 * @param result ラップ対象
	 */
	public QueryResultWrapper(QueryResult result) {
		setQueryResultWrapper(result);
	}

	/**
	 * {@link com.sforce.soap.partner.QueryResult}をラップ
	 * @param result ラップ対象
	 */
	public void setQueryResultWrapper(QueryResult result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.soap.partner.QueryResult#getRecords()}をラップ
	 * @return 検索結果
	 */
	public SObjectWrapper[] getRecords() {
		return Arrays.stream(result.getRecords())
				.map(s -> new SObjectWrapper(s))
				.collect(Collectors.toList())
				.toArray(new SObjectWrapper[0]);
	}

	/**
	 * {@link com.sforce.soap.partner.QueryResult#getSize()}をラップ
	 * @return レコード件数
	 */
	public int getSize() {
		return result.getSize();
	}

	/**
	 * {@link com.sforce.soap.partner.QueryResult#getQueryLocator()}をラップ
	 * @return クエリ位置
	 */
	public String getQueryLocator() {
		return result.getQueryLocator();
	}

	/**
	 * {@link com.sforce.soap.partner.QueryResult#isDone()}をラップ
	 * @return すべて取得済みならtrue
	 */
	public boolean isDone() {
		return result.isDone();
	}

}
