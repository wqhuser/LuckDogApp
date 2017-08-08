package com.wqh.test.test;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestLog4j {
	private static Logger logger = Logger.getLogger(TestLog4j.class);
	
	@Test
	public void test() {
		logger.info("info");
		logger.debug("debug");
		logger.error("error");
	}
}
