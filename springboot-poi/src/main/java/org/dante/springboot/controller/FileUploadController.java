package org.dante.springboot.controller;

import org.dante.springboot.poi.PoiTest;
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
	private PoiTest poiTest;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file) {
		String result = "ok";
		try {
			poiTest.poiUpload(file);
		} catch (Exception e) {
			logger.error("文件上传失败！", e);
			result = "wrong";
		}
		return result;
	}
}
