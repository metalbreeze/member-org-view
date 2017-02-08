package com.shop.controller;

import java.security.Principal;

import org.hibernate.Hibernate;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.shop.base.BaseObject;
import com.shop.dao.UserDAO;
import com.shop.model.User;
import com.shop.model.View;
import com.shop.service.UserService;

@Controller
public class UserController extends BaseObject{
	
	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	public void setUserService(UserService ps){
		this.userService = ps;
	}
	@Transactional
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String listUsers(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listUsers", this.userService.listUsers());
		return "user";
	}
	
	//For add and update User both
	@RequestMapping(value= "/user/add", method = RequestMethod.POST)
	@Transactional
	public String addUser(@ModelAttribute("user") User p){
		if(p.getId() == 0){
			//set password = 12345
			p.setPassword("$2a$11$7FDrc3dWL2JRt/GH89gpR.mBz.31T8x7YeTJ0IRzVD.UaUKn2pqjK");
			this.userService.addUser(p);
		}else{
			this.userService.updateUser(p);
		}
		return "redirect:/users";
	}

	@RequestMapping(value= "/myself", method = RequestMethod.POST)
	@Transactional
	public String myselfEdit(@ModelAttribute("user") User p){
		//TODO validation
		if(p.getId() == 0){
			p.setPassword("$2a$11$7FDrc3dWL2JRt/GH89gpR.mBz.31T8x7YeTJ0IRzVD.UaUKn2pqjK");
			this.userService.addUser(p);
		}else{
			this.userService.updateUser(p);
		}
		return "redirect:/users";
	}
	
	@RequestMapping("/user/remove/{id}")
    public String removeUser(@PathVariable("id") int id){
		
        this.userService.removeUser(id);
        return "redirect:/users";
    }
 
    @RequestMapping("/user/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model){
        model.addAttribute("user", this.userService.getUserById(id));
        model.addAttribute("listUsers", this.userService.listUsers());
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
	
	
    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "redirect:login";
    }
 
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model) {
        return "adminPage";
    }
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model ) {
         
        return "loginPage";
    }
 
    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }
 
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    @Transactional
    public String userInfo(Model model, Principal principal) {
 
        // After user login successfully.
        String userName = principal.getName();
        User u = userDAO.getUserByName(userName);
        logger.debug("User Name: "+ userName+"parent()"+u.getParent().getName());
        model.addAttribute("user", u);
        logger.debug("User Name: "+ userName);
 
        return "userInfo";
    }
 
    @RequestMapping(value = "/403", method = RequestMethod.GET)
	@Transactional
    public String accessDenied(Model model, Principal principal) {
         
        if (principal != null) {
            model.addAttribute("message", "Hi " + principal.getName()
                    + "<br> You do not have permission to access this page!");
        } else {
            model.addAttribute("msg",
                    "You do not have permission to access this page!");
        }
        return "403Page";
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
}
