package com.shop.controller;

import java.security.Principal;

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
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
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
	//TODO
	public String listMyReports(Model model,Principal principal) {
        String userName = principal.getName();
        User u = userDAO.getUserByName(userName);
		model.addAttribute("listReportCenters", this.reportCenterDAO.listReportCenters());
		return "myReport";
	}
}

