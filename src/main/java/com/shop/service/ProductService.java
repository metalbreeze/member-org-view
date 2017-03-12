package com.shop.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	static List<Product> productList = new ArrayList();  
	static {
//		productList.add(new Product(0,"",0,0));
//		productList.add(new Product(1,"茶多酚",999,100));
		productList.add(new Product(2,"东革阿里",999,100));
		productList.add(new Product(3,"卡琪花",999,100));
	}
	public Product getProductById(int i){
		if(i<2||i>3){
			return null;
		}
		return productList.get(i);
	}
	public List<Product> getProductList(){
		return productList;
	}
	public static int order_init=1;
	public static int order_send=2;
	@Transactional
	public List<User> getOrderList(int i){
		return userDAO.getOrderList(i);
	}
	@Transactional
	public void setOrderSend(int user_id){
		User u  = userDAO.getUserById(user_id);
		if (u==null){
			return;
		}
		u.setOrderStatus(order_send);
		userDAO.updateUser(u);
	}
	
}
