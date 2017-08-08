package com.wqh.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wqh.test.dao.UserMapper;
import com.wqh.test.service.TestDBService;
import com.wqh.test.vo.User;

@Service
public class TestDBServiceImpl implements TestDBService {

	@Resource
	private UserMapper dao;

	public User getUserById(int id) {
		return dao.selectByPrimaryKey(id);
	}

	public int addUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getAllUsers() {
		return null;
	}

}
