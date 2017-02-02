package com.shop.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.shop.dao.GroupDAO;
import com.shop.model.Group;
import com.shop.model.View;
import com.shop.service.GroupService;

@Controller
public class GroupController {
	
	private GroupService groupService;
	@Autowired(required=true)
	@Qualifier(value="groupDAO")
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

	@Autowired(required=true)
	@Qualifier(value="groupService")
	public void setGroupService(GroupService ps){
		this.groupService = ps;
	}
	
	@RequestMapping(value = "/groups", method = RequestMethod.GET)
	public String listGroups(Model model) {
		model.addAttribute("group", new Group());
		model.addAttribute("listGroups", this.groupService.listGroups());
		return "group";
	}
	
	//For add and update Group both
	@RequestMapping(value= "/group/add", method = RequestMethod.POST)
	public String addGroup(@ModelAttribute("group") Group p){
		
		if(p.getId() == 0){
			//new Group, add it
			this.groupService.addGroup(p);
		}else{
			//existing Group, call update
			this.groupService.updateGroup(p);
		}
		
		return "redirect:/groups";
		
	}
    @RequestMapping("/group/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        model.addAttribute("group", this.groupService.getGroupById(id));
        model.addAttribute("listGroups", this.groupService.listGroups());
        return "group";
    }
	@RequestMapping("/group/remove/{id}")
    public String removeGroup(@PathVariable("id") int id){
		
        this.groupService.removeGroup(id);
        return "redirect:/groups";
    }
	
	@JsonView(View.Collection.class)
	@RequestMapping(value = "/ajax/groups", method = RequestMethod.GET)
    @ResponseBody
	public List<Group> ajaxListgroup(@RequestParam ("id") int id) {
		if(id != 0 ) {
			List<Group> l = new ArrayList<Group>();
			l.add(groupService.getGroupById(id));
			return l;
		}else{
			return groupService.listGroups();
		}
	}
	@JsonView(View.Simple.class)
	@RequestMapping(value = "/ajax/listgroups/{id}", method = RequestMethod.GET)
    @ResponseBody
	@Transactional
	public Group ajaxList(@PathVariable ("id") int id) {
			return groupDAO.listGroupWithUsers(id);
	}
	
	@JsonView(View.Simple.class)
	@RequestMapping(value = "/listgroup", method = RequestMethod.GET)
	public String listGroup(@RequestParam ("id") int id,Model model) {
		Group g = this.ajaxList(id);
		model.addAttribute("groupName", g.getName());
		model.addAttribute("listUser1", g.getLevelUsers().get(1));
		model.addAttribute("listUser2", g.getLevelUsers().get(1));
		model.addAttribute("listUser3", g.getLevelUsers().get(1));
		model.addAttribute("listUser4", g.getLevelUsers().get(1));
		model.addAttribute("listUser5", g.getLevelUsers().get(1));
		model.addAttribute("listUser6", g.getLevelUsers().get(1));
			return "groupTable";
	}

}
