package org.dante.springboot.mongo.po;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection="user")
public class UserPO {

	@Id
    private Long id;
	@Field("name")
    private String username;
    private Integer age;
    private String gender;
    @Field("updatedate")
    private String strDate;
    private Date updateDate;
    
	public UserPO(Long id, String username, Integer age, String gender, String strDate) {
		super();
		this.id = id;
		this.username = username;
		this.age = age;
		this.gender = gender;
		this.strDate = strDate;
	}
    
    
}
