package com.shop.dao;

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
import com.shop.model.User;

@Repository
public class OperationDAOImpl extends BaseObject {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void addOperation (Operation p) {
		Session session = this.sessionFactory.getCurrentSession();
		p.setOperationDate(new Timestamp(System.currentTimeMillis()));
		session.persist(p);
		logger.info("User saved successfully, User Details="+p);
	}


	@SuppressWarnings("unchecked")
	public List<Operation> listOperations() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Operation> l = session.createQuery("from Operation").list();
		return l;
	}
	public Operation getOperationById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Operation p = (Operation) session.load(Operation.class, new Integer(id));
		logger.info("User loaded successfully, Operation details="+p);
		return p;
	}
}
