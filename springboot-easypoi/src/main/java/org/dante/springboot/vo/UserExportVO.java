package org.dante.springboot.vo;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserExportVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Excel(name = "姓名")
    private String realName;

    @Excel(name = "性别")
    private Integer sex;

    @Excel(name = "出生日期", exportFormat = "yyyy-MM-dd")
    private Date birthday;

    @Excel(name = "手机号码")
    private String phone;

    @Excel(name = "邮箱")
    private String email;

    @Excel(name = "头像地址")
    private String avatar;

    @Excel(name = "描述")
    private String remark;
}
