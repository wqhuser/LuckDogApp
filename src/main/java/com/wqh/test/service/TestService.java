package com.wqh.test.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
	public static final String[] TEST_ARRAY = { "empty", "Hello world" };

	public static String[] getTestArray() {
		return TEST_ARRAY;
	}

	public String getTitle(int i) {
		return getTestArray()[i];
	}
}
