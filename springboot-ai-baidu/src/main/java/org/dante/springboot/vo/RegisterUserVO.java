package org.dante.springboot.vo;

import org.dante.springboot.consts.BaiduFaceAIConsts;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterUserVO extends UserVO {

	private static final long serialVersionUID = 1L;

	private String imageType = BaiduFaceAIConsts.IMAGE_TYPE_BASE64;

	private String qualityControl = BaiduFaceAIConsts.QUALITY_CONTROL_LOW;
	
	private String livenessControl	= BaiduFaceAIConsts.LIVENESS_CONTROL_NORMAL;
	
	private String actionType = BaiduFaceAIConsts.ACTION_TYPE_REPLACE;
	
	
}
