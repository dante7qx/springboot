package org.dante.springboot.vo;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Person {

	private String name;
	private String address;
	private String horbby;

	public static void main(String[] args) {
		Person person = Person.builder().name("但丁").address("太平桥东里").horbby("源").build();
		System.out.println(person);
	}
}
