package org.dante.springboot.handler;

import org.dante.springboot.dto.SpiritResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理器
 * Knife4j 4.5.0 不完全支持 Springboot3.4.5，无法定义全局异常处理器
 */
//@RestControllerAdvice(basePackages = "org.dante.springboot.controller")
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public SpiritResult<?> handleException(Exception e) {
        return SpiritResult.error(500, "系统内部错误: " + e.getMessage());
    }

    // 可以添加更多特定异常处理
}