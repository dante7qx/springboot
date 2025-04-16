package org.dante.springboot.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.dante.springboot.SpringbootElUIApiApplicationTests;
import org.dante.springboot.po.DpCityCodePK;
import org.dante.springboot.po.DpCityCodePO;
import org.dante.springboot.util.WeatherCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import cn.hutool.core.lang.Console;
import cn.hutool.extra.pinyin.PinyinUtil;

public class DpCityCodeServiceTests extends SpringbootElUIApiApplicationTests {

	private final DpCityCodeService dpCityCodeService;

	DpCityCodeServiceTests(@Autowired DpCityCodeService dpCityCodeService) {
		this.dpCityCodeService = dpCityCodeService;
	}

	@Test
	void batchInsert() throws IOException {
		Map<String, String> cityCodeMap = WeatherCrawler.fetchAllCityCodes();
		List<DpCityCodePO> pos = Lists.newArrayList();
		cityCodeMap.forEach((k, v) -> {
			pos.add(new DpCityCodePO(new DpCityCodePK(v, k, PinyinUtil.getPinyin(k, "")), ""));
		});

		dpCityCodeService.batchInsert(pos);
	}
	
	@Test
	void selectByCode() {
		DpCityCodePO po = dpCityCodeService.selectByCode("101160901");
		Console.log(po);
		assertNotNull(po);
	}

}
