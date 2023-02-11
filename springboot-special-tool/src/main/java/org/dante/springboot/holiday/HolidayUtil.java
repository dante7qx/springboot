package org.dante.springboot.holiday;

import java.util.List;
import java.util.Map;

import org.dante.springboot.vo.HolidayConfig;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;

public class HolidayUtil {

	/**
	 * 获取指定年份的公共节假日以及补班信息
	 * 
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<HolidayConfig> fetchYearHoliday(int year) {
		List<HolidayConfig> holidays = Lists.newArrayList();
		String url = "http://timor.tech/api/holiday/year/" + year + "/";
		Map<?, ?> map = JSON.parseObject(HttpUtil.get(url), Map.class);
		Integer code = (Integer) map.get("code");
		if (code != null && code == 0) {
			Map<String, Map<String, Object>> holidayMap = (Map<String, Map<String, Object>>) map.get("holiday");
			for (Map<String, Object> holiday : holidayMap.values()) {
				HolidayConfig config = new HolidayConfig();
				config.setYear(year);
				config.setName(holiday.get("name").toString());
				config.setHoliday(Boolean.parseBoolean(holiday.get("holiday").toString()));
				config.setHolidayDate(DateUtil.parseDate(holiday.get("date").toString()));
				holidays.add(config);
			}
		}
		return holidays;
	}
	
}
