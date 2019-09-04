package org.dante.springboot.logger;

import javax.servlet.http.HttpServletRequest;

import org.dante.springboot.prop.SpiritProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class Logger2Controller {
	
	@Autowired
	private SpiritProperties spiritProperties;

	@GetMapping("/logger2/{id}")
	public String logger(HttpServletRequest request, @PathVariable Integer id) {
		String remoteIP = getHeader(request, "X-Real-IP");
		String host = getHeader(request, "Host");
		String xForword = getHeader(request, "X-Forwarded-For");
		String remoteAddr = request.getRemoteAddr();
		String remoteHost = request.getRemoteHost();
		String returnStr = "";

		switch (id) {
		case 1:
			returnStr = "info";
			break;
		case 2:
			returnStr = "warning";
			break;
		case 3:
			returnStr = "error";
			break;
		default:
			break;
		}
		log.info("remoteIP -> {}, xForword -> {}, host -> {}, remoteHost -> {}, remoteAddr -> {}", remoteIP, xForword, host, remoteHost, remoteAddr);
		log.info("request id {}", id);
		log.warn("request id {}", id);
		log.error("request id {}", id);
		log.info("appId {}", spiritProperties.getAliPay().getAppId());
		return "Logger ->" + returnStr;
	}
	
	private String getHeader(HttpServletRequest request, String headName) {  
	    String value = request.getHeader(headName);  
	    return !StringUtils.isEmpty(value) && !"unknown".equalsIgnoreCase(value) ? value : "";  
	}  

}
