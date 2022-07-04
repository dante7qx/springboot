package org.dante.springboot.controller;

import org.dante.springboot.po.Userinfo;
import org.dante.springboot.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
	
	@PostMapping
	public IPage<Userinfo> findPage() {
		return userinfoService.page(new Page<>(1, 2));
	}
	
}
