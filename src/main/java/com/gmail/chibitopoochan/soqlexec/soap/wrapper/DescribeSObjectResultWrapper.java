package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
import com.sforce.soap.partner.DescribeSObjectResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class DescribeSObjectResultWrapper {
	private DescribeSObjectResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public DescribeSObjectResultWrapper(){}

	/**
	 * {@link com.sforce.soap.partner.DescribeSObjectResult}をラップ
	 * @param result ラップ対象
	 */
	public DescribeSObjectResultWrapper(DescribeSObjectResult result) {
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
	public FieldsWrapper[] getFields() {
		return Arrays.stream(result.getFields())
				.map(f -> new FieldsWrapper(f))
				.collect(Collectors.toList())
				.toArray(new FieldsWrapper[0]);
	}

}
