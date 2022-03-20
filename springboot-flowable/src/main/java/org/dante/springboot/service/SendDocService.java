package org.dante.springboot.service;

import org.dante.springboot.dao.LeaveDAO;
import org.dante.springboot.po.LeavePO;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(value = "sendDocService")
public class SendDocService implements JavaDelegate {
	
	@Autowired
	private LeaveDAO leaveDAO;

	@Override
	public void execute(DelegateExecution execution) {
		try {
			log.info("发文流程=====> {} - {}", execution.getProcessInstanceId(), execution.getCurrentActivityId());
			LeavePO leave = leaveDAO.getById(12L);
			log.info("请假: {}", leave);
			throw new BpmnError("发生错误");
		} catch (Exception e) {
		   throw new BpmnError("发生错误");
	    }
		
	}
	
}
