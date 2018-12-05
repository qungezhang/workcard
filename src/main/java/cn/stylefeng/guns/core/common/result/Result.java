package cn.stylefeng.guns.core.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("返回值")
public class Result<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="消息")
	private String message;
	@ApiModelProperty(value="请求状态")
	private boolean success;
	@ApiModelProperty(value="状态码")
	private int statusCode;
	@ApiModelProperty(value="返回的实际数据")
	private T data;
	
	public Result(){}
	public Result(T data){
		this.data = data;
	}
	public Result(boolean success, T data){
		this.success = success;
		this.data = data;
	}
	public Result(boolean success, int statusCode, String message){
		this.success = success;
		this.statusCode = statusCode;
		this.message = message;
	}
	public Result(boolean success, int statusCode, T data){
		this.success = success;
		this.statusCode = statusCode;
		this.data = data;
	}
	public Result(boolean success, int statusCode, String message, T data){
		this.success = success;
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}
	
	public static<T> Result<T> ok(T data){ return ok("ok", data);}
	public static<T> Result<T> ok(String message,T data){return new Result<T>(true, ApiCode.OK, message, data);}
	
	public static<T> Result<T> error() {return error(ApiCode.INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");}
	public static<T> Result<T> error(String message) {return error(ApiCode.INTERNAL_SERVER_ERROR, message);}
	public static<T> Result<T> error(int statusCode,String message) {return new Result<T>(false, statusCode, message);}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public T getData() {
		return (T)data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
