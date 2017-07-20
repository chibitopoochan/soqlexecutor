package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.sforce.soap.partner.DescribeGlobalResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class DescribeGlobalResultWrapper {
	private DescribeGlobalResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public DescribeGlobalResultWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalResult}をラップ
	 * @param result ラップ対象のインスタンス
	 */
	public DescribeGlobalResultWrapper(DescribeGlobalResult result) {
		setDescribeGlobalResult(result);
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalResult}をラップ
	 * @param result ラップ対象のインスタンス
	 */
	public void setDescribeGlobalResult(DescribeGlobalResult result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalResult#getSobjects()}のラップ.
	 * 詳細は呼び出し先のAPIを参照のこと
	 * @return SObject記述結果一覧
	 */
	public DescribeGlobalSObjectResultWrapper[] getSobjects() {
		return Arrays.asList(result.getSobjects()).stream()
				.map(r -> new DescribeGlobalSObjectResultWrapper(r))
				.collect(Collectors.toList())
				.toArray(new DescribeGlobalSObjectResultWrapper[0]);
	}

}
