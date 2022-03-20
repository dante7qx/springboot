package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class FlowDefVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String processDefId;
	
	private String processDefName;
	
	private int processDefVersion;
	
	private String processDeployId;
	
	private Date processDeployTime;
	
	private String xml;
	
	private String diagram;
	
	private boolean isSuspended;

}
