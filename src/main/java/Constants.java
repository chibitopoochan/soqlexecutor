/**
 * システム全般で使用する定数を定義
 */
public enum Constants {
	MESSAGE_RESOURCE("Message")
	,ERR_001("ERR.001")
	,ERR_002("ERR.002")
	,MSG_001("MSG.001")
	,MSG_002("MSG.002")
	;

	/** リソース名 */
	private String resourceName;

	/**
	 * リソース名を持つ定数
	 */
	private Constants(String value) {
		resourceName = value;
	}

	/**
	 * リソース名の取得
	 * @return
	 */
	public String getValue() {
		return resourceName;
	}
}
