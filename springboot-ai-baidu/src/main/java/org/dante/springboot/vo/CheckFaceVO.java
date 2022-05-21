package org.dante.springboot.vo;

import java.io.Serializable;

import org.dante.springboot.consts.BaiduFaceAIConsts;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CheckFaceVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NonNull
	private String image;
	private String imageType = BaiduFaceAIConsts.IMAGE_TYPE_BASE64;
	private String faceField;
	private String maxFaceNum;
	private String faceType;
	private String livenessControl;

}
