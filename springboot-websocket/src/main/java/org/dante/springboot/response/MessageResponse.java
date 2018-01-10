package org.dante.springboot.response;

import lombok.Data;

@Data
public class MessageResponse {

	private String respMsg;
	
	public MessageResponse(String respMsg) {
		this.respMsg = respMsg;
	}
	
}
