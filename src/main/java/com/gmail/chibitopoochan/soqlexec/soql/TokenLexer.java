package com.gmail.chibitopoochan.soqlexec.soql;

import java.util.regex.Pattern;

/**
 * WHEREあり
select id, username from user where profileid = '00748914CdSc'and isActive = true or company__c = 'ABCDE'
 * サブクエリ、複雑なクエリあり
select id, username, (select id,name from ABC__r),company__c from Quote__c where createddate < 2019-11-10T11:03:038Z and number__c >= 89
and name like '%a<>add(b)cT2e%' or (discount_abc__c != null and picklist__c in ('Text\',','あああ')) and Step__c > -3932.1212
Order by username asc, id desc nulls first last
group by id
having count() > 1
 *
 * 課題
 * マイナス、日付、リテラルの１トークン化
 * @author mamet
 *
 */
public class TokenLexer {
	private char[] chars;
	private int index;

	public TokenLexer(String source) {
		chars = source.toCharArray();
		index = 0;
	}

	public Token peek() {
		int marker = index;
		Token token = next();
		index = marker;

		return token;
	}

	public Token next() {
		Token type;

		// サイズチェック
		if(chars.length <= index) {
			return new Token(TokenType.EOF);
		}

		char c = chars[index++];

		// 空白をスキップ
		while(Character.isWhitespace(c)) {
			if(chars.length <= index) {
				return new Token(TokenType.EOF);
			}
			c = chars[index++];
		}

		// 文字から字句を抽出
		switch(c) {
		case '(':
			type = new Token(TokenType.LPAREN);
			break;
		case ')':
			type = new Token(TokenType.RPAREN);
			break;
		case '=':
			type = new Token(TokenType.EQUAL);
			break;
		case '<':
			if(chars[index] == '=') {
				index++;
				type = new Token(TokenType.LESS_THAN);
			} else {
				type = new Token(TokenType.LESS);
			}
			break;
		case '>':
			if(chars[index] == '=') {
				index++;
				type = new Token(TokenType.GREATER_THAN);
			} else {
				type = new Token(TokenType.GREATER);
			}
			break;
		case '!':
			if(chars[index] == '=') {
				index++;
				type = new Token(TokenType.NOT_EQUAL);
			} else {
				type = new Token(TokenType.NOT_SIGN);
			}
			break;
		case ':':
			type = new Token(TokenType.COLON);
			break;
		case '.':
			type = new Token(TokenType.PERIOD);
			break;
		case ',':
			type = new Token(TokenType.COMMA);
			break;
		case '\'':
			type = analyzeLiteral();
			break;
		case '-':
			type = new Token(TokenType.MINUS);
			break;
		default:
			type = analyzeText(c);
			break;
		}

		return type;
	}

	private Token analyzeLiteral() {
		StringBuilder builder = new StringBuilder();
		boolean escapeFlag = false;

		while(chars.length > index) {
			if(!escapeFlag) {
				if(chars[index] == '\'') {
					index++;
					break;
				}

				if(chars[index] == '\\') {
					index++;
					escapeFlag = true;
					continue;
				}
			} else {
				escapeFlag = false;
			}

			builder.append(chars[index++]);

		}

		return new Token(TokenType.LITERAL, builder.toString());

	}

	private Token analyzeText(char c) {
		StringBuilder builder = new StringBuilder(Character.toString(c));
		boolean allDigit = Character.isDigit(c);

		while(chars.length > index && (
				Character.isLetterOrDigit(chars[index])
				|| chars[index] == '_'
				|| chars[index] == '-'
				|| chars[index] == ':'
				|| chars[index] == '.')) {
			if(!Character.isDigit(chars[index])) allDigit = false;
			if(!Character.isDigit(chars[index-1]) && chars[index] == '.') break;
			builder.append(chars[index++]);
		}

		if(allDigit) {
			return new Token(TokenType.DIGIT, builder.toString());
		} else {
			return analyzeLetter(builder.toString());
		}

	}

	private Token analyzeLetter(String s) {
		Token token = new Token(TokenType.TEXT, s);

		if(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z", s)) {
			token = new Token(TokenType.DATETIME, s);
		}
		if(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}", s)) {
			token = new Token(TokenType.DATE, s);
		}

		switch(s.toUpperCase()) {
		case "SELECT":
			token = new Token(TokenType.SELECT);
			break;
		case "FROM":
			token = new Token(TokenType.FROM);
			break;
		case "WHERE":
			token = new Token(TokenType.WHERE);
			break;
		case "AND":
			token = new Token(TokenType.AND);
			break;
		case "OR":
			token = new Token(TokenType.OR);
			break;
		case "NOT":
			token = new Token(TokenType.NOT);
			break;
		case "IN":
			token = new Token(TokenType.IN);
			break;
		case "GROUP":
			token = new Token(TokenType.GROUP);
			break;
		case "BY":
			token = new Token(TokenType.BY);
			break;
		case "ORDER":
			token = new Token(TokenType.ORDER);
			break;
		case "HAVING":
			token = new Token(TokenType.HAVING);
			break;
		case "NULLS":
			token = new Token(TokenType.NULLS);
			break;
		case "ASC":
			token = new Token(TokenType.ASC);
			break;
		case "DESC":
			token = new Token(TokenType.DESC);
			break;
		case "LIMIT":
			token = new Token(TokenType.LIMIT);
			break;
		case "OFFSET":
			token = new Token(TokenType.OFFSET);
			break;
		case "FIRST":
			token = new Token(TokenType.FIRST);
			break;
		case "LAST":
			token = new Token(TokenType.LAST);
			break;
		case "LIKE":
			token = new Token(TokenType.LIKE);
			break;
		case "INCLUDES":
			token = new Token(TokenType.INCLUDES);
			break;
		case "EXCLUDES":
			token = new Token(TokenType.EXCLUDES);
			break;

		}

		return token;
	}

}
