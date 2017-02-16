package com.shop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addUser(User p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("User saved successfully, User Details="+p);
	}

	@Override
	public void updateUser(User p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("User updated successfully, User Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UsersList = session.createQuery("from User").list();
		return UsersList;
	}

	@Override
	public User getUserById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		User p = (User) session.load(User.class, new Integer(id));
		logger.info("User loaded successfully, User details="+p);
		return p;
	}

	@Override
	public void removeUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User p = (User) session.load(User.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("User deleted successfully, User details="+p);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> listAvailableUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UsersList = session.createQuery("from User where group_id is null or group_id=0").list();
		return UsersList;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> listAvailableReporterCenterUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> UsersList = session.createQuery("from User where reportCenter_id is null or reportCenter_id=0").list();
		return UsersList;
	}
	@Override
	@Transactional
	public User getUserByName(String s) {
		Session session = this.sessionFactory.getCurrentSession();
		 User u = (User)session.createQuery("from User where name=:name").setString("name", s).uniqueResult();
		 logger.debug("getUserByName"+s);
		return u;
	}

}
