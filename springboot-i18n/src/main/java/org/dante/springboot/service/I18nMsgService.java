package org.dante.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class I18nMsgService {

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String msgKey) {
		return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());

	}

}
