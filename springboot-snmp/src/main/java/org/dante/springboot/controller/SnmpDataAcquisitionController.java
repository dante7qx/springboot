package org.dante.springboot.controller;

import java.util.List;

import org.dante.springboot.service.DeviceDataAcquisitionService;
import org.dante.springboot.vo.DeviceInfoDTO;
import org.dante.springboot.vo.MemoryInfoDTO;
import org.dante.springboot.vo.PortFlowRateDTO;
import org.dante.springboot.vo.PortInfoDTO;
import org.dante.springboot.vo.SnmpMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/snmpAcquisition")
public class SnmpDataAcquisitionController {
	
	@Autowired
    private DeviceDataAcquisitionService deviceDataAcquisitionService;

    /*设备基础信息*/
    @PostMapping("/deviceInfo")
    public DeviceInfoDTO getDeviceInfo(@RequestBody SnmpMessageDTO snmpInfo){
        return deviceDataAcquisitionService.queryDeviceInfo(snmpInfo);
    }

    /*CPU使用率*/
    @PostMapping("cpuUsage")
    public Double getCpuUsage(@RequestBody SnmpMessageDTO snmpInfo){
        return deviceDataAcquisitionService.queryCpuUsage(snmpInfo);
    }

    /*内存使用情况*/
    @PostMapping("/memoryUsage")
    public MemoryInfoDTO getMemoryUsage(@RequestBody SnmpMessageDTO snmpInfo){
        return deviceDataAcquisitionService.queryMemoryUsage(snmpInfo);
    }

    /*所有端口信息*/
    @PostMapping("/portInfo")
    public List<PortInfoDTO> getPortInfo(@RequestBody SnmpMessageDTO snmpInfo){
        return deviceDataAcquisitionService.queryPortInfo(snmpInfo);
    }

    /*端口入流量*/
    @PostMapping("/portInFlow")
    public List<PortFlowRateDTO> getPortInFlow(@RequestBody SnmpMessageDTO snmpInfo){
        return deviceDataAcquisitionService.queryPortInFlow(snmpInfo);
    }

    /*端口出流量*/
    @PostMapping("/portOutFlow")
    public List<PortFlowRateDTO> getPortOutFlow(@RequestBody SnmpMessageDTO snmpInfo){
        return deviceDataAcquisitionService.queryPortOutFlow(snmpInfo);
    }
}
