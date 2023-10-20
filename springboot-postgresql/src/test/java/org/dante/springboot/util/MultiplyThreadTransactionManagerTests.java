package org.dante.springboot.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import org.dante.springboot.SpringbootPostgreSQLApplicationTests;
import org.dante.springboot.service.BookinfoService;
import org.dante.springboot.service.UserinfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MultiplyThreadTransactionManagerTests extends SpringbootPostgreSQLApplicationTests {

	@Autowired
	private MultiplyThreadTransactionManager multiplyThreadTransactionManager;
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private BookinfoService bookinfoService;

	@Test
	void multiplyThreadTransaction() {
		final Integer id = 3;
		List<Runnable> tasks = new ArrayList<>();

		tasks.add(() -> {
			userinfoService.removeById(id);
			throw new RuntimeException("我就要抛出异常!");
		});
		
		tasks.add(() -> {
			bookinfoService.removeById(id);
		});
		
		multiplyThreadTransactionManager.runAsyncButWaitUntilAllDown(tasks, Executors.newCachedThreadPool());
		
		assertTrue(id == 3, "正确");
	}

}
