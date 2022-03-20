package org.dante.springboot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.dante.springboot.service.IProcessUserService;
import org.dante.springboot.vo.GroupVO;
import org.dante.springboot.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public class ProcessUserServiceImpl implements IProcessUserService {
	
	private static List<GroupVO> groups = new ArrayList<>();
	private static Map<String, List<UserVO>> groupUserMap = new HashMap<>();
	
	@PostConstruct
	public void init() {
		groups.add(new GroupVO("office_emp_group", "办公室员工"));
		groups.add(new GroupVO("office_leader_group", "办公室领导"));
		groups.add(new GroupVO("personnel_emp_group", "人事处员工"));
		groups.add(new GroupVO("personnel_leader_group", "人事处领导"));
		groups.add(new GroupVO("confidential_group", "机要"));
		groups.add(new GroupVO("leader_group", "局长"));
		
		groups.add(new GroupVO("stu_group", "学生"));
		groups.add(new GroupVO("tech_group", "老师"));
		groups.add(new GroupVO("mtech_group", "校长"));
		
		
		List<UserVO> stus = new ArrayList<>();
		stus.add(new UserVO("stu_user1", "学生1"));
		stus.add(new UserVO("stu_user2", "学生2"));
		groupUserMap.put("stu_group", stus);
		
		List<UserVO> techs = new ArrayList<>();
		techs.add(new UserVO("tech_user1", "老师1"));
		techs.add(new UserVO("tech_user2", "老师2"));
		groupUserMap.put("tech_group", techs);
		
		List<UserVO> mtechs = new ArrayList<>();
		techs.add(new UserVO("mtech_user1", "王校长"));
		groupUserMap.put("mtech_group", mtechs);
		
		List<UserVO> oemps = new ArrayList<>();
		oemps.add(new UserVO("office_emp1", "王办员"));
		groupUserMap.put("office_emp_group", oemps);
		
		List<UserVO> olemps = new ArrayList<>();
		olemps.add(new UserVO("office_leader1", "张办导"));
		groupUserMap.put("office_leader_group", olemps);
		
		List<UserVO> pemps = new ArrayList<>();
		pemps.add(new UserVO("per_emp1", "钱人员"));
		groupUserMap.put("personnel_emp_group", pemps);
		
		List<UserVO> plemps = new ArrayList<>();
		plemps.add(new UserVO("per_leader1", "郭人导"));
		groupUserMap.put("personnel_leader_group", plemps);
		
		List<UserVO> confidentialEmps = new ArrayList<>();
		confidentialEmps.add(new UserVO("confidential_user1", "李机要"));
		groupUserMap.put("confidential_group", confidentialEmps);
		
		List<UserVO> leaders = new ArrayList<>();
		leaders.add(new UserVO("leader_user1", "韩领导"));
		groupUserMap.put("leader_group", leaders);
	}

	@Override
	public List<GroupVO> getGroups() {
		return groups;
	}

	@Override
	public List<UserVO> getUserByGroupId(String groupId) {
		return groupUserMap.get(groupId);
	}

	@Override
	public GroupVO getGroupByUserId(String userId) {
		String groupId = groupUserMap.entrySet().stream().filter(m -> m.getValue().contains(new UserVO(userId)))
				.map(m -> m.getKey()).collect(Collectors.joining());

		return groups.stream().filter(g -> g.getGroupId().equals(groupId)).findFirst().get();
	}
	

}
