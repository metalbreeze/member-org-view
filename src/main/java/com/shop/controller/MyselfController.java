package com.shop.controller;

import java.math.BigDecimal;
import java.security.Principal;

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
import com.shop.model.Group;
import com.shop.model.User;
import com.shop.service.CostService;
import com.shop.service.ProductService;
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
	@Qualifier(value="productService")
	private ProductService productService;
	
	@Autowired(required=true)
	@Qualifier(value="reportCenterDAO")
	private ReportCenterDAO reportCenterDao;
	
    @Autowired(required=true)
    @Qualifier(value="encoder")
    private PasswordEncoder encoder;
    
	@RequestMapping(value= "/myself/edit", method = RequestMethod.POST)
	@Transactional
	public String myselfEdit(@ModelAttribute("user") User p,RedirectAttributes ra){
		if(p.getId() == 0){
			logger.error("myself add new user");
			User userByName = userDAO.getUserByName(p.getName());
			if (userByName!=null){
				ra.addFlashAttribute("flashMsg", "已经有同名用户");
				logger.debug( "已经有同名用户");
				return "redirect:/register";
			}else{
				User parenetUser = userDAO.getUserByName(p.getParent().getName());
				if(parenetUser==null){
					ra.addFlashAttribute("flashMsg", "错误推荐人");
					logger.debug( "错误推荐人");
					return "redirect:/register";
				}
				p.setParent(parenetUser);
				logger.debug("password  "+p.getPassword());
				logger.debug("report center"+p.getReportCenter().getId());
				String encode = encoder.encode(p.getPassword());
				p.setPassword(encode);
				p.setName(p.getName().replaceAll(" ", "").replaceAll("　",""));
				this.userService.addUser(p);
				ra.addFlashAttribute("flashMsg", "添加成功,重新登录");
				return "redirect:/login";
			}
			
		}else{
			logger.warn("shoud not go to here");
			this.userService.updateUser(p);
		}
		return "redirect:/myself";
	}
    @RequestMapping(value = "/myself", method = RequestMethod.GET)
    @Transactional
    public String userInfo(Model model, Principal principal) {
        // After user login successfully.
        String userName = principal.getName();
        User u = userDAO.getUserByNameWithChildren(userName);
        logger.debug("User Name: "+ userName+"parent()"+u.getParent());
        Group g = Group.transform(u.getGroup());
        if(g!=null)model.addAttribute("levelUsers", g.getLevelUsers());
		model.addAttribute("labels", Group.labels);
        model.addAttribute("user", u);
        model.addAttribute("list", u.getChildren());
//		model.addAttribute("listUsers", this.userService.listUsers());
//		model.addAttribute("listProducts",this.productService.getProductList() );
        logger.debug("User Name: "+ userName);
        return "myself";
    }
    
    @RequestMapping(value = "/myself/withDrawRequest", method = RequestMethod.POST)
    @Transactional
    public String withDrawRequest(Model model, Principal principal,@ModelAttribute("user") User p,RedirectAttributes ra){
        String userName = principal.getName();
        User user = userDAO.getUserByName(userName);
        if (user.getId()!=p.getId()){
        	ra.addFlashAttribute("flashMsg", "用户不对");
        }else if (user.getAccountRemain().compareTo(p.getWithdrawRequest())<0){
        	ra.addFlashAttribute("flashMsg", "额度不够");
        }else if (user.getWithdrawStatus()== CostService.withdraw_init){
        	ra.addFlashAttribute("flashMsg", "上次提现请求在等待批准,不能再次申请");
        }else {
        	ra.addFlashAttribute("flashMsg", "提现请求已发送");
        	user.setWithdrawRequest(p.getWithdrawRequest());
        	user.setWithdrawStatus(CostService.withdraw_init);
          	userDAO.updateUser(user);
        }
        return "redirect:/myself";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @Transactional
    public String register(Model model) {
    	model.addAttribute("user", new User());
        // After user login successfully.
//		model.addAttribute("listUsers", this.userService.listUsers());
		model.addAttribute("listReportCenters", reportCenterDao.listReportCenters());
		model.addAttribute("listProducts",this.productService.getProductList() );
        return "register";
    }
}
