package org.dante.springboot.controller;

import org.dromara.sms4j.core.factory.SmsFactory;
import org.dromara.sms4j.provider.enumerate.SupplierType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/")
	public String hello() {
		return "你好";
	}
	
	// 测试发送固定模板短信
    @RequestMapping("/send")
    public void sendSms() {
         //阿里云向此手机号发送短信
        SmsFactory.createSmsBlend(SupplierType.ALIBABA).sendMessage("18888888888","123456");
        //华为短信向此手机号发送短信
        SmsFactory.createSmsBlend(SupplierType.HUAWEI).sendMessage("16666666666","000000");
    }
	
}
