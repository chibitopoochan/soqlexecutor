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
			public static final String ERR_005 = "ERR.005";
			public static final String ERR_006 = "ERR.006";
			public static final String ERR_007 = "ERR.007";
			public static final String ERR_008 = "ERR.008";
			public static final String ERR_009 = "ERR.009";
			public static final String ERR_010 = "ERR.010";
			public static final String ERR_011 = "ERR.011";
			public static final String ERR_012 = "ERR.012";
			public static final String ERR_013 = "ERR.013";
		}

		public class Information {
			public static final String MSG_001 = "MSG.001";
			public static final String MSG_002 = "MSG.002";
			public static final String MSG_003 = "MSG.003";
			public static final String MSG_004 = "MSG.004";
			public static final String MSG_005 = "MSG.005";
			public static final String MSG_006 = "MSG.006";
			public static final String MSG_007 = "MSG.007";
			public static final String MSG_008 = "MSG.008";
			public static final String MSG_009 = "MSG.009";
			public static final String MSG_010 = "MSG.010";
			public static final String MSG_011 = "MSG.011";
			public static final String MSG_012 = "MSG.012";
			public static final String MSG_013 = "MSG.013";
			public static final String MSG_014 = "MSG.014";
		}

	}

	public class Properties {
		public static final String RESOURCE = "Properties";
		public static final String AUTH_END_POINT = "authEndPoint";
	}

	/**
	 * ユーザインタフェースで使用する引数、コマンド
	 */
	public class UserInterface {
		/**
		 * 対話形式のコマンド
		 */
		public class Command {
			public static final String QUIT = "quit";
			public static final String SET = "set";
			public static final String SEPARATE = " ";
		}

		/**
		 * コマンド形式のパラメータ
		 */
		public class Parameter {
			public static final String PREFIX 	= "-";
			public static final String ID		= PREFIX + "id";
			public static final String PWD		= PREFIX + "pwd";
			public static final String QUERY	= PREFIX + "query";
			public static final String ENV		= PREFIX + "env";
			public static final String SET		= PREFIX + "set";
			public static final String PROXY	= PREFIX + "proxy";

			public class Option {
				public static final String ALL = "all";
				public static final String MORE = "more";
				public static final String DELIMITA = ";";
				public static final String SIGN = "=";
				public static final String TOOL = "tool";
			}

			public class Proxy {
				public static final String HOST = "host";
				public static final String PORT = "port";
				public static final String ID = "id";
				public static final String PWD = "pwd";
			}
		}

	}

	/**
	 * SOQLに関連する定数
	 */
	public class SOQL {
		public static final String FIELD_SEPARATE_SIGN = ",";
		public static final String FIELD_RELATION_SIGN = "\\.";
		public static final String GROUPING_ANOTATION = "expr";

		public class Pattern {
			public static final String SELECT_FIELDS = "select\\s+([a-z0-9_.\\s,\\(\\)]+)\\s+from.+";
			public static final String FROM_FIELD = "\\s+from\\s+([a-z_]+)";
			public static final String LABEL_FIELDS = "toLabel\\(([a-z0-9_.]+)\\)";
			public static final String COUNT_FIELDS = "([a-z_]+)\\([a-z0-9_.]+\\)";
			public static final String QUERY_FIELDS = "\\((.+?)\\)";
			public static final String FORMAT_FIELDS = "format\\(([a-z0-9_.]+)\\)";
		}
	}

}
