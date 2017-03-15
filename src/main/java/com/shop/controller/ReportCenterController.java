package com.shop.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;
import com.shop.base.BaseObject;
import com.shop.dao.GroupDAO;
import com.shop.dao.OperationDAOImpl;
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
import com.shop.model.Group;
import com.shop.model.Operation;
import com.shop.model.ReportCenter;
import com.shop.model.User;
import com.shop.model.View;
import com.shop.service.CostService;
import com.shop.service.ReportService;

@Controller
public class ReportCenterController extends BaseObject {
	@Autowired(required = true)
	@Qualifier(value = "reportCenterDAO")
	private ReportCenterDAO reportCenterDAO;
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	@Autowired(required = true)
	@Qualifier(value = "groupDAO")
	private GroupDAO groupDAO;
	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;

	@Autowired(required = true)
	@Qualifier(value = "reportService")
	private ReportService reportService;
	
	@RequestMapping(value = "/reportCenters", method = RequestMethod.GET)
	@Transactional
	public String listReportCenters(Model model) {
		model.addAttribute("reportCenter", new ReportCenter());
		model.addAttribute("listReportCenters",
				this.reportCenterDAO.listReportCenters());
		model.addAttribute("listUsers",
				this.userDAO.listAvailableReporterCenterUsers());
		return "reportCenter";
	}

	// For add and update ReportCenter both
	@RequestMapping(value = "/reportCenter/add", method = RequestMethod.POST)
	@Transactional
	public String addReportCenter(
			@ModelAttribute("reportCenter") ReportCenter p, Principal principal) {
		String userName = principal.getName();
		User owner = userDAO.getUserByName(userName);
		if (p.getName() == null || p.getName().equals("")) {
			int userid = p.getOwner().getId();
			User name = userDAO.getUserById(userid);
			p.setName(name.getName() + "销售中心");
		}
		if (p.getId() == 0) {
			if (p.getName() == null || p.getName().equals("")) {
				int userid = p.getOwner().getId();
				User name = userDAO.getUserById(userid);
				p.setName(name.getName() + "销售中心");
			}
			this.reportCenterDAO.addReportCenter(p);
			operationDAO.addOperation(new Operation(owner, p, "添加销售中心", p
					.getElectricMoney(), "1:" + p.getMoney1() + "2:"
					+ p.getMoney2()));
		} else {
			if (p.getName() == null || p.getName().equals("")) {
				int userid = p.getOwner().getId();
				User name = userDAO.getUserById(userid);
				p.setName(name.getName() + "销售中心");
			}
			ReportCenter x = reportCenterDAO.getReportCenterById(p.getId());
			x.setOwner(p.getOwner());
			x.setMoney1(p.getMoney1());
			x.setMoney2(p.getMoney2());
			x.setElectricMoney(p.getElectricMoney());
			this.reportCenterDAO.updateReportCenter(x);
			operationDAO.addOperation(new Operation(owner, x, "修改电子币", x
					.getElectricMoney(), "1:" + x.getMoney1() + "2:"
					+ x.getMoney2()));
		}
		return "redirect:/reportCenters";

	}

	@RequestMapping(value = "/reportCenter/withDrawRequest", method = RequestMethod.POST)
	@Transactional
	public String withDrawRequest(
			@ModelAttribute("reportCenter") ReportCenter p, Principal principal,RedirectAttributes ra) {
		String userName = principal.getName();
		User owner = userDAO.getUserByName(userName);
		ReportCenter rep = reportCenterDAO.getReportCenterById(p.getId());
		if(rep==null||rep.getOwner()==null||rep.getOwner().getId()!=owner.getId()){
			ra.addFlashAttribute("flashMsg", "非法提现请求");
		}else if (rep.getAccountRemain().compareTo(p.getWithdrawRequest())<0) {
			ra.addFlashAttribute("flashMsg", "额度不够");
		}else{
			rep.setWithdrawRequest(p.getWithdrawRequest());
			rep.setWithdrawStatus(CostService.withdraw_init);
		}
		return "redirect:/myReport";
	}

