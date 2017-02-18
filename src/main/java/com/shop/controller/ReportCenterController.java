package com.shop.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Timestamp;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonView;
import com.shop.base.BaseObject;
import com.shop.dao.GroupDAO;
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
import com.shop.model.Group;
import com.shop.model.ReportCenter;
import com.shop.model.User;
import com.shop.model.View;

@Controller
public class ReportCenterController extends BaseObject{
	@Autowired(required = true)
	@Qualifier(value = "reportCenterDAO")
	private ReportCenterDAO reportCenterDAO;
	@Autowired(required = true)
	@Qualifier(value = "userDAO")
	private UserDAO userDAO;
	@Autowired(required = true)
	@Qualifier(value = "groupDAO")
	private GroupDAO groupDAO;	
	@RequestMapping(value = "/reportCenters", method = RequestMethod.GET)
	@Transactional
	public String listReportCenters(Model model) {
		model.addAttribute("reportCenter", new ReportCenter());
		model.addAttribute("listReportCenters", this.reportCenterDAO.listReportCenters());
		model.addAttribute("listUsers", this.userDAO.listAvailableReporterCenterUsers());
		return "reportCenter";
	}

	// For add and update ReportCenter both
	@RequestMapping(value = "/reportCenter/add", method = RequestMethod.POST)
	@Transactional
	public String addReportCenter(@ModelAttribute("reportCenter") ReportCenter p) {
		if (p.getName()==null||p.getName().equals("")){
			int userid = p.getOwner().getId();
			User name = userDAO.getUserById(userid);
			p.setName(name.getName()+"报单中心");
		}
		if (p.getId() == 0) {
			if (p.getName()==null||p.getName().equals("")){
				int userid = p.getOwner().getId();
				User name = userDAO.getUserById(userid);
				p.setName(name.getName()+"报单中心");
			}
			this.reportCenterDAO.addReportCenter(p);
			
		} else {
			if (p.getName()==null||p.getName().equals("")){
				int userid = p.getOwner().getId();
				User name = userDAO.getUserById(userid);
				p.setName(name.getName()+"报单中心");
			}
			ReportCenter x = reportCenterDAO.getReportCenterById(p.getId());
			x.setOwner(p.getOwner());
			x.setMoney(p.getMoney());
			this.reportCenterDAO.updateReportCenter(x);
		}

		return "redirect:/reportCenters";

	}

	@RequestMapping("/reportCenter/edit/{id}")
	@Transactional
	public String editReportCenter(@PathVariable("id") int id, Model model) {
		model.addAttribute("reportCenter", this.reportCenterDAO.getReportCenterById(id));
		model.addAttribute("listReportCenters", this.reportCenterDAO.listReportCenters());
		model.addAttribute("listUsers", this.userDAO.listAvailableReporterCenterUsers());
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
	public String listReportCenter( @ModelAttribute("message") final String  msg,
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

	
	
	@RequestMapping(value = "/myReport", method = RequestMethod.GET)
	@Transactional
	public String listMyReports(Model model,Principal principal,RedirectAttributes ra) {
        String userName = principal.getName();
        User u = userDAO.getUserByName(userName);
        ReportCenter r = reportCenterDAO.getReportCenterByOwnerId(u.getId());
        if(r!=null){
        	List<User> ul = userDAO.getUserByReportCenter(r.getId());
        	model.addAttribute("listUsers", ul);
        }
		return "myReport";
	}
	static final BigDecimal A_REPORT_COST = new BigDecimal(999);
	@RequestMapping("/myReport/active/{id}")
	@Transactional
	public String activeUser(@PathVariable("id") int id,Principal principal,RedirectAttributes ra) {
		logger.info("activeUser " + id);
        String userName = principal.getName();
        User owner = userDAO.getUserByName(userName);
        User target = userDAO.getUserById(id);
        ReportCenter r = reportCenterDAO.getReportCenterByOwnerId(owner.getId());
        if(target.getReportCenter().getId()!=r.getId() ){
        	ra.addFlashAttribute(flashMsg, "不是你的用户不能激活");
        	logger.error("activeUser 非法激活 " + id);
        }else{
			BigDecimal b = r.getMoney().add(A_REPORT_COST.negate());
			if (b.signum()==-1){
				ra.addFlashAttribute(flashMsg, "钱不够");
			}else{
				Group group = groupDAO.getAvailableGroup();
				List<User> users = group.getUsers();
				logger.info(group.getName()+" group.getUsers().size() " +users.size());
				if (users.size()==62){
					group.setEndDate(new Timestamp(System.currentTimeMillis()));
					Group group1 = new Group();
					Group group2 = new Group();
					group1.setName(group.getId()+"-A");
					group2.setName(group.getId()+"-B");
					Group.transform(group);
					for (int i = 1; i < Group.labels.length; i++) {
						List<User> ulist = group.getLevelUsers().get(Group.labels[i]);
						for (int j = 0; j < ulist.size();j++) {
							User user = (User) ulist.get(j);
							logger.debug("upgrade level:" + user.getId() + " level:"
									+ user.getLevel() + " to:" + Group.labels[i - 1]);
							user.setLevel(Group.labels[i - 1]);
							if (j >= Group.maxLabels[i] / 2) {
								user.setGroup(group2);
							} else {
								user.setGroup(group1);
							}
						}
					}
					final User userLevealA = group.getLevelUsers().get(Group.labels[0]).get(0);
					group.getUsers().clear();
					groupDAO.addGroup(group1);
					groupDAO.addGroup(group2);
					groupDAO.updateGroup(group);
					target.setLevel("E");
					target.setGroup(group2);
					target.setStatus(old_status);
					userDAO.updateUser(target);
					ra.addFlashAttribute(flashMsg, target.getName()+" 用户已经激活"
							+"\r\n 分群成功:id"+group1.getId()+"和"+group2.getId());
					logger.debug(target.getName()+" 用户已经激活"
							+"\r\n 分群成功:id"+group1.getId()+"和"+group2.getId());
					userLevealA.setLevel("F");
					userLevealA.setGroup(group1);
					userDAO.updateUser(userLevealA);
				}else{
					target.setLevel("F");
					target.setGroup(group);
					target.setStatus(old_status);
					userDAO.updateUser(target);
					ra.addFlashAttribute(flashMsg, target.getName()+" 用户已经激活");
				}
				logger.info(owner.toString()+"active a user "+target.toString()+" with money");
				r.setMoney(b);
				reportCenterDAO.updateReportCenter(r);
			}
        }
        
		return "redirect:/myReport";
	}

}


