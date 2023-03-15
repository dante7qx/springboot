package org.dante.springboot.date;

import java.util.Date;

import org.junit.jupiter.api.Test;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeaveTimeUtilTests {
	
	String schedule = "08:00,12:00,14:00,18:00";
	Date startTime = DateUtil.parseDateTime("2023-02-07 09:00:00");
	Date endTime = DateUtil.parseDateTime("2023-02-07 15:00:00");
	
	@Test
	public void calculateLeaveTime() {
		long leaveTime = LeaveTimeUtil.calculateLeaveTime(schedule, startTime, endTime);
		long calOneDayMin = LeaveTimeUtil.calOneDayMin(schedule.split(","));
		float leaveDay = LeaveTimeUtil.leaveTime2Day(leaveTime, calOneDayMin);
		log.info("请假时长 --> {} - {}", leaveTime, leaveDay);
	}
	
	@Test
	public void calculateLeaveTimeFormat() {
		log.info("请假时长 --> {}", LeaveTimeUtil.calculateLeaveTimeFormat(schedule, startTime, endTime));
	}

}
