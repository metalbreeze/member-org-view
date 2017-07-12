package com.shop.controller;

import java.sql.Timestamp;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;
import com.shop.base.BaseObject;
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
import com.shop.model.Group;
import com.shop.model.User;
import com.shop.model.View;
import com.shop.service.GroupService;
import com.shop.service.ProductService;
import com.shop.service.UserService;

@Controller
public class UserController extends BaseObject {

	@Autowired(required = true)
	@Qualifier(value = "userService")
	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	
	@Autowired(required = true)
	@Qualifier(value = "groupService")
	private GroupService groupService;
	
	@Autowired(required=true)
	@Qualifier(value="reportCenterDAO")
	private ReportCenterDAO reportCenterDao;
	
	public void setUserService(UserService ps) {
		this.userService = ps;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@Transactional
	public String listUsers(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listUsers", userDAO.listOldUsersWithOutUserIdWithChildren(-1));
		model.addAttribute("listReportCenters", reportCenterDao.listReportCenters());
		model.addAttribute("listProducts",ProductService.getProductList());
		model.addAttribute("userStatus", User.statusMap);
		return "user";
	}

	// For add and update User both
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	@Transactional
	public String addUser(@ModelAttribute("user") User p,RedirectAttributes ra) {
		final String parentName = p.getParent().getName();
		User parentUser=null;
		if(null!=parentName&&!"".endsWith(parentName)){
			if(p.getName().equalsIgnoreCase(parentName)){
				ra.addFlashAttribute("flashMsg", "推荐人和用户同1个人");
				logger.debug( "推荐人和用户同1个人");
				return "redirect:/users";				
			}
			parentUser = userDAO.getUserByName(parentName);
			if(parentUser==null){
				ra.addFlashAttribute("flashMsg", "推荐人不正确");
				logger.debug( "推荐人不正确");
				return "redirect:/users";
			}
			p.setParent(parentUser);
		}
		if (p.getId() == 0) {
			User userByName = userDAO.getUserByName(p.getName());
			// set password = 12345
			p.setPassword("$2a$11$7FDrc3dWL2JRt/GH89gpR.mBz.31T8x7YeTJ0IRzVD.UaUKn2pqjK");
			p.setName(p.getName().replaceAll(" ", "").replaceAll("　",""));
			if (userByName!=null){
				ra.addFlashAttribute("flashMsg", "已经有同名用户");
				logger.debug( "已经有同名用户");
			}else{
				logger.debug( "persist"+p);
				this.userService.persist(p);
			}
		} else {
			User userById = userDAO.getUserById(p.getId());
			if(userById!=null&&userById.getId()!=p.getId()){
				ra.addFlashAttribute("flashMsg", "已经有同名用户");
				logger.debug( "已经有同名用户");
			}else{
//				User user = userDAO.getUserById(p.getId());
				User user= userById;
				user.setName(p.getName().replaceAll(" ", "").replaceAll("　",""));
				user.setAddress(p.getAddress());
				user.setMobile(p.getMobile());
				user.setAccountNumber(p.getAccountNumber());
				user.setWechat(p.getWechat());
				user.setAlipay(p.getAlipay());
				user.setEletricMoney(p.getEletricMoney());
				if(parentUser!=null){
					user.setParent(parentUser);
				}
				user.setSiteStatus(p.getSiteStatus());
				if(p.getSiteStatus()==2){
					user.setStatus("old");
					final Timestamp activeDate = new Timestamp(System.currentTimeMillis());
					user.setActiveDate(activeDate);
					user.setPortalBsiteActiveDate(activeDate);
				}
				user.setProduct_id(p.getProduct_id());
				user.setReportCenter(p.getReportCenter());
				user.setPortalBsiteCode(p.getPortalBsiteCode());
//			user.setStatus(p.getStatus());
				user.addPortalBsiteMoney(p.getPortalBsiteMoney2());
				this.userService.updateUser(user);
			}
		}
		return "redirect:/users";
	}

	@RequestMapping("/user/remove/{id}")
	public String removeUser(@PathVariable("id") int id) {

		this.userService.removeUser(id);
		return "redirect:/users";
	}
	@RequestMapping("/user/edit/{id}")
	@Transactional
	public String editUser(@PathVariable("id") int id, Model model,RedirectAttributes ra) {
		model.addAttribute("user", this.userService.getUserById(id));
		model.addAttribute("listUsers", userDAO.listOldUsersWithOutUserIdWithChildren(id));
		model.addAttribute("listReportCenters", reportCenterDao.listReportCenters());
		model.addAttribute("listProducts",ProductService.getProductList());
		model.addAttribute("userStatus", User.statusMap);
		return "user";
	}

	@JsonView(View.Collection.class)
	@RequestMapping(value = "/ajax/usertree", method = RequestMethod.GET)
	@ResponseBody
	public User ajaxUserTree(@RequestParam("id") int id) {
		if (id != 0) {
			return null;
		} else {
			return userService.getUserById(id);
		}
	}

	@RequestMapping(value = "/listScore/{id}", method = RequestMethod.GET)
	@Transactional
	public String listScore(@PathVariable("id") int id,Model model) {
		User u = userDAO.getUserById(id);
		model.addAttribute("listUsers", this.userDAO.getChildrenGroupScore(u));
		userDAO.getChildrenGroupScore(u);
		model.addAttribute("userStatus", User.statusMap);
		return "listScore";
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserService getUserService() {
		return userService;
	}

	public GroupService getgroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
}
