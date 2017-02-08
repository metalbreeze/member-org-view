package com.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.BaseObject;
import com.shop.dao.*;
import com.shop.model.*;

@Service
public class UserServiceImpl extends BaseObject implements UserService , UserDetailsService{
	
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
	@Transactional
	public List<User> listAvailableUsers() {
		return this.UserDAO.listAvailableUsers();
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException{
		if(this.UserDAO==null)logger.debug("userDao null");
		User u = this.UserDAO.getUserByName(arg0);
		if ( u == null ){
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(u.getName(), u.getPassword(), 
		                 true, true, true, true, getGrantedAuthorities(u));
	}
	
	@Transactional(readOnly=true)
	private List<GrantedAuthority> getGrantedAuthorities(User user){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		for(Profile up : user.getProfiles()){
			authorities.add(new SimpleGrantedAuthority("ROLE_"+up.getName()));
		}
		return authorities;
	}
    public void autologin(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

//        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }
}
