package org.dante.springboot.data.person.domain;

public class TestNativeSql{

	private Integer id;
	private String name;

	public TestNativeSql() {
	}
	
	public TestNativeSql(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TestNativeSql [id=" + id + ", name=" + name + "]";
	}

}
