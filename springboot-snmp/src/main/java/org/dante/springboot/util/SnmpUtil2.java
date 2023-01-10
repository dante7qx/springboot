package org.dante.springboot.util;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpUtil2 {
	private Snmp snmp = null;

    private Address targetAddress = null;

    private TransportMapping transport = null;
    
    public void init() throws IOException {
        //目标主机的ip地址 和 端口号
        targetAddress = GenericAddress.parse("udp:192.168.1.1/162");
        transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);
        transport.listen();
    }
    
    public ResponseEvent sendV2cTrap() throws IOException {

        PDU pdu = new PDU();
        VariableBinding v = new VariableBinding();
        v.setOid(SnmpConstants.sysName);
        v.setVariable(new OctetString("SnmpV2 Trap"));
        pdu.add(v);
        pdu.setType(PDU.TRAP);

        // set target
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("risun8768!#"));
        target.setAddress(targetAddress);

        // retry times when commuication error
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        // send pdu, return response
        return snmp.send(pdu, target);

    }
    
    public static void main(String[] args) {

        SnmpUtil2 poc = new SnmpUtil2();

        try {
            poc.init();
            ResponseEvent resp = poc.sendV2cTrap();
            System.out.println(resp);
            /*
            PDU response = resp.getResponse();
            for (int i = 0; i < response.size(); i++) {
            	VariableBinding vb = response.get(i);
            	System.out.println(vb.getOid().toString());
            	System.out.println(vb.getVariable().toString());
            }
            */
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
