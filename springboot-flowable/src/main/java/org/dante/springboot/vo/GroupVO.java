package org.dante.springboot.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String groupId;
	private String groupName;
}
