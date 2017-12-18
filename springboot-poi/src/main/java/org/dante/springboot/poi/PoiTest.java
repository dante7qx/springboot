package org.dante.springboot.poi;

import java.io.InputStream;
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
public class PoiTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PoiTest.class);

	@Async
	public Future<String> poiUpload(MultipartFile file) {
		try {
			InputStream ins = file.getInputStream();
			Thread.sleep(10000);
			Workbook workbook = new XSSFWorkbook(ins);
			Sheet sheet = workbook.getSheetAt(0);
			LOGGER.info(sheet.getSheetName());
			workbook.close();
			
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
