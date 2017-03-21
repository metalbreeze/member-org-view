package com.shop.controller;

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
import com.shop.dao.UserDAO;
import com.shop.model.Group;
import com.shop.model.User;
import com.shop.model.View;
import com.shop.service.GroupService;
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
	
	public void setUserService(UserService ps) {
		this.userService = ps;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@Transactional
	public String listUsers(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listUsers", userDAO.listOldUsersWithOutUserId(-1));
		model.addAttribute("userStatus", User.statusMap);
		return "user";
	}

	// For add and update User both
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	@Transactional
	public String addUser(@ModelAttribute("user") User p,RedirectAttributes ra) {
		if (p.getId() == 0) {
			// set password = 12345
			p.setPassword("$2a$11$7FDrc3dWL2JRt/GH89gpR.mBz.31T8x7YeTJ0IRzVD.UaUKn2pqjK");
			User userByName = userDAO.getUserByName(p.getName());
			if (userByName!=null){
				ra.addFlashAttribute("flashMsg", "已经有同名用户");
				logger.debug( "已经有同名用户");
			}else{
				this.userService.addUser(p);
			}
		} else {
			User user = userDAO.getUserById(p.getId());
			user.setName(p.getName().replaceAll(" ", "").replaceAll("　",""));
			user.setAddress(p.getAddress());
			user.setMobile(p.getMobile());
			user.setAccountNumber(p.getAccountNumber());
			user.setWechat(p.getWechat());
			user.setAlipay(p.getAlipay());
			user.setParent(p.getParent());
//			user.setStatus(p.getStatus());
			this.userService.updateUser(user);
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
		model.addAttribute("listUsers", userDAO.listOldUsersWithOutUserId(id));
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
