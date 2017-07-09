package com.shop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.shop.base.BaseObject;
import com.shop.model.ShopOrder;
import com.shop.model.ShopOrderItem;

@Repository
public class ShopOrderItemDAO extends BaseObject {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	public void persistShopOrderItem (ShopOrderItem p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.debug("ShopOrderItem saved successfully, ShopOrderItem Details="+p);
	}
	public void removeShopOrderItem (ShopOrderItem p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(p);
		logger.debug("ShopOrderItem removed successfully, ShopOrderItem Details="+p);
	}
}
