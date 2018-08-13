package org.dante.springboot.security.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dante.springboot.security.pub.LoginUser;
import org.dante.springboot.security.util.LoginUserUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionController {
	
	@RequestMapping(value = "/session-timeout")
    public void sessionTimeout(HttpServletRequest request,HttpServletResponse response) throws IOException {  
        System.out.println("<================================================>");
		if (request.getHeader("x-requested-with") != null  
                && request.getHeader("x-requested-with").equalsIgnoreCase(  
                        "XMLHttpRequest")) { // ajax 超时处理  
            response.getWriter().print("timeout");  //设置超时标识
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().close();
        } else {
        	try {
	        	LoginUser loginUser = LoginUserUtil.loginUser();
	        	if(loginUser != null) {
	        		response.sendRedirect(request.getContextPath() + "/mainpage");  
	        	} else {
	        		response.sendRedirect(request.getContextPath() + "/loginpage");  
	        	}
        	} catch (Exception e) {
        		response.sendRedirect(request.getContextPath() + "/loginpage");
			}
        }
    }
}
