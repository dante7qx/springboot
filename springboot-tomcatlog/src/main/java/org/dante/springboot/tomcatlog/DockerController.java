package org.dante.springboot.tomcatlog;

import java.text.NumberFormat;
import java.time.Instant;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.val;

@RestController
public class DockerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DockerController.class);
	
	@Autowired
	private MsgProp msgProp;

	@GetMapping("/docker")
	public String docker() {
		Runtime runtime = Runtime.getRuntime();
		final NumberFormat format = NumberFormat.getInstance();
		final long maxMemory = runtime.maxMemory();
		final long allocatedMemory = runtime.totalMemory();
		final long freeMemory = runtime.freeMemory();
		final long mb = 1024 * 1024;
		final String mega = " MB";

		LOGGER.info("========================== Memory Info ==========================");
		LOGGER.info("Free memory: " + format.format(freeMemory / mb) + mega);
		LOGGER.info("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
		LOGGER.info("Max memory: " + format.format(maxMemory / mb) + mega);
		LOGGER.info("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
		LOGGER.info("=================================================================\n");

		return msgProp.getMsg() + "Docker，你现在位于腾讯 Gaia 平台！";
	}
	
	@PostMapping("/docker")
	public String dockerPost(@RequestBody MsgVO msg) {
		return msg.toString();
	}
	
	@GetMapping("/")
	public MsgVO msg(HttpServletRequest request) throws InterruptedException {
		handleReqProxy(request);
		Cookie[] cookies = request.getCookies();
		 if (null==cookies) {  
			 LOGGER.info("没有cookie==============");  
         } else {  
             for(Cookie cookie : cookies){  
                 cookie.setValue(null);  
                 cookie.setMaxAge(0);// 立即销毁cookie  
                 cookie.setPath("/");  
                 LOGGER.info("被删除的cookie名字为:"+cookie.getName());  
             }  
         } 
		return new MsgVO("S_KQS0932", msgProp.getMsg(), Instant.now().toEpochMilli(), "涯");
	}
	
	@GetMapping("/msg")
	public MsgVO msg2(HttpServletRequest request) throws InterruptedException {
		handleReqProxy(request);
		return new MsgVO("S_KQS0932", msgProp.getMsg(), Instant.now().toEpochMilli(), "涯");
	}
	
	@GetMapping("/healthz")
	public String healthz(HttpServletRequest request) {
		String returnStr = "UP - ".concat(IPUtils.getIpAddr(request));
		LOGGER.info("---> {}", returnStr);
		try {
			Thread.sleep(msgProp.getSleep() * 1000);
		} catch (InterruptedException e) {
		}
		return returnStr;
	}
	
	private void handleReqProxy(HttpServletRequest request) {
		String xHeader = request.getHeader("X-DANTE-REQ");
		String xName = request.getParameter("X-NAME");
		String kongHeader = request.getHeader("Host");
		String xcId = request.getHeader("X-Consumer-ID");
		
		LOGGER.info("Header Kong Header {}, X-DANTE-REQ {}, Param xName {}", kongHeader, xHeader, xName);
		LOGGER.info("Basic Auth X-Consumer-ID => {}", xcId);
		LOGGER.info("---> IP {} 请求.", IPUtils.getIpAddr(request));
	}
	
}