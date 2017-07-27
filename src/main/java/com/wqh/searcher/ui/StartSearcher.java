package com.wqh.searcher.ui;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wqh.common.BaseController;
import com.wqh.searcher.utils.TestMain;

@Controller
public class StartSearcher extends BaseController{

	@Override
	@RequestMapping({"/","StartSearcher"})
	public String performTask(Map<String, Object> model) throws Exception {
		TestMain test = new TestMain();
		File file = test.test();
		logger.debug(file.getAbsolutePath());
		model.put("current", new StringBuilder(this.getClass().toString()).append(" started at ").append(new Date()));
		return "start";
	}
	
}
