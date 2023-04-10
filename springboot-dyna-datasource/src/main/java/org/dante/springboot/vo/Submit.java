package org.dante.springboot.vo;

import lombok.Data;

@Data
public class Submit {

    /**集团客户名称*/
    private String ecName;
    /**用户名*/
    private String apId;
    /**密码*/
    private String secretKey;
    /**手机号码逗号分隔。(如“1813728****,1813728****,1813728****”)*/
    private String mobiles;
    /**发送短信内容*/
    private String content;
    /**网关签名编码，必填，签名编码在中国移动集团开通帐号后分配，可以在云MAS网页端管理子系统-SMS接口管理功能中下载。*/
    private String sign;
    /**扩展码，根据向移动公司申请的通道填写，如果申请的精确匹配通道，则填写空字符串("")，否则添加移动公司允许的扩展码。*/
    private String addSerial;
    /**按文档要求加密后的mac*/
    private String mac;

}
