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
import com.shop.dao.SiteOptionDAO;
import com.shop.dao.UserDAO;
import com.shop.model.Cost;
import com.shop.model.Operation;
import com.shop.model.ReportCenter;
import com.shop.model.SiteOption;
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
	private ReportCenterDAO reportCenterDAO;
	
	@Autowired(required = true)
	@Qualifier(value = "siteOptionDAO")
	private SiteOptionDAO siteOptionDAO;

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
		final BigDecimal userCostMoney = costService.getTotalReGroupMoney();
		model.addAttribute("userCostMoney", userCostMoney);
		final BigDecimal userAward = costService.getUserAwardMoney();
		model.addAttribute("totolAward", userAward);
		final BigDecimal platformCost1 = costService.getplatformCost1();
		model.addAttribute(CostService.platformCost1, platformCost1);
		final BigDecimal platformCost2 = costService.getplatformCost2();
		model.addAttribute(CostService.platformCost2, platformCost2);
//		BigDecimal withdraw = userDAO.getWithdraw();
//		if (withdraw == null) {
//			withdraw = new BigDecimal(0);
//		}
//		model.addAttribute("withdraw", withdraw);
		BigDecimal reportCenterCost = reportCenterDAO.getReportCenterCost();
		if (reportCenterCost == null) {
			reportCenterCost = new BigDecimal(0);
		}
		model.addAttribute("reportCenterCost", reportCenterCost);
		final BigDecimal totalCost = userCostMoney.add(userAward).add(platformCost1).add(platformCost2)
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
		model.addAttribute("userList", userDAO.listOldUsersWithOutUserId(-1));
		return "userFinance";
	}


	@RequestMapping(value = "/platformCost", method = RequestMethod.POST)
	@Transactional
	public String platformCost(@RequestParam(value="platformCost1",required = false) String s1,
			@RequestParam(value="platformCost2",required = false) String s2) {
		BigDecimal b1 = null;
		BigDecimal b2 = null;
		try{
			b1=new BigDecimal(s1);
		}catch(NumberFormatException e){
			logger.debug(e.toString());
		}
		try{
			b2=new BigDecimal(s2);
		}catch(NumberFormatException e){
			logger.debug(e.toString());
		}
		if (b1!=null){
			SiteOption key1 = siteOptionDAO.getSiteOptionByKey(CostService.platformCost1);
			key1.setMoney(b1);
			siteOptionDAO.addSiteOption(key1);
		}
		if(b2!=null){
			SiteOption key2 = siteOptionDAO.getSiteOptionByKey(CostService.platformCost2);
			key2.setMoney(b2);
			siteOptionDAO.addSiteOption(key2);
		}
		logger.debug("platformCost"+s1+"/"+s2);
		return "redirect:/platformFinance";
	}
	
	@RequestMapping(value = "/platformWithdraw/agree/{id}", method = RequestMethod.GET)
	@Transactional
	public String userFinanceAgree(@PathVariable int id) {
		User u = userDAO.getUserById(id);
		if (u != null ){
			u.setWithdrawStatus(CostService.withdraw_agree);
			userDAO.updateUser(u);
		}
		return "redirect:/userFinance";
	}
	
	@RequestMapping(value = "/platformWithdraw/disagree/{id}", method = RequestMethod.GET)
	@Transactional
	public String userFinanceDisagree(@PathVariable int id) {
		User u = userDAO.getUserById(id);
		if (u != null ){
			u.setWithdrawStatus(CostService.withdraw_disagree);
			userDAO.updateUser(u);
		}
		return "redirect:/userFinance";
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
	@RequestMapping(value = "/finance/withdraw/{id}", method = RequestMethod.GET)
	@Transactional
	public String platformWithdraw(@PathVariable("id") int id) {
		User user = userDAO.getUserById(id);
		final BigDecimal withdrawRequest = user.getWithdrawRequest();
		user.addWithdraw(withdrawRequest);
		user.setWithdrawRequest(new BigDecimal(0));
		user.setWithdrawDate(new Timestamp(System.currentTimeMillis()));
		user.setWithdrawStatus(CostService.withdraw_send);
		userDAO.updateUser(user);
		operationDAO.addOperation(new Operation(user,null,"发放提现",withdrawRequest));
		return "userFinance";
	}
	@RequestMapping(value = "/financeWithdraw", method = RequestMethod.GET)
	@Transactional
	public String financeUsers(Model model) {
		model.addAttribute("userList", userDAO.listWithdrawStatusUsers(CostService.withdraw_agree));
		model.addAttribute("listReportCenters", reportCenterDAO.listWithdrawStatus(CostService.withdraw_agree));
		return "financeWithdraw";
	}
	@RequestMapping(value = "/financeWithdraw/already", method = RequestMethod.GET)
	@Transactional
	public String financeUsersAlready(Model model) {
		model.addAttribute("userList", userDAO.listWithdrawStatusUsers(CostService.withdraw_send));
		model.addAttribute("listReportCenters", reportCenterDAO.listWithdrawStatus(CostService.withdraw_send));
		return "financeWithdrawAlready";
	}
	@RequestMapping(value = "/financeReportCenter/{id}", method = RequestMethod.GET)
	@Transactional
	public String withDrawRequest(
			@PathVariable int id, Principal principal,RedirectAttributes ra) {
		ReportCenter rep = reportCenterDAO.getReportCenterById(id);
		if(rep==null){
			ra.addFlashAttribute("flashMsg", "非法提现请求");
		}else if (rep.getAccountRemain().compareTo(rep.getWithdrawRequest())<0) {
			ra.addFlashAttribute("flashMsg", "额度不够");
		}else{
			BigDecimal withdrawRequest = rep.getWithdrawRequest();
			rep.addWithdraw(withdrawRequest);
			rep.setWithdrawRequest(new BigDecimal(0));
			rep.setWithdrawStatus(CostService.withdraw_send);
			rep.setWithdrawDate(new Timestamp(System.currentTimeMillis()));
			reportCenterDAO.updateReportCenter(rep);
			operationDAO.addOperation(new Operation(null,rep,"提现",withdrawRequest));
		}
		return "redirect:/financeWithdraw";
	}
}
