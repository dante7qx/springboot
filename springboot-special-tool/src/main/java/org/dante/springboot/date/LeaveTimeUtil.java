package org.dante.springboot.date;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.dante.springboot.holiday.HolidayUtil;
import org.dante.springboot.util.DateUtils;
import org.dante.springboot.vo.HolidayConfig;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 请假时长工具类
 * 
 * @author dante
 *
 */
public class LeaveTimeUtil {

	/**
	 * 计算请假时长
	 * 
	 * @param schedule 排班配置 （例如：08:00,12:00,14:00,18:00）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long calculateLeaveTime(String schedule, Date startTime, Date endTime) {
		long leaveTime = 0;
		if(StrUtil.isNotEmpty(schedule)) {
			String[] segArr = schedule.split(",");
			// 公共假日
			List<HolidayConfig> holidays = HolidayUtil.fetchYearHoliday(DateUtil.thisYear());
			// 一天的考勤事件（分钟）
			long oneDayMin = calOneDayMin(segArr);
			// 午休时间（分钟）
			long midRestMin = calMidRestMin(segArr);
			// 一天内请假
			if(DateUtil.isSameDay(startTime, endTime)) {
				// 需要考勤
				if(needAttendance(holidays, startTime)) {
					leaveTime = diffSameDayMin(startTime, endTime, segArr, midRestMin);
				}
			} else {
				leaveTime = diffCrossDayMin(startTime, endTime, segArr, holidays, oneDayMin, midRestMin);
			}
		}
		return leaveTime;
	}
	
	/**
	 * 计算请假时长
	 * 
	 * @param schedule 排班配置 （例如：08:00,12:00,14:00,18:00）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String calculateLeaveTimeFormat(String schedule, Date startTime, Date endTime) {
		return formatLeaveTime(calculateLeaveTime(schedule, startTime, endTime), calOneDayMin(schedule.split(",")));
	}
	
	/**
	 * 跨天请假时长（分钟）
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private static long diffCrossDayMin(Date d1, Date d2, String[] segArr, List<HolidayConfig> holidays, long oneDayMin, long midRestMin) {
		long diffMin = 0L;
		LocalDate localDate1 = DateUtils.toLocalDate(d1);
		LocalDate localDate2 = DateUtils.toLocalDate(d2);
		for (LocalDate date = localDate1; (date.isBefore(localDate2) || date.isEqual(localDate2)); date = date.plusDays(1)) {
			final LocalDate plusDate = date;
			boolean needAttendance = needAttendance(holidays, DateUtils.toDate(plusDate));
			if(localDate1.compareTo(plusDate) == 0) {
				// 开始时间
				diffMin += needAttendance ? calStartCrossDayMin(d1, segArr, oneDayMin, midRestMin) : 0L;
			} else if(localDate2.compareTo(plusDate) == 0) {
				// 结束时间
				diffMin += needAttendance ? calEndCrossDayMin(d2, segArr, oneDayMin, midRestMin) : 0L;
			} else if(needAttendance) {
				diffMin += oneDayMin;
			}
		}
		
		return diffMin;
	}
	
	/**
	 * 计算跨天起始天请假时长
	 * 
	 * @param startTime
	 * @param segArr
	 * @param oneDayMin
	 * @param midRestMin
	 * @return
	 */
	private static long calStartCrossDayMin(Date startTime, String[] segArr, long oneDayMin, long midRestMin) {
		long leaveTime = 0L;
		String asStr = segArr[0];
		String aeStr = segArr[1];
		String psStr = segArr[2];
		String peStr = segArr[3];
		int as = Integer.parseInt(asStr.replaceAll(":", ""));
		int ae = Integer.parseInt(aeStr.replaceAll(":", ""));
		int ps = Integer.parseInt(psStr.replaceAll(":", ""));
		int pe = Integer.parseInt(peStr.replaceAll(":", ""));
		int d1hm = Integer.parseInt(DateUtil.formatTime(startTime).replaceAll(":", "").substring(0, 4));
		if(d1hm >= pe) {
			return 0L;
		}
		Date start = startTime;
		Date end = DateUtil.parseDateTime(DateUtil.formatDate(startTime) + " " + peStr + ":00");
		if(d1hm <= as) {
			leaveTime = oneDayMin;
		} else if(d1hm <= ae) {
			leaveTime = DateUtil.between(start, end, DateUnit.MINUTE) - midRestMin;
		} else if(d1hm > ae && d1hm <= ps) {
			start = DateUtil.parseDateTime(DateUtil.formatDate(startTime) + " " + psStr + ":00");
			leaveTime = DateUtil.between(start, end, DateUnit.MINUTE);
		} else if(d1hm > ps) {
			leaveTime = DateUtil.between(start, end, DateUnit.MINUTE);
		}
		return leaveTime;
	}
	
