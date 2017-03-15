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
public class CostService extends BaseObject{
	
	static List<Cost> costList = new ArrayList();  
	static {
//		productList.add(new Product(1,"茶多酚",999,100));
		costList.add(new Cost(2,"产品成本",100));
		costList.add(new Cost(3,"其他支出",100));
	}
	public List<Cost> getProductList(){
		return costList;
	}
	public Cost getProductCost(){
		return costList.get(0);
	}
	public Cost getOtherCost(){
		return costList.get(1);
	}
	public static int withdraw_init=1;
	public static int withdraw_send=2;
	public static int withdraw_agree=3;
	public static int withdraw_disagree=4;
}
