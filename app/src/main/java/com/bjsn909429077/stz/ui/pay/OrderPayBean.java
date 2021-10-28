package com.bjsn909429077.stz.ui.pay;

/**
 * @author XuZhuChao
 * @create 2021/9/27
 * @Describe
 */
public class OrderPayBean {


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
        private WxSignDTO wxSign;

        private String aliSign;

        public String getAliSign() {
            return aliSign;
        }

        public void setAliSign(String aliSign) {
            this.aliSign = aliSign;
        }

        public WxSignDTO getWxSign() {
            return wxSign;
        }

        public void setWxSign(WxSignDTO wxSign) {
            this.wxSign = wxSign;
        }

        public static class WxSignDTO {
            private String nonce_str;
            private String appid;
            private String sign;
            private String trade_type;
            private String return_msg;
            private String result_code;
            private String mch_id;
            private String return_code;
            private String prepay_id;
            private String timestamp;

            public String getNonce_str() {
                return nonce_str == null ? "" : nonce_str;
            }

            public void setNonce_str(String nonce_str) {
                this.nonce_str = nonce_str;
            }

            public String getAppid() {
                return appid == null ? "" : appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getSign() {
                return sign == null ? "" : sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTrade_type() {
                return trade_type == null ? "" : trade_type;
            }

            public void setTrade_type(String trade_type) {
                this.trade_type = trade_type;
            }

            public String getReturn_msg() {
                return return_msg == null ? "" : return_msg;
            }

            public void setReturn_msg(String return_msg) {
                this.return_msg = return_msg;
            }

            public String getResult_code() {
                return result_code == null ? "" : result_code;
            }

            public void setResult_code(String result_code) {
                this.result_code = result_code;
            }

            public String getMch_id() {
                return mch_id == null ? "" : mch_id;
            }

            public void setMch_id(String mch_id) {
                this.mch_id = mch_id;
            }

            public String getReturn_code() {
                return return_code == null ? "" : return_code;
            }

            public void setReturn_code(String return_code) {
                this.return_code = return_code;
            }

            public String getPrepay_id() {
                return prepay_id == null ? "" : prepay_id;
            }

            public void setPrepay_id(String prepay_id) {
                this.prepay_id = prepay_id;
            }

            public String getTimestamp() {
                return timestamp == null ? "" : timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
