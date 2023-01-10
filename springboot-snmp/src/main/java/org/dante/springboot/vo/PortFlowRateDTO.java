package org.dante.springboot.vo;

import lombok.Data;

@Data
public class PortFlowRateDTO {
	
	private Long finalValue;  //计算结果
    private Long firstFlow;   //第一次采集数据
    private Long timeLag;     //时间间隔
    private Long secondFlow;  //第二次采集数据
    private String portName;  //端口名
	
}
