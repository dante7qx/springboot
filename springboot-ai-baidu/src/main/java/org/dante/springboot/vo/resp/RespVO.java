package org.dante.springboot.vo.resp;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;

@Data
public class RespVO<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigInteger logId;
	
	private String errorMsg;
	
	private T data;
	
}
