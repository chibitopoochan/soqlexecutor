package com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.GetUserInfoResultWrapper;
import com.sforce.soap.partner.GetUserInfoResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class PartnerGetUserInfoResultWrapper implements GetUserInfoResultWrapper {
	private GetUserInfoResult result;

	/**
	 * {@link com.sforce.soap.partner.GetUserInfoResult}をラップ
	 * @param result ラップ対象
	 */
	public PartnerGetUserInfoResultWrapper(GetUserInfoResult userInfo) {
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
