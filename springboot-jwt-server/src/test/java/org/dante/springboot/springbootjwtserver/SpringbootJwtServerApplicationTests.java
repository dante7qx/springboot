package org.dante.springboot.springbootjwtserver;

import org.dante.springboot.springbootjwtserver.dao.UserDAO;
import org.dante.springboot.springbootjwtserver.po.UserPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootJwtServerApplicationTests {

	@Autowired
	private UserDAO userDAO;
	
	@Test
	@Transactional
	public void testFindUserByName() {
		try {
			UserPO user = userDAO.findByUserName("dante");
			System.out.println(user.getRoles());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
