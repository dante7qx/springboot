package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EasyExcel是一个基于Java的简单、省内存的读写Excel的开源项目。在尽可能节约内存的情况下支持读写百M的Excel。
 * <a href="https://www.yuque.com/easyexcel/doc/easyexcel">EasyExcel</a>
 * <p>
 * FastExcel 是由原 EasyExcel 作者创建的新项目。因为阿里宣布停止更新 EasyExcel，所以作者创建了一个新的项目 FastExcel，
 * 并将其开源，完全兼容原 EasyExcel 的所有功能和特性。
 *
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootEasyExcelApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEasyExcelApplication.class, args);
	}
}
