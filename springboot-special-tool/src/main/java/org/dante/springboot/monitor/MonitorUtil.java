package org.dante.springboot.monitor;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

public class MonitorUtil {
	
	
	public static void main(String[] args) {
        String data = "{\"sig\":\"82be8ccd9608a0e36a830a65d9b85c40\",\"operatorType\":1}";

        HttpRequest request = HttpRequest.post("https://open.andmu.cn/v3/open/api/token")
                .header("appid", "195a6f9e81cd4ac4b0545e71703b7447")
                .header("md5", "819d348922d7342f235ba3d1e3f1b2f7")
                .header("timestamp", "1663060228423")
                .header("version", "1.0.0")
                .header("User-Agent", "Apipost client Runtime/+https://www.apipost.cn/")
                .header("Content-Type", "application/json")
                .header("signature", "Z9cwlEfRFqGnX6czu0WCizomT4JOGv+QoNnvP8Z32bVZWvCsm1LbtaelZ4hELKgJlmGYjO8+8uNiInK1SY25WuD8Up5Fmw5UEgctZR1X4DTxA/mUcTULF1vqNkJ6W+iGuTgjFuIJTq2gxfJslNWP4Au1zsGJr7O2JZDEDVWnn18=")
                .body(data);

        HttpResponse response = request.execute();
        Assert.isTrue(response.isOk(), "Request failed with status: " + response.getStatus());
        System.out.println(response.body());
    }
	
}
