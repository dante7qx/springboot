package org.dante.springboot.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "t_attachment")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class AttachmentPO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long attachmentId;
	private String fileName;
	private Date createTime;

}
