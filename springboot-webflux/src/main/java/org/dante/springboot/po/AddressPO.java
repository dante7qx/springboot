package org.dante.springboot.po;

import java.time.Instant;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection="m_address")
public class AddressPO {
	@Id
	private String id;
	@Indexed(unique = true)
	private String code;
	private String name;
	private Date createOn = Date.from(Instant.now());
}
