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
import com.shop.dao.GroupDAO;
import com.shop.dao.OperationDAOImpl;
import com.shop.dao.ReportCenterDAO;
import com.shop.dao.UserDAO;
import com.shop.model.Group;
import com.shop.model.Operation;
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

	@Autowired(required = true)
	@Qualifier(value = "operationDAOImpl")
	private OperationDAOImpl operationDAO;

	@Autowired(required=true)
	@Qualifier(value="productService")
	private ProductService productService;
	
	@Autowired(required=true)
	@Qualifier(value="reportCenterDAO")
	private ReportCenterDAO reportCenterDao;
	
	
	@Autowired(required=true)
	@Qualifier(value="groupDAO")
	private GroupDAO groupDAO;
	
    @Autowired(required=true)
    @Qualifier(value="encoder")
    private PasswordEncoder encoder;
    
    Object registLock=new Object();
	@RequestMapping(value= "/myself/edit", method = RequestMethod.POST)
	@Transactional
	public String myselfEdit(@ModelAttribute("user") User p,RedirectAttributes ra){
		if(p.getId() == 0){
			logger.error("myself add new user");
			p.setName(p.getName().replaceAll(" ", "").replaceAll("　",""));
			User userByName = userDAO.getUserByName(p.getName());
			if (userByName!=null){
				ra.addFlashAttribute("flashMsg", "已经有同名用户");
				logger.debug( "已经有同名用户");
				return "redirect:/register";
			}else{
				synchronized (registLock) {
					
				User parenetUser = userDAO.getUserByName(p.getParent().getName());
				if(parenetUser==null){
					ra.addFlashAttribute("flashMsg", "错误推荐人");
					logger.debug( "错误推荐人");
					return "redirect:/register";
				}
				p.setParent(parenetUser);
				if(null==parenetUser.getChildrenCount()){
					parenetUser.setChildrenCount(1);
				}else{
					parenetUser.setChildrenCount(parenetUser.getChildrenCount()+1);
				}
				logger.debug("password  "+p.getPassword());
				logger.debug("report center"+p.getReportCenter().getId());
				String encode = encoder.encode(p.getPassword());
				p.setPassword(encode);
				this.userService.updateUser(parenetUser);
				this.userService.addUser(p);
				}
				ra.addFlashAttribute("flashMsg", "添加成功,重新登录");
				return "redirect:/login";
			}
			
		}else{
			logger.warn("shoud not go to here");
//			this.userService.updateUser(p);
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
		model.addAttribute("group", g);
        model.addAttribute("user", u);
        model.addAttribute("list", u.getChildren());
//        不看工作群
//        model.addAttribute("currentGroup", Group.transform(groupDAO.getAvailableGroup()));
//		model.addAttribute("listUsers", this.userService.listUsers());
//		model.addAttribute("listProducts",this.productService.getProductList() );
        model.addAttribute("withdrawDescription",CostService.withdrawDescription);
        logger.debug("User Name: "+ userName);
        return "myself";
    }
    @RequestMapping(value = "/myself/changePasswd", method = RequestMethod.POST)
    @Transactional
    public String changePasswd(Model model, Principal principal,@ModelAttribute("user") User p,RedirectAttributes ra) {
        // After user login successfully.
        String userName = principal.getName();
        User u = userDAO.getUserByName(userName);
        if(p.getPassword()==null||"".equals(p.getPassword())||!encoder.matches(p.getPassword(), u.getPassword())){
        	ra.addFlashAttribute(flashMsg,"旧密码不匹配");
        }else{
	        String encode = encoder.encode(p.getPassword_2());
	        u.setPassword(encode);
	        userDAO.updateUser(u);
	        ra.addFlashAttribute(flashMsg,"新密码修改成功");
        }
        return "redirect:/myself";
    }   
    @RequestMapping(value = "/myself/withDrawRequest", method = RequestMethod.POST)
    @Transactional
    public String withDrawRequest(Model model, Principal principal,@ModelAttribute("user") User p,RedirectAttributes ra){
        String userName = principal.getName();
        synchronized(FinanceController.userWithdraw){
	        User user = userDAO.getUserByName(userName);
	        if (user.getId()!=p.getId()){
	        	ra.addFlashAttribute("flashMsg", "用户不对");
	        }else if (new BigDecimal(0).compareTo(p.getWithdrawRequest())>=0){
	        	ra.addFlashAttribute("flashMsg", "金额不对");
	        }else if (user.getAccountRemain().compareTo(p.getWithdrawRequest())<0){
	        	ra.addFlashAttribute("flashMsg", "额度不够");
	        }else if (null == user.getWithdrawStatus()||user.getWithdrawStatus()==CostService.withdraw_disagree
	        		||user.getWithdrawStatus()==CostService.withdraw_send){
	        	ra.addFlashAttribute("flashMsg", "待审核");
	        	user.setWithdrawRequest(p.getWithdrawRequest());
	        	user.setWithdrawStatus(CostService.withdraw_init);
	          	userDAO.updateUser(user);
	          	operationDAO.addOperation(new Operation(user,null,"申请提现",user.getWithdrawRequest(),null));
	        }else if (CostService.withdraw_init == user.getWithdrawStatus()||user.getWithdrawStatus()==CostService.withdraw_agree){
	        	ra.addFlashAttribute("flashMsg", "上次提现请求在等待批准,不能再次申请");
	        }else {
	        	ra.addFlashAttribute("flashMsg", "有异常,请联系管理员");
	        	logger.info("should not go those code");
	        }
        }
        return "redirect:/myself";
    }

    
    @RequestMapping(value = "/myself/BsiteWithDrawRequest", method = RequestMethod.POST)
    @Transactional
    public String bsiteWithDrawRequest(Model model, Principal principal,@ModelAttribute("user") User p,RedirectAttributes ra){
        String userName = principal.getName();
        synchronized(FinanceController.userWithdraw){
	        User user = userDAO.getUserByName(userName);
	        if (user.getId()!=p.getId()){
	        	ra.addFlashAttribute("flashMsg", "用户不对");
	        }else if (new BigDecimal(0).compareTo(p.getPortalBsiteWithdrawRequest())>=0){
	        	ra.addFlashAttribute("flashMsg", "内网金额不对");
	        }else if (user.getBsiteAccountRemain().compareTo(p.getPortalBsiteWithdrawRequest())<0){
	        	ra.addFlashAttribute("flashMsg", "内网额度不够");
	        }else if (null == user.getPortalBsiteWithdrawRequestStatus()
	        		||0 == user.getPortalBsiteWithdrawRequestStatus()
	        		||user.getPortalBsiteWithdrawRequestStatus()==CostService.withdraw_disagree
	        		||user.getPortalBsiteWithdrawRequestStatus()==CostService.withdraw_send){
	        	ra.addFlashAttribute("flashMsg", "内网待审核");
	        	user.setPortalBsiteWithdrawRequest(p.getPortalBsiteWithdrawRequest());
	        	user.setPortalBsiteWithdrawStatus(CostService.withdraw_init);
	          	userDAO.updateUser(user);
	          	operationDAO.addOperation(new Operation(user,null,"内网申请提现",user.getPortalBsiteWithdrawRequest(),null));
	        }else if (CostService.withdraw_init == user.getPortalBsiteWithdrawStatus()||user.getPortalBsiteWithdrawStatus()==CostService.withdraw_agree){
	        	ra.addFlashAttribute("flashMsg", "上次提现请求在等待批准,不能再次申请");
	        }else {
	        	ra.addFlashAttribute("flashMsg", "有异常,请联系管理员");
	        	logger.info("should not go those code");
	        }
        }
        return "redirect:/myself";
    }
    @RequestMapping(value = "/register", method = {RequestMethod.GET,RequestMethod.POST})
    @Transactional
    public String register(Model model) {
    	model.addAttribute("user", new User());
        // After user login successfully.
//		model.addAttribute("listUsers", this.userService.listUsers());
		model.addAttribute("listReportCenters", reportCenterDao.listReportCenters());
		model.addAttribute("listProducts",ProductService.getCurrentProductList() );
		model.addAttribute("listSiteBProducts",ProductService.getCurrentSiteBProductList());
        return "register";
    }
}
