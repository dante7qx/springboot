package org.dante.springboot.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dante.springboot.constants.DeviceConfigOidConstants;
import org.dante.springboot.constants.DeviceHardwareOidConstants;
import org.dante.springboot.constants.DevicePortOidConstants;
import org.dante.springboot.prop.ManufacturerEnum;
import org.dante.springboot.vo.DeviceInfoDTO;
import org.dante.springboot.vo.MemoryInfoDTO;
import org.dante.springboot.vo.PortInfoDTO;
import org.dante.springboot.vo.SnmpMessageDTO;
import org.snmp4j.PDU;
import org.snmp4j.Target;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnmpDeviceDataAcquisition extends SnmpConfiguration {

	
	private static final int PDU_GET = PDU.GET;
    private static final int PDU_WALK = PDU.GETBULK;
    private static final int PORT_OPEN = 1;
    private static final String PORT_ERROR_NAME = "NULL0";

    SnmpMessageDTO snmpMessageDTO;
    Target target;

    public SnmpDeviceDataAcquisition(SnmpMessageDTO snmpMessageDTO){
        this.snmpMessageDTO = snmpMessageDTO;
    }

    /*初始化SNMP参数*/
    public void init(){
        String deviceIp = "udp:" + snmpMessageDTO.getIp() + "/161";
        this.target = createTarget(snmpMessageDTO.getVersion(), snmpMessageDTO.getCommunity(), deviceIp);
        initSnmpV2();
    }

    /*关闭SNMP开启的服务*/
    public void snmpClose(){
        try {
            snmp.close();
            transportMapping.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*采集设备基础信息*/
    public DeviceInfoDTO acquireDeviceInfo(){
        init();
        DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO();

        // 查询使用的oid
        ArrayList<String> oidList = new ArrayList<>();
        oidList.add(DeviceConfigOidConstants.sysDescr);
        oidList.add(DeviceConfigOidConstants.sysObjectID);

        List<String> configList = createPDUs(oidList, PDU_GET, target);
        // String portNumber = createPDU(DevicePortOidConstants.IF_DESCR, PDU_WALK, target);
        String portNumber = createTable(DevicePortOidConstants.IF_NUMBER, PDU_WALK, target).get(0);
        String deviceName = configList.get(0);
        String deviceObjectId = configList.get(1);

        deviceInfoDTO.setDeviceName(deviceName);
        //调用判断厂商id的方法，返回对应id的厂商名
        deviceInfoDTO.setEquipmentManufacturer(manufacturerEstimate(deviceObjectId));
        deviceInfoDTO.setPortNumber(Integer.parseInt(portNumber));

        snmpClose();
        return deviceInfoDTO;
    }

    /*采集CPU使用率*/
    public Double acquireCpu(){
        init();
        List<String> cpuList = createTable(DeviceHardwareOidConstants.CPU_HR_PROCESSOR_LOAD, PDU_GET, target);

        double sum = 0;
        for (String s : cpuList) {
            System.out.println(s);
            sum += Double.parseDouble(s);
        }

        snmpClose();
        return sum / cpuList.size();
    }

    /*采集内存使用信息*/
    public MemoryInfoDTO acquireMemoryUsage(){
        init();
        ArrayList<String> oidList = new ArrayList<>();
        oidList.add(DeviceHardwareOidConstants.MEMORY_TOTAL);   //物理内存总容量
        oidList.add(DeviceHardwareOidConstants.MEMORY_AVAIL);   //物理内存空闲容量
        oidList.add(DeviceHardwareOidConstants.MEMORY_BUFFER);  //物理内存缓冲容量
        oidList.add(DeviceHardwareOidConstants.MEMORY_CACHE);   //物理内存缓存容量

        List<String> memoryInfoList = createPDUs(oidList, PDU_GET, target);
        long memoryTotal = Long.parseLong(memoryInfoList.get(0));
        long memoryFree = Long.parseLong(memoryInfoList.get(1));
        long memoryBuffer = Long.parseLong(memoryInfoList.get(2));
        long memoryCache = Long.parseLong(memoryInfoList.get(3));

        //获取物理内存已使用容量
        long memoryUsed = (memoryTotal - memoryFree - memoryBuffer - memoryCache) + (memoryBuffer + memoryCache);
        //计算最终物理内存使用率
        double memoryUsage = ((double) memoryUsed / (double) memoryTotal) * 100;

        MemoryInfoDTO memoryInfoDTO = new MemoryInfoDTO();
        memoryInfoDTO.setTotal(memoryTotal);
        memoryInfoDTO.setUsed(memoryUsed);
        memoryInfoDTO.setFree(memoryFree);
        memoryInfoDTO.setBuffer(memoryBuffer);
        memoryInfoDTO.setCache(memoryCache);
        memoryInfoDTO.setMemoryUsage(memoryUsage);

        return memoryInfoDTO;
    }



    /*采集设备所有端口信息*/
    public List<PortInfoDTO> acquirePortInfo(){
        init();
        List<PortInfoDTO> portInfoDTOS = new ArrayList<>();

        List<String> oidList = new ArrayList<>();
        oidList.add(DevicePortOidConstants.IF_DESCR); // 端口名
        oidList.add(DevicePortOidConstants.IF_PHYS_ADDRESS); //端口MAC地址
        oidList.add(DevicePortOidConstants.IF_SPEED); // 端口最大带宽
        oidList.add(DevicePortOidConstants.IF_OPER_STATUS); //端口状态

        Map<Integer, List<String>> map = createTables(oidList, PDU_GET, target);
        for (Integer key : map.keySet()) {
            PortInfoDTO portInfoDTO = new PortInfoDTO();

            List<String> resultList = map.get(key);
            portInfoDTO.setPortName(resultList.get(0));
            portInfoDTO.setPortMac(resultList.get(1));
            portInfoDTO.setPortMaxBw(Long.parseLong(resultList.get(2)));
            portInfoDTO.setPortStatus(Integer.parseInt(resultList.get(3)));

            portInfoDTOS.add(portInfoDTO);
        }

        snmpClose();
        return portInfoDTOS;
    }

    /*采集所有端口入流量*/
    public Map<String, Long> acquirePortInFlow(){
        init();
        LinkedHashMap<String, Long> flowRateMap = new LinkedHashMap<>();

        ArrayList<String> oidList = new ArrayList<>();
        oidList.add(DevicePortOidConstants.IF_DESCR); // 端口名
        oidList.add(DevicePortOidConstants.IF_HC_IN_OCTETS); // 端口入流量

        Map<Integer, List<String>> InFlowMap = createTables(oidList, PDU_GET, target);

        for (Integer key: InFlowMap.keySet()) {
            List<String> valueList = InFlowMap.get(key);
            flowRateMap.put(valueList.get(0),Long.parseLong(valueList.get(1)));
        }
        snmpClose();
        return flowFiltration(flowRateMap);
    }

    /*采集所有端口出流量*/
    public Map<String, Long> acquirePortOutFlow(){
        init();
        LinkedHashMap<String, Long> flowRateMap = new LinkedHashMap<>();

        ArrayList<String> oidList = new ArrayList<>();
        oidList.add(DevicePortOidConstants.IF_DESCR); // 端口名
        oidList.add(DevicePortOidConstants.IF_HC_OUT_OCTETS); // 端口出流量

        Map<Integer, List<String>> OutFlowMap = createTables(oidList, PDU_GET, target);

        for (Integer key: OutFlowMap.keySet()) {
            List<String> valueList = OutFlowMap.get(key);
            flowRateMap.put(valueList.get(0),Long.parseLong(valueList.get(1)));
        }

        snmpClose();
        return flowFiltration(flowRateMap);
    }

    /**
     * 设备采集端口过滤
     * 过滤内容:
     *      1. 端口状态为 2 的端口  (1为开,2为关)
     *      2. 端口名为 NULL0 的端口
     * @param map 初始采集数据
     * @return 过滤后数据
     */
    private Map<String, Long> flowFiltration(Map<String, Long> map){
        init();
        Map<String, Long> resultMap = new LinkedHashMap<>();

        // 采集设备所有端口状态信息
        List<String> statusList = createTable(DevicePortOidConstants.IF_OPER_STATUS, PDU_GET, target);

        if (statusList.size() != map.size()){
            log.info("<端口异常数据过滤> || 端口数量有误!!!");
            return map;
        }

        // 转换map的keySet集合类型
        ArrayList<String> keyList = new ArrayList<>(map.keySet());
        for (int i = 0; i < keyList.size(); i++) {
            String portName = keyList.get(i);
            int portStatus = Integer.parseInt(statusList.get(i));

            if (!portName.equals(PORT_ERROR_NAME)) {
                if (portStatus == PORT_OPEN) {
                    resultMap.put(portName,map.get(portName));
                }
            }
        }

        snmpClose();
        return resultMap;
    }

    /**
     * 查询设备厂商
     * @param objectOid  厂商分类oid （我也不知道这个oid是干啥的，凑合看看就得了）
     * @return 厂商名
     */
    private String manufacturerEstimate(String objectOid){
        String[] idArray = objectOid.split("\\.");
        String objectId = idArray[6];

        ManufacturerEnum[] manufacturerEnums = ManufacturerEnum.values();
        //遍历枚举类，判断传递参数是否存在相同
        for (ManufacturerEnum manufacturerEnum : manufacturerEnums) {
            Integer enumObjectId = manufacturerEnum.getObjectId();
            if (enumObjectId.equals(Integer.parseInt(objectId))) {
                return manufacturerEnum.getManufacturerName();
            }
        }
        return "未知";
    }
}
