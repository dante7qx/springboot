package org.dante.springboot.poi;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PoiTask {

	private static final Logger LOGGER = LoggerFactory.getLogger(PoiTask.class);

	@Async("poiAsyncTask")
	public Future<String> poiUpload(List<File> files) {
		LOGGER.info("开始异步处理文件......");
		try {
			for (File file : files) {
				Thread.sleep(100);
				Workbook workbook = new XSSFWorkbook(file);
				Sheet sheet = workbook.getSheetAt(0);
				LOGGER.info(sheet.getSheetName());
				workbook.close();
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return new AsyncResult<>("11111");
	}
	
	@Async("poiAsyncTask")
	public Future<String> poiUpload(MultipartFile[] files) {
		LOGGER.info("开始异步处理文件......");
		try {
			for (MultipartFile file : files) {
				InputStream ins = file.getInputStream();
				Thread.sleep(100);
				Workbook workbook = new XSSFWorkbook(ins);
				Sheet sheet = workbook.getSheetAt(0);
				LOGGER.info(sheet.getSheetName());
				workbook.close();
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return new AsyncResult<>("11111");
	}
	
	@Async
	public Future<String> poiTest(InputStream ins) {
		try {
			Workbook workbook = new XSSFWorkbook(ins);
			Sheet sheet = workbook.getSheetAt(0);
			LOGGER.info(sheet.getSheetName());
			Thread.sleep(5000);
			workbook.close();
			
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return new AsyncResult<>("11111");
	}
	
}
