package com.shop.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shop.base.BaseObject;
import com.shop.dao.OperationDAOImpl;
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
import com.shop.model.Cost;
import com.shop.model.Operation;
import com.shop.model.User;
import com.shop.service.CostService;
import com.shop.service.UserService;

@Controller
public class FinanceController extends BaseObject {

	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "reportCenterDAO")
	private ReportCenterDAO reportCenterDao;

	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;

	@Autowired(required = true)
	@Qualifier(value = "encoder")
	private PasswordEncoder encoder;

	@Autowired(required = true)
	@Qualifier(value = "costService")
	private CostService costService;

	@RequestMapping(value = "/platformFinance", method = RequestMethod.GET)
	@Transactional
	public String finance(Model model) {

		final int sum = userDAO.getOldUser() * 999;
		model.addAttribute("sellTotal", sum);
		final BigDecimal productCost = costService.getProductCost().getCost();
		model.addAttribute("produceCost", productCost);
		final BigDecimal otherCost = costService.getOtherCost().getCost();
		model.addAttribute("otherCost", otherCost);
		BigDecimal withdraw = userDAO.getWithdraw();
		if (withdraw == null) {
			withdraw = new BigDecimal(0);
		}
		model.addAttribute("withdraw", withdraw);
		BigDecimal reportCenterCost = reportCenterDao.getReportCenterCost();
		if (reportCenterCost == null) {
			reportCenterCost = new BigDecimal(0);
		}
		model.addAttribute("reportCenterCost", reportCenterCost);
		final BigDecimal totalCost = productCost.add(otherCost).add(withdraw)
				.add(reportCenterCost);
		model.addAttribute("totalCost", totalCost);
		BigDecimal remain = new BigDecimal(sum).add(totalCost.negate());
		model.addAttribute("remain", remain);
		return "platformFinance";
	}

	@RequestMapping(value = "/userFinanceByID", method = RequestMethod.GET)
	@Transactional
	public String userFinanceByID(@RequestParam("id") int id,Model model) {
		int i;
		if (id == 0) {
			i = 20;
		} else {
			i = id;
		}
		User u = userDAO.getUserById(i);
		userDAO.getChildrenGroupScore(u);
		List<User> l =  getAllChildren(u);
		model.addAttribute("userList", l);
		return "userFinance";
	}

	@RequestMapping(value = "/userFinance", method = RequestMethod.GET)
	@Transactional
	public String userFinance(Model model) {
		model.addAttribute("userList", userDAO.listOldUsers());
		return "userFinance";
	}
	
	static List<User> getAllChildren(User u) {
		ArrayList<User> list = new ArrayList<User>();
		list.add(u);
		for (Iterator iterator = u.getChildren().iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			list.addAll(getAllChildren(user));
		}
		return list;
	}
	@RequestMapping(value = "/saleMoneyList", method = RequestMethod.GET)
	@Transactional
	public String saleMoneyList(Model model) {
		if(hasRole("ROLE_ADMIN")){
			model.addAttribute("userList", userDAO.listUserOrderBySaleMoney(-1));
		}else{
			model.addAttribute("userList", userDAO.listUserOrderBySaleMoney(10));
		}
		return "saleMoneyList";
	}
	@RequestMapping(value = "/platformWithdraw/{id}", method = RequestMethod.GET)
	@Transactional
	public String platformWithdraw(@PathVariable("id") int id) {
		User user = userDAO.getUserById(id);
		final BigDecimal withdrawRequest = user.getWithdrawRequest();
		user.addWithdraw(withdrawRequest);
		user.setWithdrawRequest(new BigDecimal(0));
		user.setWithdrawDate(new Timestamp(System.currentTimeMillis()));
		user.setWithdrawStatus(CostService.withdraw_send);
		userDAO.updateUser(user);
		operationDAO.addOperation(new Operation(user,null,"同意提现",withdrawRequest));
		return "redirect:/userFinance";
	}
}
