package org.dante.springboot.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupUtil {

	public static Connection.Response login(String loginUrl, String username, String password) throws IOException {
		Map<String,String> data = new HashMap<>();
	    data.put("username", username);
	    data.put("password", password);

	    Connection.Response login = Jsoup.connect(loginUrl)
	    		.timeout(30000)
	    		.validateTLSCertificates(false)
	            .ignoreContentType(true) // 忽略类型验证
	            .followRedirects(false) // 禁止重定向
	            .postDataCharset("utf-8")
	            .header("Upgrade-Insecure-Requests","1")
	            .header("Accept","application/json")
	            .header("Content-Type","application/x-www-form-urlencoded")
	            .header("X-Requested-With","XMLHttpRequest")
	            .header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
	            .data(data)
	            .method(Connection.Method.POST)
	            .execute();
	    
	    return login;
	}
	
	public static void main(String[] args) {
		try {
			Response login = JsoupUtil.login("https://192.168.1.1:4430/login.php", "admin", "risun8768!");
			
			 Document document = Jsoup.connect("https://192.168.1.1:4430/main.htm")
					 .validateTLSCertificates(false)
			            // 取出login对象里面的cookies
			            .cookies(login.cookies())
			            .get();
			 if(document != null) {
				 System.out.println(document.html());
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
