package com.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.dao.*;
import com.shop.model.*;

@Service
public class UserServiceImpl implements UserService {
	
	private com.shop.dao.UserDAO UserDAO;

	public void setUserDAO(UserDAO UserDAO) {
		this.UserDAO = UserDAO;
	}

	@Override
	@Transactional
	public void addUser(User p) {
		this.UserDAO.addUser(p);
	}

	@Override
	@Transactional
	public void updateUser(User p) {
		this.UserDAO.updateUser(p);
	}

	@Override
	@Transactional
	public List<User> listUsers() {
		return this.UserDAO.listUsers();
	}

	@Override
	@Transactional
	public User getUserById(int id) {
		return this.UserDAO.getUserById(id);
	}

	@Override
	@Transactional
	public void removeUser(int id) {
		this.UserDAO.removeUser(id);
	}

	@Override
	public List<User> listAvailableUsers() {
		return this.UserDAO.listAvailableUsers();
	}

}
