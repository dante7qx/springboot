package org.dante.springboot;

import java.util.concurrent.TimeUnit;

import org.dante.springboot.email.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpringbootEmailApplicationTests {

	@Autowired
	private MailService mailService;
	
	@Test
	public void sendEmail() {
		mailService.sendSimpleMail("307909697@qq.com", "测试邮件", "来自Springboot 163 的问候2！");
	}
	
	@Test
	public void sendEmailHtml() {
		try {
			mailService.sendMediaMail("sunchao.0129@163.com", "测试邮件", "来自Springboot 163 的问候2！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void sendEmailHtmlAsync() {
		try {
			log.info("开始发送邮件。。。");
			mailService.sendMediaMailAsync("sunchao.0129@163.com", "测试邮件异步", "来自Springboot 163 的问候2！");
			log.info("发送邮件完成。。。");
			ThreadUtil.sleep(20, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
