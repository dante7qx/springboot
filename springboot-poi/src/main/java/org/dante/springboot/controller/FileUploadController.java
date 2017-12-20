package org.dante.springboot.controller;

import org.dante.springboot.service.PoiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
