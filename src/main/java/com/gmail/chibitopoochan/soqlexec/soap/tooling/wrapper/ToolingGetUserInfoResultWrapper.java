package com.gmail.chibitopoochan.soqlexec.soap.tooling.wrapper;

import com.gmail.chibitopoochan.soqlexec.soap.wrapper.GetUserInfoResultWrapper;
import com.sforce.soap.tooling.GetUserInfoResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class ToolingGetUserInfoResultWrapper implements GetUserInfoResultWrapper {
	private GetUserInfoResult result;

	/**
	 * {@link com.sforce.soap.partner.GetUserInfoResult}をラップ
	 * @param result ラップ対象
	 */
	public ToolingGetUserInfoResultWrapper(GetUserInfoResult userInfo) {
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
