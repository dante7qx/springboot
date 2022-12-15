package org.dante.springboot.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 全局异常拦截 
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        log.error("用户认证错误。", e);
        return SaResult.error(e.getMessage());
    }
	
}
