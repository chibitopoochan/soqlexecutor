package com.gmail.chibitopoochan.soqlexec.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter;

/**
 * CUI処理を振り分けるクラス
 */
public class ProcessorFactoryImpl extends ProcessorFactory {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	private CommandProcessor command = new CommandProcessor();
	private DialogProcessor dialog = new DialogProcessor();
	private InvalidProcessor invalid = new InvalidProcessor();

	/**
	 * コマンド形式の処理を行うProcessorの設定
	 * @param command コマンド形式の処理を行うProcessor
	 */
	public void setCommandProcessor(CommandProcessor command) {
		this.command = command;
	}

	/**
	 * 対話形式の処理を行うProcessorの設定
	 * @param dialog 対話形式の処理を行うProcessor
	 */
	public void setDialog(DialogProcessor dialog) {
		this.dialog = dialog;
	}

	/**
	 * 処理を行わない（不正を表す）Processorの設定
	 * @param invalid 不正を表すProcessor
	 */
	public void setInvalid(InvalidProcessor invalid) {
		this.invalid  = invalid;
	}

	/**
	 * CUI処理の実行インスタンスを生成
	 * @param args 実行時パラメータ
	 * @return CUI処理を実行インスタンス
	 */
	@Override
	public Processor buildProcessor(String[] args) {
		Map<String, String> argPearMap = new HashMap<>();

		// パラメータをMapに詰める
		String key = null;
		for(String arg : args) {
			if(key == null) {
				key = arg;
			} else {
				argPearMap.put(key, arg);
				key = null;
			}
		}
		if(key != null) {
			argPearMap.put(key, "");
		}

		// 処理するProcessorの決定
		Processor processor = null;
		if(!isCorrectParameter(argPearMap)) {
			processor = invalid;
		} else if(argPearMap.containsKey(Parameter.QUERY)) {
			processor = command;
		} else {
			processor = dialog;
		}
		processor.setParameter(argPearMap);

		return processor;

	}

	/**
	 * 実行時パラメータの値チェック.
	 * @param argPearMap パラメータのキーと値のMap
	 * @return
	 */
	private boolean isCorrectParameter(Map<String, String> argPearMap) {
		// 値が存在するか？
		if(!argPearMap.keySet().stream().allMatch(k -> argPearMap.get(k).length() != 0)) {
			logger.error(resources.getString(Constants.Message.Error.ERR_006));
			logger.error(resources.getString(Constants.Message.Information.MSG_014));
			return false;
		}

		// 存在するパラメータか？
		if(!argPearMap.keySet().stream().allMatch(ProcessorFactoryImpl::isExistParameter)){
			logger.error(resources.getString(Constants.Message.Error.ERR_011));
			logger.error(resources.getString(Constants.Message.Information.MSG_014));
			return false;
		}

		// 必須項目が存在するか
		if(!argPearMap.containsKey(Parameter.ID) || !argPearMap.containsKey(Parameter.PWD)) {
			logger.error(resources.getString(Constants.Message.Error.ERR_011));
			logger.error(resources.getString(Constants.Message.Information.MSG_014));
			return false;
		}

		// 値の形式が正しいか？(SETのみ検証)
		if(argPearMap.containsKey(Parameter.SET)) {
			// SETの指定値を解析
			String value = argPearMap.get(Parameter.SET);
			for(String option : value.split(Parameter.Option.DELIMITA)) {
				String[] pear = option.split(Parameter.Option.SIGN);

				// ペアになっているか？
				if(pear.length != 2){
					logger.error(resources.getString(Constants.Message.Error.ERR_007));
					return false;
				}

				// 存在するオプションか？
				if(!Parameter.Option.ALL.equals(pear[0])
						&& !Parameter.Option.MORE.equals(pear[0])) {
					logger.error(resources.getString(Constants.Message.Error.ERR_008), pear[0]);
					return false;
				}

			}

		}

		return true;

	}

	/**
	 * パラメータが存在するかチェック
	 * @param value 値
	 * @return 存在するならtrue
	 */
	public static boolean isExistParameter(String value) {
		return Parameter.ID.equals(value)
				|| Parameter.PWD.equals(value)
				|| Parameter.QUERY.equals(value)
				|| Parameter.ENV.equals(value)
				|| Parameter.SET.equals(value);
	}

}
