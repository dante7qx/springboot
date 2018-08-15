package org.dante.springboot.docker;

import java.text.NumberFormat;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DockerController.class);
	
	@Value("${hello.msg}")
	private String hello;

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

		return hello + "Docker！";
	}
	
	@GetMapping("/")
	public MsgVO msg(HttpServletRequest request) {
		LOGGER.info("---> IP {} 请求.", IPUtils.getIpAddr(request));
		return new MsgVO("S_KQS0932", "卡秋莎", Instant.now().toEpochMilli(), "涯");
	}
	
	@GetMapping("/healthz")
	public String healthz(HttpServletRequest request) {
		String returnStr = "UP - ".concat(IPUtils.getIpAddr(request));
		LOGGER.info("---> {}", returnStr);
		return returnStr;
	}
	
}
