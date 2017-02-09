package com.shop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shop.base.BaseObject;
import com.shop.dao.UserDAO;
import com.shop.model.User;
import com.shop.service.UserService;
@Controller
public class CommonController extends BaseObject {

	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	private UserService userService;
	
    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "redirect:login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model ) {
        return "loginPage";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model) {
        return "adminPage";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "redirect:login";
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
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
