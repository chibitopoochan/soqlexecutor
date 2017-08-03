package com.gmail.chibitopoochan.soqlexec.ui;

import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.ENV;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.ID;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.PWD;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.QUERY;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.SET;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;
import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.Option;
import com.sforce.ws.ConnectionException;

/**
 * コマンドの実行
 * <h3>コマンド形式</h3>
 * SOQLExecutor -id [username] -pwd [password] -query [SOQL] (-env URL | -set OPTION=VALUE;OPTION=VALUE...)
 */
public class CommandProcessor implements Processor {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
	private static final ResourceBundle properties = ResourceBundle.getBundle(Constants.Properties.RESOURCE);

	private String username;
	private String password;
	private String soql;
	private String env;
	private boolean more;
	private boolean all;

	private SOQLExecutor executor;

	public CommandProcessor() {
		executor = new SOQLExecutor();
	}

	/**
	 * パラメータの設定.
	 * キー項目、値は入力値チェック済みを想定
	 * @param parameter パラメータのキー項目／値のセット
	 */
	@Override
	public void setParameter(Map<String, String> parameter) {
		username = parameter.get(ID);
		password = parameter.get(PWD);
		soql = parameter.get(QUERY);

		if(parameter.containsKey(ENV)) {
			env = parameter.get(ENV);
		} else {
			env = properties.getString(Constants.Properties.UserInterface.AUTH_END_POINT);
		}

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

	}

	@Override
	public void execute() {
		// SFDCへ接続
		SalesforceConnectionFactory factory = new SalesforceConnectionFactory(env, username, password);
		if(factory.login()) {
			logger.info("");
		} else {
			logger.warn("");
			return;
		}

		// パラメータを指定
		executor.setPartnerConnection(factory.getConnectionWrapper());
		executor.setAllOption(all);
		executor.setMoreOption(more);

		try {
			List<Map<String, String>> result = executor.execute(soql);
			result.stream().flatMap(m -> m.keySet().stream().map(k -> k + "|" + m.get(k))).forEach(System.out::println);;
		} catch (ConnectionException e) {
			logger.error(e.toString());
			logger.warn("",e);
		}

		// ログアウト
		if(factory.logout()){
			logger.info("");
		} else {
			logger.warn("");
		}
	}

}
