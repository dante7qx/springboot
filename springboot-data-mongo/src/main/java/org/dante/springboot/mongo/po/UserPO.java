package org.dante.springboot.mongo.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection="user")
public class UserPO {

	@Id
    private Long id;
	@Field("name")
    private String username;
    private Integer age;
}
