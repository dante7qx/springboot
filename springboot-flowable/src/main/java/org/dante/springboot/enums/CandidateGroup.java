package org.dante.springboot.enums;

public enum CandidateGroup {
	
	STU("stu", "stu_group", "学生"), 
	TECH("tech", "tech_group", "老师"), 
	MTECH("mtech", "tech_group", "校长");
	
	private String code;
	private String value;
	private String desc;
	
	private CandidateGroup(String code, String value, String desc) {
		this.code = code;
		this.value = value;
		this.desc = desc;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getDesc() {
		return desc;
	}
	
}
