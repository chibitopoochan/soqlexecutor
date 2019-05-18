package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public interface DescribeSObjectResultWrapper {
	/**
	 * DescribeSObjectResult#getFields()をラップ
	 * @param result 項目一覧
	 */
	public FieldsWrapper[] getFields();

}
