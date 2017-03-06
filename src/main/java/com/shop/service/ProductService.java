package com.shop.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.base.BaseObject;
import com.shop.dao.*;
import com.shop.model.*;

@Service
public class ProductService extends BaseObject{
	
	static List<Product> productList = new ArrayList();  
	static {
//		productList.add(new Product(1,"茶多酚",999,100));
		productList.add(new Product(2,"东革阿里",999,100));
		productList.add(new Product(3,"卡琪花",999,100));
	}
	public List<Product> getProductList(){
		return productList;
	}
}
