package org.dante.springboot;

import org.dante.springboot.email.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEmailApplicationTests {

	@Autowired
	private MailService mailService;
	
	@Test
	public void sendEmail() {
		mailService.sendSimpleMail("307909697@qq.com", "测试邮件", "来自Springboot 163 的问候！");
	}

}
