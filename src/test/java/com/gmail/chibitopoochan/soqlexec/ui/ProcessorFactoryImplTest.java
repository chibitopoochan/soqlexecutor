package com.gmail.chibitopoochan.soqlexec.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.gmail.chibitopoochan.soqlexec.ui.mock.CommandProcessorMock;
import com.gmail.chibitopoochan.soqlexec.ui.mock.DialogProcessorMock;
import com.gmail.chibitopoochan.soqlexec.ui.mock.InvalidProcessorMock;
import com.gmail.chibitopoochan.soqlexec.util.Constants.UserInterface.Parameter;

public class ProcessorFactoryImplTest {
	private ProcessorFactoryImpl factory;
	private DialogProcessorMock dialog;
	private CommandProcessorMock command;
	private InvalidProcessorMock invalid;

	/**
	 * テスト前の共通設定
	 */
	@Before public void setup() {
		// モックを生成
		dialog = new DialogProcessorMock();
		command = new CommandProcessorMock();
		invalid = new InvalidProcessorMock();

		// 検証対象にモックを設定
		factory = new ProcessorFactoryImpl();
		factory.setCommandProcessor(command);
		factory.setDialog(dialog);
		factory.setInvalid(invalid);

	}

	/**
	 * コマンド形式の必須パラメータのみのケース
	 */
	@Test public void testCommand() {
		String[] parameter = {Parameter.ID,"id",Parameter.PWD, "pwd", Parameter.QUERY,"query"};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(command));

		Map<String, String> resultParameter = command.getParameter();
		assertThat(resultParameter.get(Parameter.ID), is(parameter[1]));
		assertThat(resultParameter.get(Parameter.PWD), is(parameter[3]));
		assertThat(resultParameter.get(Parameter.QUERY), is(parameter[5]));

	}

	/**
	 * 対話形式の必須パラメータのみのケース
	 */
	@Test public void testDialog() {
		String[] parameter = {Parameter.ID,"id",Parameter.PWD, "pwd"};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(dialog));

		Map<String, String> resultParameter = dialog.getParameter();
		assertThat(resultParameter.get(Parameter.ID), is(parameter[1]));
		assertThat(resultParameter.get(Parameter.PWD), is(parameter[3]));

	}

	/**
	 * コマンド形式の最大パラメータのケース
	 */
	@Test public void testCommandAllParameter() {
		String[] parameter = {
				Parameter.ID,"id",
				Parameter.PWD, "pwd",
				Parameter.QUERY,"query",
				Parameter.ENV, "env",
				Parameter.SET, "all=true;more=FALSE"};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(command));

		Map<String, String> resultParameter = command.getParameter();
		assertThat(resultParameter.get(Parameter.ID), is(parameter[1]));
		assertThat(resultParameter.get(Parameter.PWD), is(parameter[3]));
		assertThat(resultParameter.get(Parameter.QUERY), is(parameter[5]));
		assertThat(resultParameter.get(Parameter.ENV), is(parameter[7]));
		assertThat(resultParameter.get(Parameter.SET), is(parameter[9]));

	}

	/**
	 * 対話形式の最大パラメータのケース
	 */
	@Test public void testDialogAllParameter() {
		String[] parameter = {
				Parameter.ID,"id",
				Parameter.PWD, "pwd",
				Parameter.ENV, "env",
				Parameter.SET, "all=false;more=false"};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(dialog));

		Map<String, String> resultParameter = dialog.getParameter();
		assertThat(resultParameter.get(Parameter.ID), is(parameter[1]));
		assertThat(resultParameter.get(Parameter.PWD), is(parameter[3]));
		assertThat(resultParameter.get(Parameter.ENV), is(parameter[5]));
		assertThat(resultParameter.get(Parameter.SET), is(parameter[7]));

	}

	/**
	 * パラメータが無いケース
	 */
	@Test public void testNoParameter() {
		String[] parameter = {};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(invalid));

	}

	/**
	 * パラメータの値がないケース
	 */
	@Test public void testMissingValue() {
		String[] parameter = {Parameter.ID,"id",Parameter.PWD};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(invalid));

	}

	/**
	 * パラメータ名が不正なケース
	 */
	@Test public void testInvalidName() {
		String[] parameter = {
				Parameter.ID,"id",
				"p", "pwd",
				Parameter.ENV, "env",
				Parameter.SET, "all=false;more=false"};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(invalid));

	}

	/**
	 * SETパラメータの形式が不正なケース
	 * パラメータ名が不正なパターン
	 */
	@Test public void testInvalidSetOption() {
		String[] parameter = {
				Parameter.ID,"id",
				Parameter.PWD, "pwd",
				Parameter.ENV, "env",
				Parameter.SET, "ALL=false;more=false"};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(invalid));

	}

	/**
	 * SETパラメータの形式が不正なケース
	 * 値がないパターン
	 */
	@Test public void testMissingSetOption() {
		String[] parameter = {
				Parameter.ID,"id",
				Parameter.PWD, "pwd",
				Parameter.ENV, "env",
				Parameter.SET, "more="};

		Processor result = factory.buildProcessor(parameter);

		assertThat(result, is(invalid));

	}

	/**
	 * パラメータチェックの検証
	 */
	@Test public void testParameterCheck() {
		// 各パラメータで検証
		assertTrue(ProcessorFactoryImpl.isExistParameter(Parameter.ID));
		assertTrue(ProcessorFactoryImpl.isExistParameter(Parameter.PWD));
		assertTrue(ProcessorFactoryImpl.isExistParameter(Parameter.QUERY));
		assertTrue(ProcessorFactoryImpl.isExistParameter(Parameter.ENV));
		assertTrue(ProcessorFactoryImpl.isExistParameter(Parameter.SET));

		// 存在しないパラメータで検証
		assertFalse(ProcessorFactoryImpl.isExistParameter("test"));

	}
}
