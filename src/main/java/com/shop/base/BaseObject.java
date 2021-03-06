package com.shop.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


public class BaseObject {
	public static final Logger logger = LoggerFactory.getLogger(BaseObject.class);
	public static final String flashMsg="flashMsg";
	public static final String old_status="old";
	public static final String new_status="new";
	public boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
	public void info(Object o , String s){
		if(o==null){
			logger.info(s);
		}else if (o instanceof RedirectAttributes){
			((RedirectAttributes)o).addAttribute(flashMsg,s);
			logger.info(s);
		}else{
			logger.info(o.toString()+" : "+s);
		}
	}
	public void error(Object o , String s){
		if(o==null){
			logger.error(s);
		}else if (o instanceof RedirectAttributes){
			((RedirectAttributes)o).addAttribute(flashMsg,s);
			logger.error(s);
		}else{
			logger.error(o.toString()+" : "+s);
		}
	}
	public void info(String s){
		logger.info(s);
	}
	public void error(String s){
		logger.error(s);
	}
}
