package com.bjsn909429077.stz.ui.pay;

/**
 * @author XuZhuChao
 * @create 2021/9/27
 * @Describe
 */
public class AliPayBean {


    private int code;
    private String msg;
    private DataDTO data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        private String aliSign;

        public String getAliSign() {
            return aliSign;
        }

        public void setAliSign(String aliSign) {
            this.aliSign = aliSign;
        }
    }
}
