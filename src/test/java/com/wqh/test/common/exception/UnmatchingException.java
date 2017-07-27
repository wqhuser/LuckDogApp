package com.wqh.test.common.exception;

public class UnmatchingException extends Exception {
	
	private static final long serialVersionUID = 123456;

	public UnmatchingException(String message) {
		super(message);
	}

	public UnmatchingException(String message, Throwable e) {
		super(message, e);
	}

	public UnmatchingException(Throwable e) {
		super(e);
	}
}
