package org.dante.springboot.data.vo;

import java.io.Serializable;

/**
 * 实现序列化接口，因为使用Jackson做序列化需要一个空构造
 * 
 * @author dante
 *
 */
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private Integer age;

	public Person() {
		super();
	}

	public Person(String id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
