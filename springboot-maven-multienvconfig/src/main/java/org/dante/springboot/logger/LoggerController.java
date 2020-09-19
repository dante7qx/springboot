package org.dante.springboot.logger;

import javax.servlet.http.HttpServletRequest;

import org.dante.springboot.prop.SpiritProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggerController {

	private final static Logger logger = LoggerFactory.getLogger(LoggerController.class);
	
	@Autowired
	private SpiritProperties spiritProperties;

	@GetMapping("/logger/{id}")
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
		logger.info("remoteIP -> {}, xForword -> {}, host -> {}, remoteHost -> {}, remoteAddr -> {}", remoteIP, xForword, host, remoteHost, remoteAddr);
		logger.info("request id {}", id);
		logger.warn("request id {}", id);
		logger.error("request id {}", id);
		logger.info("appId {}", spiritProperties.getAliPay().getAppId());
		return "Logger ->".concat(returnStr).concat("<br>").concat(spiritProperties.toString());
	}
	
	private String getHeader(HttpServletRequest request, String headName) {  
	    String value = request.getHeader(headName);  
	    return !StringUtils.isEmpty(value) && !"unknown".equalsIgnoreCase(value) ? value : "";  
	}  

}
