package org.dante.springboot.controller;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FileUploadController {
	
	private static final String FOLDER = "/Users/dante/Documents/Project/java-world/springboot/springboot-fileupload/upload/";

	@PostMapping(value = "/upload")
	public String uploadEncode(@RequestParam("photos") MultipartFile[] photos, @RequestParam Map<String, String> params,
			HttpServletRequest request) {
		log.info("=================> 附件个数 {}", photos.length);
		log.info("参数type => {}", new String(Base64.getDecoder().decode(params.get("type"))));
		log.info("参数businessId => {}", new String(Base64.getDecoder().decode(params.get("businessId"))));
		String result = "ok";
		try {
			int x = 8;
			for (MultipartFile file : photos) {
				x++;
				log.info(file.getOriginalFilename());
				FileUtils.writeByteArrayToFile(
						new File(FOLDER + x + ".jpg"),
						Base64.getDecoder().decode(new String(file.getBytes()).replace("\r\n", "")));
			}
		} catch (Exception e) {
			log.error("文件上传失败！", e);
			result = "wrong";
		}
		return result;
	}
}
