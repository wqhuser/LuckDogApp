package com.wqh.test.vo;

public class User {
	private Integer userId;

	private String userName;

	private Byte userAge;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public Byte getUserAge() {
		return userAge;
	}

	public void setUserAge(Byte userAge) {
		this.userAge = userAge;
	}
}