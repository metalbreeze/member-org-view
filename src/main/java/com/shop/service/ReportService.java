package com.shop.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.base.BaseObject;
import com.shop.dao.*;
import com.shop.model.*;

@Service
public class ReportService extends BaseObject {

	@Autowired(required = true)
	@Qualifier(value = "reportCenterDAO")
	private ReportCenterDAO reportCenterDAO;
	/**
	 * 
	 */
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	@Autowired(required = true)
	@Qualifier(value = "groupDAO")
	private GroupDAO groupDAO;
	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;
	
	private static int getUpperPos(int pos){
		if(pos%2==1){
			return (pos+1)/2;
		}else{
			return pos/2;
		}
	}
	public void activeUserSiteB(User target,RedirectAttributes ra) {
		info("active site B"+ target.getId());
		final Timestamp portalBsiteActiveDate = new Timestamp(System.currentTimeMillis());
		target.setPortalBsiteActiveDate(portalBsiteActiveDate);
		target.setStatus("old");
		target.setOrderStatus(ProductService.order_init);
		target.setActiveDate(portalBsiteActiveDate);
		if(null!=target.getParent()&&null!=target.getProduct_id()&&0!=target.getProduct_id()){
			target.getParent().addSaleMoney(100);
			// 有空测试下直推/推荐
			userDAO.updateUser(target.getParent());
			operationDAO.addOperation(new Operation(target.getParent(), target.getReportCenter(),
					"直推奖", 100, "内网直推奖余额"+target.getParent().getSaleMoney() + " 直推目标:"
							+ target.getId()));
		}
		ProductService.getProductById(target.getProduct_id()).getCost();
		target.addReGroupMoney(ProductService.getProductById(target.getProduct_id()));
	}
	static int BonusPerTick=90;
	@Transactional
	public void activeUser(User owner, int id, RedirectAttributes ra) {
		info("activeUser " + id);
		User target = userDAO.getUserById(id);
		ReportCenter r = target.getReportCenter(); 
		if(owner==null){
			owner=new User();
			owner.setName("admintest or junit");
			owner.setId(0);
			info("test for admin");
		}else if (r.getId()!=reportCenterDAO.getReportCenterByOwnerId(owner.getId()).getId()) {
			error(ra, "不是你的用户不能激活");
			return;
		}
		if("old".equals(target.getStatus())){
			error(ra, "用户已经激活");
			return;
		}
		if (r.getElectricMoney() == null) {
			r.setElectricMoney(new BigDecimal(0));
		}
		
		BigDecimal b = r.getElectricMoney().add(ProductService.getProductById(target.getProduct_id()).getPrice().negate());
		if (b.signum() == -1) {
			error(ra, "钱不够");
			return;
		}
		if(null!=target.getParent()){
			if(!"old".equals(target.getParent().getStatus())){
				error(ra,"推荐人没有激活");
				return;
			}
		}
		r.setElectricMoney(b);
		// 每报一单 10
		BigDecimal money1before = r.getMoney1();
		int addMoney1=0;
		if (ProductService.getProductById(target.getProduct_id()).getPrice().compareTo(new BigDecimal(1000))>0){
			addMoney1=20;
		}else if (ProductService.getProductById(target.getProduct_id()).getPrice().compareTo(new BigDecimal(500))>0) {
			addMoney1=10;
		}
		r.addMoney1(addMoney1);
		reportCenterDAO.updateReportCenter(r);
		BigDecimal money1after = r.getMoney1();
		operationDAO.addOperation(new Operation(target,r,"费用1",addMoney1,"before"+money1before+"after"+money1after));
		if (target.getSiteStatus()==2){
			//
			activeUserSiteB(target,ra);
			return;
		}
		Group group = groupDAO.getAvailableGroup();
		Group.transform(group);
		String string = group.getAvailbleLabes().get(0);
		target.setLevel(string);
//		target.addBonusBeforeMoneyByLevel(string);
		target.setStatus(old_status);
		target.setPosition(userDAO.getCurrentPosiztionByGroup(group, string) + 1);
		target.setGroup(group);
		target.setOrderStatus(ProductService.order_init);
		if(target.getProduct_id()==null||target.getProduct_id()<3||target.getProduct_id()>5){
			target.addReGroupMoney(ProductService.getProductById(4));
		}else{
			target.addReGroupMoney(ProductService.getProductById(target.getProduct_id()));
		}
		target.setActiveDate(new Timestamp(System.currentTimeMillis()));
		//TODO 
		userDAO.updateUser(target);
//      i don't know why refresh not works
//		groupDAO.refresh(group);
//		group=groupDAO.getGroupById(group.getId());
		group.getUsers().add(target);
		group.transform();
		int size = group.getLevelUsers().get("F").size();
		logger.debug("group.getUsers()"+group.getUsers());
		if(size>=0){//&&size%2==0
			logger.debug("group.getLevelUsers().get(F)"+group.getLevelUsers().get("F").toString());
			int currentPos=getUpperPos(target.getPosition());
			logger.debug("currentPos"+currentPos);
			User userE = group.getLevelUsers().get("E").get(currentPos-1);
			userE.addBonusMoney(BonusPerTick);
			currentPos=getUpperPos(currentPos);
			logger.debug("currentPos"+currentPos);
			User userD = group.getLevelUsers().get("D").get(currentPos-1);
//			userD.addBonusMoney(BonusPerTick);
//			userD.addPortalBsiteMoney(BonusPerTick);
			userD.addTransferBsiteMoney(BonusPerTick);
			if(userD.getTransferBsiteMoney().compareTo(new BigDecimal(90*4))>=0){
				userD.setSiteStatus(3);
				userD.setPortalBsiteActiveDate(new Timestamp(System.currentTimeMillis()));
			}
			currentPos=getUpperPos(currentPos);
			logger.debug("currentPos"+currentPos);
			User userC = group.getLevelUsers().get("C").get(currentPos-1);
			userC.addBonusMoney(BonusPerTick);
			currentPos=getUpperPos(currentPos);
			logger.debug("currentPos"+currentPos);
			User userB = group.getLevelUsers().get("B").get(currentPos-1);
			userB.addBonusMoney(BonusPerTick);
			currentPos=getUpperPos(currentPos);
			logger.debug("currentPos"+currentPos);
			User userA = group.getLevelUsers().get("A").get(currentPos-1);
			userA.addBonusMoney(BonusPerTick);
			userDAO.updateUser(userA);
			userDAO.updateUser(userB);
			userDAO.updateUser(userC);
			userDAO.updateUser(userD);
			userDAO.updateUser(userE);
			operationDAO.addOperation(userA.getReportCenter(),BonusPerTick,userA,"分红余额"+userA.getBonusMoney()+"-"+target.getId()+"/"+target.getName()+":激活F");
			operationDAO.addOperation(userB.getReportCenter(),BonusPerTick,userB,"分红余额"+userB.getBonusMoney()+"-"+target.getId()+"/"+target.getName()+":激活F");
			operationDAO.addOperation(userC.getReportCenter(),BonusPerTick,userC,"分红余额"+userC.getBonusMoney()+"-"+target.getId()+"/"+target.getName()+":激活F");
			operationDAO.addOperation(userD.getReportCenter(),0,userD,"分红余额"+userD.getBonusMoney()+"-"+target.getId()+"/"+target.getName()+":激活F,用于B站");
			operationDAO.addOperation(userE.getReportCenter(),BonusPerTick,userE,"分红余额"+userE.getBonusMoney()+"-"+target.getId()+"/"+target.getName()+":激活F");
		}
		info(ra,target.getName() + " 用户已经激活");
		if (group.getUsers().size() == 63) {
			logger.info(group.getName() + " group.getUsers().size() =63");
			group.setEndDate(new Timestamp(System.currentTimeMillis()));
			Group group1 = new Group();
			Group group2 = new Group();
			group1.setName(group.getId() + "-A");
			group2.setName(group.getId() + "-B");
			group.setRemark(group.getLevelUsers().toString());
			for (int i = 1; i < Group.labels.length; i++) {
				List<User> ulist = group.getLevelUsers().get(Group.labels[i]);
				for (int j = 0; j < ulist.size(); j++) {
					User user = (User) ulist.get(j);
					logger.debug("upgrade level:" + user.getId() + " level:"
							+ user.getLevel() + " to:" + Group.labels[i - 1]);
					user.setLevel(Group.labels[i - 1]);
					// 会员分红奖
					//旧版 user.addBonusMoney(Group.levelMoney[i]);
					//旧版 Operation op = new Operation();
					//旧版 op.setMoney(new BigDecimal(Group.levelMoney[i]));
					//旧版 op.setOperation(Group.labels[i] + "-" + Group.labels[i - 1]);
					//旧版 op.setReportCenter(r);
					//旧版 op.setUser(user);
					//旧版 operationDAO.addOperation(op);
					if (j >= Group.maxLabels[i] / 2) {
						user.setGroup(group2);
						user.setPosition(j - Group.maxLabels[i] / 2 + 1);
					} else {
						user.setGroup(group1);
						user.setPosition(j + 1);
					}
//					userDAO.updateUser(user);
				}
			}
			final User userLevealA = group.getLevelUsers().get(Group.labels[0])
					.get(0);
			group.getUsers().clear();
			groupDAO.addGroup(group1);
			groupDAO.addGroup(group2);
			groupDAO.updateGroup(group);
			// target.setLevel("E");
			// target.setGroup(group2);
			// target.setStatus(old_status);
			// userDAO.updateUser(target);
			info(ra,target.getName() + " 用户已经激活" + "\r\n 分群成功:id"
					+ group1.getId() + "和" + group2.getId());
			userLevealA.setLevel("F");
			userLevealA.setPosition(1);
			userLevealA.setGroup(groupDAO.getAvailableGroup());
//			userLevealA.addBonusMoney(4000);
//复投不产生订单			
//			Integer product_id = userLevealA.getProduct_id();
//			if(product_id==null||product_id<4){
//				product_id=4;
//				userLevealA.setProduct_id(product_id);
//			}
//			userLevealA.addReGroupMoney(ProductService.getProductById(product_id));
//			userLevealA.setOrderStatus(ProductService.order_init);
			userDAO.updateUser(userLevealA);
			// 分享回馈奖
//			final User userParent = userLevealA.getParent();
//			if (userParent != null) {
//				userParent.addFeedbackMoney(3000);
//				operationDAO.addOperation(new Operation(target.getParent(), r,
//						"回馈奖", 3000));
//				userParent.setChildrenCount(userParent.getChildrenCount()+1);
//				userDAO.updateUser(userParent);
//			}
			// 出局服务费
			//取消出局服务费
//			operationDAO.addOperation(new Operation(userLevealA, userLevealA.getReportCenter(), "费用2/出局", 90));
//			info("money2"+r.getName()+":"+userLevealA.getName()+":"+r.getMoney2());
//			userLevealA.getReportCenter().addMoney2(90);
//			info("money2_after"+r.getName()+":"+userLevealA.getName()+":"+r.getMoney2());
		}
		if (target.getParent() != null) {
			target.getParent().addSaleMoney(500);
			// 有空测试下直推/推荐
			userDAO.updateUser(target.getParent());
			operationDAO.addOperation(new Operation(target.getParent(), r,
					"直推奖", 500, "外网直推奖余额"+target.getParent().getSaleMoney() + "  :"
							+ target.getId() ));
		} else {
			operationDAO.addOperation(new Operation(target, r, "无直推人", 500));
		}
		info(owner + "active a user " + target.toString()
				+ " with money");
		// return "redirect:/myReport";
	}
}
