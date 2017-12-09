package org.dante.springboot.ws.endpoint;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.dante.springboot.ws.GetTestRequest;
import org.dante.springboot.ws.GetTestResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.SoapHeader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Endpoint
public class TestsEndpoint {
	private static final String NAMESPACE_URI = "http://org.dante.springboot/ws";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTestRequest")
	@ResponsePayload
	public GetTestResponse getTest(@RequestPayload GetTestRequest request, SoapHeader header) {
		Iterator<QName> attributes = header.getAllAttributes();
		attributes.forEachRemaining(a -> {
			log.info(a.getLocalPart() + " -- " + a.getNamespaceURI());
		});
		log.info("SoapHeader ==> {}, GetTestRequest ==> {}", header.getName(), request.getName());
		GetTestResponse response = new GetTestResponse();
		response.setResult(dateToXmlDate(Date.from(Instant.now())));
		return response;
	}
	
	private static XMLGregorianCalendar dateToXmlDate(Date date){  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        DatatypeFactory dtf = null;  
         try {  
            dtf = DatatypeFactory.newInstance();  
        } catch (DatatypeConfigurationException e) {  
        }  
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();  
        dateType.setYear(cal.get(Calendar.YEAR));  
        //由于Calendar.MONTH取值范围为0~11,需要加1  
        dateType.setMonth(cal.get(Calendar.MONTH)+1);  
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));  
        dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));  
        dateType.setMinute(cal.get(Calendar.MINUTE));  
        dateType.setSecond(cal.get(Calendar.SECOND));  
        return dateType;  
    }
}
