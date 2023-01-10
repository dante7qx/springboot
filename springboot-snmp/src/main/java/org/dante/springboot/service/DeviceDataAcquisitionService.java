package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.vo.DeviceInfoDTO;
import org.dante.springboot.vo.MemoryInfoDTO;
import org.dante.springboot.vo.PortFlowRateDTO;
import org.dante.springboot.vo.PortInfoDTO;
import org.dante.springboot.vo.SnmpMessageDTO;

public interface DeviceDataAcquisitionService {
	/**
     * 查询设备基本信息
     */
    DeviceInfoDTO queryDeviceInfo(SnmpMessageDTO snmpInfo);
    /**
     * 查询设备所有端口信息
     */
    List<PortInfoDTO> queryPortInfo(SnmpMessageDTO snmpInfo);
    /**
     * 查询设备CPU使用率
     */
    Double queryCpuUsage(SnmpMessageDTO snmpInfo);
    /**
     * 查询设备内存使用情况
     */
    MemoryInfoDTO queryMemoryUsage(SnmpMessageDTO snmpInfo);
    /**
     * 查询设备所有端口入流量
     */
    List<PortFlowRateDTO> queryPortInFlow(SnmpMessageDTO snmpInfo);
    /**
     * 查询设备所有端口出流量
     */
    List<PortFlowRateDTO> queryPortOutFlow(SnmpMessageDTO snmpInfo);
}
