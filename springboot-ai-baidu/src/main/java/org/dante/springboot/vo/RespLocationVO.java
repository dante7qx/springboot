package org.dante.springboot.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class RespLocationVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 人脸区域离左边界的距离 */
	private Double left;
	/** 人脸区域离上边界的距离 */
	private Double top;
	/** 人脸区域的宽度 */
	private Double width;
	/** 人脸区域的高度 */
	private Double height;
	/** 人脸框相对于竖直方向的顺时针旋转角，[-180,180] */
	private Integer rotation;
}
