package org.dante.springboot.po;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PersonId")
	private PersonPO person;
	private Date updateDate;
}
