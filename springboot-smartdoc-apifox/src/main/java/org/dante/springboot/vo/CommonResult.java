package org.dante.springboot.vo;

public class CommonResult<T> extends BaseResult<T>  {

	private static final long serialVersionUID = 4556589869015440551L;

	public CommonResult() {
	}
	
	public CommonResult(boolean success, String message) {
        this.setSuccess(success);
        this.setMsg(message);
    }

    public CommonResult(boolean success) {
        this.setSuccess(success);
    }

    public CommonResult(String code, String message) {
        this.setCode(code);
        this.setMsg(message);
    }

    public CommonResult(boolean success, String message, T data) {
        this.setSuccess(success);
        this.setMsg(message);
        this.setData(data);
    }

   

    public CommonResult<T> setResult(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return (T) super.getData();
    }
}
