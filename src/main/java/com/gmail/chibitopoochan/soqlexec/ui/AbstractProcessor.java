package com.gmail.chibitopoochan.soqlexec.ui;

import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.ENV;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.ID;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.PWD;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.QUERY;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.SET;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.Option;
import com.sforce.ws.ConnectionException;

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

	// データの出力先（デフォルトは標準出力）
	private OutputStream out = System.out;

	// 列の区切り文字
	private String separate = "|";

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

	/**
	 * 出力先の指定
	 * @param out 出力先
	 */
	public void setOutputStream(OutputStream out) {
		this.out = out;
	}

	/**
	 * SOQLの実行.
	 * 実行結果は指定の出力先に出力
	 * @throws ConnectionException SOQL実行時エラー
	 */
	protected void executeSOQL() throws ConnectionException {
		try(PrintWriter writer = new PrintWriter(out)) {
			// クエリを実行
			List<Map<String, String>> result = getSOQLExecutor().execute(getQuery());

			// 結果が空なら処理終了
			if(result.isEmpty()) return;

			// ヘッダーを出力
			String[] headers = result.get(0).keySet().toArray(new String[0]);
			writer.println(Arrays.stream(headers).collect(Collectors.joining(separate)));

			// 行を出力
			result.stream().map(r ->
				Arrays.stream(headers).map(h -> r.get(h)).collect(Collectors.joining(separate))
			).forEach(writer::println);

			writer.flush();
		}
	}

	/**
	 * クエリの設定
	 * @param query クエリ
	 */
	protected void setQuery(String query) {
		this.soql = query;
	}

	/**
	 * クエリの取得
	 * @return クエリ
	 */
	protected String getQuery() {
		return soql;
	}

	/**
	 * ユーザ名の取得
	 * @return ユーザ名
	 */
	protected String getUsername() {
		return username;
	}

	/**
	 * パスワードの取得
	 * @return パスワード
	 */
	protected String getPassword() {
		return password;
	}

	/**
	 * Moreオプションの取得
	 * @return 有効ならtrue
	 */
	protected boolean isMore() {
		return more;
	}

	/**
	 * Allオプションの取得
	 * @return 有効ならtrue
	 */
	protected boolean isAll() {
		return all;
	}

	/**
	 * エンドポイントの取得
	 * @return エンドポイント（URL）
	 */
	protected String getEnv() {
		return env;
	}

	/**
	 * SOQL実行インスタンスの取得
	 * @return SOQLExecutor
	 */
	protected SOQLExecutor getSOQLExecutor() {
		return executor;
	}

}
