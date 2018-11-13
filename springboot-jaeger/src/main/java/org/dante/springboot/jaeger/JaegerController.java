package org.dante.springboot.jaeger;

import java.text.NumberFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class JaegerController {

	@Value("${spring.application.name}")
	private String appName;
	@Autowired
	private MsgProp msgProp;
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/runtime")
	public String docker() {
		Runtime runtime = Runtime.getRuntime();
		final NumberFormat format = NumberFormat.getInstance();
		final long maxMemory = runtime.maxMemory();
		final long allocatedMemory = runtime.totalMemory();
		final long freeMemory = runtime.freeMemory();
		final long mb = 1024 * 1024;
		final String mega = " MB";

		log.info("========================== Memory Info ==========================");
		log.info("Free memory: " + format.format(freeMemory / mb) + mega);
		log.info("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
		log.info("Max memory: " + format.format(maxMemory / mb) + mega);
		log.info("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
		log.info("=================================================================\n");

		return "你好，".concat(msgProp.getMsg());
	}
	
	@GetMapping("/")
	public MsgVO msg(HttpServletRequest request) throws InterruptedException {
		return new MsgVO("S_KQS0932", msgProp.getMsg(), Instant.now().toEpochMilli(), "涯");
	}
	
	@GetMapping("/call") 
	public ResponseEntity<List<MsgVO>> call() {
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("appName", appName);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);		
		ResponseEntity<List<MsgVO>> resp = restTemplate.exchange(msgProp.getReqUrl(), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<MsgVO>>() {});
		return resp;
	}
	
	@GetMapping("/svc2")
	public List<MsgVO> svc2(HttpServletRequest request) throws InterruptedException {
		String header = request.getHeader("appName");
		String[] reqUrl = msgProp.getReqUrl().split(",");
		MsgVO vo1 = restTemplate.getForObject(reqUrl[0].concat("?appName={appName}"), MsgVO.class, appName.concat(" -> ").concat(header));
		MsgVO vo2 = restTemplate.getForObject(reqUrl[1].concat("?appName={appName}"), MsgVO.class, appName.concat(" -> ").concat(header));
		return Arrays.asList(vo1, vo2);
	}
	
	@GetMapping("/svc3")
	public MsgVO svc3(HttpServletRequest request) throws InterruptedException {
		String param = request.getParameter("appName");
		return new MsgVO(appName.concat(" -> ").concat(param), msgProp.getMsg(), Instant.now().toEpochMilli(), "木");
	}
	
	@GetMapping("/svc4")
	public MsgVO svc4(HttpServletRequest request) throws InterruptedException {
		String param = request.getParameter("appName");
		return new MsgVO(appName.concat(" -> ").concat(param), msgProp.getMsg(), Instant.now().toEpochMilli(), "辰");
	}
	
	@GetMapping("/healthz")
	public String healthz(HttpServletRequest request) throws InterruptedException {
		String returnStr = "UP - ".concat(IPUtils.getIpAddr(request));
		log.info("---> {}", returnStr);
		Thread.sleep(msgProp.getSleep() * 1000);
		return returnStr;
	}
	
}
