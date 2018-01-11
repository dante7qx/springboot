package org.dante.springboot.po;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class UserPO {

	private Long id;
	private String name;
	private int age;
	private List<ContactPO> contacts;
	
	public List<ContactPO> getContacts() {
		if(this.contacts == null) {
			this.contacts = Lists.newArrayList();
		}
		return this.contacts;
	}
}
