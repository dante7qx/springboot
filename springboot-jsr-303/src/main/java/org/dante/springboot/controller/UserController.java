package org.dante.springboot.controller;

import javax.validation.Valid;

import org.dante.springboot.vo.Groups;
import org.dante.springboot.vo.Result;
import org.dante.springboot.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@PostMapping("/list")
	public Result list(@RequestBody @Valid UserVO user) {
		return Result.ok("用户列表");
	}

	@PostMapping("/add")
	public Result save(@RequestBody @Validated(Groups.Add.class) UserVO user) {
		return Result.ok();
	}

	@PostMapping("/update")
	public Result update(@RequestBody @Validated(Groups.Update.class) UserVO user) {
		return Result.ok();
	}
}

/**
	
curl -X POST http://localhost:8101/user/list -H 'Content-Type: application/json' -d '{"id": 100, "password": "123@qwe"}'

curl -X POST http://localhost:8101/user/add -H 'Content-Type: application/json' -d '{"password": "123@qwe", "email": "test@126"}'

curl -X POST http://localhost:8101/user/update -H 'Content-Type: application/json' -d '{"name": "dante", "email": "dante@163.com", "phone": "139202911", "gender": 3}'


 */
