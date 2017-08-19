package com.gmail.chibitopoochan.soqlexec.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.gmail.chibitopoochan.soqlexec.ui.mock.ProcessorMock;

/**
 * SOQLExecutorのテストクラス
 */
public class SOQLExecutorTest {
	private String[] args = new String[]{"param1","param2"};
	private ProcessorMock mock = new ProcessorMock();

	@Before public void setup() {
		ProcessorFactory.setProcessorFactory(new ProcessorFactory() {
			@Override
			public Processor buildProcessor(String[] args) {
				Map<String, String> map = new HashMap<>();
				map.put(args[0], args[1]);
				mock.setParameter(map);
				return mock;
			}
		});

	}

	@Test public void testMain() {
		SOQLExecutor.main(args);

		assertTrue(mock.isExecute());
		assertThat(mock.getParameter().get(args[0]),is(args[1]));

	}

}
