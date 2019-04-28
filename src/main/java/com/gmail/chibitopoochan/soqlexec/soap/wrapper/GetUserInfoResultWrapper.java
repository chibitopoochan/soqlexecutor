package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import com.sforce.soap.partner.GetUserInfoResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class GetUserInfoResultWrapper {
	private GetUserInfoResult result;

	/**
	 * {@link com.sforce.soap.partner.GetUserInfoResult}をラップ
	 * @param result ラップ対象
	 */
	public GetUserInfoResultWrapper(GetUserInfoResult userInfo) {
		result = userInfo;
	}

	/**
	 * {@link com.sforce.soap.partner.GetUserInfoResult#getOrganizationId()}をラップ
	 * @return 組織ID
	 */
	public String getOrganizationId() {
		return result.getOrganizationId();
	}

}
