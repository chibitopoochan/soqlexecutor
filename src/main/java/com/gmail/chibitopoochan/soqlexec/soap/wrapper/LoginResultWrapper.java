package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

import com.sforce.soap.partner.LoginResult;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public class LoginResultWrapper {
	private LoginResult result;

	/**
	 * ラップ対象を持たせずにインスタンス化
	 */
	public LoginResultWrapper(){}

	/**
	 * {@link com.sforce.soap.partner.LoginResult}をラップ
	 * @param result ラップ対象
	 */
	public LoginResultWrapper(LoginResult result) {
		setLoginResult(result);
	}

	/**
	 * {@link com.sforce.soap.partner.LoginResult}をラップ
	 * @param result ラップ対象
	 */
	public void setLoginResult(LoginResult result) {
		this.result = result;
	}

	/**
	 * {@link com.sforce.soap.partner.LoginResult#setSessionId(String)}をラップ
	 * @param sessionId セッションID
	 */
	public void setSessionId(String sessionId) {
		result.setSessionId(sessionId);
	}

	/**
	 * {@link com.sforce.soap.partner.LoginResult#getSessionId()}をラップ
	 * @return セッションID
	 */
	public String getSessionId() {
		return result.getSessionId();
	}

	/**
	 * {@link com.sforce.soap.partner.LoginResult#setServerUrl(String)}をラップ
	 * @param serverUrl 接続先URL
	 */
	public void setServerUrl(String serverUrl) {
		result.setServerUrl(serverUrl);
	}

	/**
	 * {@link com.sforce.soap.partner.LoginResult#getServerUrl()}をラップ
	 * @return 接続先URL
	 */
	public String getServerUrl() {
		return result.getServerUrl();
	}

}
