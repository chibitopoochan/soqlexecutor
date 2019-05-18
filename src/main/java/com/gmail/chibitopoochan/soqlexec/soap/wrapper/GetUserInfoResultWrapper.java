package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public interface GetUserInfoResultWrapper {
	/**
	 * {@link com.sforce.soap.partner.GetUserInfoResult#getOrganizationId()}をラップ
	 * @return 組織ID
	 */
	public String getOrganizationId();

}
