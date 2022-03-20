package org.dante.springboot.quartz.util;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.dante.springboot.quartz.util.DateUtils.TimeFormat;
import org.springframework.util.StringUtils;

public class SpecialDateEditor extends PropertyEditorSupport {

	@Override  
    public void setAsText(String text) {  
		if(! StringUtils.hasLength(text)) {
			setValue(null); 
			return;
		}
		Date date = null; 
		if(text.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
			date = DateUtils.parseDateTime(text);
		} else if(text.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
			date = DateUtils.parseDateTime(text, TimeFormat.LONG_DATE_MINU_PATTERN_LINE);
		} else if(text.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
			date = DateUtils.parseDate(text);
		} else if(text.matches("^\\d{4}-\\d{1,2}$")) {
			date = DateUtils.parseDate(text + "-01");
		}
        setValue(date);  
    } 
	
}
