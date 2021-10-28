package com.bjsn909429077.stz.bean;

public class LiveOrderBean {


    /**
     * code : 0
     * msg : ok
     * data : {"id":2,"liveId":2,"couponPrice":"0","isCoupon":0,"payWay":0,"totalPrice":"34","integralPrice":"0","payPrice":"0","name":"43531","liveTime":"2021-09-15 00:00-00:00","couponId":null,"couponName":null,"isIntegral":0}
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
         * id : 2
         * liveId : 2
         * couponPrice : 0
         * isCoupon : 0
         * payWay : 0
         * totalPrice : 34
         * integralPrice : 0
         * payPrice : 0
         * name : 43531
         * liveTime : 2021-09-15 00:00-00:00
         * couponId : null
         * couponName : null
         * isIntegral : 0
         */

        private int id;
        private int liveId;
        private String couponPrice;
        private int isCoupon;
        private int payWay;
        private String totalPrice;
        private String integralPrice;
        private String payPrice;
        private String name;
        private String liveTime;
        private Object couponId;
        private Object couponName;
        private int isIntegral;
        private String price;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLiveId() {
            return liveId;
        }

        public void setLiveId(int liveId) {
            this.liveId = liveId;
        }

        public String getCouponPrice() {
            return couponPrice;
        }

        public void setCouponPrice(String couponPrice) {
            this.couponPrice = couponPrice;
        }

        public int getIsCoupon() {
            return isCoupon;
        }

        public void setIsCoupon(int isCoupon) {
            this.isCoupon = isCoupon;
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

        public String getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(String integralPrice) {
            this.integralPrice = integralPrice;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLiveTime() {
            return liveTime;
        }

        public void setLiveTime(String liveTime) {
            this.liveTime = liveTime;
        }

        public Object getCouponId() {
            return couponId;
        }

        public void setCouponId(Object couponId) {
            this.couponId = couponId;
        }

        public Object getCouponName() {
            return couponName;
        }

        public void setCouponName(Object couponName) {
            this.couponName = couponName;
        }

        public int getIsIntegral() {
            return isIntegral;
        }

        public void setIsIntegral(int isIntegral) {
            this.isIntegral = isIntegral;
        }
    }
}
