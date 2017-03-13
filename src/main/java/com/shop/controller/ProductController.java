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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.base.BaseObject;
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
import com.shop.model.User;
import com.shop.service.ProductService;
import com.shop.service.UserService;
@Controller
public class ProductController extends BaseObject {

	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	
	@Autowired(required=true)
	@Qualifier(value="userService")
	private UserService userService;


	@Autowired(required=true)
	@Qualifier(value="productService")
	private ProductService productService;
	
	@Autowired(required=true)
	@Qualifier(value="reportCenterDAO")
	private ReportCenterDAO reportCenterDao;
	
	@RequestMapping(value= "/product/edit/{id}", method = RequestMethod.GET)
	@Transactional
	public String myselfEdit(@PathVariable("id") int id, Model model) {
		productService.setOrderSend(id);
		return "redirect:/product/orders";
	}
	@RequestMapping(value= "/product/orders", method = RequestMethod.GET)
	@Transactional
	public String myselfEdit(Model model) {
		List<User> list2 = productService.getOrderList(2);
		List<User> list3 = productService.getOrderList(3);
		model.addAttribute("product2", productService.getProductById(2));
		model.addAttribute("list2", list2);
		model.addAttribute("product3", productService.getProductById(3));
		model.addAttribute("list3", list3);
		return "userOrders";
	}
	@RequestMapping(value= "/product/oldOrders", method = RequestMethod.GET)
	@Transactional
	public String oldOrders(Model model) {
		List<User> list2 = productService.getOldOrderList(2);
		List<User> list3 = productService.getOldOrderList(3);
		model.addAttribute("product2", productService.getProductById(2));
		model.addAttribute("list2", list2);
		model.addAttribute("product3", productService.getProductById(3));
		model.addAttribute("list3", list3);
		return "userOldOrders";
	}
}
