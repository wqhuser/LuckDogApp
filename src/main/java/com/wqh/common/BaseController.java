package com.wqh.common;

import java.util.Map;

import org.apache.log4j.Logger;

public abstract class BaseController {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	public abstract String performTask(Map<String, Object> model) throws Exception;
}
