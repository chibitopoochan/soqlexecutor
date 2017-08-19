package com.gmail.chibitopoochan.soqlexec.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.gmail.chibitopoochan.soqlexec.soap.SalesforceConnectionFactory;
import com.gmail.chibitopoochan.soqlexec.soap.mock.PartnerConnectionWrapperMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.SOQLExecutorMock;
import com.gmail.chibitopoochan.soqlexec.soap.mock.SalesforceConnectionFactoryMock;
import com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter;

public class CommandProcessorTest {
	private CommandProcessor processor;
	private Map<String, String> parameter;
	private SOQLExecutorMock executor;
	private SalesforceConnectionFactoryMock factory;
	private PartnerConnectionWrapperMock partner;
	private ByteArrayOutputStream toTest;
	private SOQLExecutorMock.QueryMoreMock more;

	@Before public void setup() {
		// モックを作成
		executor = new SOQLExecutorMock();
		toTest = new ByteArrayOutputStream();
		partner = new PartnerConnectionWrapperMock();
		processor = new CommandProcessor();
		factory = new SalesforceConnectionFactoryMock();
		more = executor.new QueryMoreMock();

		// 共通のパラメータを設定
		parameter = new HashMap<>();
		parameter.put(Parameter.ID, "id");
		parameter.put(Parameter.PWD, "pwd");

		// 参照先を指定
		executor.setQueryMore(more);
		processor.setSOQLExecutor(executor);
		processor.setOutputStream(toTest);
		factory.setPartnerConnection(partner);
		SalesforceConnectionFactory.setSalesforceConnectionFactory(factory);

	}

	/**
	 * SOQLが正常に実行されるケース
	 * 取得結果が１件のパターン
	 */
	@Test public void testQuerySingleRecord() {
		// パラメータを設定
		String query = "select id from user";
		parameter.put(Parameter.QUERY, query);
		processor.setParameter(parameter);

		// 実行結果を設定
		Map<String, String> record = new HashMap<>();
		record.put("id", "value");
		List<Map<String, String>> result = new LinkedList<>();
		result.add(record);
		executor.putResult(query, result);
		more.setMore(result);

		processor.execute();

		assertTrue(factory.isLogin());
		assertThat(executor.getPartnerConnection(), is(partner));
		assertFalse(executor.getAllOption());
		assertTrue(factory.isLogout());

		StringBuilder out = new StringBuilder();
		out.append("id")
		.append(System.lineSeparator())
		.append("value")
		.append(System.lineSeparator());

		assertThat(toTest.toString(), is(out.toString()));

	}

	/**
	 * SOQLが正常に実行されるケース
	 * 取得結果が複数件のパターン
	 */
	@Test public void testQueryMultiRecord() {
		// パラメータを設定
		String query = "select id,email from user";
		parameter.put(Parameter.QUERY, query);
		processor.setParameter(parameter);

		// 実行結果を設定
		Map<String, String> record = new HashMap<>();
		record.put("id", "id1");
		record.put("email", "email1");
		Map<String, String> record2 = new HashMap<>();
		record2.put("id", "id2");
		record2.put("email", "email2");

		List<Map<String, String>> result = new LinkedList<>();
		result.add(record);
		result.add(record2);
		executor.putResult(query, result);
		more.setMore(result);

		processor.execute();

		assertTrue(factory.isLogin());
		assertThat(executor.getPartnerConnection(), is(partner));
		assertFalse(executor.getAllOption());
		assertTrue(factory.isLogout());

		StringBuilder out = new StringBuilder();
		out.append("id|email")
		.append(System.lineSeparator())
		.append("id1|email1")
		.append(System.lineSeparator())
		.append("id2|email2")
		.append(System.lineSeparator());

		assertThat(toTest.toString(), is(out.toString()));

	}

	/**
	 * SOQLが正常に実行されるケース
	 * 取得結果が0件のパターン
	 */
	@Test public void testQueryNoRecord() {
		// パラメータを設定
		String query = "select id,email from user";
		parameter.put(Parameter.QUERY, query);
		processor.setParameter(parameter);

		// 実行結果を設定
		List<Map<String, String>> result = new LinkedList<>();
		executor.putResult(query, result);
		more.setMore(result);

		processor.execute();

		assertTrue(factory.isLogin());
		assertThat(executor.getPartnerConnection(), is(partner));
		assertFalse(executor.getAllOption());
		assertTrue(factory.isLogout());

		StringBuilder out = new StringBuilder();

		assertThat(toTest.toString(), is(out.toString()));

	}

	/**
	 * Moreで追加レコードを取得するケース
	 */
	@Test public void testQueryMoreRecord() {
		// パラメータを設定
		String query = "select id from user";
		parameter.put(Parameter.QUERY, query);
		parameter.put(Parameter.SET, Parameter.Option.MORE + Parameter.Option.SIGN + "true");
		processor.setParameter(parameter);

		// 実行結果を設定
		Map<String, String> record = new HashMap<>();
		record.put("id", "value");
		List<Map<String, String>> result = new LinkedList<>();
		result.add(record);
		executor.putResult(query, result);
		more.setMore(result);

		processor.execute();

		assertTrue(factory.isLogin());
		assertThat(executor.getPartnerConnection(), is(partner));
		assertFalse(executor.getAllOption());
		assertTrue(factory.isLogout());

		StringBuilder out = new StringBuilder();
		out
		.append("id")
		.append(System.lineSeparator())
		.append("value")
		.append(System.lineSeparator())
		.append("value")
		.append(System.lineSeparator());

		assertThat(toTest.toString(), is(out.toString()));

	}

	/**
	 * SOQLでエラーが発生するケース
	 */
	@Test public void testSOQLFailed() {
		// パラメータを設定
		String query = "select id,email from user";
		parameter.put(Parameter.QUERY, query);
		processor.setParameter(parameter);

		executor.setError(true);

		processor.execute();

		assertTrue(processor.isOccurredError());

	}

	/**
	 * ログインエラーが発生するケース
	 */
	@Test public void testLoginError() {
		// パラメータを設定
		String query = "select id,email from user";
		parameter.put(Parameter.QUERY, query);
		processor.setParameter(parameter);

		factory.setLoginError(true);

		processor.execute();

		assertTrue(processor.isOccurredError());

	}

}
