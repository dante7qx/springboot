package org.dante.springboot.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dante.springboot.service.UserService;
import org.dante.springboot.util.ExcelUtil;
import org.dante.springboot.vo.Result;
import org.dante.springboot.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<UserVO> userList = userService.list();
        ExcelUtil.exportExcel(response, "用户列表", "用户数据", UserVO.class, userList);
    }

    @PostMapping("/import")
    public Result<String> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        List<UserVO> userList = ExcelUtil.importExcel(file, UserVO.class);
        userService.saveBatch(userList);
        return Result.success("导入成功");
    }

}
