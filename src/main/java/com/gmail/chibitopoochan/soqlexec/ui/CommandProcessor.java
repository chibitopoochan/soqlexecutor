package com.gmail.chibitopoochan.soqlexec.ui;

import java.util.ResourceBundle;

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

	private boolean occurredError;

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

		// SOQLを実行
		try {
			executeSOQL();
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
