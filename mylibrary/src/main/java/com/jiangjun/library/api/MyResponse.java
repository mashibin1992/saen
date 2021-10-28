package com.jiangjun.library.api;

import java.io.Serializable;

/**
 * Created by jiangjun on 2019/8/26 11:56
 */
public class MyResponse<T> implements Serializable {


    //结果码
    private int code = 1;
    /*错误信息:msg*/
    private String msg;
    /*真实数据 data或者result*/
    private T  data;


    @Override
    public String toString() {
        return "MyResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", result=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public MyResponse<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public MyResponse<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getResult() {
        return data;
    }

    public MyResponse<T> setResult(T result) {
        this.data = result;
        return this;
    }


}

