package com.gmail.chibitopoochan.soqlexec.soql;

import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.AND;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.ASC;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.BY;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.COMMA;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.DATE;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.DATETIME;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.DESC;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.DIGIT;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.EOF;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.EQUAL;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.EXCLUDES;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.FIRST;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.FROM;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.GREATER;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.GREATER_THAN;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.GROUP;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.HAVING;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.IN;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.INCLUDES;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.LAST;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.LESS;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.LESS_THAN;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.LIKE;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.LIMIT;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.LITERAL;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.LPAREN;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.NOT;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.NOT_EQUAL;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.NOT_SIGN;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.NULLS;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.OFFSET;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.OR;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.ORDER;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.PERIOD;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.RPAREN;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.SELECT;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.TEXT;
import static com.gmail.chibitopoochan.soqlexec.soql.TokenType.WHERE;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 構文解析の実行
 * 下記の構文を元に解析する
 *
 * v47.0
 * SELECT fieldList [subquery][...]
 * [TYPEOF typeOfField whenExpression[...] elseExpression END][...]
 * FROM objectType[,...]
 * [USING SCOPE filterScope]
 * [WHERE conditionExpression]
 * [WITH [DATA CATEGORY] filteringExpression]
 * [GROUP BY {fieldGroupByList|ROLLUP (fieldSubtotalGroupByList)|CUBE (fieldSubtotalGroupByList)}
 * [HAVING havingConditionExpression] ]
 * [ORDER BY fieldOrderByList {ASC|DESC} [NULLS {FIRST|LAST}] ]
 * [LIMIT numberOfRowsToReturn]
 * [OFFSET numberOfRowsToSkip]
 * [FOR {VIEW  | REFERENCE}[,...] ]
 *       [ UPDATE {TRACKING|VIEWSTAT}[,...] ]
 *
 * BNF整理後
 * <query specification> ::= SELECT <select list> <table expression>
 * <select list> ::= <select sublist> [{<comma> <select sublist>}...]
 * <select sublist> ::= <value expression> | <subquery>
 * <value expression> ::= <function expression> | <string value expression>
 * <function expression> ::= <sentence> <left paren> <string value expression> <right paren>
 * <string value expression> ::= <sentence> [{<period> <sentence>}]
 * <subquery> ::= <left paren> <query specification> <right paren>
 * <table expression> ::= <from clause> [where clause] [group by clause] [having clause] [order by clause] [limit clause] [offset clause]
 * <from clause> ::= FROM <sentence>
 * <where clause> ::= WHERE <search condition list>
 * <search condition list> ::= <search condition> | <paren search condition>
 * <paren search condition> ::= <left paren> <search condition list> <right paren>
 * <search condition> ::= <field expression> [<logical operator> <field expression>]
 * <field expression> ::= <value expression> <condition> <search values>
 * <logical operator> ::= AND | OR
 * <search values> ::= <single value> | <multi values>
 * <single value> ::= <literal> | <number> | <datetime>
 * <multi values> ::= <left paren> <single value> [{<comma> <single value>}] <right paren>
 * <group by clause> ::= GROUP BY <value expression> [{<comma> <value expression>}]
 * <having clause> ::= HAVING <search condition>
 * <order by clause> ::= ORDER BY <value expression> [<order specify>] [<nulls specify>] {<comma> <value expression> [<ordder specify>] [<nulls specify>]}
 * <order specify> ::= ASC | DESC
 * <nulls specify> ::= NULLS (FIRST | LAST)
 * <limit clause> ::= LIMIT <number>
 * <offset clause> ::= OFFSET <number>
 * <number> ::= 数字(符号、小数あり)
 * <datetime> ::= 英数字と記号(:-)
 * <sentence> ::= 英数字と記号_
 * <condition> ::= < | > | = | ! | IN | NOT IN | != | LIKE | includes | excludes
 * <literal> ::= '文字列'
 * <left paren> ::= (
 * <right paren> ::= )
 *
 * サポート外
 * For,With,TypeOf,Using Scopeは対象外
 * 文字種別のチェックは行わない
 * Rollup, Cubeには未対応
 * 別名にも未対応
 *
 * @author mamet
 *
 */
public class QueryAnalyzeUtils {

	private TokenLexer lexer;

	private SOQL soql;

	private int functionIndex = 0;

	private QueryAnalyzeUtils(String query) {
		lexer = new TokenLexer(query);
		soql = new SOQL();
	}

	/**
	 * 構文解析の実行
	 * @throws Exception
	 */
	public static SOQL analyze(String query) throws TokenException {
		QueryAnalyzeUtils utils = new QueryAnalyzeUtils(query);
		return utils.querySpecification();
	}

	/**
	 * <query specification> ::= SELECT <select list> <table expression>
	 * @throws Exception
	 */
	public SOQL querySpecification() throws TokenException {
		subquerySpecification();
		check(EOF);
		return soql;
	}

