package com.wqh.test.dao;

import java.util.List;

import com.wqh.test.vo.User;

public interface TestDAO {
	public User getUserById(int id);

	public int insertUser(User user);

	public List getAll(int i);
}
