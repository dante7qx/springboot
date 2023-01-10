package org.dante.springboot.vo;

import lombok.Data;

@Data
public class DeviceInfoDTO {
	private String equipmentManufacturer; // 设备产商
	private String ipv4Address; // 设备ipv4地址
	private String deviceName; // 设备名
	private Integer portNumber; // 端口数量
}
