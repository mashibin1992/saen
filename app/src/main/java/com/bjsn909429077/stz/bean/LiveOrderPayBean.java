package com.bjsn909429077.stz.bean;

public class LiveOrderPayBean {

    /**
     * code : 0
     * data : {"couponId":0,"couponName":0,"couponPrice":"","id":0,"integralPrice":"","isCoupon":0,"isIntegral":0,"liveId":0,"liveTime":"","name":"","payPrice":"","payWay":0,"totalPrice":""}
     * msg :
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * couponId : 0
         * couponName : 0
         * couponPrice :
         * id : 0
         * integralPrice :
         * isCoupon : 0
         * isIntegral : 0
         * liveId : 0
         * liveTime :
         * name :
         * payPrice :
         * payWay : 0
         * totalPrice :
         */

        private int couponId;
        private int couponName;
        private String couponPrice;
        private int id;
        private String integralPrice;
        private int isCoupon;
        private int isIntegral;
        private int liveId;
        private String liveTime;
        private String name;
        private String payPrice;
        private int payWay;
        private String totalPrice;

        public int getCouponId() {
            return couponId;
        }

        public void setCouponId(int couponId) {
            this.couponId = couponId;
        }

        public int getCouponName() {
            return couponName;
        }

        public void setCouponName(int couponName) {
            this.couponName = couponName;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(String integralPrice) {
            this.integralPrice = integralPrice;
        }

        public int getIsCoupon() {
            return isCoupon;
        }

        public void setIsCoupon(int isCoupon) {
            this.isCoupon = isCoupon;
        }

        public int getIsIntegral() {
            return isIntegral;
        }

        public void setIsIntegral(int isIntegral) {
            this.isIntegral = isIntegral;
        }

        public int getLiveId() {
            return liveId;
        }

        public void setLiveId(int liveId) {
            this.liveId = liveId;
        }

        public String getLiveTime() {
            return liveTime;
        }

        public void setLiveTime(String liveTime) {
            this.liveTime = liveTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public int getPayWay() {
            return payWay;
        }

        public void setPayWay(int payWay) {
            this.payWay = payWay;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
}
