package com.wqh.test.ui;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wqh.test.service.TestDBService;

@Controller
@RequestMapping("/user")
public class TestDBController {
	@Resource
	private TestDBService service;
	
	@RequestMapping("/list")
	public String showAllUser(Map<String, Object> model) {
		
		return "allUser";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String showUser(@RequestParam("userId") String id, Model model) {
		model.addAttribute("user", service.getUserById(Integer.valueOf(id)));
		return "user";
	}
}
