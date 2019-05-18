package com.gmail.chibitopoochan.soqlexec.ui;

import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Command.QUIT;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Command.SEPARATE;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Command.SET;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.Option.ALL;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.Option.DELIMITA;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.Option.MORE;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.Option.SIGN;
import static com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter.Option.TOOL;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.soap.SOQLExecutor;
import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Constants.Message.Information;

/**
 * 対話形式でSOQLを実行.
 * <h3>対話形式</h3>
 * SOQLExecutor -id [username] -pwd [password] (ENV | OPTION | PROXY)
 * ENV:-env [URL]
 * OPTION:-set all=[true/false];more=[true/false];tool=[true/false]
 * PROXY:-proxy host=[URL];port=[port];id=[id];pwd=[pwd]
 */
public class DialogProcessor extends AbstractProcessor {
	// 入力待ちの記号
	public static final String WAIT_SIGN = ">";

	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(DialogProcessor.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	// 状態管理
	private StringBuilder query = new StringBuilder(); // クエリのバッファ
	private boolean inQuery; // クエリの受付中か
	private boolean exit; // 処理が終了したか
	private boolean occurredError; // 一度でもエラーが発生したか
	private Optional<String> errorMessage = Optional.empty();

	// 入出力先（初期は標準入出力）
	private InputStream in = System.in;

	/**
	 * SOQLの実行
	 */
	@Override
	public void execute() {
		PrintWriter writer = new PrintWriter(getOutputStream(), true);

		// SFDCへ接続
		writer.println(resources.getString(Information.MSG_012));
		SalesforceConnectionFactory factory;
		if(isProxyConnection()) {
			// Proxyを使用
			factory = SalesforceConnectionFactory.newInstance(getEnv(), getUsername(), getPassword(), isTool(),
					getProxyHost(), getProxyPort(), getProxyUsername(), getPassword());
		} else {
			factory = SalesforceConnectionFactory.newInstance(getEnv(), getUsername(), getPassword(), isTool());
		}
		if(!factory.login()) {
			occurredError = true;
			return;
		}
		writer.println(resources.getString(Information.MSG_013));

		// パラメータを指定
		SOQLExecutor executor = getSOQLExecutor();
		executor.setPartnerConnection(factory.getPartnerConnection());
		executor.setAllOption(isAll());

		// ユーザ入力を処理
		try(Scanner scan = new Scanner(in)){
			writer.printf(WAIT_SIGN);
			while(!exit && scan.hasNextLine()) {
				analyzeLine(scan.nextLine());
				errorMessage.ifPresent(writer::println);
				errorMessage = Optional.empty();
				if(!exit) writer.printf(WAIT_SIGN);
			}

		}

	}

	/**
	 * 入力された行の実行.
	 * @param line 入力行
	 */
	private void analyzeLine(String line) {
		logger.debug(resources.getString(Constants.Message.Information.MSG_011), line);

		if(!inQuery) analyzeCommand(line);
		if(inQuery) analyzeQuery(line);

	}

	/**
	 * 入力行をクエリとして実行.
	 * 行の最後が「;」以外なら、入力途中として保持
	 * @param line 入力行
	 */
	private void analyzeQuery(String line) {
		// 行をクエリに追加
		query.append(line);

		// 最後が「;」で無ければ入力中と判断
		if(!line.endsWith(DELIMITA)) {
			query.append(System.lineSeparator());
			return;
		}

		// 実行前のクエリの整備
		setQuery(query.toString().substring(0, query.length()-1));

		try {
			executeSOQL();
		} catch (Exception e) {
			logger.warn(resources.getString(Constants.Message.Error.ERR_010), e);
			errorMessage = Optional.of(e.toString());
			occurredError = true;
		} finally {
			query.setLength(0);
			inQuery = false;
		}

	}

	/**
	 * オプションの設定を実行
	 * @param key オプション
	 * @param value 設定値
	 */
	private void analyzeSetOption(String key, boolean value) {
		if(MORE.equals(key)) {
			setMore(value);
		} else if(ALL.equals(key)) {
			getSOQLExecutor().setAllOption(value);
		} else if(TOOL.equals(key)) {
			setTool(value);
		} else {
			logger.warn(resources.getString(Constants.Message.Error.ERR_008), key);
			errorMessage = Optional.of(resources.getString(Constants.Message.Error.ERR_011));
			occurredError = true;
		}
	}

	/**
	 * 入力行をコマンドとして実行.
	 * @param elements 入力行
	 */
	private void analyzeCommand(String line) {
		String[] elements = line.toLowerCase().split(SEPARATE);

		switch(elements[0]) {
		case QUIT:
			exit = true;
			break;
		case SET:
			if(elements.length != 2) {
				logger.warn(resources.getString(Constants.Message.Error.ERR_007));
				occurredError = true;
				break;
			}

			String[] parameters = elements[1].split(SIGN);

			if(parameters.length != 2) {
				logger.warn(resources.getString(Constants.Message.Error.ERR_007));
				errorMessage = Optional.of(resources.getString(Constants.Message.Error.ERR_012));
				occurredError = true;
				break;
			}
			analyzeSetOption(parameters[0], Boolean.valueOf(parameters[1]));
			break;
		default:
			inQuery = true;
			break;
		}
	}

	/**
	 * 入力元の指定.
	 * @param in 入力元
	 */
	public void setInputStream(InputStream in) {
		this.in = in;
	}

	/**
	 * 処理の終了
	 * @return 処理が終了しているならtrue
	 */
	public boolean isExit() {
		return exit;
	}

	/**
	 * エラーの発生.
	 * @return 一度でもエラーが発生していればtrue
	 */
	public boolean isOccurredError() {
		return occurredError;
	}
}
