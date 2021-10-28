package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/20 08:59
 **/
public class AllOrderBean {
    private int code;
    private List<DataBean> data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private String actuallyPrice;
        private int coursePackageId;
        private String coverPath;
        private List<?> giveCourseNames;
        private int isTextbook;
        private String orderId;
        private String orderName;
        private String orderTime;
        private int orderType;
        private int payState;
        private String totalPrice;
        private String wdEfficientBTime;
        private String wdEfficientETime;

        public String getActuallyPrice() {
            return actuallyPrice;
        }

        public void setActuallyPrice(String actuallyPrice) {
            this.actuallyPrice = actuallyPrice;
        }

        public int getCoursePackageId() {
            return coursePackageId;
        }

        public void setCoursePackageId(int coursePackageId) {
            this.coursePackageId = coursePackageId;
        }

        public String getCoverPath() {
            return coverPath;
        }

        public void setCoverPath(String coverPath) {
            this.coverPath = coverPath;
        }

        public List<?> getGiveCourseNames() {
            return giveCourseNames;
        }

        public void setGiveCourseNames(List<?> giveCourseNames) {
            this.giveCourseNames = giveCourseNames;
        }

        public int getIsTextbook() {
            return isTextbook;
        }

        public void setIsTextbook(int isTextbook) {
            this.isTextbook = isTextbook;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public int getOrderType() {
            return orderType;
        }

        public void setOrderType(int orderType) {
            this.orderType = orderType;
        }

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getWdEfficientBTime() {
            return wdEfficientBTime;
        }

        public void setWdEfficientBTime(String wdEfficientBTime) {
            this.wdEfficientBTime = wdEfficientBTime;
        }

        public String getWdEfficientETime() {
            return wdEfficientETime;
        }

        public void setWdEfficientETime(String wdEfficientETime) {
            this.wdEfficientETime = wdEfficientETime;
        }
    }
}
