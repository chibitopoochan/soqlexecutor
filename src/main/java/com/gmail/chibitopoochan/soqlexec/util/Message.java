package com.gmail.chibitopoochan.soqlexec.util;

import java.util.ResourceBundle;

public class Message {
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	/**
	 * リソースのメッセージにパラメータを埋め込んだ文字列を返します.
	 * @param key リソースキー
	 * @param parameter パラメータ
	 * @return メッセージ
	 */
	public static String get(String key, String...parameter) {
		return String.format(resources.getString(key).replaceAll("\\{\\}", "%s"), (Object[]) parameter);
	}

}
