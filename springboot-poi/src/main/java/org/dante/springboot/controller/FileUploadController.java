package org.dante.springboot.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.dante.springboot.service.PoiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private PoiService poiService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile[] files) {
		String result = "ok";
		try {
			poiService.importExcel(files);
		} catch (Exception e) {
			logger.error("文件上传失败！", e);
			result = "wrong";
		}
		return result;
	}

	@GetMapping("/export")
	public void export(HttpServletResponse response) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		this.createExcel(workbook);
		response.setHeader("Content-disposition",
				"attachment;filename=" + URLEncoder.encode("考勤统计.xls", StandardCharsets.UTF_8.name()));
		// 定义输出类型
		response.setContentType("application/octet-stream");
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.flush();
			workbook.close();
		}
	}
	
	private void createExcel(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);

		HSSFSheet sheet = workbook.createSheet("sheet");

		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell_00 = row0.createCell(0);
		cell_00.setCellStyle(style);
		cell_00.setCellValue("日期");
		HSSFCell cell_01 = row0.createCell(1);
		cell_01.setCellStyle(style);
		cell_01.setCellValue("午别");

		HSSFRow row1 = sheet.createRow(1);
		HSSFCell cell_10 = row1.createCell(0);
		cell_10.setCellStyle(style);
		cell_10.setCellValue("20180412");
		HSSFCell cell_11 = row1.createCell(1);
		cell_11.setCellStyle(style);
		cell_11.setCellValue("上午");

		HSSFRow row2 = sheet.createRow(2);
		HSSFCell cell_21 = row2.createCell(1);
		cell_21.setCellStyle(style);
		cell_21.setCellValue("下午");

		// 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
		// 行和列都是从0开始计数，且起始结束都会合并
		// 这里是合并excel中日期的两行为一行
		CellRangeAddress region = new CellRangeAddress(1, 2, 0, 0);
		sheet.addMergedRegion(region);
	}

}
