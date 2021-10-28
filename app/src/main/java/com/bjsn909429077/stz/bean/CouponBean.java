package com.bjsn909429077.stz.bean;

import java.io.Serializable;
import java.util.List;

public class CouponBean {


    /**
     * code : 0
     * data : [{"availableCoursePackageList":[],"courseCouponId":0,"courseCouponName":"","courseCouponTemplateId":0,"effectiveBtime":0,"effectiveDay":0,"effectiveEtime":0,"effectiveType":0,"enablePrice":0,"isAvailable":0,"price":0}]
     * msg :
     */

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

    public static class DataBean implements Serializable {
        /**
         * availableCoursePackageList : []
         * courseCouponId : 0
         * courseCouponName :
         * courseCouponTemplateId : 0
         * effectiveBtime : 0
         * effectiveDay : 0
         * effectiveEtime : 0
         * effectiveType : 0
         * enablePrice : 0
         * isAvailable : 0
         * price : 0
         */

        private List<String> availableCoursePackageList;
        private int courseCouponId;
        private String courseCouponName;
        private int courseCouponTemplateId;
        private int effectiveBtime;
        private int effectiveDay;
        private int effectiveEtime;
        private int effectiveType;
        private int enablePrice;
        private int isAvailable;
        private int price;

        public List<String> getAvailableCoursePackageList() {
            return availableCoursePackageList;
        }

        public void setAvailableCoursePackageList(List<String> availableCoursePackageList) {
            this.availableCoursePackageList = availableCoursePackageList;
        }

        public int getCourseCouponId() {
            return courseCouponId;
        }

        public void setCourseCouponId(int courseCouponId) {
            this.courseCouponId = courseCouponId;
        }

        public String getCourseCouponName() {
            return courseCouponName;
        }

        public void setCourseCouponName(String courseCouponName) {
            this.courseCouponName = courseCouponName;
        }

        public int getCourseCouponTemplateId() {
            return courseCouponTemplateId;
        }

        public void setCourseCouponTemplateId(int courseCouponTemplateId) {
            this.courseCouponTemplateId = courseCouponTemplateId;
        }

        public int getEffectiveBtime() {
            return effectiveBtime;
        }

        public void setEffectiveBtime(int effectiveBtime) {
            this.effectiveBtime = effectiveBtime;
        }

        public int getEffectiveDay() {
            return effectiveDay;
        }

        public void setEffectiveDay(int effectiveDay) {
            this.effectiveDay = effectiveDay;
        }

        public int getEffectiveEtime() {
            return effectiveEtime;
        }

        public void setEffectiveEtime(int effectiveEtime) {
            this.effectiveEtime = effectiveEtime;
        }

        public int getEffectiveType() {
            return effectiveType;
        }

        public void setEffectiveType(int effectiveType) {
            this.effectiveType = effectiveType;
        }

        public int getEnablePrice() {
            return enablePrice;
        }

        public void setEnablePrice(int enablePrice) {
            this.enablePrice = enablePrice;
        }

        public int getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(int isAvailable) {
            this.isAvailable = isAvailable;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
