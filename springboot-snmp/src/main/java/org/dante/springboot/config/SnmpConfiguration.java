package org.dante.springboot.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

public class SnmpConfiguration {
	
	public PDU pdu;
    public Snmp snmp;
    public TransportMapping<?> transportMapping;

    public String username;
    public int version;
    public int timeout = 3000;
    public int retry = 2;

    /**
     * 开启多线程以及snmp监听
     * 支持SNMP v3 版本
     * @param uName v3协议用户名
     * @param authPasswd auth密码
     * @param privPasswd priv密码
     * */
    public void initSnmpV3(String uName, String authPasswd, String privPasswd){
        this.username = uName;

        //初始化多线程消息转发类
        MessageDispatcherImpl messageDispatcher = new MessageDispatcherImpl();
        //其中要增加三种处理模型，如果snmp初始初始化使用的是Snmp(TransportMapping<? extends Address> transportMapping) ,就不需要增加
        messageDispatcher.addMessageProcessingModel(new MPv1());
        messageDispatcher.addMessageProcessingModel(new MPv2c());
        //当要支持SNMPv3版本时，需要配置user
        OctetString localEngineID = new OctetString(MPv3.createLocalEngineID());
        USM usm = new USM(SecurityProtocols.getInstance().addDefaultProtocols(), localEngineID, 0);

        OctetString username = new OctetString(uName);
        OctetString authPass = new OctetString(authPasswd);
        OctetString privPass = new OctetString(privPasswd);
        UsmUser user = new UsmUser(username, AuthMD5.ID, authPass, PrivDES.ID, privPass);

        usm.addUser(user.getSecurityName(),user);
        messageDispatcher.addMessageProcessingModel(new MPv3(usm));

        try {
            transportMapping = new DefaultUdpTransportMapping();
            snmp = new Snmp(messageDispatcher, transportMapping);
            //开启snmp监听
            snmp.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启snmp多线程以及snmp监听
     * 支持SNMP v1、v2c 版本
     */
    public void initSnmpV2(){
        //初始化多线程消息转发类
        MessageDispatcherImpl messageDispatcher = new MessageDispatcherImpl();
        messageDispatcher.addMessageProcessingModel(new MPv1());
        messageDispatcher.addMessageProcessingModel(new MPv2c());
        try {
            transportMapping = new DefaultUdpTransportMapping();
            snmp = new Snmp(messageDispatcher, transportMapping);
            //开启snmp监听
            snmp.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成目标对象
     * @return Target 目标对象
     * @param version snmp版本 (0=v1; 1=v2c; 2=v3)
     * @param community snmp设置的团体名
     * @param address 访问设备的ip地址 (例：udp:124.95.146.88/161)
     * */
    public Target createTarget(int version, String community, String address){
        this.version = version;

        Target target = null;

        //判断输入version是否正确
        if(!(version== SnmpConstants.version1 || version==SnmpConstants.version2c || version==SnmpConstants.version3)){
            System.out.println("version is err!!!");
            return null;
        }
        //如果version为SNMPv3
        if(version==SnmpConstants.version3){
            target = new UserTarget();
            target.setSecurityLevel(SecurityLevel.AUTH_PRIV);   //设置v3安全级别
            target.setSecurityName(new OctetString(username));  //设置v3用 户名
//            System.out.println("version is SNMPv3!!!");
        }
        //如果version为SNMPv1
        else{
            target = new CommunityTarget();
            ((CommunityTarget) target).setCommunity(new OctetString(community));  //设置团体名
            //如果version为SNMPv2c
            if(version==SnmpConstants.version2c){
                target.setSecurityModel(SecurityModel.SECURITY_MODEL_SNMPv2c);
            }
        }
        target.setAddress(GenericAddress.parse(address));  //设置发送报文地址
        target.setTimeout(timeout);  //设置一次请求的时间
        target.setRetries(retry);    //设置请求重试次数

        return target;
    }


    /**
     * 创建请求报文
     * @param oid 使用的oid
     * @param pduRequest 请求方式 GET/WALK
     * @param target snmp信息
     * @return String类型查询内容
     */
    public String createPDU(String oid, int pduRequest, Target target){
        if (version == SnmpConstants.version3) { pdu = new ScopedPDU(); }
        else { pdu = new PDUv1(); }
        pdu.setType(pduRequest);
        pdu.add(new VariableBinding(new OID(oid)));

        try {
            ResponseEvent send = snmp.send(pdu, target);
            Vector<? extends VariableBinding> variableBindings = (Vector<? extends VariableBinding>) send.getResponse().getVariableBindings();
            return variableBindings.get(0).getVariable().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建请求报文
     * @param oidList 使用的多个oid
     * @param pduRequest 请求方式 GET/WALK
     * @param target snmp信息
     * @return 结果集合
     */
    public List<String> createPDUs(List<String> oidList, int pduRequest, Target target){
        if (version == SnmpConstants.version3) { pdu = new ScopedPDU(); }
        else { pdu = new PDUv1(); }
        pdu.setType(pduRequest);
        for (String oid : oidList) { pdu.add(new VariableBinding(new OID(oid))); }

        List<String> resultList = new ArrayList<>();
        try {
            ResponseEvent send = snmp.send(pdu, target);
            Vector<? extends VariableBinding> variableBindings = (Vector<? extends VariableBinding>) send.getResponse().getVariableBindings();
            for (VariableBinding variableBinding : variableBindings) {
                String result = variableBinding.getVariable().toString();
                resultList.add(result);
            }
            return resultList;
        } catch (IOException e) {
            e.printStackTrace();
            return resultList;
        }
    }

    /**
     * Table方式创建请求报文
     * @param oid 查询的oid
     * @param pduRequest 请求方式 GET/WALK
     * @param target snmp信息
     * @return 结果集合
     */
    public List<String> createTable(String oid, int pduRequest, Target target){
        List<String> resultList = new ArrayList<>();

        // 转换参数oid数据类型
        OID[] oidArray = {new OID(oid)};

        TableUtils tableUtils = new TableUtils(snmp, new DefaultPDUFactory(pduRequest));

        List<TableEvent> eventList = tableUtils.getTable(target, oidArray, null, null);
        for (TableEvent tableEvent : eventList) {
            VariableBinding[] vb = tableEvent.getColumns();
            if (vb == null) continue;
            String variable = vb[0].getVariable().toString();
            resultList.add(variable);
        }
        return resultList;
    }

    /**
     * Table方式创建请求报文
     * @param oidList 查询的oid
     * @param pduRequest 请求方式 GET/WALK
     * @param target snmp信息
     * @return 结果集合
     */
    public Map<Integer, List<String>> createTables(List<String> oidList, int pduRequest, Target target){
        Map<Integer, List<String>> resultMap = new LinkedHashMap<>();


        // 转换接收的oid数据类型
        OID[] oidArray = new OID[oidList.size()];
        for (int i = 0; i < oidList.size(); i++) { oidArray[i] = new OID(oidList.get(i)); }

        TableUtils tableUtils = new TableUtils(snmp, new DefaultPDUFactory(pduRequest));
        List<TableEvent> eventList = tableUtils.getTable(target, oidArray, null, null);

        int d = 0;
        for (TableEvent tableEvent : eventList) {
            VariableBinding[] columns = tableEvent.getColumns();
            List<String> resultList = new ArrayList<>();

            for (VariableBinding column : columns) {
                String result = column.getVariable().toString();
                resultList.add(result);
            }

            resultMap.put(d++, resultList);
        }
        return resultMap;
    }

    /**
     * @param oid 传入一个oid，获取这个oid分支下的所有oid
     * @param pduRequest 请求方式 GET/WALK
     * @param target snmp信息
     * @return 获取到的oid
     */
    public List<String> getOidBranch(String oid, int pduRequest, Target target){
        List<String> list = new ArrayList<>();

        // 转换参数oid数据类型
        OID[] oidArray = {new OID(oid)};
        TableUtils tableUtils = new TableUtils(snmp, new DefaultPDUFactory(pduRequest));
        List<TableEvent> eventList = tableUtils.getTable(target, oidArray, null, null);
        for (TableEvent tableEvent : eventList) {
            VariableBinding[] vb = tableEvent.getColumns();
            if (vb == null) continue;
            String variable = vb[0].getOid().toString();
            list.add(variable);
        }
        return list;
    }

	
}
