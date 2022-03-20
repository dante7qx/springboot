package org.dante.springboot.controller;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.dante.springboot.controller.DateUtils.TimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new SpecialDateEditor());
	}

}

class SpecialDateEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) {
		if (!StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
		Date date = null;
		if (text.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
			date = DateUtils.parseDateTime(text);
		} else if (text.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
			date = DateUtils.parseDateTime(text, TimeFormat.LONG_DATE_MINU_PATTERN_LINE);
		} else if (text.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
			date = DateUtils.parseDate(text);
		} else if (text.matches("^\\d{4}-\\d{1,2}$")) {
			date = DateUtils.parseDate(text + "-01");
		}
		setValue(date);
	}

}

class DateUtils {

	/**
	 * 获取当前日期
	 * 
	 * @return date
	 */
	public static Date currentDate() {
		return Date.from(Instant.now());
	}

	/**
	 * 获取当前日期 （yyyy-MM-dd）
	 * 
	 * @return String
	 */
	public static String getCurrentDate() {
		return LocalDate.now().toString();
	}

	/**
	 * 获取当前日期 （yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return String
	 */
	public static String getCurrentDatetime() {
		return TimeFormat.LONG_DATE_PATTERN_LINE.formatter.format(LocalDateTime.now());
	}

	/**
	 * 获取当前日期 （yyyy-MM-dd HH:mm:ss.SSS）
	 * 
	 * @return String
	 */
	public static String getCurrentDateWithMilliSecond() {
		return TimeFormat.LONG_DATE_PATTERN_WITH_MILSEC_LINE.formatter.format(LocalDateTime.now());
	}

	/**
	 * String 转 Date （yyyy-mm-dd HH:mm:ss）
	 *
	 * @param timeStr
	 * @return
	 */
	public static Date parseDateTime(String timeStr) {
		return localDateTimeToDate(LocalDateTime.parse(timeStr, TimeFormat.LONG_DATE_PATTERN_LINE.formatter));
	}

	/**
	 * String 转 Date (yyyy-mm-dd）
	 *
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static Date parseDate(String timeStr) {
		return localDateToDate(LocalDate.parse(timeStr, TimeFormat.SHORT_DATE_PATTERN_LINE.formatter));
	}

	/**
	 * String 转 Date (不能设置 hh, mm ,ss)
	 *
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static Date parseDate(String timeStr, TimeFormat format) {
		return localDateToDate(LocalDate.parse(timeStr, format.formatter));
	}

	/**
	 * String 转 LocalDateTime
	 *
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static LocalDateTime parseLocalDateTime(String timeStr, TimeFormat format) {
		return LocalDateTime.parse(timeStr, format.formatter);
	}

	/**
	 * String 转 Date (设置 hh, mm ,ss)
	 *
	 * @param timeStr
	 * @param format
	 * @return
	 */
	public static Date parseDateTime(String timeStr, TimeFormat format) {
		return localDateTimeToDate(LocalDateTime.parse(timeStr, format.formatter));
	}

	/**
	 * Date 转 String （yyyy-MM-dd）
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		return formatDate(date, TimeFormat.SHORT_DATE_PATTERN_LINE);
	}

	/**
	 * Date 转 String （yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, TimeFormat.LONG_DATE_PATTERN_LINE);
	}

	/**
	 * Date 转 String （格式使用format）
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, TimeFormat format) {
		return format.formatter.format(dateToLocalDateTime(date));
	}

	/**
	 * LocalDateTime 转 Date
	 * 
	 * @param localDateTime
	 * @return
	 */
	private static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * localDate 转 Date
	 * 
	 * @param localDate
	 * @return
	 */
	private static Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Date 转 LocalDateTime
	 * 
	 * @param date
	 * @return
	 */
	private static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Date 转 LocalDate
	 * 
	 * @param date
	 * @return
	 */
	private static LocalDate dateToLocalDate(Date date) {
		return dateToLocalDateTime(date).toLocalDate();
	}

	/**
	 * 为指定日期添加指定天数 (时分秒为0)
	 * 
	 * @param sourceDate
	 * @param day
	 * @return
	 */
	public static Date addDayNoTime(Date sourceDate, int day) {
		LocalDate localDate = dateToLocalDate(sourceDate);
		LocalDate targetDate = localDate.plus(day, ChronoUnit.DAYS);
		return localDateToDate(targetDate);
	}

	/**
	 * 为指定日期添加指定月数 (时分秒为0)
	 * 
	 * @param sourceDate
	 * @param month
	 * @return
	 */
	public static Date addMonthNoTime(Date sourceDate, int month) {
		LocalDate localDate = dateToLocalDate(sourceDate);
		LocalDate targetDate = localDate.plus(month, ChronoUnit.MONTHS);
		return localDateToDate(targetDate);
	}

	/**
	 * 为指定日期添加指定年数 (时分秒为0)
	 * 
	 * @param sourceDate
	 * @param month
	 * @return
	 */
	public static Date addYearNoTime(Date sourceDate, int year) {
		LocalDate localDate = dateToLocalDate(sourceDate);
		LocalDate targetDate = localDate.plus(year, ChronoUnit.YEARS);
		return localDateToDate(targetDate);
	}

	/**
	 * 为指定日期添加指定天数
	 * 
	 * @param sourceDate
	 * @param day
	 * @return
	 */
	public static Date addDayWithTime(Date sourceDate, int day) {
		LocalDateTime localDate = dateToLocalDateTime(sourceDate);
		LocalDateTime targetDate = localDate.plusDays(day);
		return localDateTimeToDate(targetDate);
	}

	/**
	 * 为指定日期添加指定月数
	 * 
	 * @param sourceDate
	 * @param month
	 * @return
	 */
	public static Date addMonthWithTime(Date sourceDate, int month) {
		LocalDateTime localDate = dateToLocalDateTime(sourceDate);
		LocalDateTime targetDate = localDate.plusMonths(month);
		return localDateTimeToDate(targetDate);
	}

	/**
	 * 为指定日期添加指定年数
	 * 
	 * @param sourceDate
	 * @param year
	 * @return
	 */
	public static Date addYearWithTime(Date sourceDate, int year) {
		LocalDateTime localDate = dateToLocalDateTime(sourceDate);
		LocalDateTime targetDate = localDate.plusYears(year);
		return localDateTimeToDate(targetDate);
	}

	/**
	 * 时间格式
	 */
	public enum TimeFormat {
		/**
		 * 短时间格式
		 * 
		 * SHORT_DATE_PATTERN_YEAR_MONTH 只能用于format
		 */
		SHORT_DATE_PATTERN_YEAR_MONTH("yyyyMM"), SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
		SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"), SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
		SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

		/**
		 * 长时间格式
		 */
		LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"), LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
		LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"), LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),
		LONG_DATE_MINU_PATTERN_LINE("yyyy-MM-dd HH:mm"),

		/**
		 * 长时间格式 带毫秒
		 */
		LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
		LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
		LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
		LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");

		private transient DateTimeFormatter formatter;

		TimeFormat(String pattern) {
			formatter = DateTimeFormatter.ofPattern(pattern);
		}
	}

}
