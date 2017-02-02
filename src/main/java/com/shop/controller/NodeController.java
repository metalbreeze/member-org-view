package com.shop.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.shop.model.Group;
import com.shop.model.GroupJson;
import com.shop.model.Node;
import com.shop.model.NodeJson;
import com.shop.model.View;
import com.shop.service.GroupService;
import com.shop.service.NodeService;
import com.shop.service.UserService;

@Controller
public class NodeController {
	
	private NodeService nodeService;
	private GroupService groupService;
	private UserService userService;
	
	@Autowired(required=true)
	@Qualifier(value="nodeService")
	public void setNodeService(NodeService ps){
		this.nodeService = ps;
	}
	
	@Autowired(required=true)
	@Qualifier(value="groupService")
	public void setGroupService(GroupService ps){
		this.groupService = ps;
	}
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService ps){
		this.userService = ps;
	}
		
	@RequestMapping(value = "/nodes", method = RequestMethod.GET)
	public String listNodes(Model model) {
		model.addAttribute("node", new Node());
		model.addAttribute("listGroup",groupService.listGroups());
		model.addAttribute("listUsers", this.userService.listAvailableUsers());
		return "node";
	}

	@JsonView(View.Simple.class)
	@RequestMapping(value = "/trees", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<GroupJson> listTrees(Model model) {
		NodeJson nj = new NodeJson();
		List<Group> listGroups = groupService.listGroups();
		ArrayList<GroupJson> list = new ArrayList<GroupJson>();
		for (Iterator iterator = listGroups.iterator(); iterator.hasNext();) {
			Group group = (Group) iterator.next();
			GroupJson gj = new GroupJson();
			gj.setGroup(group.getName());
			Node node  =null;
			if(node!=null){
				gj.setTree(NodeJson.transform(node));
			}
			list.add(gj);	
		}
		return list;
	}

	@RequestMapping(value = "/gettest", method = RequestMethod.GET)
	@ResponseBody
	public Node gettest(Model model) {
		Node nj = new Node();
		nj.setId(99999);
		return nj;
	}

	//For add and update Node both
	@RequestMapping(value= "/node/add", method = RequestMethod.POST)
	public String addNode(@ModelAttribute("node") Node p){
		
		if(p.getId() == 0){
			//new Node, add it
			this.nodeService.addNode(p);
		}else{
			//existing Node, call update
//			this.nodeService.updateNode(p);
		}
		
		return "redirect:/nodes";
		
	}
	
	@RequestMapping("/node/remove/{id}")
    public String removeNode(@PathVariable("id") int id){
		
//        this.nodeService.removeNode(id);
        return "redirect:/nodes";
    }
 
    @RequestMapping("/node/edit/{id}")
    public String editNode(@PathVariable("id") int id, Model model){
        model.addAttribute("node", this.nodeService.getNodeById(id));
        model.addAttribute("listNodes", this.nodeService.listNodes());
        return "node";
    }

}
