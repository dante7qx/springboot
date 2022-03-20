package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.dto.LeaveDTO;
import org.dante.springboot.po.LeavePO;

public interface ILeaveService {
	
	public List<LeavePO> findListByUserId(String userId);
	
	public LeavePO findById(Long leaveId);
	
	public LeavePO saveLeave(LeaveDTO dto) throws Exception;
	
	public LeavePO updateLeave(LeavePO po);
}
