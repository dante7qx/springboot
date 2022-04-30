package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * EasyExcel是一个基于Java的简单、省内存的读写Excel的开源项目。在尽可能节约内存的情况下支持读写百M的Excel。
 * 
 * https://www.yuque.com/easyexcel/doc/easyexcel
 * 
 * @author dante
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class})
public class SpringbootJimuReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJimuReportApplication.class, args);
	}
}
