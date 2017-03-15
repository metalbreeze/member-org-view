package com.shop.service;

import java.math.BigDecimal;
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
public class CostService extends BaseObject{
	@Autowired(required = true)
	@Qualifier(value = "siteOptionDAO")
	private SiteOptionDAO siteOptionDAO;
	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;

	public BigDecimal getTotalReGroupMoney(){
		return userDAO.getTotalReGroupMoney();
	}
	public BigDecimal getUserAwardMoney(){
		return userDAO.getTotalSpendMoney();
	}
	public BigDecimal getplatformCost1(){
		final BigDecimal money = siteOptionDAO.getSiteOptionByKey(platformCost1).getMoney();
		return money==null?new BigDecimal(0):money;
	}
	public BigDecimal getplatformCost2(){
		final BigDecimal money = siteOptionDAO.getSiteOptionByKey(platformCost2).getMoney();
		return money==null?new BigDecimal(0):money;
	}
	public static String platformCost1="platformCost1";
	public static String platformCost2="platformCost2";
	public static int withdraw_init=1;
	public static int withdraw_send=2;
	public static int withdraw_agree=3;
	public static int withdraw_disagree=4;
}
