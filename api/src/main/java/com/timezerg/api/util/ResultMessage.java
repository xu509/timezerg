package com.timezerg.api.util;

/**
 * 请求结果消息
 * 
 */
public enum ResultMessage {

	OK("0", "成功"),
	PARAM_ERROR("11","参数错误");


	private String result;
	private String msg;

	ResultMessage(String result, String msg) {
		this.result = result;
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
