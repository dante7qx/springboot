package org.dante.springboot.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success = false;

    private String msg;

    private T data;

    private String code;

    private String timestamp;
}
