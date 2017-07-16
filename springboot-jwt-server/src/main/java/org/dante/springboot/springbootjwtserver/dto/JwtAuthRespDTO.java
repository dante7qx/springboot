package org.dante.springboot.springbootjwtserver.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class JwtAuthRespDTO implements Serializable {
	private static final long serialVersionUID = 8785209667742191918L;
	
	@Getter
    private final String token;
    
}
