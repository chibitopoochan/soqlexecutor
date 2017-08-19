package com.gmail.chibitopoochan.soqlexec.ui;

import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.ENV;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.ID;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.PWD;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.QUERY;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.SET;

import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.Option;

public abstract class AbstractProcessor implements Processor {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(AbstractProcessor.class);
	private static final ResourceBundle properties = ResourceBundle.getBundle(Constants.Properties.RESOURCE);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	// SOQL実行に使用するパラメータ
	private String username;
	private String password;
	private String soql;
	private String env;
	private boolean more;
	private boolean all;

	// SOQLの実行クラス
	private SOQLExecutor executor;

	/**
	 * 標準のSOQL実行インスタンスを持つ
	 * AbstractProcessorのコンストラクタ
	 */
	public AbstractProcessor() {
		setSOQLExecutor(new SOQLExecutor());
	}

	/**
	 * SOQL実行クラスの設定
	 * @param executor
	 */
	public void setSOQLExecutor(SOQLExecutor executor) {
		this.executor = executor;
	}

	/**
	 * パラメータの設定.
	 * キー項目、値は入力値チェック済みを想定
	 * @param parameter パラメータのキー項目／値のセット
	 */
	@Override
	public void setParameter(Map<String, String> parameter) {
		// 必須パラメータを取得
		username = parameter.get(ID);
		password = parameter.get(PWD);

		if(parameter.containsKey(QUERY)) {
			soql = parameter.get(QUERY);
		} else {
			soql = "";
		}

		// 任意パラメータを取得
		if(parameter.containsKey(ENV)) {
			env = parameter.get(ENV);
		} else {
			env = properties.getString(Constants.Properties.UserInterface.AUTH_END_POINT);
		}

		// SETオプションのパラメータを取得
		if(parameter.containsKey(SET)) {
			String options = parameter.get(SET);
			String[] option = options.split(Option.DELIMITA);
			for(String s : option) {
				String[] values = s.split(Option.SIGN);
				if(Option.MORE.equals(values[0])){
					more = Boolean.parseBoolean(values[1]);
				}
				if(Option.ALL.equals(values[0])){
					all = Boolean.parseBoolean(values[1]);
				}
			}
		}

		logger.info(resources.getString(Constants.Message.Information.MSG_010), username, password, env, soql, all, more);

	}

	protected String getQuery() {
		return soql;
	}

	protected String getUsername() {
		return username;
	}

	protected String getPassword() {
		return password;
	}

	protected boolean isMore() {
		return more;
	}

	protected boolean isAll() {
		return all;
	}

	protected String getEnv() {
		return env;
	}

	protected SOQLExecutor getSOQLExecutor() {
		return executor;
	}

}