	@RequestMapping(value = "/platformReportCenterWithDrawRequest/{id}", method = RequestMethod.GET)
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
			reportCenterDAO.updateReportCenter(rep);
			operationDAO.addOperation(new Operation(null,rep,"提现",withdrawRequest));
		}
		return "redirect:/reportCenters";
	}	
	@RequestMapping("/reportCenter/edit/{id}")
	@Transactional
	public String editReportCenter(@PathVariable("id") int id, Model model) {
		model.addAttribute("reportCenter",
				this.reportCenterDAO.getReportCenterById(id));
		model.addAttribute("listReportCenters",
				this.reportCenterDAO.listReportCenters());
		model.addAttribute("listUsers",
				this.userDAO.listAvailableReporterCenterUsers());
		return "reportCenter";
	}

	@RequestMapping("/reportCenter/remove/{id}")
	@Transactional
	public String removeReportCenter(@PathVariable("id") int id) {

		this.reportCenterDAO.removeReportCenter(id);
		return "redirect:/reportCenters";
	}

	@JsonView(View.Simple.class)
	@RequestMapping(value = "/reportCenters/11", method = RequestMethod.GET)
	@Transactional
	public String listReportCenter(@ModelAttribute("message") final String msg,
			@ModelAttribute("reportCenter") ReportCenter u, Model model) {
		ReportCenter g = reportCenterDAO.getReportCenterById(u.getId());
		model.addAttribute("reportCenter", g);
		return "reportCenterTable";
	}

	public ReportCenterDAO getReportCenterDAO() {
		return reportCenterDAO;
	}

	public void setReportCenterDAO(ReportCenterDAO reportCenterDAO) {
		this.reportCenterDAO = reportCenterDAO;
	}

	@RequestMapping(value = "/reportCenter/{id}", method = RequestMethod.GET)
	@Transactional
	public String listReports(@PathVariable("id") int id, Model model) {
		ReportCenter r = reportCenterDAO.getReportCenterById(id);
		if (r != null) {
			List<User> ul = userDAO.getUserByReportCenter(r.getId());
			model.addAttribute("listUsers", ul);
		}
		model.addAttribute("reportCenter", r);
		return "myReport";
	}

	@RequestMapping(value = "/myReport", method = RequestMethod.GET)
	@Transactional
	public String listMyReports(Model model, Principal principal,
			RedirectAttributes ra) {
		String userName = principal.getName();
		User u = userDAO.getUserByName(userName);
		ReportCenter r = reportCenterDAO.getReportCenterByOwnerId(u.getId());
		if (r != null) {
			List<User> ul = userDAO.getUserByReportCenter(r.getId());
			model.addAttribute("listUsers", ul);
		}
		model.addAttribute("reportCenter", r);
		return "myReport";
	}

	static final BigDecimal A_REPORT_COST = new BigDecimal(999);
	@RequestMapping("/myReport/active/{id}")
	@Transactional
	public String activeUser(@PathVariable("id") int id, Principal principal,
			RedirectAttributes ra) {
		logger.info("activeUser " + id);
		String userName = principal.getName();
		User owner = userDAO.getUserByName(userName);
		reportService.activeUser(owner, id, ra);
//		if (target.getReportCenter().getId() != r.getId()) {
//			ra.addFlashAttribute(flashMsg, "不是你的用户不能激活");
//			logger.error("activeUser 非法激活 " + id);
//		} else {
//			if (r.getElectricMoney() == null) {
//				r.setElectricMoney(new BigDecimal(0));
//			}
//			BigDecimal b = r.getElectricMoney().add(A_REPORT_COST.negate());
//			if (b.signum() == -1) {
//				ra.addFlashAttribute(flashMsg, "钱不够");
//			} else {
//				r.setElectricMoney(b);
//				Group group = groupDAO.getAvailableGroup();
//				List<User> users = group.getUsers();
//				logger.info(group.getName() + " group.getUsers().size() "
//						+ users.size());
//				if (users.size() == 62) {
//					target.setLevel("F");
//					target.setStatus(old_status);
//					users.add(target);
//					group.setEndDate(new Timestamp(System.currentTimeMillis()));
//					Group group1 = new Group();
//					Group group2 = new Group();
//					group1.setName(group.getId() + "-A");
//					group2.setName(group.getId() + "-B");
//					Group.transform(group);
//					for (int i = 1; i < Group.labels.length; i++) {
//						List<User> ulist = group.getLevelUsers().get(
//								Group.labels[i]);
//						for (int j = 0; j < ulist.size(); j++) {
//							User user = (User) ulist.get(j);
//							logger.debug("upgrade level:" + user.getId()
//									+ " level:" + user.getLevel() + " to:"
//									+ Group.labels[i - 1]);
//							user.setLevel(Group.labels[i - 1]);
//							// 会员分红奖
//							user.addBonusMoney(Group.levelMoney[i - 1]);
//							Operation op = new Operation();
//							op.setMoney(new BigDecimal(Group.levelMoney[i - 1]));
//							op.setOperation(Group.labels[i] + "-"
//									+ Group.labels[i - 1]);
//							op.setReportCenter(r);
//							op.setUser(user);
//							operationDAO.addOperation(op);
//							if (j >= Group.maxLabels[i] / 2) {
//								user.setGroup(group2);
//								user.setPosition(j - Group.maxLabels[i] / 2 + 1);
//							} else {
//								user.setGroup(group1);
//								user.setPosition(j + 1);
//							}
//						}
//					}
//					final User userLevealA = group.getLevelUsers()
//							.get(Group.labels[0]).get(0);
//					group.getUsers().clear();
//					groupDAO.addGroup(group1);
//					groupDAO.addGroup(group2);
//					groupDAO.updateGroup(group);
//					// target.setLevel("E");
//					// target.setGroup(group2);
//					// target.setStatus(old_status);
//					// userDAO.updateUser(target);
//					ra.addFlashAttribute(flashMsg,
//							target.getName() + " 用户已经激活" + "\r\n 分群成功:id"
//									+ group1.getId() + "和" + group2.getId());
//					logger.debug(target.getName() + " 用户已经激活" + "\r\n 分群成功:id"
//							+ group1.getId() + "和" + group2.getId());
//					userLevealA.setLevel("F");
//					userLevealA.setPosition(1);
//					userLevealA.setGroup(group1);
//					userDAO.updateUser(userLevealA);
//					// 分享回馈奖
//					if (userLevealA.getParent() != null) {
//						userLevealA.getParent().addFeedbackMoney(3000);
//						operationDAO.addOperation(new Operation(target
//								.getParent(), r, "回馈奖", 3000));
//					}
//					Operation op = new Operation();
//					op.setMoney(90);
//					op.setOperation("费用2");
//					op.setReportCenter(r);
//					op.setUser(userLevealA);
//					operationDAO.addOperation(op);
//					// 有空测试下.
//					if (userLevealA.getParent() != null) {
//						userDAO.updateUser(userLevealA.getParent());
//					}
//					// 出局服务费
//					Operation op1 = new Operation();
//					op1.setMoney(90);
//					op1.setOperation("出局");
//					op1.setReportCenter(r);
//					op1.setUser(userLevealA);
//					operationDAO.addOperation(op1);
//					r.addMoney1(90);
//				} else {
//					groupDAO.refresh(group);
//					Group.transform(group);
//					String string = group.getAvailbleLabes().get(0);
//					logger.debug(" getAvailbleLabes  " + string);
//					target.setLevel(string);
//					target.setGroup(group);
//					target.setStatus(old_status);
//					int i = userDAO.getCurrentPosiztionByGroup(group, "F");
//					target.setPosition(i + 1);
//					userDAO.updateUser(target);
//					ra.addFlashAttribute(flashMsg, target.getName() + " 用户已经激活");
//				}
//				if (target.getParent() != null) {
//					target.getParent().addSaleMoney(100);
//					// 有空测试下.直推奖
//					userDAO.updateUser(target.getParent());
//					operationDAO.addOperation(new Operation(target.getParent(),
//							r, "直推奖", 100, target.getParent().getSaleMoney()
//									+ "xx 直推" + target.getId() + "直推"));
//				} else {
//					operationDAO.addOperation(new Operation(target, r,
//							"直推奖设定不了", 100));
//				}
//				logger.info(owner.toString() + "active a user "
//						+ target.toString() + " with money");
//				// 每报一单 10
//				Operation op = new Operation();
//				op.setMoney(10);
//				op.setOperation("费用1");
//				op.setReportCenter(r);
//				op.setUser(target);
//				operationDAO.addOperation(op);
//				r.addMoney1(10);
//				reportCenterDAO.updateReportCenter(r);
//			}
//		}
		return "redirect:/myReport";
	}
	@RequestMapping("/myReport/delete/{id}")
	@Transactional
	public String deleteUser(@PathVariable("id") int id, Principal principal,
			RedirectAttributes ra) {
		logger.info("activeUser " + id);
		String userName = principal.getName();
		User owner = userDAO.getUserByName(userName);
		User target = userDAO.getUserById(id);
		if (target.getReportCenter()!=null&&target.getReportCenter().getOwner().getId()==owner.getId()){
			userDAO.removeUser(target.getId());
		}else{
			ra.addAttribute(flashMsg, "非本销售中心用户");
		}
				
		return "redirect:/myReport";
	}
	

}
