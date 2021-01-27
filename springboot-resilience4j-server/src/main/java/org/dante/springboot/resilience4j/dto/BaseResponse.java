package org.dante.springboot.resilience4j.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class BaseResponse<T> {
	private T data;
	private ResponseType responseType;
	private String message;
}
