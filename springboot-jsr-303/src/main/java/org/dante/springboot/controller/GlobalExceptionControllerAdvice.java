package org.dante.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.dante.springboot.vo.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "org.dante.springboot.controller")
public class GlobalExceptionControllerAdvice {

	@ExceptionHandler(value = { MethodArgumentNotValidException.class, BindException.class })
	public Result handleVaildException(Exception e) {
		BindingResult bindingResult = null;
		if (e instanceof MethodArgumentNotValidException) {
			bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
		} else if (e instanceof BindException) {
			bindingResult = ((BindException) e).getBindingResult();
		}
		Map<String, String> errorMap = new HashMap<>(16);
		bindingResult.getFieldErrors()
			.forEach((fieldError) -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
		return Result.build(400, "非法参数 !", errorMap);
	}

}
