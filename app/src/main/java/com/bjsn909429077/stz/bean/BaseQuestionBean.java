package com.bjsn909429077.stz.bean;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/7 15:02
 **/
public class BaseQuestionBean {
    private int code;
    private String success;
    private String msg;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
