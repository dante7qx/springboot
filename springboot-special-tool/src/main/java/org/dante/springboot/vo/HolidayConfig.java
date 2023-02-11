package org.dante.springboot.vo;

import java.util.Date;

import lombok.Data;

/**
 * 公共节假日
 * 
 * @author dante
 *
 */
@Data
public class HolidayConfig {

	private String name;

	/** 年份 */
	private Integer year;

	/** 假日（0 否 1 是） */
	private Boolean holiday;

	/** 日期 */
	private Date holidayDate;
}
