package org.dante.springboot.springbootjwtserver.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthReqDTO implements Serializable {
	
    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;
    
}
