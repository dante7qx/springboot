package org.dante.springboot.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dante.springboot.poi.PoiTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

@Service
public class PoiService {

	@Autowired
	private PoiTask poiTest;
	@Autowired
	private MultipartProperties multipartProperties;
	
	/**
	 * 上传文件异步处理
	 * 1、SpringMVC会将上传文件保存到临时目录，当请求返回后，临时目录的文件会被删除
	 * 2、需要现将文件自己保存到一个文件夹中，异步方法处理的应该是这些文件
	 * 
	 * @param files
	 * @throws IOException
	 */
	public void importExcel(MultipartFile[] files) throws IOException {
		System.out.println("开始文件处理......");
		List<File> tmpFiles = Lists.newArrayList();
		String location = multipartProperties.getLocation();
		for (MultipartFile file : files) {
			File tmpFile = new File(location+File.separatorChar+file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(file.getInputStream(), tmpFile);
			tmpFiles.add(tmpFile);
		}
		
		poiTest.poiUpload(tmpFiles);
		System.out.println("等待异步文件处理......");
	}
	
}
