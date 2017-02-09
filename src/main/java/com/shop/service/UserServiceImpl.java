package com.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.BaseObject;
import com.shop.dao.*;
import com.shop.model.*;

@Service
public class UserServiceImpl extends BaseObject implements UserService{
	
	private com.shop.dao.UserDAO userDAO;

	public void setUserDAO(UserDAO UserDAO) {
		this.userDAO = UserDAO;
	}

	@Override
	@Transactional
	public void addUser(User p) {
		this.userDAO.addUser(p);
	}

	@Override
	@Transactional
	public void updateUser(User p) {
		this.userDAO.updateUser(p);
	}

	@Override
	@Transactional
	public List<User> listUsers() {
		return this.userDAO.listUsers();
	}

	@Override
	@Transactional
	public User getUserById(int id) {
		return this.userDAO.getUserById(id);
	}

	@Override
	@Transactional
	public void removeUser(int id) {
		this.userDAO.removeUser(id);
	}

	@Override
	@Transactional
	public List<User> listAvailableUsers() {
		return this.userDAO.listAvailableUsers();
	}


}
