package org.dante.springboot.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookVO implements Serializable {
	
	private static final long serialVersionUID = -3712230614324696962L;
	private Long id;
	private String title;
	private String author;
	private BigDecimal price;
	
	
	public static void main(String[] args) throws UnknownHostException {
		InetAddress ia = InetAddress.getLocalHost();
		String host = ia.getHostName();//获取计算机主机名 
		String IP= ia.getHostAddress();//获取计算机IP 
		System.out.println(host + " == " + IP);
	}
	
}
