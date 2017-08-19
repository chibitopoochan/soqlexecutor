package com.gmail.chibitopoochan.soqlexec.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;

import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.soap.mock.PartnerConnectionWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.SOQLExecutorMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.SalesforceConnectionFactoryMock;
import com.gmail.chibitopoochan.soqlexec.util.Constants;
import com.gmail.chibitopoochan.soqlexec.util.Constants.Message.Information;
import com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter;

/**
 * DialogProcessorのテストクラス
 */
public class DialogProcessorTest {
	private static final ResourceBundle resources = ResourceBundle.getBundle(Constants.Message.RESOURCE);

	// 検証に使用するインスタンス
	private SalesforceConnectionFactoryMock factory;
	private SOQLExecutorMock executor;
	private PartnerConnectionWrapperMock connection;
	private DialogProcessor pro;

	/**
	 * テスト前の共通設定
	 */
	@Before public void setup() {
		// オプションの設定
		StringBuilder option = new StringBuilder();
		option.append(Parameter.Option.ALL)
		.append(Parameter.Option.SIGN)
		.append("false")
		.append(Parameter.Option.DELIMITA)
		.append(Parameter.Option.MORE)
		.append(Parameter.Option.SIGN)
		.append("false");

		// パラメータの設定
		Map<String, String> parameter = new HashMap<>();
		parameter.put(Parameter.ID,  "Id");
		parameter.put(Parameter.PWD, "Pwd");
		parameter.put(Parameter.ENV, "Env");
		parameter.put(Parameter.SET, option.toString());

		// 検証に使用するモック
		connection = new PartnerConnectionWrapperMock();
		factory = new SalesforceConnectionFactoryMock();
		factory.setPartnerConnection(connection);
		executor = new SOQLExecutorMock();
		pro = new DialogProcessor();
		pro.setSOQLExecutor(executor);
		pro.setParameter(parameter);
		SalesforceConnectionFactory.setSalesforceConnectionFactory(factory);

	}