	/**
	 * <subquery specification> ::= SELECT <select list> <table expression>
	 * @throws Exception
	 */
	public void subquerySpecification() throws TokenException {
		check(SELECT);
		selectList();
		tableExpression();
	}

	/**
	 *
	 * <select list> ::= <select sublist> [{<comma> <select sublist>}...]
	 * @throws Exception
	 */
	public void selectList()throws TokenException {
		selectSubList();
		while(nextIs(COMMA)) {
			check(COMMA);
			selectSubList();
		}
	}

	/**
	 *
	 * <select sublist> ::= <value expression> | <subquery>
	 */
	public void selectSubList() throws TokenException {
		if(nextIs(LPAREN)) {
			subquery(true);
		} else {
			soql.addSelectField(valueExpression());
		}

	}

	/**
	 * <value expression> ::= <function expression> | <string value expression>
	 */
	public SOQLField valueExpression() throws TokenException {
		SOQLField field;

		Token token = lexer.next();
		if(nextIs(LPAREN)) {
			field = functionExpression(token);

		} else {
			field = stringValueExpression(token);
		}

		return field;
	}

	/**
	 *
	 * <function expression> ::= <sentence> <left paren> <string value expression> <right paren>
	 * @throws Exception
	 */
	public SOQLField functionExpression(Token token) throws TokenException{
		check(token, TEXT);
		String name = token.getValue();
		check(LPAREN);
		SOQLField value = stringValueExpression(lexer.next());
		check(RPAREN);

		SOQLField field;
		if(SOQL.SUMMARY_FUNCTION.contains(token.getValue())) {
			field = new SOQLField("expr" + functionIndex);
			field.setLabel(String.format("%s(%s)",name,value));
			functionIndex++;
		} else {
			field = value;
			field.setLabel(String.format("%s(%s)",name,value));
		}

		return field;

	}

	/**
	 * <string value expression> ::= <sentence> [{<period> <sentence>}]
	 */
	public SOQLField stringValueExpression(Token token) throws TokenException {
		SOQLField field = new SOQLField(sentence(token));
		SOQLField parent = field;

		while(nextIs(PERIOD)) {
			check(PERIOD);
			SOQLField child = new SOQLField(sentence());
			parent.setRelation(child);
			parent = child;
		}

		return field;
	}

	public String sentence() throws TokenException {
		Token token = lexer.next();
		return sentence(token);
	}

	public String sentence(Token token) throws TokenException {
		check(token, TEXT);
		return token.getValue();
	}

	/**
	 * <subquery> ::= <left paren> <subquery specification> <right paren>
	 * @throws TokenException
	 */
	public void subquery(boolean lparenCheck) throws TokenException{
		SOQL parentQuery = soql;
		soql = new SOQL();

		if(lparenCheck) check(LPAREN);
		subquerySpecification();
		check(RPAREN);

		SOQLField field = new SOQLField(soql.getFromObject());
		field.setSubQuery(soql);
		parentQuery.addSelectField(field);
		soql = parentQuery;
	}

	/**
	 * <table expression> ::= <from clause> [where clause] [group by clause] [having clause] [order by clause] [limit clause] [offset clause]
	 * @throws TokenException
	 *
	 */
	public void tableExpression() throws TokenException{
		fromCaluse();

		if(nextIs(WHERE)) whereCaluse();
		if(nextIs(GROUP)) groupByClause();
		if(nextIs(HAVING)) havingClause();
		if(nextIs(ORDER)) orderByClause();
		if(nextIs(LIMIT)) limitClause();
		if(nextIs(OFFSET)) offsetClause();
	}

	/**
	 * <from clause> ::= FROM <sentence>
	 * @throws TokenException
	 *
	 */
	public void fromCaluse() throws TokenException{
		check(FROM);
		soql.setFromObject(sentence());
	}

	/**
	 * <where clause> ::= WHERE <search condition list>
	 * @throws TokenException
	 *
	 */
	public void whereCaluse() throws TokenException{
		check(WHERE);
		searchConditionList();
	}

	/**
	 * <search condition list> ::= <search condition> | <paren search condition>
	 * @throws TokenException
	 *
	 */
	public void searchConditionList() throws TokenException{
		if(nextIs(LPAREN)) {
			parenSearchCondition();
		} else {
			searchCondtion();
		}
	}

	/**
	 * <paren search condition> ::= <left paren> <search condition list> <right paren>
	 * @throws TokenException
	 *
	 */
	public void parenSearchCondition() throws TokenException{
		check(LPAREN);
		searchConditionList();
		check(RPAREN);
	}

	/**
	 * <search condition> ::= <field expression> [<logical operator> <field expression>]
	 * @throws TokenException
	 *
	 */
	public void searchCondtion() throws TokenException{
		fieldExpression();
		while(nextIs(AND,OR)) {
			logicalOperator();
			fieldExpression();
		}
	}

