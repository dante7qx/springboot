package org.dante.springboot.email.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.MimeMessage;

import org.dante.springboot.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailProperties emailProperties;
	@Autowired
	private SpringTemplateEngine templateEngine;

	@Override
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailProperties.getUsername());
        message.setTo(to);
        message.setBcc("");
        message.setCc("dante7qx@gmail.com");
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info("简单邮件已经发送。");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！", e);
        }
	}

	@Override
	public void sendMediaMail(String to, String subject, String content) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        
        helper.addAttachment("附件1.png", new ClassPathResource("templates/images/email.png"));
    	helper.addAttachment("附件2.png", new UrlResource("https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg"));
    	helper.addAttachment("附件3.png", FileUtil.file("/Users/dante/Documents/Project/java-world/springboot/springboot-email/src/main/resources/templates/images/email.png"));
    	
        Context context = new Context();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Peter Milanovich!");
        model.put("address", "Company Inc, 3 Abbey Road, San Francisco CA 94102");
        model.put("sign", "JavaByDeveloper");
        model.put("type", "TRANSACTIONAL");
        
        context.setVariables(model);
        
        String html = templateEngine.process("sms", context);
        helper.setTo(to);
        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom(emailProperties.getUsername());
        mailSender.send(message);
	}

	@Async
	@Override
	public void sendMediaMailAsync(String to, String subject, String content) throws Exception {
		ThreadUtil.sleep(5, TimeUnit.SECONDS);
		sendMediaMail(to, subject, content);
	}

}
