package org.dante.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;

/**
 * Sa-Token-SSO Server端 Controller 
 * 
 * @author dante
 *
 */
@RestController
public class SsoServerController {
	
	/*
     * SSO-Server端：处理所有SSO相关请求 (下面的章节我们会详细列出开放的接口) 
     */
    @RequestMapping("/sso/*")
    public Object ssoRequest() {
    	return SaSsoProcessor.instance.serverDister();
    }

    /**
     * 配置SSO相关参数 
     */
    @Autowired
    private void configSso(SaSsoConfig sso) {
        // 配置：未登录时返回的View 
		sso.setNotLoginView(() -> {
			return new ModelAndView("sa-login.html");
		});

        // 配置：登录处理函数 
        sso.setDoLoginHandle((name, pwd) -> {
            // 此处仅做模拟登录，真实环境应该查询数据进行登录 
            if("sa".equals(name) && "123456".equals(pwd)) {
                StpUtil.login(10001);
                return SaResult.ok("登录成功！").setData(StpUtil.getTokenValue());
            }
            return SaResult.error("登录失败！");
        });

        
    }
	
}
