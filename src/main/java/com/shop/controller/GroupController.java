package com.shop.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.shop.dao.GroupDAO;
import com.shop.dao.GroupDAOImpl;
import com.shop.dao.UserDAO;
import com.shop.model.Group;
import com.shop.model.User;
import com.shop.model.View;
import com.shop.service.GroupService;

@Controller
public class GroupController {
	private static final Logger logger = LoggerFactory
			.getLogger(GroupController.class);
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;

	@Autowired(required = true)
	@Qualifier(value = "groupDAO")
	private GroupDAO groupDAO;

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	@Autowired(required = true)
	@Qualifier(value = "groupService")
	private GroupService groupService;

	public void setGroupService(GroupService ps) {
		this.groupService = ps;
	}

	@RequestMapping(value = "/groups", method = RequestMethod.GET)
	public String listGroups(Model model) {
		model.addAttribute("group", new Group());
		model.addAttribute("listGroups", this.groupService.listGroups());
		return "group";
	}

	// For add and update Group both
	@RequestMapping(value = "/group/add", method = RequestMethod.POST)
	public String addGroup(@ModelAttribute("group") Group p) {

		if (p.getId() == 0) {
			// new Group, add it
			this.groupService.addGroup(p);
		} else {
			// existing Group, call update
			this.groupService.updateGroup(p);
		}

		return "redirect:/groups";

	}

	@RequestMapping("/group/edit/{id}")
	public String editUser(@PathVariable("id") int id, Model model) {
		model.addAttribute("group", this.groupService.getGroupById(id));
		model.addAttribute("listGroups", this.groupService.listGroups());
		return "group";
	}

	@RequestMapping("/group/remove/{id}")
	public String removeGroup(@PathVariable("id") int id) {

		this.groupService.removeGroup(id);
		return "redirect:/groups";
	}

	@JsonView(View.Collection.class)
	@RequestMapping(value = "/ajax/groups", method = RequestMethod.GET)
	@ResponseBody
	public List<Group> ajaxListgroup(@RequestParam("id") int id) {
		if (id != 0) {
			List<Group> l = new ArrayList<Group>();
			l.add(groupService.getGroupById(id));
			return l;
		} else {
			return groupService.listGroups();
		}
	}

	@JsonView(View.Simple.class)
	@RequestMapping(value = "/ajax/listgroups/{id}", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public Group ajaxList(@PathVariable("id") int id) {
		return Group.transform(groupDAO.getGroupById(id));
	}

	@JsonView(View.Simple.class)
	@RequestMapping(value = "/listgroup", method = RequestMethod.GET)
	@Transactional
	public String listGroup( @ModelAttribute("message") final String  msg,
							@ModelAttribute("group1") final Group group1 ,
							@ModelAttribute("group2") final Group group2 ,
							@ModelAttribute("user") User u, Model model) {
		logger.debug("/listgroup group id" + u.getGroup().getId()+"with alert msg:"+msg);
		Group g = this.ajaxList(u.getGroup().getId());
		model.addAttribute("group", g);
		// Hibernate.initialize(g.getUsers());
		// for html Select
		model.addAttribute("levelUsers", g.getLevelUsers());
		model.addAttribute("groupList", groupDAO.listGroups());
		model.addAttribute("labels", Group.labels);
		model.addAttribute("aviableLabels", g.getAvailbleLabes());
		model.addAttribute("users", userDAO.listAvailableUsers());
		model.addAttribute("message", msg);
		model.addAttribute("group1", group1);
		model.addAttribute("group2", group2);
		return "groupTable";
	}

	@JsonView(View.Simple.class)
	@RequestMapping(value = "/group/addUser", method = RequestMethod.POST)
	@Transactional
	public String groupAddUser(@ModelAttribute("user") User u, Model model,
			RedirectAttributes redirectAttributes) {
		// Hibernate.initialize(g.getUsers());

		User old = userDAO.getUserById(u.getId());
		Group group = groupDAO.getGroupById(u.getGroup().getId());
		int currentsize = group.getUsers().size();
		logger.debug("currentsize" + currentsize);
		if (62 == currentsize) {
			logger.info("group.getUsers().size() 分群" + group.getUsers().size());
			group.setName(group.getName() + "-A");
			Group group2 = new Group();
			group2.setName(group.getName() + "-B");
			groupDAO.addGroup(group2);
			Group.transform(group);
			for (int i = 1; i < Group.labels.length; i++) {
				List<User> ulist = group.getLevelUsers().get(Group.labels[i]);

				for (int j = 0; j < ulist.size();) {
					User user = (User) ulist.get(j);
					logger.debug("upgrade level:" + user.getId() + " level:"
							+ user.getLevel() + " to:" + Group.labels[i - 1]);
					user.setLevel(Group.labels[i - 1]);
					if (j == Group.maxLabels[i] / 2) {
						user.setGroup(group2);
						ulist.remove(user);
					} else {
						j++;
					}
				}
				// Hibernate.initialize(group2.getUsers());
				// for(int j=Group.maxLabels[i]/2;j<Group.maxLabels[i];j++){
				// User remove = group.getUsers().remove(Group.maxLabels[i]/2);
				// logger.debug("add group2 user id:"+remove.getId());
				// group2.getUsers().add(remove);
				// }
			}
			logger.debug("add group2 id:" + group2.getId());
			old.setLevel(Group.upgradeLevel(u.getLevel()));
			old.setGroup(group2);
			group.getLevelUsers().get(Group.labels[0]).get(0).setLevel("F");
			groupDAO.updateGroup(group);
			groupDAO.updateGroup(group2);
			redirectAttributes.addFlashAttribute("message", group.getName()+ "分群，变成A群和B群");
			redirectAttributes.addFlashAttribute("group1", group);
			redirectAttributes.addFlashAttribute("group2", group2);
		}else{
			old.setLevel(u.getLevel());
			old.setGroup(u.getGroup());
		}
		redirectAttributes.addAttribute("group.id", u.getGroup().getId());
		return "redirect:/listgroup";

	}
}

