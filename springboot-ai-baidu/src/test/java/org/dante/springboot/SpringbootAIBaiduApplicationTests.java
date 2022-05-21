package org.dante.springboot;

import java.util.List;

import org.dante.springboot.consts.GroupEnum;
import org.dante.springboot.util.BaiduFaceUtil;
import org.dante.springboot.vo.resp.RespVO;
import org.dante.springboot.vo.resp.UserFaceRespVO;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SpringbootAIBaiduApplicationTests {
	
	@Autowired
	private BaiduFaceUtil baiduFaceUtil;
	
	@Test
	public void getGroupUsers() {
		baiduFaceUtil.getGroupUsers(GroupEnum.CN_GRP.id());
	}
	
	@Test
	public void getUserFaces() throws JsonMappingException, JsonProcessingException, JSONException {
		RespVO<List<UserFaceRespVO>> resp = baiduFaceUtil.getUserFaces("628857f68412569e57d58977", GroupEnum.CN_GRP.id());
		log.info("Resp => {}", resp);
	}
	
	@Test
	public void deleteUserGroup() {
		baiduFaceUtil.deleteUserGroup(GroupEnum.CN_GRP.id());
	}
	
}
