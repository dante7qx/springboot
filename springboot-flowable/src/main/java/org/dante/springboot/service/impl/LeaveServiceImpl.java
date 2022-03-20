package org.dante.springboot.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dante.springboot.consts.IConstant;
import org.dante.springboot.dao.LeaveDAO;
import org.dante.springboot.dto.LeaveDTO;
import org.dante.springboot.po.LeavePO;
import org.dante.springboot.service.IFlowService;
import org.dante.springboot.service.ILeaveService;
import org.dante.springboot.util.UserUtil;
import org.dante.springboot.vo.StartProcessInstanceVO;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LeaveServiceImpl implements ILeaveService {
	
	@Autowired
	public LeaveDAO leaveDAO;
	@Autowired
	private IFlowService flowService;

	@Override
	public List<LeavePO> findListByUserId(String userId) {
		return leaveDAO.findByUserId(userId);
	}

	@Override
	public LeavePO findById(Long leaveId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public LeavePO saveLeave(LeaveDTO dto) throws Exception {
		LeavePO po = new LeavePO();
		po.setStartTime(dto.getStartTime());
		po.setEndTime(dto.getEndTime());
		po.setLeaveReason(dto.getLeaveReason());
		po.setUserId(UserUtil.currentUser());
		po.setCreateTime(Date.from(Instant.now()));
		
		leaveDAO.save(po);
		
		dto.setLeaveId(po.getLeaveId());
		
		// 构造启动流程实例
		StartProcessInstanceVO startProcessInstanceVO = buildStartProcessInstance(dto);
		ProcessInstance processInstance = flowService.startProcessInstanceByKey(startProcessInstanceVO);
		po.setProcInstId(processInstance.getId());  // 设置流程实例ID
		leaveDAO.save(po);
		return po;
	}

	@Override
	public LeavePO updateLeave(LeavePO po) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private StartProcessInstanceVO buildStartProcessInstance(LeaveDTO dto) {
		StartProcessInstanceVO vo = new StartProcessInstanceVO();
		vo.setBusinessKey(dto.getLeaveId() + "");
		vo.setProcessDefinitionKey(IConstant.LEAVE_PROCESS_DEFINITION_KEY);
		vo.setApplicant(UserUtil.currentUser());
		vo.setSubmitter(UserUtil.currentUser());
		vo.setOperType(dto.getOperType());
		vo.setSystemSn("leave-flow");
		vo.setFormName("【"+UserUtil.currentUser()+"】请假");
		
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("days", ChronoUnit.DAYS.between(toLocalDate(dto.getStartTime()), toLocalDate(dto.getEndTime())) );
		vo.setVariables(variables);
		return vo;
	}
	
	private LocalDate toLocalDate(Date date) {
		Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDate();
	}

}
