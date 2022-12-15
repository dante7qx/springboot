package org.dante.springboot.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

/**
 * Sa-Token-SSO Client1端 Controller 
 * 
 * @author dante
 *
 */
@RestController
public class SsoClientController {
	
	// SSO-Client端：首页 
    @GetMapping("/")
    public String index() {
    	 String str = "<h2>Sa-Token SSO-Client 2 应用端</h2>" + 
         		"<p>当前会话是否登录：" + StpUtil.isLogin() + "</p>" + 
                 "<p><a href=\"javascript:location.href='/sso/login?back=' + encodeURIComponent(location.href);\">登录</a> " + 
                 "<a href='/sso/logout?back=self'>注销</a></p>";
    	 return str;
    }

    // 全局异常拦截 
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        e.printStackTrace(); 
        return SaResult.error(e.getMessage());
    }
	
}
