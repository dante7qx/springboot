package org.dante.springboot.vo;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class QueryVO {
	
	private Map<String, Object> q;
	
	public Map<String, Object> getQ() {
		if(q == null) {
			q = Maps.newHashMap();
		}
		return q;
	}
}
