package org.dante.springboot.docker;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class MsgPropValidator implements Validator {
	final Pattern pattern = Pattern.compile("^(?:\\d){1,}{1,3}(?:\\.)(?:\\d){1,3}(?:\\.)(?:\\d){1,3}(?:\\.)(?:\\d){1,3}");

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == MsgProp.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "host", "host.empty");
		ValidationUtils.rejectIfEmpty(errors, "port", "port.empty");
		MsgProp msg = (MsgProp) target;
		if(msg.getHost() != null && !this.pattern.matcher(msg.getHost()).matches()) {
			errors.rejectValue("host", "无效的 Host！");
		}
	}

}
