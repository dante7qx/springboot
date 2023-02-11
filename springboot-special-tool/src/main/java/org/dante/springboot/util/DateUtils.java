package org.dante.springboot.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateUtils {

	/**
	 * LocalDate ==> Date
	 */
	public static Date toDate(LocalDate temporalAccessor) {
		LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
		ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
		return Date.from(zdt.toInstant());
	}
	
	/**
	 * Date ==> LocalDate
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(Date date) {
	    return date.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
}
