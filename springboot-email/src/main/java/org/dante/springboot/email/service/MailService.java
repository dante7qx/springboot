package org.dante.springboot.email.service;

public interface MailService {
	public void sendSimpleMail(String to, String subject, String content);
}
