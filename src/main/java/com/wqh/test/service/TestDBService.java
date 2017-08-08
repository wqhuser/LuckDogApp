package com.wqh.test.service;

import java.util.List;

import com.wqh.test.vo.User;

public interface TestDBService {

	public User getUserById(int id);

	public int addUser(User user);

	public List getAllUsers();
}
