package org.dante.springboot;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.dante.springboot.converter.MsgConvertMapper;
import org.dante.springboot.vo.MsgDTO;
import org.dante.springboot.vo.MsgPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringbootApi4TestNgApplicationTests {
	
	@Autowired
	private MsgConvertMapper msgConvertMapper;
	
	@Test
	public void testMsgConvert() {
		MsgPO po = new MsgPO();
		po.setMsgId(UUID.randomUUID().toString().replace("-", ""));
		po.setMsg("测试消息");
		po.setSendTime(Date.from(Instant.now()));
		
		MsgDTO dto = msgConvertMapper.toDTO(po);
		log.info("UUID: {}", UUID.randomUUID().toString());
		log.info("MSGDTO: {}", dto);
		
		MsgPO poConvert = msgConvertMapper.toPO(dto);
		log.info("MsgPO: {}", poConvert);
	}
	
}
