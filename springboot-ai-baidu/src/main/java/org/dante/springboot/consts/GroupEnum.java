package org.dante.springboot.consts;

public enum GroupEnum {
	
	CN_GRP("CN_GRP_001", "普通用户组");
	
	private String id;
	private String grpName;
	
	private GroupEnum(String id, String grpName) {
		this.id = id;
		this.grpName = grpName;
	}
	
	public String id() {
		return this.id;
	}
	
	public String grpName() {
		return this.grpName;
	}
}
