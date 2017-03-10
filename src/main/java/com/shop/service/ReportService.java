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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.base.BaseObject;
import com.shop.dao.*;
import com.shop.model.*;

@Service
public class ReportService extends BaseObject {

	@Autowired(required = true)
	@Qualifier(value = "reportCenterDAO")
	private ReportCenterDAO reportCenterDAO;
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	@Autowired(required = true)
	@Qualifier(value = "groupDAO")
	private GroupDAO groupDAO;
	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;
	
	@Transactional
	public void activeUser(User owner, int id, RedirectAttributes ra) {
		info("activeUser " + id);
		User target = userDAO.getUserById(id);
		ReportCenter r = target.getReportCenter(); 
		if(owner==null){
			info("test for admin");
		}else if (r.getId()!=reportCenterDAO.getReportCenterByOwnerId(owner.getId()).getId()) {
			error(ra, "不是你的用户不能激活");
			return;
		}
		if (r.getElectricMoney() == null) {
			r.setElectricMoney(new BigDecimal(0));
		}
		BigDecimal b = r.getElectricMoney().add(new BigDecimal(999).negate());
		if (b.signum() == -1) {
			error(ra, "钱不够");
			return;
		}
		r.setElectricMoney(b);
		Group group = groupDAO.getAvailableGroup();
		Group.transform(group);
		String string = group.getAvailbleLabes().get(0);
		target.setLevel(string);
		target.addBonusBeforeMoneyByLevel(string);
		target.setStatus(old_status);
		target.setPosition(userDAO.getCurrentPosiztionByGroup(group, string) + 1);
		target.setGroup(group);
		userDAO.updateUser(target);
		groupDAO.refresh(group);
		Group.transform(group);
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
					user.addBonusMoney(Group.levelMoney[i]);
					Operation op = new Operation();
					op.setMoney(new BigDecimal(Group.levelMoney[i]));
					op.setOperation(Group.labels[i] + "-" + Group.labels[i - 1]);
					op.setReportCenter(r);
					op.setUser(user);
					operationDAO.addOperation(op);
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
			userLevealA.addBonusMoney(4000);
			userDAO.updateUser(userLevealA);
			// 分享回馈奖
			if (userLevealA.getParent() != null) {
				userLevealA.getParent().addFeedbackMoney(3000);
				operationDAO.addOperation(new Operation(target.getParent(), r,
						"回馈奖", 3000));
				userDAO.updateUser(userLevealA.getParent());
			}
			// 出局服务费
			
			operationDAO.addOperation(new Operation(userLevealA, userLevealA.getReportCenter(), "费用2/出局", 90));
//			info("money2"+r.getName()+":"+userLevealA.getName()+":"+r.getMoney2());
			userLevealA.getReportCenter().addMoney2(90);
//			info("money2_after"+r.getName()+":"+userLevealA.getName()+":"+r.getMoney2());
		}
		if (target.getParent() != null) {
			target.getParent().addSaleMoney(100);
			// 有空测试下
			userDAO.updateUser(target.getParent());
			operationDAO.addOperation(new Operation(target.getParent(), r,
					"直推奖", 100, target.getParent().getSaleMoney() + "xx 直推"
							+ target.getId() + "直推"));
		} else {
			operationDAO.addOperation(new Operation(target, r, "直推奖设定不了", 100));
		}
		info(owner + "active a user " + target.toString()
				+ " with money");
		// 每报一单 10
		operationDAO.addOperation(new Operation(target,r,"费用1",10));
		r.addMoney1(10);
		reportCenterDAO.updateReportCenter(r);
		// return "redirect:/myReport";
	}
}
