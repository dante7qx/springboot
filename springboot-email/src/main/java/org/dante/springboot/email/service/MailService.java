package org.dante.springboot.email.service;

public interface MailService {
	
	public void sendSimpleMail(String to, String subject, String content);
	
	public void sendMediaMail(String to, String subject, String content) throws Exception;
	
	public void sendMediaMailAsync(String to, String subject, String content) throws Exception;
}