	/**
	 * 计算跨天终止天请假时长
	 * 
	 * @param endTime
	 * @param oneDayMin
	 * @param midRestMin
	 * @return
	 */
	private static long calEndCrossDayMin(Date endTime, String[] segArr, long oneDayMin, long midRestMin) {
		long leaveTime = 0L;
		String asStr = segArr[0];
		String aeStr = segArr[1];
		String psStr = segArr[2];
		String peStr = segArr[3];
		int as = Integer.parseInt(asStr.replaceAll(":", ""));
		int ae = Integer.parseInt(aeStr.replaceAll(":", ""));
		int ps = Integer.parseInt(psStr.replaceAll(":", ""));
		int pe = Integer.parseInt(peStr.replaceAll(":", ""));
		int d2hm = Integer.parseInt(DateUtil.formatTime(endTime).replaceAll(":", "").substring(0, 4));
		if(d2hm <= as) {
			return 0L;
		}
		Date start = DateUtil.parseDateTime(DateUtil.formatDate(endTime) + " " + asStr + ":00");
		Date end = endTime;
		if(d2hm <= ae) {
			leaveTime = DateUtil.between(start, end, DateUnit.MINUTE);
		} else if(d2hm > ae && d2hm <= ps) {
			end = DateUtil.parseDateTime(DateUtil.formatDate(endTime) + " " + psStr + ":00");
			leaveTime = DateUtil.between(start, end, DateUnit.MINUTE) - midRestMin;
		} else if(d2hm > ps && d2hm <= pe) {
			leaveTime = DateUtil.between(start, end, DateUnit.MINUTE) - midRestMin;
		} else if(d2hm > pe) {
			end = DateUtil.parseDateTime(DateUtil.formatDate(endTime) + " " + peStr + ":00");
			leaveTime = oneDayMin;
		}
		
		return leaveTime;
	}
	
	/**
	 * 判断指定日期是否需要考勤
	 * 
	 * @param holidays
	 * @param date
	 * @return
	 */
	private static boolean needAttendance(List<HolidayConfig> holidays, Date date) {
		boolean needAttendance = true;
		// 是否周末
		boolean isWeekend = DateUtil.date(date).isWeekend();
		HolidayConfig config = holidays.stream()
				.filter(h -> DateUtil.formatDate(h.getHolidayDate()).equals(DateUtil.formatDate(date)))
				.findFirst()
				.orElse(null);
		// 非节假日或补班
		if(config == null) {
			needAttendance = !isWeekend;
		} else {
			needAttendance = !config.getHoliday();
		}
		return needAttendance;
	}
	
	/**
	 * 每天的考勤时间（分钟）
	 * 
	 * @param segArr
	 * @return
	 */
	public static long calOneDayMin(String[] segArr) {
		String asStr = segArr[0];
		String aeStr = segArr[1];
		String psStr = segArr[2];
		String peStr = segArr[3];
		Date asDate = DateUtil.parseDateTime(LocalDate.now().toString() + " " + asStr + ":00");
		Date aeDate = DateUtil.parseDateTime(LocalDate.now().toString() + " " + aeStr + ":00");
		Date psDate = DateUtil.parseDateTime(LocalDate.now().toString() + " " + psStr + ":00");
		Date peDate = DateUtil.parseDateTime(LocalDate.now().toString() + " " + peStr + ":00");
		long commMin = DateUtil.between(asDate, aeDate, DateUnit.MINUTE)
				+ DateUtil.between(psDate, peDate, DateUnit.MINUTE);

		return commMin;
	}
	
