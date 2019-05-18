package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalResultWrapper;
import com.sforce.soap.partner.DescribeGlobalResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class PartnerDescribeGlobalResultWrapper implements DescribeGlobalResultWrapper {
	private DescribeGlobalResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public PartnerDescribeGlobalResultWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalResult}をラップ
	 * @param result ラップ対象のインスタンス
	 */
	public PartnerDescribeGlobalResultWrapper(DescribeGlobalResult result) {
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
	public PartnerDescribeGlobalSObjectResultWrapper[] getSobjects() {
		return Arrays.asList(result.getSobjects()).stream()
				.map(r -> new PartnerDescribeGlobalSObjectResultWrapper(r))
				.collect(Collectors.toList())
				.toArray(new PartnerDescribeGlobalSObjectResultWrapper[0]);
	}

}
