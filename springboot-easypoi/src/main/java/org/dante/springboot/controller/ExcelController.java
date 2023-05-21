package org.dante.springboot.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.dante.springboot.util.ExcelUtil;
import org.dante.springboot.vo.UserExportVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导入导出Excel
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/eap")
public class ExcelController {

	@GetMapping("/export")
	public void export(HttpServletResponse response) {
		// 查询要导出的数据
		List<UserExportVO> users = new ArrayList<>();
		users.add(new UserExportVO("悟纤", 1, new Date(), "18688888888", "1688@qq.com",
				"https://picx.zhimg.com/80/v2-e141b3376b01e54409346bfcc9037e62_1440w.jpg", "公众号SpringBoot"));
		users.add(new UserExportVO("师傅", 1, new Date(), "18666666666", "1888@qq.com",
				"https://picx.zhimg.com/80/v2-e141b3376b01e54409346bfcc9037e62_1440w.jpg", "公众号SpringBoot"));

		ExcelUtil.exportExcelX(users, "测试导出表", "sheet1", UserExportVO.class, "测试导出表.xlsx", response);
	}
}
