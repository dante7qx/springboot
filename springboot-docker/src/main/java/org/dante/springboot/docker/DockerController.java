package org.dante.springboot.docker;

import java.text.NumberFormat;
import java.time.Instant;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.util.StrUtil;

@RestController
public class DockerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DockerController.class);
	
	@Autowired
	private MsgProp msgProp;

	@Value("${hello.msg}")
	private String ss;
	
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
		LOGGER.info("Free memory: {}", format.format(freeMemory / mb) + mega);
		LOGGER.info("Allocated memory: {}", format.format(allocatedMemory / mb) + mega);
		LOGGER.info("Max memory: {}", format.format(maxMemory / mb) + mega);
		LOGGER.info("Total free memory: {}", format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
		LOGGER.info("=================================================================\n");

		return msgProp.getMsg() + "Docker，你现在位于腾讯 Gaia 平台！";
	}
	
	@GetMapping("/prop")
	public String propertiesUtil() {
		String msg = PropertiesUtils.getString("hello.msg");
		LOGGER.warn(msg);
		LOGGER.error(msg);
		return msg;
	}
	
	@PostMapping("/docker")
	public String dockerPost(@RequestBody MsgVO msg) {
		return msg.toString();
	}
	
	@GetMapping("/ip")
	public String ip(HttpServletRequest request) {
		return IPUtils.getIpAddr(request).concat(" <==> ").concat(IPUtils2.getIpFromRequest(request));
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
		return new MsgVO("S_KQS0932", msgProp.getMsg(), Instant.now().toEpochMilli(), msgProp.getCacheName());
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
	
	@GetMapping("/undefined/{id}")
	public String undefined(@PathVariable String id) throws InterruptedException {
		String result = StrUtil.isNullOrUndefined(id) ? "参数格式错误" : id;
		return "result -> " + result;
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