	/**
	 * <field expression> ::= <value expression> <condition> <search values>
	 * <condition> ::= < | > | = | ! | IN | NOT IN | != | LIKE
	 * @throws TokenException
	 *
	 */
	public void fieldExpression() throws TokenException{
		valueExpression();

		if(nextIs(NOT_SIGN)) {
			check(NOT_SIGN);
			check(LIKE, IN);
		} else {
			check(LESS,GREATER,EQUAL,LESS_THAN,GREATER_THAN, NOT,IN,NOT_EQUAL,LIKE,INCLUDES, EXCLUDES);
		}

		searchValues();

	}

	/**
	 * <logical operator> ::= AND | OR
	 * @throws TokenException
	 *
	 */
	public void logicalOperator() throws TokenException{
		check(AND, OR);
	}

	/**
	 * <search values> ::= <single value> | <multi values>
	 * @throws TokenException
	 *
	 */
	public void searchValues() throws TokenException {
		if(nextIs(LPAREN)) {
			multiValues();
		} else {
			singleValue();
		}
	}

	/**
	 * <single value> ::= <literal> | <number> | <datetime> | <sentence>
	 * @throws TokenException
	 *
	 */
	public void singleValue() throws TokenException{
		if(nextIs(LITERAL)) {
			check(LITERAL);
		} else if(nextIs(DIGIT)) {
			check(DIGIT);
		} else if(nextIs(DATETIME)) {
			check(DATETIME);
		} else if(nextIs(DATE)) {
			check(DATE);
		} else if(nextIs(TEXT)) {
			sentence();
		} else {
			throw new TokenException(lexer.peek(), "LITERAL || DIGIT || DATETIME || DATE || TEXT");
		}

	}

	/**
	 * <multi values> ::= <left paren> <sentence> [{<comma> <sentence>}] <right paren>
	 * @throws TokenException
	 *
	 */
	public void multiValues() throws TokenException{
		check(LPAREN);

		if(nextIs(SELECT)) {
			subquery(false);
		} else {
			singleValue();
			while(nextIs(COMMA)) {
				check(COMMA);
				singleValue();
			}
		}

		check(RPAREN);
	}

	/**
	 * <group by clause> ::= GROUP BY <value expression> [{<comma> <value expression>}]
	 * @throws TokenException
	 *
	 */
	public void groupByClause() throws TokenException{
		check(GROUP);
		check(BY);
		valueExpression();
		if(nextIs(COMMA)) {
			check(COMMA);
			valueExpression();
		}
	}

	/**
	 * <having clause> ::= HAVING <search condition>
	 * @throws TokenException
	 *
	 */
	public void havingClause() throws TokenException{
		check(HAVING);
		searchCondtion();
	}

	/**
	 * <order by clause> ::= ORDER BY <value expression> [<order specify>] [<nulls specify>]
	 * 						{<comma> <value expression> [<ordder specify>] [<nulls specify>]}
	 * @throws TokenException
	 *
	 */
	public void orderByClause() throws TokenException{
		check(ORDER);
		check(BY);
		valueExpression();
		if(nextIs(ASC,DESC)) orderSpecify();
		if(nextIs(NULLS)) nullsSpecify();

		while(nextIs(COMMA)) {
			check(COMMA);
			valueExpression();
			if(nextIs(ASC,DESC)) orderSpecify();
			if(nextIs(NULLS)) nullsSpecify();
		}

	}

	/**
	 * <order specify> ::= ASC | DESC
	 * @throws TokenException
	 *
	 */
	public void orderSpecify() throws TokenException{
		check(ASC,DESC);
	}

	/**
	 * <nulls specify> ::= NULLS (FIRST | LAST)
	 * @throws TokenException
	 *
	 */
	public void nullsSpecify() throws TokenException{
		check(NULLS);
		check(FIRST,LAST);
	}

	/**
	 * <limit clause> ::= LIMIT <number>
	 * @throws TokenException
	 *
	 */
	public void limitClause() throws TokenException{
		check(LIMIT);
		check(DIGIT);
	}

	/**
	 * <offset clause> ::= OFFSET <number>
	 * @throws TokenException
	 *
	 */
	public void offsetClause() throws TokenException{
		check(OFFSET);
		check(DIGIT);
	}

	private boolean nextIs(TokenType...expected) {
		TokenType token = lexer.peek().getType();
		return Stream.of(expected).anyMatch(t -> token == t);
	}

	private void check(TokenType...expected) throws TokenException {
		Token token = lexer.next();
		check(token, expected);
	}

	private void check(Token token, TokenType...expected) throws TokenException {
		if(Stream.of(expected).noneMatch(t -> token.getType() == t)){
			throw new TokenException(token, Stream.of(expected).map(t -> t.toString()).collect(Collectors.joining(",")));
		}
	}

	public class TokenException extends Exception {
		private static final long serialVersionUID = -3857861447620511157L;

		TokenException(Token token, String expected) {
			super(String.format("Unexpected token %s(%s) expected %s",token.getType(), token.getValue(), expected));
		}
	}
}
