package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.ObjectWrapper;
import com.gmail.chibitopoochan.soqlexec.soap.wrapper.QueryResultWrapper;
import com.sforce.soap.tooling.QueryResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class ToolingQueryResultWrapper implements QueryResultWrapper {
	private QueryResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public ToolingQueryResultWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.QueryResult}をラップ
	 * @param result ラップ対象
	 */
	public ToolingQueryResultWrapper(QueryResult result) {
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
	public ObjectWrapper[] getRecords() {
		return Arrays.stream(result.getRecords())
				.map(s -> new ToolingSObjectWrapper(s))
				.collect(Collectors.toList())
				.toArray(new ObjectWrapper[0]);
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
