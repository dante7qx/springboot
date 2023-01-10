package org.dante.springboot.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SNMPUtil {
	public static final int DEFAULT_VERSION = SnmpConstants.version2c;
    public static final String DEFAULT_PROTOCOL = "udp";
    public static final int DEFAULT_PORT = 161;
    public static final long DEFAULT_TIMEOUT = 3 * 1000L;
    public static final int DEFAULT_RETRY = 3;

    /**
     * 创建对象communityTarget
     *
     * @param targetAddress
     * @param community
     * @param version
     * @param timeOut
     * @param retry
     * @return CommunityTarget
     */
    public static CommunityTarget createDefault(String ip, String community) {
        Address address = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip + "/" + DEFAULT_PORT);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(address);
        target.setVersion(DEFAULT_VERSION);
        target.setTimeout(DEFAULT_TIMEOUT); // milliseconds
        target.setRetries(DEFAULT_RETRY);
        return target;
    }
    
    /*获取信息*/
    public static JSONArray snmpGet(String ip, String community, String oid) {
        CommunityTarget target = createDefault(ip, community);
        JSONArray jsonArray = new JSONArray();
        Snmp snmp = null;
        try {
            PDU pdu = new PDU();
            // pdu.add(new VariableBinding(new OID(new int[]
            // {1,3,6,1,2,1,1,2})));
            pdu.add(new VariableBinding(new OID(oid)));
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            log.info("-------> 发送PDU <-------");
            pdu.setType(PDU.GET);
            ResponseEvent respEvent = snmp.send(pdu, target);
            log.info("PeerAddress:" + respEvent.getPeerAddress());
            PDU response = respEvent.getResponse();
            if (response == null) {
                log.info("response is null, request time out");
            } else {
                log.info("response pdu size is " + response.size());
                for (int i = 0; i < response.size(); i++) {
                    VariableBinding vb = response.get(i);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid", vb.getOid().toString());
                    jsonObject.put("value", vb.getVariable().toString());
                    jsonArray.add(jsonObject);
//                    log.info(vb.getOid() + " = " + vb.getVariable());
                }
            }
            log.info("SNMP GET one OID value finished !");
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("SNMP Get Exception:" + e);
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return jsonArray;
    }
    
    /*获取列表信息，一次获取多条信息*/
    public static JSONArray snmpGetList(String ip, String community, List<String> oidList) {
        CommunityTarget target = createDefault(ip, community);
        JSONArray jsonArray = new JSONArray();
        Snmp snmp = null;
        try {
            PDU pdu = new PDU();
            for (String oid : oidList) {
                pdu.add(new VariableBinding(new OID(oid)));
            }
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            log.info("-------> 发送PDU <-------");
            pdu.setType(PDU.GET);
            ResponseEvent respEvent = snmp.send(pdu, target);
            log.info("PeerAddress:" + respEvent.getPeerAddress());
            PDU response = respEvent.getResponse();
            if (response == null) {
                log.info("response is null, request time out");
            } else {
                log.info("response pdu size is " + response.size());
                for (int i = 0; i < response.size(); i++) {
                    VariableBinding vb = response.get(i);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid", vb.getOid().toString());
                    jsonObject.put("value", vb.getVariable().toString());
                    jsonArray.add(jsonObject);
//                    log.info(vb.getOid() + " = " + vb.getVariable());
                }
            }
            log.info("SNMP GET one OID value finished !");
            return jsonArray;
        } catch (Exception e) {
            log.info("SNMP Get Exception:" + e);
            e.printStackTrace();
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return jsonArray;
    }
    
    /*异步获取信息列表*/
    public static JSONArray snmpAsynGetList(String ip, String community, List<String> oidList) {
        CommunityTarget target = createDefault(ip, community);
        JSONArray jsonArray = new JSONArray();
        Snmp snmp = null;
        try {
            PDU pdu = new PDU();
            for (String oid : oidList) {
                pdu.add(new VariableBinding(new OID(oid)));
            }
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            log.info("-------> 发送PDU <-------");
            pdu.setType(PDU.GET);
            ResponseEvent respEvent = snmp.send(pdu, target);
            log.info("PeerAddress:" + respEvent.getPeerAddress());
            PDU response = respEvent.getResponse();
            /*异步获取*/
            final CountDownLatch latch = new CountDownLatch(1);
            ResponseListener listener = new ResponseListener() {
                public void onResponse(ResponseEvent event) {
                    ((Snmp) event.getSource()).cancel(event.getRequest(), this);
                    PDU response = event.getResponse();
                    PDU request = event.getRequest();
                    log.info("[request]:" + request);
                    if (response == null) {
                        log.info("[ERROR]: response is null");
                    } else if (response.getErrorStatus() != 0) {
                        log.info("[ERROR]: response status"
                                + response.getErrorStatus() + " Text:"
                                + response.getErrorStatusText());
                    } else {
                        log.info("Received response Success!");
                        for (int i = 0; i < response.size(); i++) {
                            VariableBinding vb = response.get(i);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("oid", vb.getOid().toString());
                            jsonObject.put("value", vb.getVariable().toString());
                            jsonArray.add(jsonObject);
//                            log.info(vb.getOid() + " = " + vb.getVariable());
                        }
                        log.info("SNMP Asyn GetList OID finished. ");
                        latch.countDown();
                    }
                }
            };
            pdu.setType(PDU.GET);
            snmp.send(pdu, target, null, listener);
            log.info("asyn send pdu wait for response...");
            boolean wait = latch.await(30, TimeUnit.SECONDS);
            log.info("latch.await =:" + wait);
            snmp.close();
            log.info("SNMP GET one OID value finished !");
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("SNMP Get Exception:" + e);
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return jsonArray;
    }

    /*获取表格*/
    public static JSONArray snmpWalk(String ip, String community, String targetOid) {
        CommunityTarget target = createDefault(ip, community);
        TransportMapping transport = null;
        JSONArray jsonArray = new JSONArray();
        Snmp snmp = null;
        try {
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();
            PDU pdu = new PDU();
            OID targetOID = new OID(targetOid);
            pdu.add(new VariableBinding(targetOID));
            boolean finished = false;
            log.info("----> snmp start <----");
            while (!finished) {
                VariableBinding vb = null;
                ResponseEvent respEvent = snmp.getNext(pdu, target);
                PDU response = respEvent.getResponse();
                if (null == response) {
                    log.info("responsePDU == null");
                    finished = true;
                    break;
                } else {
                    vb = response.get(0);
                }
                // check finish
                finished = checkWalkFinished(targetOID, pdu, vb);
                if (!finished) {
//                    log.info("==== walk each vlaue :");
//                    log.info(vb.getOid() + " = " + vb.getVariable());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("oid", vb.getOid().toString());
                    jsonObject.put("value", vb.getVariable().toString());
                    jsonArray.add(jsonObject);
                    // Set up the variable binding for the next entry.
                    pdu.setRequestID(new Integer32(0));
                    pdu.set(0, vb);
                } else {
                    log.info("SNMP walk OID has finished.");
                    snmp.close();
                }
            }
            log.info("----> demo end <----");
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("SNMP walk Exception: " + e);
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return jsonArray;
    }

    private static boolean checkWalkFinished(OID targetOID, PDU pdu, VariableBinding vb) {
        boolean finished = false;
        if (pdu.getErrorStatus() != 0) {
            log.info("[true] responsePDU.getErrorStatus() != 0 ");
            log.info(pdu.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
            log.info("[true] vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < targetOID.size()) {
            log.info("[true] vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
            log.info("[true] targetOID.leftMostCompare() != 0");
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            System.out
                    .println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(targetOID) <= 0) {
            log.info("[true] Variable received is not "
                    + "lexicographic successor of requested " + "one:");
            log.info(vb.toString() + " <= " + targetOID);
            finished = true;
        }
        return finished;
    }

    /*异步获取表格*/
    public static JSONArray snmpAsynWalk(String ip, String community, String oid) {
        final CommunityTarget target = createDefault(ip, community);
        JSONArray jsonArray = new JSONArray();
        Snmp snmp = null;
        try {
            log.info("----> snmp start <----");
            DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            snmp.listen();
            final PDU pdu = new PDU();
            final OID targetOID = new OID(oid);
            final CountDownLatch latch = new CountDownLatch(1);
            pdu.add(new VariableBinding(targetOID));
            ResponseListener listener = new ResponseListener() {
                public void onResponse(ResponseEvent event) {
                    ((Snmp) event.getSource()).cancel(event.getRequest(), this);
                    try {
                        PDU response = event.getResponse();
                        // PDU request = event.getRequest();
                        // log.info("[request]:" + request);
                        if (response == null) {
                            log.info("[ERROR]: response is null");
                        } else if (response.getErrorStatus() != 0) {
                            log.info("[ERROR]: response status" + response.getErrorStatus() + " Text:" + response.getErrorStatusText());
                        } else {
//                            log.info("Received Walk response value :");
                            VariableBinding vb = response.get(0);
                            boolean finished = checkWalkFinished(targetOID, pdu, vb);
                            if (!finished) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("oid", vb.getOid().toString());
                                jsonObject.put("value", vb.getVariable().toString());
                                jsonArray.add(jsonObject);
//                                log.info(vb.getOid() + " = " + vb.getVariable());
                                pdu.setRequestID(new Integer32(0));
                                pdu.set(0, vb);
                                ((Snmp) event.getSource()).getNext(pdu, target, null, this);
                            } else {
                                log.info("SNMP Asyn walk OID value success !");
                                latch.countDown();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        latch.countDown();
                    }
                }
            };
            snmp.getNext(pdu, target, null, listener);
            log.info("pdu 已发送,等待异步处理结果...");
            boolean wait = latch.await(3, TimeUnit.SECONDS);
            log.info("latch.await =:" + wait);
            snmp.close();
            log.info("----> snmp end <----");
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("SNMP Asyn Walk Exception:" + e);
            return jsonArray;
        }
    }

    /*设置信息*/
    public static void setPDU(String ip, String community, String oid) throws IOException {
        CommunityTarget target = createDefault(ip, community);
        Snmp snmp = null;
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid), new OctetString("shangrao")));
        pdu.setType(PDU.SET);
        DefaultUdpTransportMapping transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        snmp.listen();
        log.info("-------> 发送PDU <-------");
        snmp.send(pdu, target);
        snmp.close();
    }
    
    public static void main(String[] args) {
    	String ip = "192.168.1.1";
      String community = "risun8768!#";
      
//      String oid = "1.3.6.1.4.1.4881.1.1.10.2.58.1.3.1.1.2";
//      String oid = "1.3.6.1.4.1.4881.1.1.10.2.58.1.3.1";
//      String oid = "1.3.6.1.4.1.4881.1.1.10.2.2.1.3";
//      String oid = "1.3.6.1.4.1.4881.1.1.10.2.2.1.1.1.2"; // IP对应Mac
      
      String oid = "1.3.6.1.4.1.4881.1.1.10.2.2.1.1.1.2";

      JSONArray snmpGet = SNMPUtil.snmpGet(ip, community, oid);
      System.out.println(snmpGet);

//      List<String> oidList=new ArrayList<>();
//      oidList.add("1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.3.1");
//      oidList.add("1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.4.1");
//      oidList.add("1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.5.1");
//      
//      oidList.add("1.3.6.1.4.1.4881.1.1.10.2.36.1.1.1.0");
//      oidList.add("1.3.6.1.4.1.4881.1.1.10.2.36.1.1.2.0");
//      oidList.add("1.3.6.1.4.1.4881.1.1.10.2.36.1.1.3.0");
      
      
//      
//      JSONArray jsonArray = SNMPUtil.snmpAsynGetList(ip, community, oidList);
//      System.out.println(jsonArray);
      
//      JSONArray jsonArray = SNMPUtil.snmpAsynWalk(ip, community, oid);
//      System.out.println(jsonArray);
      
      JSONArray snmpWalk = SNMPUtil.snmpWalk(ip, community, oid);
      System.out.println(snmpWalk);
//      ThreadUtil.sleep(5*60*1000);
	}
    
}
