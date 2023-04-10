package org.dante.springboot;

import javax.xml.bind.DatatypeConverter;

import org.dante.springboot.vo.Submit;
import org.junit.jupiter.api.Test;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

public class ChinaMobileTests {
	
	@Test
	public void sendSms() {
		Submit submit = new Submit();
		submit.setEcName("接口联调账号");
		submit.setApId("lzfzwy");
		submit.setSecretKey("Passwd@#1234");
		submit.setMobiles("18211011254");
		submit.setContent("你好，移动。");
		submit.setSign("bM16CfN2B");
		submit.setAddSerial("10657204006");
		
		StringBuffer buf = new StringBuffer();
		buf.append(submit.getEcName())
		.append(submit.getApId())
		.append(submit.getSecretKey())
		.append(submit.getMobiles())
		.append(submit.getContent())
		.append(submit.getSign())
		.append(submit.getAddSerial());
		
		System.out.println("source ==> " + buf.toString());
		String mac = DigestUtil.md5Hex(buf.toString(), "UTF-8");
		submit.setMac(mac);
		System.out.println("mac ==>" + mac);
		String param = JSONUtil.toJsonStr(submit);
		System.out.println("param ==>" + param);
		String base64 = DatatypeConverter.printBase64Binary(param.getBytes());
		System.out.println("base64 ==>" + base64);
		
//		HttpRequest req = HttpUtil.createPost("http://112.35.1.155:1992/sms/norsubmit");
//		Map<String, String> header = Maps.newHashMap();
//		header.put("Content-Type", "application/json");
//		req.addHeaders(header);
//		req.body(base64);
//		HttpResponse execute = req.execute();
//		System.out.println(execute.body());

		String result = HttpUtil.post("http://112.35.1.155:1992/sms/norsubmit", base64);
		System.out.println("result ==>" + result);
	}
	
}
