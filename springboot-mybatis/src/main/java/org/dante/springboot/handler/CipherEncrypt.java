package org.dante.springboot.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CipherEncrypt {
	
	private String value;
	
	@Override
    public String toString() {
        return value;
    }
}