	/**
	 * 「quit」で終了するケース
	 * 小文字のパターン
	 * @throws IOException
	 */
	@Test public void testLowerCommand() throws IOException {
		ByteArrayInputStream byTest = new ByteArrayInputStream("quit".getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));

	}

	/**
	 * 「Quit」で終了するケース
	 * 一部大文字のパターン
	 * @throws IOException
	 */
	@Test public void testSomeLowerCommand() throws IOException {
		ByteArrayInputStream byTest = new ByteArrayInputStream("Quit".getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));

	}

	/**
	 * 「QUIT」で終了するケース
	 * 大文字のパターン
	 * @throws IOException
	 */
	@Test public void testUpperCommand() throws IOException {
		ByteArrayInputStream byTest = new ByteArrayInputStream("QUIT".getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));

	}

	/**
	 * 「quit;」で終了しないケース
	 * @throws IOException
	 */
	@Test public void testCommandFailed() throws IOException {
		ByteArrayInputStream byTest = new ByteArrayInputStream("quit;".getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertFalse(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));

	}

	/**
	 * 「set all=true」でALLが有効になるケース
	 * 小文字のパターン
	 */
	@Test public void testAllOption() {
		StringBuilder input = new StringBuilder();
		input.append("set all=true")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertTrue(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));

	}

	/**
	 * 「SET more=true」でMOREが有効になるケース
	 * 大文字のパターン
	 */
	@Test public void testMoreOption() {
		StringBuilder input = new StringBuilder();
		input.append("SET more=true")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertTrue(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));

	}

	/**
	 * 「set all=true;」でfalseが設定されるケース
	 * ";"はオプションでは不要
	 */
	@Test public void testAllOptionFailed() {
		StringBuilder input = new StringBuilder();
		input.append("set all=true")
		.append(System.lineSeparator())
		.append("set all=true;")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
	}

	/**
	 * 「set」「;」でエラーになるケース
	 * キー、値がないパターン
	 */
	@Test public void testSetHasNotParameter() {
		StringBuilder input = new StringBuilder();
		input.append("set")
		.append(System.lineSeparator())
		.append(";")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertTrue(pro.isOccurredError());
	}

	/**
	 * 「set =true」でエラーになるケース
	 * キーがないパターン
	 */
	@Test public void testSetHasNotKey() {
		StringBuilder input = new StringBuilder();
		input.append("set =true")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertTrue(pro.isOccurredError());
	}

	/**
	 * 「set all=」でエラーになるケース
	 * 値がないパターン
	 */
	@Test public void testSetHasNotValue() {
		StringBuilder input = new StringBuilder();
		input.append("set all=")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertTrue(pro.isOccurredError());

	}

	/**
	 * 「set abc=true」でエラーになるケース
	 * キー名が正しくないパターン
	 */
	@Test public void testSetInvalidKey() {
		StringBuilder input = new StringBuilder();
		input.append("set abc=true")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertTrue(pro.isOccurredError());

	}

	/**
	 * 「set all=abc」でfalseが設定されるケース
	 * 不正な値のパターン
	 * "true"以外はすべてfalse扱いになる
	 */
	@Test public void testSetInvalidValue() {
		StringBuilder input = new StringBuilder();
		input.append("set all=true")
		.append(System.lineSeparator())
		.append("set all=abc")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

	}

	/**
	 * 「set all = true」でエラーになるケース
	 * 無効な値が含まれるパターン
	 * "="の前後はスペースは入れられない
	 */
	@Test public void testSetInvalidDelimita() {
		StringBuilder input = new StringBuilder();
		input.append("set all = true")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertTrue(pro.isOccurredError());

	}

	/**
	 * 「SELECT Id From user;」「quit」で正常終了するケース
	 * 検索結果一行を取得できるケース
	 */
	@Test public void testQuerySingleRecord() {
		// 検索結果
		Map<String, String> result1 = new HashMap<>();
		result1.put("Id", "result1");

		List<Map<String, String>> result = new ArrayList<>();
		result.add(result1);

		executor.putResult("SELECT Id From user", result);

		// 入力
		StringBuilder input = new StringBuilder();
		input.append("SELECT Id From user;")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

		StringBuilder output = new StringBuilder();
		output
		.append(resources.getString(Information.MSG_012))
		.append(System.lineSeparator())
		.append(resources.getString(Information.MSG_013))
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN)
		.append("Id")
		.append(System.lineSeparator())
		.append("result1")
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN);
		assertThat(toTest.toByteArray(), is(output.toString().getBytes()));

	}

	/**
	 * 「SELECT Id From user;」「quit」で正常終了するケース
	 * 検索結果二行を取得できるケース
	 */
	@Test public void testQueryMultiRecord() {
		// 検索結果
		Map<String, String> result1 = new LinkedHashMap<>();
		result1.put("Id", "Id1");
		result1.put("name", "name1");
		result1.put("email", "email1");

		Map<String, String> result2 = new LinkedHashMap<>();
		result2.put("Id", "Id2");
		result2.put("name", "name2");
		result2.put("email", "email2");

		List<Map<String, String>> result = new ArrayList<>();
		result.add(result1);
		result.add(result2);

		executor.putResult("SELECT Id,Name,Email From user", result);

		// 入力
		StringBuilder input = new StringBuilder();
		input.append("SELECT Id,Name,Email From user;")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

		StringBuilder output = new StringBuilder();
		output
		.append(resources.getString(Information.MSG_012))
		.append(System.lineSeparator())
		.append(resources.getString(Information.MSG_013))
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN)
		.append("Id|name|email")
		.append(System.lineSeparator())
		.append("Id1|name1|email1")
		.append(System.lineSeparator())
		.append("Id2|name2|email2")
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN);
		assertThat(toTest.toString(), is(output.toString()));

	}

	/**
	 * 「SELECT Id From user;」「quit」で正常終了
	 * 検索結果が０行のパターン
	 */
	@Test public void testQueryNoRecord() {
		List<Map<String, String>> result = new ArrayList<>();

		executor.putResult("SELECT Id From user", result);

		StringBuilder input = new StringBuilder();
		input.append("SELECT Id From user;")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

		StringBuilder output = new StringBuilder();
		output
		.append(resources.getString(Information.MSG_012))
		.append(System.lineSeparator())
		.append(resources.getString(Information.MSG_013))
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN)
		.append(DialogProcessor.WAIT_SIGN);

		assertThat(toTest.toString(), is(output.toString()));

	}

	/**
	 * 「SELECT Id From user;」を二回実行して、「quit」で正常終了
	 * クエリを二回実行するパターン
	 */
	@Test public void testMultiQuery() {
		// 検索結果
		Map<String, String> result1 = new HashMap<>();
		result1.put("Id", "result1");

		List<Map<String, String>> result = new ArrayList<>();
		result.add(result1);

		executor.putResult("SELECT Id From user", result);

		// 入力
		StringBuilder input = new StringBuilder();
		input.append("SELECT Id From user;")
		.append(System.lineSeparator())
		.append("SELECT Id From user;")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

		StringBuilder output = new StringBuilder();
		output
		.append(resources.getString(Information.MSG_012))
		.append(System.lineSeparator())
		.append(resources.getString(Information.MSG_013))
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN)
		.append("Id")
		.append(System.lineSeparator())
		.append("result1")
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN)
		.append("Id")
		.append(System.lineSeparator())
		.append("result1")
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN);
		assertThat(toTest.toString(), is(output.toString()));

	}

	/**
	 * 「SELECT Id From user」「quit」でエラーとならず、終了しないケース
	 * ";"が無いとSOQLの入力中と判定される
	 */
	@Test public void testInvalidQuery() {
		List<Map<String, String>> result = new ArrayList<>();

		executor.putResult("SELECT Id From user\nquit", result);

		StringBuilder input = new StringBuilder();
		input.append("SELECT Id From user")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertFalse(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

		StringBuilder output = new StringBuilder();
		output
		.append(resources.getString(Information.MSG_012))
		.append(System.lineSeparator())
		.append(resources.getString(Information.MSG_013))
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN)
		.append(DialogProcessor.WAIT_SIGN)
		.append(DialogProcessor.WAIT_SIGN);

		assertThat(toTest.toString(), is(output.toString()));

	}

	/**
	 * 「select id from user where name="abc;def";」でエラーにならないケース
	 * 最後の";"以外は解釈しない
	 */
	@Test public void testQueryWithSemicoron() {
		Map<String, String> result1 = new HashMap<>();
		result1.put("Id", "result1");

		List<Map<String, String>> result = new ArrayList<>();
		result.add(result1);

		executor.putResult("SELECT Id From user where name=\"abc;def\"", result);

		StringBuilder input = new StringBuilder();
		input.append("SELECT Id From user where name=\"abc;def\";")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

		StringBuilder output = new StringBuilder();
		output
		.append(resources.getString(Information.MSG_012))
		.append(System.lineSeparator())
		.append(resources.getString(Information.MSG_013))
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN)
		.append("Id")
		.append(System.lineSeparator())
		.append("result1")
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN);
		assertThat(toTest.toString(), is(output.toString()));

	}

	/**
	 * 「select id from user where name="abc;def;」はエラーが発生しない
	 * SOQL自体は不正なのでSalesforceからエラーが返る
	 * SOQLが正しい形式なのかはチェックしない
	 */
	@Test public void testInvalidQueryParameter() {
		Map<String, String> result1 = new HashMap<>();
		result1.put("Id", "result1");

		List<Map<String, String>> result = new ArrayList<>();
		result.add(result1);

		executor.putResult("SELECT Id From user where name=\"abc;def", result);

		StringBuilder input = new StringBuilder();
		input.append("SELECT Id From user where name=\"abc;def;")
		.append(System.lineSeparator());

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertFalse(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

	}

	/**
	 * 「select   id<改行><改行>from user<改行>;」でエラーにならないケース
	 * 改行を含んでもエラーにならない
	 */
	@Test public void testQueryWithReturnCode() {
		Map<String, String> result1 = new HashMap<>();
		result1.put("Id", "result1");

		List<Map<String, String>> result = new ArrayList<>();
		result.add(result1);

		StringBuilder query = new StringBuilder();
		query.append("select   id")
		.append(System.lineSeparator())
		.append(System.lineSeparator())
		.append("from user")
		.append(System.lineSeparator());

		executor.putResult(query.toString(), result);

		StringBuilder input = new StringBuilder();
		input.append(query.toString())
		.append(";")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();

		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);
		pro.execute();

		assertTrue(factory.isLogin());
		assertFalse(executor.getAllOption());
		assertFalse(executor.getMoreOption());
		assertTrue(pro.isExit());
		assertThat(executor.getPartnerConnection(), is(connection));
		assertFalse(pro.isOccurredError());

		StringBuilder output = new StringBuilder();
		output
		.append(resources.getString(Information.MSG_012))
		.append(System.lineSeparator())
		.append(resources.getString(Information.MSG_013))
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN)
		.append(DialogProcessor.WAIT_SIGN)
		.append(DialogProcessor.WAIT_SIGN)
		.append(DialogProcessor.WAIT_SIGN)
		.append("Id")
		.append(System.lineSeparator())
		.append("result1")
		.append(System.lineSeparator())
		.append(DialogProcessor.WAIT_SIGN);
		assertThat(toTest.toString(), is(output.toString()));

	}

	/**
	 * ログインエラーになるケース
	 */
	@Test public void testLoginError() {
		factory.setLoginError(true);

		pro.execute();

		assertTrue(pro.isOccurredError());

	}

	/**
	 * クエリ実行時にエラーが発生するケース
	 */
	@Test public void testSOQLFailed() {
		// 検索結果
		Map<String, String> result1 = new HashMap<>();
		result1.put("Id", "result1");

		List<Map<String, String>> result = new ArrayList<>();
		result.add(result1);

		executor.putResult("SELECT Id From user", result);

		// 入力
		StringBuilder input = new StringBuilder();
		input.append("SELECT Id From user;")
		.append(System.lineSeparator())
		.append("quit");

		ByteArrayInputStream byTest = new ByteArrayInputStream(input.toString().getBytes());
		ByteArrayOutputStream toTest = new ByteArrayOutputStream();
		pro.setInputStream(byTest);
		pro.setOutputStream(toTest);

		executor.setError(true);

		pro.execute();

		assertTrue(pro.isOccurredError());

	}

}
