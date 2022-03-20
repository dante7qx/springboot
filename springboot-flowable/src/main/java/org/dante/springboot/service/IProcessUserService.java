package org.dante.springboot.service;

import java.util.List;

import org.dante.springboot.vo.GroupVO;
import org.dante.springboot.vo.UserVO;

public interface IProcessUserService {
	
	public List<GroupVO> getGroups();
	
	public List<UserVO> getUserByGroupId(String groupId);
	
	public GroupVO getGroupByUserId(String userId);
	
}
