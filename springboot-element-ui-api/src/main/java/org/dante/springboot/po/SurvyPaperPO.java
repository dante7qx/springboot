package org.dante.springboot.po;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "spi_survy_paper")
@TypeDef( name = "json", typeClass = JsonType.class )
@NoArgsConstructor
public class SurvyPaperPO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type;
	
	@Type(type = "json")
	@Column(columnDefinition = "json")
	private Map<String, Object> paperInfo = new HashMap<>();
	
	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;

}
