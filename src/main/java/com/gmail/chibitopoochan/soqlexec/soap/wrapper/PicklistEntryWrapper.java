package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public interface PicklistEntryWrapper {
	/**
	 * {@link com.sforce.soap.partner.PicklistEntry#isActive()}のラップ
	 * @return 選択肢が有効ならtrue
	 */
	public boolean isActive();

	/**
	 * {@link com.sforce.soap.partner.PicklistEntry#getValue()}のラップ
	 * @return 選択肢の値
	 */
	public String getValue();

}
