package com.bjsn909429077.stz.bean;

public class WDQbean {


    /**
     * code : 0
     * msg : ok
     * data : {"wdId":28,"qrCodeUrl":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/KEm2cHsr6J1630572818252.png"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * wdId : 28
         * qrCodeUrl : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/KEm2cHsr6J1630572818252.png
         */

        private int wdId;
        private String qrCodeUrl;

        public int getWdId() {
            return wdId;
        }

        public void setWdId(int wdId) {
            this.wdId = wdId;
        }

        public String getQrCodeUrl() {
            return qrCodeUrl;
        }

        public void setQrCodeUrl(String qrCodeUrl) {
            this.qrCodeUrl = qrCodeUrl;
        }
    }
}
