package org.dante.springboot.data.person.domain;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_Person")
@NamedQuery(name = "Person.withNameAndAddressNamedQuery", query = "select p from Person p where p.name = ?1 and p.address = ?2")
public class Person {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private Integer age;
	private String address;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String remark;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PersonId")
	private List<Hobby> hobbys;

	public Person() {
	}
	
	public Person(String name, int age, String address) {
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public Person(String name, int age, String address, String remark) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.remark = remark;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Hobby> getHobbys() {
		return hobbys;
	}

	public void setHobbys(List<Hobby> hobbys) {
		this.hobbys = hobbys;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
