package org.dante.springboot.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

import org.dante.springboot.prop.GenderVal;

import lombok.Data;

@Data
public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Null(message = "新增不需要指定id", groups = Groups.Add.class)
	@NotNull(message = "修改需要指定id", groups = Groups.Update.class)
	private Integer id;
	@NotBlank(message = "用户名不能为空", groups = { Groups.Add.class, Groups.Update.class })
	private String username;
	@Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$", message = "密码必须为8~16个字母和数字组合", groups = { Groups.Add.class, Groups.Update.class })
	private String password;
	@Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "手机号不合规", groups = { Groups.Add.class, Groups.Update.class })
	private String phone;
	@Email(groups = { Groups.Add.class, Groups.Update.class })
	private String email;
	@GenderVal(message = "性别应指定相应的值", vals = { 1, 2 }, groups = { Groups.Add.class, Groups.Update.class })
	private Integer gender;

}
