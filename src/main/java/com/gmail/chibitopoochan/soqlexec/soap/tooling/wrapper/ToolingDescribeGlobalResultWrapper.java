package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeGlobalResultWrapper;
import com.sforce.soap.tooling.DescribeGlobalResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class ToolingDescribeGlobalResultWrapper implements DescribeGlobalResultWrapper {
	private DescribeGlobalResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public ToolingDescribeGlobalResultWrapper() {}

	/**
	 * {@link com.sforce.soap.partner.DescribeGlobalResult}をラップ
	 * @param result ラップ対象のインスタンス
	 */
	public ToolingDescribeGlobalResultWrapper(DescribeGlobalResult result) {
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
	public ToolingDescribeGlobalSObjectResultWrapper[] getSobjects() {
		return Arrays.asList(result.getSobjects()).stream()
				.map(r -> new ToolingDescribeGlobalSObjectResultWrapper(r))
				.collect(Collectors.toList())
				.toArray(new ToolingDescribeGlobalSObjectResultWrapper[0]);
	}

}
