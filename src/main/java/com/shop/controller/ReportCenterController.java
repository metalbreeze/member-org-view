package com.shop.controller;

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
import com.shop.dao.ReportCenterDAO;
import com.shop.model.ReportCenter;
import com.shop.model.View;

@Controller
public class ReportCenterController extends BaseObject{
	@Autowired(required = true)
	@Qualifier(value = "reportCenterDAO")
	private ReportCenterDAO reportCenterDAO;



	@RequestMapping(value = "/reportCenters", method = RequestMethod.GET)
	public String listReportCenters(Model model) {
		model.addAttribute("reportCenter", new ReportCenter());
		model.addAttribute("listReportCenters", this.reportCenterDAO.listReportCenters());
		return "reportCenter";
	}

	// For add and update ReportCenter both
	@RequestMapping(value = "/reportCenter/add", method = RequestMethod.POST)
	public String addReportCenter(@ModelAttribute("reportCenter") ReportCenter p) {

		if (p.getId() == 0) {
			// new ReportCenter, add it
			this.reportCenterDAO.addReportCenter(p);
		} else {
			// existing ReportCenter, call update
			this.reportCenterDAO.updateReportCenter(p);
		}

		return "redirect:/reportCenters";

	}

	@RequestMapping("/reportCenter/edit/{id}")
	public String editReportCenter(@PathVariable("id") int id, Model model) {
		model.addAttribute("reportCenter", this.reportCenterDAO.getReportCenterById(id));
		return "reportCenter";
	}

	@RequestMapping("/reportCenter/remove/{id}")
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

}

