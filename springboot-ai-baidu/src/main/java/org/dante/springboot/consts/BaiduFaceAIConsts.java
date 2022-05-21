package org.dante.springboot.consts;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaiduFaceAIConsts {
	/** 图片类型 */
	/** BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M */
	public static final String IMAGE_TYPE_BASE64 = "BASE64";
	/** URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长) */
	public static final String IMAGE_TYPE_URL = "URL";
	/** FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个 */
	public static final String IMAGE_TYPE_FACE_TOKEN = "BASE64";
	
	/** 图片质量 */
	/** 不进行控制，默认值 */
	public static final String QUALITY_CONTROL_NONE = "NONE";
	/** 较低的质量要求 */
	public static final String QUALITY_CONTROL_LOW = "LOW";
	/** 一般的质量要求 */
	public static final String QUALITY_CONTROL_NORMAL = "NORMAL";
	/** 较高的质量要求 */
	public static final String QUALITY_CONTROL_HIGH = "HIGH";
	
	/** 活体检测 */
	/** 不进行控制，默认值 */
	public static final String LIVENESS_CONTROL_NONE = "NONE";
	/** 较低的活体要求(高通过率 低攻击拒绝率) */
	public static final String LIVENESS_CONTROL_LOW = "LOW";
	/** 一般的活体要求(平衡的攻击拒绝率, 通过率)  */
	public static final String LIVENESS_CONTROL_NORMAL = "NORMAL";
	/** 较高的活体要求(高攻击拒绝率 低通过率) */
	public static final String LIVENESS_CONTROL_HIGH = "HIGH";
	
	/** 操作方式 */
	/** 当user_id在库中已经存在时，对此user_id重复注册时，新注册的图片默认会追加到该user_id下，默认值 */
	public static final String ACTION_TYPE_APPEND = "APPEND";
	/** 当对此user_id重复注册时,则会用新图替换库中该user_id下所有图片 */
	public static final String ACTION_TYPE_REPLACE = "REPLACE";
}
