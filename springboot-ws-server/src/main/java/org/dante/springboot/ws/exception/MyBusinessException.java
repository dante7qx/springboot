package org.dante.springboot.ws.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.SERVER)
public class MyBusinessException extends Exception {

	private static final long serialVersionUID = -8146706947232179939L;

	public MyBusinessException(String message) {
        super(message);
    }
}
