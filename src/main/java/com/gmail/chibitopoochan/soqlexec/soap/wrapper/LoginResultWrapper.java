package com.gmail.chibitopoochan.soqlexec.soap.wrapper;

/**
 * SalesforceAPIのラップ.
 * 実API呼び出しを分離して依存を下げます。
 */
public interface LoginResultWrapper {
	/**
	 * {@link com.sforce.soap.partner.LoginResult#setSessionId(String)}をラップ
	 * @param sessionId セッションID
	 */
	public void setSessionId(String sessionId);

	/**
	 * {@link com.sforce.soap.partner.LoginResult#getSessionId()}をラップ
	 * @return セッションID
	 */
	public String getSessionId();

	/**
	 * {@link com.sforce.soap.partner.LoginResult#setServerUrl(String)}をラップ
	 * @param serverUrl 接続先URL
	 */
	public void setServerUrl(String serverUrl);

	/**
	 * {@link com.sforce.soap.partner.LoginResult#getServerUrl()}をラップ
	 * @return 接続先URL
	 */
	public String getServerUrl();

}
