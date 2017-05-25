package org.dante.demo.swagger.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiModel(value = "用户对象")
public class UserVO {
	
	@ApiModelProperty(value = "用户Id", required = true, position=1)
	private Long id;
	
	@ApiModelProperty(value = "用户帐号", required = true, position=2)
	private String account;
	
	@ApiModelProperty(value = "用户系数", position=3)
	private Double balance;
	
	@ApiModelProperty(value = "生日(yyyy-MM-dd)", example="1988-08-08", position=4)
	private String birthday;

	public UserVO() {
	}

	public UserVO(Long id, String account, Double balance, String birthday) {
		super();
		this.id = id;
		this.account = account;
		this.balance = balance;
		this.birthday = birthday;
	}

	@ApiOperation(value="Id", notes="用户唯一标识")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", account=" + account + ", balance=" + balance + ", birthday=" + birthday + "]";
	}

}
