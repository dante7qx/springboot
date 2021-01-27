package org.dante.springboot.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "git_doc_chapter")
public class GitDocChapterPO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@Column(name = "git_doc_id")
	private String gitDocId;
	private String title;
	private int level;
	private int seq;
	private String pid;
	@Column(name = "source_doc_addr")
	private String sourceDocAddr;
	@Column(name = "doc_page_addr")
	private String docPageAddr;
	
}