	/**
	 * 午休时间（分钟）
	 * 
	 * @param segArr
	 * @return
	 */
	private static long calMidRestMin(String[] segArr) {
		String aeStr = segArr[1];
		String psStr = segArr[2];
		Date aeDate = DateUtil.parseDateTime(LocalDate.now().toString() + " " + aeStr + ":00");
		Date psDate = DateUtil.parseDateTime(LocalDate.now().toString() + " " + psStr + ":00");
		return DateUtil.between(aeDate, psDate, DateUnit.MINUTE);
	}
	
	/**
	 * 一天内请假时长（分钟）
	 * 
	 * @param d1
	 * @param d2
	 * @param asStr
	 * @param aeStr
	 * @param psStr
	 * @param peStr
	 * @param minus
	 * @return
	 */
	private static long diffSameDayMin(Date d1, Date d2, String[] segArr, long minus) {
		long diffMin = 0L;
		String asStr = segArr[0];
		String aeStr = segArr[1];
		String psStr = segArr[2];
		String peStr = segArr[3];
		int as = Integer.parseInt(asStr.replaceAll(":", ""));
		int ae = Integer.parseInt(aeStr.replaceAll(":", ""));
		int ps = Integer.parseInt(psStr.replaceAll(":", ""));
		int pe = Integer.parseInt(peStr.replaceAll(":", ""));
		int d1hm = Integer.parseInt(DateUtil.formatTime(d1).replaceAll(":", "").substring(0, 4));
		int d2hm = Integer.parseInt(DateUtil.formatTime(d2).replaceAll(":", "").substring(0, 4));
		if(d1hm >= pe || d2hm <= as) {
			return 0;
		} 
		Date start = d1;
		if(d1hm < as) {
			start = DateUtil.parseDateTime(DateUtil.formatDate(d1) + " " + asStr + ":00");
		} else if(d1hm >=ae && d1hm <= ps) {
			start = DateUtil.parseDateTime(DateUtil.formatDate(d1) + " " + psStr + ":00");
		}
		Date end = d2;
		if(d2hm > pe) {
			end = DateUtil.parseDateTime(DateUtil.formatDate(d2) + " " + peStr + ":00");
		} else if(d2hm >= ae && d2hm <= ps) {
			end = DateUtil.parseDateTime(DateUtil.formatDate(d2) + " " + aeStr + ":00");
		}
		diffMin = DateUtil.between(start, end, DateUnit.MINUTE);
		int starthm = Integer.parseInt(DateUtil.formatTime(start).replaceAll(":", "").substring(0, 4));
		int endhm = Integer.parseInt(DateUtil.formatTime(end).replaceAll(":", "").substring(0, 4));
		if(starthm <=ae && endhm >= ps) {
			diffMin -= minus;
		}
		return diffMin;
	}
	
	/**
	 * 格式化请假时长，格式：xx天xx小时xx分钟
	 * 
	 * 
	 * @param leaveTime
	 * @param oneDayMin
	 * @return
	 */
	private static String formatLeaveTime(long leaveTime, long oneDayMin) {
		StringBuilder display = new StringBuilder();
		long day = leaveTime / oneDayMin;
		long hour = leaveTime % oneDayMin / 60;
		long min = leaveTime % oneDayMin % 60;
		display.append(day > 0 ? day + "天" : "").append(hour > 0 ? hour + "小时" : "").append(min > 0 ? min + "分钟" : "");
		return StrUtil.isNotEmpty(display) ? display.toString() : "非工作时间，不用请假！";
	}
	
	/**
	 * 请假时长（分钟）转为天数
	 * 
	 * @param leaveTime
	 * @param oneDayMin
	 * @return
	 */
	public static float leaveTime2Day(long leaveTime, long oneDayMin) {
		float leaveDay = 0f;
		long day = leaveTime / oneDayMin;
		long hour = leaveTime % oneDayMin / 60;
		long min = leaveTime % oneDayMin % 60;
		
		if(day == 0) {
			return 0f;
		} 
		if(hour > 0) {
			leaveDay = Float.valueOf(day + "." + hour);
		} else if(min > 0) {
			leaveDay = Float.valueOf(day + ".1");
		} else {
			leaveDay = Float.valueOf(day);
		}
		return leaveDay;
	}
	
}
