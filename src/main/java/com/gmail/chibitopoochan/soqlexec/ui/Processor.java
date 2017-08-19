package com.gmail.chibitopoochan.soqlexec.ui;

import java.util.Map;

import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;

/**
 * ユーザの要求を処理する
 */
public interface Processor {

	/**
	 * 処理の実行
	 */
	void execute();

	/**
	 * パラメータの設定
	 * @param parameter パラメータ
	 */
	void setParameter(Map<String, String> parameter);

	/**
	 * SOQL実行インスタンスの設定
	 * @param executor SOQL実行インスタンス
	 */
	void setSOQLExecutor(SOQLExecutor executor);

}
