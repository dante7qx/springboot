package org.dante.springboot.context;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HelloContext {

	private String msgId;
	private Date msgDate;
	private String reqArg;
	private String action;

}
