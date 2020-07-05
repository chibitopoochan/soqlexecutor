package com.gmail.chibitopoochan.soqlexec.soql;

public enum TokenType {
	 LPAREN		// (
	,RPAREN		// )
	,COMMA		// ,
	,PERIOD		// .
	,LESS		// <
	,EQUAL		// =
	,NOT		// !
	,COLON		// :
	,GREATER	// >
	,TEXT		// text
	,LITERAL   // 'text'
	,EOF		// EOF
	,MINUS		// -
	,NOT_EQUAL	// !=
	,LESS_THAN	// <=
	,GREATER_THAN	// >=
	,DIGIT		// 0-9
	,INVALID	// invalid
	,SELECT		// SELECT
	,FROM		// FROM
	,WHERE		// WHERE
	,AND		// AND
	,OR			// OR
	,NOT_SIGN	// NOT
	,IN			// IN
	,ORDER		// ORDER
	,BY			// BY
	,GROUP		// GROUP
	,HAVING		// HAVING
	,ASC		// ASC
	,DESC		// DESC
	,NULLS		// NULLS
	,LIMIT		// LIMIT
	,OFFSET		// OFFSET
	,FIRST		// FIRST
	,LAST		// LAST
	,LIKE		// LIKE
	,INCLUDES	// INCLUDES
	,EXCLUDES	// EXCLUDES
	,DATETIME	// 日時
	,DATE;		// 日付

}
