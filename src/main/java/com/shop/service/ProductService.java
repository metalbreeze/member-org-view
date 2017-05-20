package com.shop.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.BaseObject;
import com.shop.dao.*;
import com.shop.model.*;

@Service
public class ProductService extends BaseObject{
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;
	
	static List<Product> productList = new ArrayList<Product>();  
	static {
		productList.add(new Product(0,"无产品",0,0));
		productList.add(new Product(1,"茶多酚",999,300));
		productList.add(new Product(2,"东革阿里",999,200));
		productList.add(new Product(3,"卡琪花",999,200));
		productList.add(new Product(4,"2盒东革阿里",1998,400));
		productList.add(new Product(5,"2盒卡琪花",1998,400));
		productList.add(new Product(6,"1盒东革阿里 1盒卡琪花",1998,400));
	}
	static List<Product> currentProductList = new ArrayList<Product>();  
	static {
		currentProductList.add(productList.get(4));
		currentProductList.add(productList.get(5));
		currentProductList.add(productList.get(6));
	}
	static List<Product> currentSiteBProductList = new ArrayList<Product>();  
	static {
		currentSiteBProductList.add(productList.get(2));
		currentSiteBProductList.add(productList.get(3));
	}
	public static Product getProductById(int i){
		if(i<0||i>6){
			return productList.get(0);
		}
		return productList.get(i);
	}
	public static List<Product> getCurrentProductList(){
		return currentProductList;
	}
	public static List<Product> getProductList(){
		return productList;
	}
	public static int order_init=1;
	public static int order_send=2;
	@Transactional
	public List<User> getOrderList(int i){
		return userDAO.getOrderList(i);
	}
	@Transactional
	public List<User> getOldOrderList(int i){
		return userDAO.getOldOrderList(i);
	}
	@PreAuthorize("hasRole('ROLE_SENDER')")
	@Transactional
	public void setOrderSend(int user_id){
		User u  = userDAO.getUserById(user_id);
		if (u==null){
			return;
		}
		u.setOrderStatus(order_send);
		u.setOrderSendDate(new Timestamp(System.currentTimeMillis()));
		userDAO.updateUser(u);
		operationDAO.addOperation(new Operation(u, null, "发快递单", 0));
	}
	public static List<Product> getCurrentSiteBProductList() {
		return currentSiteBProductList;
	}
	
}
