package org.dante.springboot.po;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.Type;
//import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "spi_survy_paper")
//@TypeDef( name = "json", typeClass = JsonType.class )
@NoArgsConstructor
public class SurvyPaperPO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type;
	
	@Type(JsonType.class)
	@Column(columnDefinition = "json")
	private Map<String, Object> paperInfo = new HashMap<>();
	
	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;

}
