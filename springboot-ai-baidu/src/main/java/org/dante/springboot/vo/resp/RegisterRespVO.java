package org.dante.springboot.vo.resp;

import java.io.Serializable;

import org.dante.springboot.vo.RespLocationVO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RegisterRespVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("face_token")
	private String faceToken;
	
	private RespLocationVO location;
	
}
