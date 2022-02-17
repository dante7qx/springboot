package org.dante.springboot.controller;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import org.dante.springboot.converter.MsgConvertMapper;
import org.dante.springboot.vo.MsgDTO;
import org.dante.springboot.vo.MsgPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MapStructController {
	
	@Autowired
	private MsgConvertMapper msgConvertMapper;
	
	@GetMapping("/mapstruct/{msg}")
	public MsgDTO getMsg(@PathVariable String msg) {
		MsgPO po = new MsgPO();
		po.setMsgId(UUID.randomUUID().toString().replace("-", ""));
		po.setMsg(msg);
		po.setSendTime(Date.from(Instant.now()));
		
		MsgDTO dto = msgConvertMapper.toDTO(po);
		log.info("UUID: {}", UUID.randomUUID().toString());
		log.info("MSGDTO: {}", dto);
		
		MsgPO poConvert = msgConvertMapper.toPO(dto);
		log.info("MsgPO: {}", poConvert);
		return dto;
	}
	
}
