package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.DescribeSObjectResultWrapper;
/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
import com.sforce.soap.partner.DescribeSObjectResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class PartnerDescribeSObjectResultWrapper implements DescribeSObjectResultWrapper {
	private DescribeSObjectResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public PartnerDescribeSObjectResultWrapper(){}

	/**
	 * {@link com.sforce.soap.partner.DescribeSObjectResult}をラップ
	 * @param result ラップ対象
	 */
	public PartnerDescribeSObjectResultWrapper(DescribeSObjectResult result) {
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
	public PartnerFieldsWrapper[] getFields() {
		return Arrays.stream(result.getFields())
				.map(f -> new PartnerFieldsWrapper(f))
				.collect(Collectors.toList())
				.toArray(new PartnerFieldsWrapper[0]);
	}

}
