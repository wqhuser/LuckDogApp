package com.wqh.test.ui;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wqh.test.service.TestService;

@Controller
public class TestController {

	private TestService service;
	
	@Autowired
	public TestController(TestService service) {
		this.service = service;
	}

	@RequestMapping({"/test"})
	public String showHomePage(Map<String, Object> model) {
		model.put("title", service.getTitle(1));
		return "home";
	}

}
