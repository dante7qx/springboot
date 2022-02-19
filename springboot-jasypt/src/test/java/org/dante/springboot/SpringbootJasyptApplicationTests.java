package org.dante.springboot;

import java.util.List;

import org.dante.springboot.dao.PersonDAO;
import org.dante.springboot.po.PersonPO;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootJasyptApplicationTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootJasyptApplicationTests.class);
	
	@Autowired
	private PersonDAO personDAO; 
	@Autowired
	@Qualifier("spiritEncryptorBean")
    private StringEncryptor stringEncryptor;
	
	@Test
	public void findAll() {
		List<PersonPO> persons = personDAO.findAll();
		LOGGER.info(persons.toString());
	}

	@Test
    public void encryptPwd() {
        String encrypt = stringEncryptor.encrypt("iamdante");
        LOGGER.info("====> {}", encrypt); 
        String decrypt = stringEncryptor.decrypt(encrypt);
        LOGGER.info(decrypt);
    }
}
