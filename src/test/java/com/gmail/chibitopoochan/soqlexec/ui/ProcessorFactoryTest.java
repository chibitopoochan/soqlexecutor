package com.gmail.chibitopoochan.soqlexec.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gmail.chibitopoochan.soqlexec.ui.mock.ProcessorMock;

/**
 * ProcessorFactoryのテストクラス
 */
public class ProcessorFactoryTest {
	private ProcessorMock mock = new ProcessorMock();
	private Map<String, String> parameter = new HashMap<>();

	/**
	 * Processor作成の検証
	 */
	@Test public void testNewProcessor() {
		ProcessorFactory.setProcessorFactory(new ProcessorFactory() {
			@Override
			public Processor buildProcessor(String[] args) {
				mock.setParameter(parameter);
				return mock;
			}
		});

		ProcessorMock result = (ProcessorMock) ProcessorFactory.newProcessor(null);
		assertThat(result, is(mock));
		assertThat(result.getParameter(), is(parameter));

	}

}
