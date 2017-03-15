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
import com.shop.model.SiteOption;
import com.shop.model.User;

@Repository
public class SiteOptionDAO extends BaseObject {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void addSiteOption (SiteOption p) {
		Session session = this.sessionFactory.getCurrentSession();
		logger.debug(p.toString());
		session.save(p);
	}


	@SuppressWarnings("unchecked")
	public List<SiteOption> listSiteOptions() {
		Session session = this.sessionFactory.getCurrentSession();
		List<SiteOption> l = session.createQuery("from SiteOption").list();
		return l;
	}
	public SiteOption getSiteOptionById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		SiteOption p = (SiteOption) session.load(SiteOption.class, new Integer(id));
		return p;
	}
	public SiteOption getSiteOptionByKey(String key) {
		Session session = this.sessionFactory.getCurrentSession();		
		SiteOption p = (SiteOption) session.createQuery("from SiteOption where key1=:key").setString("key", key).uniqueResult();
		return p;
	}
}
