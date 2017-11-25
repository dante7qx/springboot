package org.dante.springboot.quartz.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author dante
 *
 */
public class DateUtils {
	
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
     * String 转 Date	(yyyy-mm-dd）
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
    	if(date == null) {
    		return "";
    	}
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
     * 时间格式
     */
    public enum TimeFormat {
        /**
         * 短时间格式
         * 
         * SHORT_DATE_PATTERN_YEAR_MONTH 只能用于format
         */
    	SHORT_DATE_PATTERN_YEAR_MONTH("yyyyMM"),
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

        /**
         * 长时间格式
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),

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
