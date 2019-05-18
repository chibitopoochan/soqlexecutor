package com.gmail.chibitopoochan.soqlexec.soap.mock;

import com.gmail.chibitopoochan.soqlexec.soap.partner.wrapper.PartnerLoginResultWrapper;

public class LoginResultWrapperMock extends PartnerLoginResultWrapper {
	private String sessionId;
	private String serverUrl;

	/**
	 *
	 * @param sessionId
	 */
	@Override
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getSessionId() {
		return this.sessionId;
	}

	/**
	 *
	 * @param serverUrl
	 */
	@Override
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public String getServerUrl() {
		return serverUrl;
	}

}
