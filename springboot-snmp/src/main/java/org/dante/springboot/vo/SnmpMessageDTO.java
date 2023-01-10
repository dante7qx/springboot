package org.dante.springboot.vo;

import lombok.Data;

@Data
public class SnmpMessageDTO {
	private String ip;              //设备ip
    private Integer version;        //snmp的version
    private String community;       //snmp的团体名
    private String username;        //snmp3V用户名
    private String authPassword;    //snmp3V auth 密码
    private String privPassword;    //snmp3V priv 密码
}
