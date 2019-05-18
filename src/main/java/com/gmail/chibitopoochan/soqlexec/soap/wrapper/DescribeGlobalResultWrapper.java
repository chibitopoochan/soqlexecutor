package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public interface DescribeGlobalResultWrapper {
	/**
	 * DescribeGlobalResult#getSobjects()のラップ.
	 * 詳細は呼び出し先のAPIを参照のこと
	 * @return SObject記述結果一覧
	 */
	public DescribeGlobalSObjectResultWrapper[] getSobjects();

}
