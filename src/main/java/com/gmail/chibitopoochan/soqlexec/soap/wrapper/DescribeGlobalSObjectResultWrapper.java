package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import com.sforce.soap.partner.DescribeGlobalSObjectResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class DescribeGlobalSObjectResultWrapper {
	private DescribeGlobalSObjectResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public DescribeGlobalSObjectResultWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult}をラップ
	 * @param result ラップ対象
	 */
	public DescribeGlobalSObjectResultWrapper(DescribeGlobalSObjectResult result) {
		setDescribeGlobalSObjectResult(result);
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult}をラップ
	 * @param result ラップ対象
	 */
	public void setDescribeGlobalSObjectResult(DescribeGlobalSObjectResult result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getName()}のラップ
	 * @return オブジェクト名
	 */
	public String getName() {
		return result.getName();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getLabel()}のラップ
	 * @return ラベル名
	 */
	public String getLabel() {
		return result.getLabel();
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalSObjectResult#getKeyPrefix()}のラップ
	 * @return オブジェクトのプレフィックス
	 */
	public String getKeyPrefix() {
		return result.getKeyPrefix();
	}

}
