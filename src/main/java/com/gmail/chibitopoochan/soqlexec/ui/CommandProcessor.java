package com.gmail.chibitopoochan.soqlexec.ui;

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
import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.sforce.ws.ConnectionException;

/**
 * コマンドの実行
 * <h3>コマンド形式</h3>
 * SOQLExecutor -id [username] -pwd [password] -query [SOQL] (-env URL | -set OPTION=VALUE;OPTION=VALUE...)
 */
public class CommandProcessor extends AbstractProcessor {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	private OutputStream out = System.out;
	private boolean occurredError;

	// 列の区切り文字
	private String separate = "|";

	public void setOutputStream(OutputStream out) {
		this.out = out;
	}

	/**
	 * SOQL処理の実行
	 */
	@Override
	public void execute() {
		// SFDCへ接続
		SalesforceConnectionFactory factory =
				SalesforceConnectionFactory.newInstance(getEnv(), getUsername(), getPassword());
		if(!factory.login()) {
			occurredError = true;
			return;
		}

		// パラメータを指定
		SOQLExecutor executor = getSOQLExecutor();
		executor.setPartnerConnection(factory.getPartnerConnection());
		executor.setAllOption(isAll());
		executor.setMoreOption(isMore());

		// SOQLを実行
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
		} catch (ConnectionException e) {
			logger.error(resources.getString(Constants.Message.Error.ERR_009), e.toString());
			logger.debug(resources.getString(Constants.Message.Error.ERR_010),e);
			occurredError = true;
		} finally {
			// ログアウト
			factory.logout();

		}

	}

	/**
	 * エラーの発生.
	 * @return 一度でもエラーが発生していればtrue
	 */
	public boolean isOccurredError() {
		return occurredError;
	}

}
