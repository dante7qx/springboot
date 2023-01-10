package org.dante.springboot.util;

import java.util.List;

import com.google.common.collect.Lists;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;

public class CoreCode {
	
	public static void main(String[] args) {
		List<String> ips = Lists.newArrayList();
		for (int i = 1; i < 255; i++) {
			String ip = "192.168.1." + (i+1);
			String str = RuntimeUtil.execForStr("ping " + ip + " -t 1 -c 1");
			if(StrUtil.contains(str, "100.0% packet loss")) {
				System.out.println(ip);
			} else {
				ips.add(ip);
			}
		}
		System.out.println(ips.size());
//		System.out.println(ips);
	}


}
