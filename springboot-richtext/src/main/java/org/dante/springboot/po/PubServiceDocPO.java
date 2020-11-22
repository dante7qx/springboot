package org.dante.springboot.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pub_service_doc")
public class PubServiceDocPO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@Column(name = "service_title")
	private String serviceTitle;
	@Column(name = "doc_content")
	private String docContent;
}
