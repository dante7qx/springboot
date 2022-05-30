package org.dante.springboot.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArgVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String activityId;
	private String approval;
}
