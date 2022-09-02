package org.dante.springboot.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Response {
	private int code; // 状态码
	private String msg; // 返回结果
}
