package com.gmail.chibitopoochan.soqlexec.ui;

/**
 * パラメータから適切なProcessorを選出.
 */
public abstract class ProcessorFactory {

	// テスト用にインスタンスを経由して処理する
	private static ProcessorFactory instance = new ProcessorFactoryImpl();

	/**
	 * 処理振り分けを行うインスタンスの設定
	 * @param instance 処理振り分けを行うインスタンス
	 */
	public static void setProcessorFactory(ProcessorFactory instance) {
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
	public abstract Processor buildProcessor(String[] args);

}
