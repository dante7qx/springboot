package org.dante.springboot.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PageReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private String keywords;

	private Integer pageNo;

	private Integer pageSize;

}
