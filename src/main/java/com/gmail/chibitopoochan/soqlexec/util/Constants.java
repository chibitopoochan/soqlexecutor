package com.gmail.chibitopoochan.soqlexec.util;
/**
 * システム全般で使用する定数を定義
 */
public class Constants {

	/**
	 *  ユーザやログに表示するメッセージ
	 */
	public class Message {
		public static final String RESOURCE = "Message";

		public class Error {
			public static final String ERR_001 = "ERR.001";
			public static final String ERR_002 = "ERR.002";
			public static final String ERR_003 = "ERR.003";
			public static final String ERR_004 = "ERR.004";
		}

		public class Information {
			public static final String MSG_001 = "MSG.001";
			public static final String MSG_002 = "MSG.002";
			public static final String MSG_003 = "MSG.003";
			public static final String MSG_004 = "MSG.004";
			public static final String MSG_005 = "MSG.005";
		}

	}

	/**
	 * SOQLに関連する定数
	 */
	public class SOQL {
		public static final String FIELD_SEPARATE_SIGN = ",";
		public static final String FIELD_RELATION_SIGN = "\\.";
		public static final String SUBQUERY_LEFT_SIGN = "(";

		public class Pattern {
			public static final String SELECT_FIELDS = "select\\s+([a-z0-9_.\\s,]+)\\s+from.+";
		}
	}

}
