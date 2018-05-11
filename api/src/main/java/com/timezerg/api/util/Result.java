package com.timezerg.api.util;

/**
 * Created by xnx on 2018/5/11.
 */
public class Result {
    protected String result;
    protected String msg;
    protected Object data;

    public Result(ResultMessage message) {
        super();
        this.result = message.getResult();
        this.msg = message.getMsg();
    }

    public Result(ResultMessage message, Object data) {
        super();
        this.result = message.getResult();
        this.msg = message.getMsg();
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
