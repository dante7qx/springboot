package org.dante.springboot.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.dante.springboot.consts.GroupEnum;
import org.dante.springboot.prop.BaiduProp;
import org.dante.springboot.vo.CheckFaceVO;
import org.dante.springboot.vo.RegisterUserVO;
import org.dante.springboot.vo.resp.RegisterRespVO;
import org.dante.springboot.vo.resp.RespVO;
import org.dante.springboot.vo.resp.UserFaceRespVO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baidu.aip.face.AipFace;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BaiduFaceUtil {
	
	@Autowired
	private BaiduProp baiduProp;
	
	private static volatile AipFace client;
	private static volatile ObjectMapper mapper = new ObjectMapper();
	
	@PostConstruct
	public void init() {
		client = new AipFace(baiduProp.getAppId(), baiduProp.getApiKey(), baiduProp.getSecretKey());
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
	}
	
	/**
	 * 人脸注册
	 * 
	 * @return
	 * @throws JSONException 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	public RespVO<RegisterRespVO> register(RegisterUserVO userVO) throws JsonMappingException, JsonProcessingException, JSONException {
		userVO.setUserId(IdUtil.objectId());
		HashMap<String, String> options = Maps.newHashMap();
	    options.put("user_info", userVO.getUserName());
	    options.put("quality_control", userVO.getQualityControl());
	    options.put("liveness_control", userVO.getLivenessControl());
	    options.put("action_type", userVO.getActionType());
	    String imgBase64 = userVO.getImgBase64().split(";base64,")[1];
	    JSONObject result = client.addUser(imgBase64, userVO.getImageType(), GroupEnum.CN_GRP.id(), userVO.getUserId(), options);
	    log.info("==> {}", result.toString());
	    BigInteger logId = result.getBigInteger("log_id");
	    String errorMsg = result.getString("error_msg");
	    RespVO<RegisterRespVO> resp = new RespVO<>();
	    if("SUCCESS".equals(errorMsg)) {
	    	resp.setData(mapper.readValue(result.getJSONObject("result").toString(), RegisterRespVO.class));
	    } else {
	    	resp.setErrorMsg(errorMsg);
	    }
	    resp.setLogId(logId);
	    
	    return resp;
	}
	
	/**
	 * 获取用户列表
	 * 
	 * @param groupId
	 */
	public RespVO<List<String>> getGroupUsers(String groupId) {
		// 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("start", "0");
	    options.put("length", "50");
	    
	    // 获取用户列表
	    JSONObject result = client.getGroupUsers(groupId, options);
	    log.info("==> {}", result.toString());
	    BigInteger logId = result.getBigInteger("log_id");
	    String errorMsg = result.getString("error_msg");
	    RespVO<List<String>> resp = new RespVO<>();
	    if("SUCCESS".equals(errorMsg)) {
	    	resp.setData(result.getJSONObject("result").getJSONArray("user_id_list").toList().stream().map(String::valueOf).collect(Collectors.toList()));
	    } else {
	    	resp.setErrorMsg(errorMsg);
	    }
	    resp.setLogId(logId);
	    return resp;
	}
	
	/**
	 * 获取用户人脸列表
	 * 
	 * @param userId
	 * @param groupId
	 * @throws JSONException 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	public RespVO<List<UserFaceRespVO>> getUserFaces(String userId, String groupId) throws JsonMappingException, JsonProcessingException, JSONException {
		JSONObject result = client.faceGetlist(userId, groupId, new HashMap<String, String>());
		log.info("==> {}", result.toString());
		BigInteger logId = result.getBigInteger("log_id");
	    String errorMsg = result.getString("error_msg");
	    RespVO<List<UserFaceRespVO>> resp = new RespVO<>();
	    if("SUCCESS".equals(errorMsg)) {
	    	TypeReference<List<UserFaceRespVO>> typeReference = new TypeReference<List<UserFaceRespVO>>() {};
	    	resp.setData(mapper.readValue(result.getJSONObject("result").getJSONArray("face_list").toString(), typeReference));
	    } else {
	    	resp.setErrorMsg(errorMsg);
	    }
	    resp.setLogId(logId);
	    return resp;
	}
	
	/**
	 * 删除用户组
	 * 
	 * @param groupId
	 */
	public void deleteUserGroup(String groupId) {
		HashMap<String, String> options = new HashMap<String, String>();
	    // 删除用户组
	    JSONObject result = client.groupDelete(groupId, options);
	    log.info("==> {}", result.toString());
	}
	
	/**
	 * 人脸检测
	 * 
	 */
	public String faceDetect(CheckFaceVO vo) {
		// 传入可选参数调用接口
	    HashMap<String, Object> options = new HashMap<String, Object>();
	    if(StringUtils.hasText(vo.getFaceField())) {
	    	options.put("face_field", vo.getFaceField());
	    }
	    if(StringUtils.hasText(vo.getMaxFaceNum())) {
	    	options.put("max_face_num", vo.getMaxFaceNum());
	    }
	    if(StringUtils.hasText(vo.getFaceType())) {
	    	options.put("face_type", vo.getFaceType());
	    }
	    if(StringUtils.hasText(vo.getLivenessControl())) {
	    	options.put("liveness_control", vo.getLivenessControl());
	    }
	    String imgBase64 = vo.getImage().split(";base64,")[1];
	    JSONObject result = client.detect(imgBase64, vo.getImageType(), options);
	    log.info("==> {}", result.toString());
	    return result.toString();
	}
	
}
