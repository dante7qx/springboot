package org.dante.springboot.dao;

import java.util.List;

import org.dante.springboot.SpringbootPostgreSQLApplicationTests;
import org.dante.springboot.po.Userinfo;
import org.dante.springboot.vo.UserBorrowRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInfoMapperTests extends SpringbootPostgreSQLApplicationTests {
	
	@Autowired
	private UserinfoMapper userinfoMapper;
	
	@Test
	public void selectAll() {
		List<Userinfo> userInfos = userinfoMapper.selectList(null);
		log.info("{}", userInfos);
	}
	
	@Test
	public void selectPage() {
		Page<Userinfo> page = new Page<>(1, 2);
		Page<Userinfo> result = userinfoMapper.selectPage(page, null);
		log.info("==> {} -> {}", result.getRecords(), result.getPages());
	}
	
	@Test
	public void selectLastUserBorrowRecord() {
		List<UserBorrowRecord> records = userinfoMapper.selectLastUserBorrowRecord();
		log.info("==> {}", records);
		
	}
}
