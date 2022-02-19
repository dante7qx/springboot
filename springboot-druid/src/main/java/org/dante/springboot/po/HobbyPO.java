package org.dante.springboot.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "T_Hobby")
public class HobbyPO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String hobby;
	private String updateBy;
	private Boolean isDelete;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PersonId")
	private PersonPO person;
	private Date updateDate;
}
