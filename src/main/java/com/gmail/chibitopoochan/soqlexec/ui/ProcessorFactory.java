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
public class ProcessorFactory {
	// クラス共通の参照
	private static final Logger logger = LoggerFactory.getLogger(CommandProcessor.class);
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	// テスト用にインスタンスを経由して処理する
	private static ProcessorFactory instance = new ProcessorFactory();

	/**
	 * 処理振り分けを行うインスタンスの設定
	 * @param instance 処理振り分けを行うインスタンス
	 */
	public void setProcessorFactory(ProcessorFactory instance) {
		ProcessorFactory.instance = instance;
	}

	/**
	 * CUI処理の実行インスタンスを取得
	 * @param args 実行時パラメータ
	 * @return CUI処理を実行インスタンス
	 */
	public static Processor newProcessor(String[] args) {
		return instance.buildProcessor(args);
	}

	/**
	 * CUI処理の実行インスタンスを生成
	 * @param args 実行時パラメータ
	 * @return CUI処理を実行インスタンス
	 */
	private Processor buildProcessor(String[] args) {
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

		if(!isCorrectParameter(argPearMap)) {
			return new InvalidProcessor();
		}

		// コマンド形式を構築
		Processor processor = new CommandProcessor();
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
		if(!argPearMap.keySet().stream().allMatch(k -> argPearMap.containsKey(k))) {
			logger.error(resources.getString(Constants.Message.Error.ERR_006));
			return false;
		}

		// 存在するパラメータか？
		if(argPearMap.keySet().stream().noneMatch(ProcessorFactory::isExistParameter)){
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
