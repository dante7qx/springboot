package org.dante.springboot;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.dante.springboot.dao.AttachmentDAO;
import org.dante.springboot.dao.DeployProcessDAO;
import org.dante.springboot.dao.LeaveDAO;
import org.dante.springboot.mybatis.mapper.TestMapper;
import org.dante.springboot.mybatis.vo.TestVO;
import org.dante.springboot.po.AttachmentPO;
import org.dante.springboot.service.IProcessUserService;
import org.dante.springboot.vo.GroupVO;
import org.dante.springboot.vo.NativeSQLVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpringbootHikariCPApplicationTests {
	
	@Autowired
	private DeployProcessDAO deployProcessDAO;
	@Autowired
	private AttachmentDAO attachmentDAO;
	@Autowired
	private LeaveDAO leaveDAO;
	@Autowired
	private TestMapper testMapper;
	@Autowired
	private IProcessUserService processUserService;
	
	@Test
	public void findGroupUser() {
		log.info("老师: {}", processUserService.getUserByGroupId("tech_group"));
		String userId = "stu_user1";
		GroupVO groupVO = processUserService.getGroupByUserId(userId);
		log.info("{} 所属组 {}", userId, groupVO);
	}
	
	@Test
	public void nativeSql() {
		List<NativeSQLVO> xx = deployProcessDAO.xx();
		log.info("==> {} - {}", xx.get(0).getName(), xx.get(0).getAge());
		
		List<TestVO> testVos = testMapper.queryDeployProcess();
		log.info("TestVOs => {}", testVos);
	}
	
	@Test
	public void saveAttachment() {
		AttachmentPO po = new AttachmentPO();
		po.setFileName("x.xml");
		po.setCreateTime(Date.from(Instant.now()));
		attachmentDAO.save(po);
		
		log.info("附件Id: {}", po.getAttachmentId());
		attachmentDAO.deleteById(po.getAttachmentId());
	}

}
