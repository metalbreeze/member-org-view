package com.shop.service;

import java.util.List;

import com.shop.model.User;



public interface UserService {

	public void addUser(User p);
	public void updateUser(User p);
	public List<User> listUsers();
	public User getUserById(int id);
	public void removeUser(int id);
	public List<User> listAvailableUsers();
	void persist(User p);
	
}
