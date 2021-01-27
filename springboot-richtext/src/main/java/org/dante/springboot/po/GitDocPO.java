package org.dante.springboot.po;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "git_doc")
public class GitDocPO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String title;

}
