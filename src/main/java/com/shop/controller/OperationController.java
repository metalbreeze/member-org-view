package com.shop.controller;

import java.security.Principal;
import java.util.List;

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
import com.shop.dao.OperationDAOImpl;
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
import com.shop.model.Operation;
import com.shop.model.User;
import com.shop.service.UserService;
@Controller
public class OperationController extends BaseObject {

	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	private UserService userService;

	
	@Autowired(required=true)
	@Qualifier(value="reportCenterDAO")
	private ReportCenterDAO reportCenterDao;
	
	
	@Autowired(required=true)
	@Qualifier(value="operationDAOImpl")
	private OperationDAOImpl operationDAO;
	
    @Autowired(required=true)
    @Qualifier(value="encoder")
    private PasswordEncoder encoder;
    
	@RequestMapping(value= "/listoperations", method = RequestMethod.GET)
	@Transactional
	public String listUsers(Model model) {
		List<Operation> list = operationDAO.listOperations();
		model.addAttribute("operationList", list);
		return "operation";
	}
	
}
