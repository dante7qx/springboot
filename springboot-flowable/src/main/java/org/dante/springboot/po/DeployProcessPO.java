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
@Table(name = "t_deploy_process")
public class DeployProcessPO {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long deployProcessId;
	private String deploymentId;
	private String processDefinitionId;
	private String processName;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attachment_id")
	private AttachmentPO attachment;
	private Date createTime;

}
