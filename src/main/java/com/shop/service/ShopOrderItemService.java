package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.shop.base.BaseObject;
import com.shop.dao.OperationDAOImpl;
import com.shop.dao.ShopOrderDAO;
import com.shop.dao.ShopOrderItemDAO;
import com.shop.dao.UserDAO;

@Service
public class ShopOrderItemService extends BaseObject{
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;
	@Autowired(required = true)
	@Qualifier(value = "shopOrderItemDAO")
	private ShopOrderItemDAO shopOrderItemDAO;
	public UserDAO getUserDAO() {
		return userDAO;
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	public OperationDAOImpl getOperationDAO() {
		return operationDAO;
	}
	public void setOperationDAO(OperationDAOImpl operationDAO) {
		this.operationDAO = operationDAO;
	}
	public ShopOrderItemDAO getShopOrderItemDAO() {
		return shopOrderItemDAO;
	}
	public void setShopOrderItemDAO(ShopOrderItemDAO shopOrderItemDAO) {
		this.shopOrderItemDAO = shopOrderItemDAO;
	}
}
