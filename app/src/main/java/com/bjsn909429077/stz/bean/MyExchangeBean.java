package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/17 22:34
 **/
public class MyExchangeBean {
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
        private String courseName;
        private String coursePackageEfficientETime;
        private int coursePackageId;
        private int coursePackagePrice;
        private int coursePackagePriceId;
        private String courseSecondName;
        private String exchangeEfficientBTime;
        private String exchangeEfficientETime;
        private List<?> giveCourseNames;
        private int redemptionCodeId;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCoursePackageEfficientETime() {
            return coursePackageEfficientETime;
        }

        public void setCoursePackageEfficientETime(String coursePackageEfficientETime) {
            this.coursePackageEfficientETime = coursePackageEfficientETime;
        }

        public int getCoursePackageId() {
            return coursePackageId;
        }

        public void setCoursePackageId(int coursePackageId) {
            this.coursePackageId = coursePackageId;
        }

        public int getCoursePackagePrice() {
            return coursePackagePrice;
        }

        public void setCoursePackagePrice(int coursePackagePrice) {
            this.coursePackagePrice = coursePackagePrice;
        }

        public int getCoursePackagePriceId() {
            return coursePackagePriceId;
        }

        public void setCoursePackagePriceId(int coursePackagePriceId) {
            this.coursePackagePriceId = coursePackagePriceId;
        }

        public String getCourseSecondName() {
            return courseSecondName;
        }

        public void setCourseSecondName(String courseSecondName) {
            this.courseSecondName = courseSecondName;
        }

        public String getExchangeEfficientBTime() {
            return exchangeEfficientBTime;
        }

        public void setExchangeEfficientBTime(String exchangeEfficientBTime) {
            this.exchangeEfficientBTime = exchangeEfficientBTime;
        }

        public String getExchangeEfficientETime() {
            return exchangeEfficientETime;
        }

        public void setExchangeEfficientETime(String exchangeEfficientETime) {
            this.exchangeEfficientETime = exchangeEfficientETime;
        }

        public List<?> getGiveCourseNames() {
            return giveCourseNames;
        }

        public void setGiveCourseNames(List<?> giveCourseNames) {
            this.giveCourseNames = giveCourseNames;
        }

        public int getRedemptionCodeId() {
            return redemptionCodeId;
        }

        public void setRedemptionCodeId(int redemptionCodeId) {
            this.redemptionCodeId = redemptionCodeId;
        }
    }
}
