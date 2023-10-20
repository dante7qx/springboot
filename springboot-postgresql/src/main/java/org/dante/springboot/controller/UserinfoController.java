package org.dante.springboot.controller;

import java.util.Map;

import org.dante.springboot.po.Userinfo;
import org.dante.springboot.service.BookinfoService;
import org.dante.springboot.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;

/**
 * <p>
 * 借阅者表 前端控制器
 * </p>
 *
 * @author dante
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/user")
public class UserinfoController {
	
	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private BookinfoService bookinfoService;
	
	@PostMapping("/page")
	public IPage<Userinfo> findPage() {
		return userinfoService.page(new Page<>(1, 2));
	}
	
	@PostMapping("/ub/{id}")
	public Map<String, Object> findUserBook(@PathVariable Integer id) {
		Map<String, Object> result = Maps.newHashMap();
		result.put("user", userinfoService.getById(id));
		result.put("book", bookinfoService.getById(id));
		return result;
	}
	
}
