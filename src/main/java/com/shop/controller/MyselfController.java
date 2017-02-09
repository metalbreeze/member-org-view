package com.shop.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.base.BaseObject;
import com.shop.dao.UserDAO;
import com.shop.model.User;
import com.shop.service.UserService;
@Controller
public class MyselfController extends BaseObject {

	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	private UserService userService;
	
    @Autowired(required=true)
    @Qualifier(value="encoder")
    private PasswordEncoder encoder;
    
	@RequestMapping(value= "/myself/edit", method = RequestMethod.POST)
	public String myselfEdit(@ModelAttribute("user") User p,RedirectAttributes ra){
		if(p.getId() == 0){
			logger.error("myself add new user");
			User userByName = userDAO.getUserByName(p.getName());
			if (userByName!=null){
				ra.addFlashAttribute("flashMsg", "已经有同名用户");
				logger.debug( "已经有同名用户");
				return "redirect:/register";
			}else{
				logger.debug("password  "+p.getPassword());
				String encode = encoder.encode(p.getPassword());
				logger.debug("password diff"+encoder.matches(encode, "$2a$11$7FDrc3dWL2JRt/GH89gpR.mBz.31T8x7YeTJ0IRzVD.UaUKn2pqjK"));
				logger.debug("password diff"+encoder.matches("12345", "$2a$11$7FDrc3dWL2JRt/GH89gpR.mBz.31T8x7YeTJ0IRzVD.UaUKn2pqjK"));
				logger.debug("password diff"+encoder.matches("12345", encode));
				p.setPassword(encode);
				this.userService.addUser(p);
				ra.addFlashAttribute("flashMsg2", "添加成功,重新登录");
				return "redirect:/login";
			}
			
		}else{
			this.userService.updateUser(p);
		}
		return "redirect:/myself";
	}
    @RequestMapping(value = "/myself", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {
        // After user login successfully.
        String userName = principal.getName();
        User u = userDAO.getUserByName(userName);
        logger.debug("User Name: "+ userName+"parent()"+u.getParent().getName());
        model.addAttribute("user", u);
		model.addAttribute("listUsers", this.userService.listUsers());
        logger.debug("User Name: "+ userName);
        return "myself";
    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
    	model.addAttribute("user", new User());
        // After user login successfully.
		model.addAttribute("listUsers", this.userService.listUsers());
        return "register";
    }
}
