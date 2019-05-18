package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeSObjectResultWrapper;
import com.sforce.soap.tooling.DescribeSObjectResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class ToolingDescribeSObjectResultWrapper implements DescribeSObjectResultWrapper {
	private DescribeSObjectResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public ToolingDescribeSObjectResultWrapper(){}

	/**
	 * {@link com.sforce.soap.partner.DescribeSObjectResult}をラップ
	 * @param result ラップ対象
	 */
	public ToolingDescribeSObjectResultWrapper(DescribeSObjectResult result) {
		setDescribeSObjectResultWrapper(result);
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeSObjectResult}をラップ
	 * @param result ラップ対象
	 */
	public void setDescribeSObjectResultWrapper(DescribeSObjectResult result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.soap.partner.DescribeSObjectResult#getFields()}をラップ
	 * @param result 項目一覧
	 */
	public ToolingFieldsWrapper[] getFields() {
		return Arrays.stream(result.getFields())
				.map(f -> new ToolingFieldsWrapper(f))
				.collect(Collectors.toList())
				.toArray(new ToolingFieldsWrapper[0]);
	}

}
