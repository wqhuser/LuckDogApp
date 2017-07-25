package com.wqh.test.ui;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wqh.test.service.TestService;

@Controller
public class TestController {

	@Resource
	private TestService service;

	@RequestMapping({"/","/home"})
	public String showHomePage(Map<String, Object> model) {
		model.put("title", service.getTitle(1));
		return "home";
	}

}
