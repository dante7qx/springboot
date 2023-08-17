package org.dante.springboot;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootMagicApiApplicationTests {
	
	public static void main(String[] args) {
		String str = """ 
				haha hello name
				nihao sima wo
				haha cls
				cks haha cls
				""";
		System.out.println(str.getBytes().length);
		System.out.println(str.getBytes().length + 4);
	}
	
}
