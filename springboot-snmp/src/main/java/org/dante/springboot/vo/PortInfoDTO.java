package org.dante.springboot.vo;

import lombok.Data;

@Data
public class PortInfoDTO {
	private String portName;        //端口名
    private String portMac;         //端口mac地址
    private Long portMaxBw;         //端口最大带宽
    private Integer portStatus;     //端口状态
}
