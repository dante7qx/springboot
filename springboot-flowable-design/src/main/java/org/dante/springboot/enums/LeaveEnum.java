package org.dante.springboot.enums;

public enum LeaveEnum {

	CG("CG", "草稿"), TJ("TJ", "提交"), TG("TG", "审批通过"), TH("BH", "审批驳回");

	private String code;
	private String value;

	private LeaveEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String code() {
		return code;
	}
	
	public String value() {
		return value;
	}

	
}
