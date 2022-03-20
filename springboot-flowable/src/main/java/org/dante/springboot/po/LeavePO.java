package org.dante.springboot.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_leave")
public class LeavePO {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long leaveId;
	private String userId;
	private String procInstId;
	private Date startTime;
	private Date endTime;
	private String leaveReason;
	private Date createTime;
}
