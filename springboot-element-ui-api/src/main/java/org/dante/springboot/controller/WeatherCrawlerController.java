package org.dante.springboot.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.dante.springboot.po.DpCityCodePO;
import org.dante.springboot.service.DpCityCodeService;
import org.dante.springboot.util.WeatherCrawler;
import org.dante.springboot.vo.PageReq;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherCrawlerController {
	
	private DpCityCodeService dpCityCodeService;
	
	public WeatherCrawlerController(DpCityCodeService dpCityCodeService) {
		this.dpCityCodeService = dpCityCodeService;
	}
	
	@PostMapping("/list_city")
	public ResponseEntity<Page<DpCityCodePO>> getList(@RequestBody PageReq param) {
		
		return ResponseEntity.ok(dpCityCodeService.selectCityCodePage(param));
	}
	
	@PostMapping("/{code}")
	public Map<String, String> getInfo(@PathVariable String code) throws IOException {
		String ch = dpCityCodeService.selectByCode(code).getPk().getCh();
		String weatherInfo = WeatherCrawler.getWeatherInfoByCode(ch, code);
		Map<String, String> result = new HashMap<>();
		result.put("data", weatherInfo);
		return result;
	}
	
}
