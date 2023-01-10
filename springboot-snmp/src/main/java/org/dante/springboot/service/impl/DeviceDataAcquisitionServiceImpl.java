package org.dante.springboot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dante.springboot.config.SnmpDeviceDataAcquisition;
import org.dante.springboot.service.DeviceDataAcquisitionService;
import org.dante.springboot.vo.DeviceInfoDTO;
import org.dante.springboot.vo.MemoryInfoDTO;
import org.dante.springboot.vo.PortFlowRateDTO;
import org.dante.springboot.vo.PortInfoDTO;
import org.dante.springboot.vo.SnmpMessageDTO;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeviceDataAcquisitionServiceImpl implements DeviceDataAcquisitionService {

	private SnmpDeviceDataAcquisition snmpConfig;


    @Override
    public DeviceInfoDTO queryDeviceInfo(SnmpMessageDTO snmpInfo) {
        this.snmpConfig = new SnmpDeviceDataAcquisition(snmpInfo);
        return snmpConfig.acquireDeviceInfo();
    }

    @Override
    public List<PortInfoDTO> queryPortInfo(SnmpMessageDTO snmpInfo) {
        this.snmpConfig = new SnmpDeviceDataAcquisition(snmpInfo);
        return snmpConfig.acquirePortInfo();
    }

    @Override
    public Double queryCpuUsage(SnmpMessageDTO snmpInfo) {
        this.snmpConfig = new SnmpDeviceDataAcquisition(snmpInfo);
        return snmpConfig.acquireCpu();
    }

    @Override
    public MemoryInfoDTO queryMemoryUsage(SnmpMessageDTO snmpInfo) {
        this.snmpConfig = new SnmpDeviceDataAcquisition(snmpInfo);
        return snmpConfig.acquireMemoryUsage();
    }

    @Override
    public List<PortFlowRateDTO> queryPortInFlow(SnmpMessageDTO snmpInfo) {
        this.snmpConfig = new SnmpDeviceDataAcquisition(snmpInfo);

        Map<String, Long> inFlowMap1 = snmpConfig.acquirePortInFlow();
        long time1 = new Date().getTime();

        sleepByQueryFlow(); // 停止五秒

        long time2 = new Date().getTime();
        Map<String, Long> inFlowMap2 = snmpConfig.acquirePortInFlow();

        //计算两次获取时间差
        long time = (time2-time1)/1000;
        log.info("入流量采集设备:{}\t || 花费时间:{}/s",snmpInfo.getIp(),time);

        return flowAlgorithm(inFlowMap1, inFlowMap2, time, snmpInfo.getIp());
    }

    @Override
    public List<PortFlowRateDTO> queryPortOutFlow(SnmpMessageDTO snmpInfo) {
        this.snmpConfig = new SnmpDeviceDataAcquisition(snmpInfo);

        Map<String, Long> outFlowMap1 = snmpConfig.acquirePortOutFlow();
        long time1 = new Date().getTime();

        sleepByQueryFlow(); // 停止五秒

        long time2 = new Date().getTime();
        Map<String, Long> outFlowMap2 = snmpConfig.acquirePortOutFlow();

        //计算两次获取时间差
        long time = (time2-time1)/1000;
        log.info("出流量采集设备:{}\t || 花费时间:{}/s",snmpInfo.getIp(),time);

        return flowAlgorithm(outFlowMap1, outFlowMap2, time, snmpInfo.getIp());
    }

    /**
     * 采集流量及算方式
     * @param flow1 第一次采集的数据
     * @param flow2 第二次采集的数据
     * @param timeLag 两次采集数据之间间隔
     * @param ip 被采集设备ip
     * @return 计算之后的流量数据
     */
    private List<PortFlowRateDTO> flowAlgorithm (Map<String, Long> flow1, Map<String, Long> flow2, Long timeLag, String ip){
        ArrayList<PortFlowRateDTO> resultList = new ArrayList<>();
        for (String key : flow1.keySet()) {
            PortFlowRateDTO portFlow = new PortFlowRateDTO();

            Long value1 = flow1.get(key);
            Long value2 = flow2.get(key);
            long resultValue = (value2-value1) / timeLag * 8 ;

            portFlow.setPortName(key);
            portFlow.setFirstFlow(value1);
            portFlow.setTimeLag(timeLag);
            portFlow.setSecondFlow(value2);
            portFlow.setFinalValue(resultValue);

            resultList.add(portFlow);
        }
        return resultList;
    }

    /**
     * 调用Thread.sleep()方法
     */
    private void sleepByQueryFlow(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
