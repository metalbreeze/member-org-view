package com.shop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.shop.base.BaseObject;
import com.shop.model.ShopOrder;

@Repository
public class ShopOrderDAO extends BaseObject {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	public void persistShopOrder (ShopOrder p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.debug("ShopOrder saved successfully, ShopOrder Details="+p);
	}
	public void removeShopOrder (ShopOrder p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(p);
		logger.debug("ShopOrder removed successfully, ShopOrder Details="+p);
	}
}
