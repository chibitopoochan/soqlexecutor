package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public interface QueryResultWrapper {
	/**
	 * {@link com.sforce.soap.partner.QueryResult#getRecords()}をラップ
	 * @return 検索結果
	 */
	public ObjectWrapper[] getRecords();

	/**
	 * {@link com.sforce.soap.partner.QueryResult#getSize()}をラップ
	 * @return レコード件数
	 */
	public int getSize();

	/**
	 * {@link com.sforce.soap.partner.QueryResult#getQueryLocator()}をラップ
	 * @return クエリ位置
	 */
	public String getQueryLocator();

	/**
	 * {@link com.sforce.soap.partner.QueryResult#isDone()}をラップ
	 * @return すべて取得済みならtrue
	 */
	public boolean isDone();

}
