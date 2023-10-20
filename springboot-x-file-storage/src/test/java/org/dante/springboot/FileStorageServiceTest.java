package org.dante.springboot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.lang.Console;

class FileStorageServiceTest extends SpringbootXFileStorageApplicationTests {

	@Autowired
    private FileStorageService fileStorageService;
	
	@Test
	void apiTest() {
		FileInfo fileInfo = fileStorageService.getFileInfoByUrl("http://127.0.0.1:8101/local-plus/6531f1019df846c0aa79762c.jar");
		Console.log("FileInfo -> {}", fileInfo);
		//文件是否存在
		boolean exists = fileStorageService.exists("http://127.0.0.1:8101/local-plus/6531f1019df846c0aa79762c.jar");
		assertTrue(exists);
	}
	
}

