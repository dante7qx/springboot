package org.dante.springboot.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@RestController
public class ClientController {

	@GetMapping("/ip")
	public InetAddress getIP() {
		InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return localHost;
	}

	@GetMapping("/addr")
	public String getAddr() throws BadHanyuPinyinOutputFormatCombination {
		StringBuilder builder = new StringBuilder();
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			builder.append("Host -> ").append(localHost.getHostName()).append("\n").append("IP -> ")
					.append(localHost.getHostAddress()).append("\n");
			String pingyin = "博大的中华汉字";
			
			HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
			outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			builder.append(pingyin).append("\n").append(PinyinHelper.toHanYuPinyinString(pingyin, outputFormat, " ", false));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	@GetMapping("/timeout")
	public String checkTimeout() {
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "ok";
	}
}
