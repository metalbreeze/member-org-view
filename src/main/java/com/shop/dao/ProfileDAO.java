package com.shop.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.BaseObject;
import com.shop.model.Operation;
import com.shop.model.Profile;
import com.shop.model.ReportCenter;
import com.shop.model.User;

@Repository
public class ProfileDAO extends BaseObject {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	public void persistProfile (Profile p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.debug("Operation saved successfully, Operation Details="+p);
	}
	public void removeProfile (Profile p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(p);
		logger.debug("Operation saved successfully, Operation Details="+p);
	}
}
