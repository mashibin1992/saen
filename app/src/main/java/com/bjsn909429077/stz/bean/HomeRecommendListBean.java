package com.bjsn909429077.stz.bean;

import java.util.List;

public class HomeRecommendListBean {


    /**
     * code : 0
     * msg : ok
     * data : [{"coursePackageId":7,"coverPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/H2SWeQSQae1631346990931.png","courseName":"《初级会计实务1》","courseSecondName":"《初级会计实务1》副标题","isFree":1,"price":100,"discountPrice":90,"isDiscount":1,"leftTime":1402123,"isBuy":0},{"coursePackageId":9,"coverPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/5pz33fiG6C1631347114422.png","courseName":"《备考方案1》","courseSecondName":"《备考方案1》副标题","isFree":1,"price":50,"discountPrice":40,"isDiscount":1,"leftTime":1402123,"isBuy":0},{"coursePackageId":10,"coverPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/5pz33fiG6C1631347114422.png","courseName":"《备考方案1》","courseSecondName":"《备考方案1》副标题","isFree":1,"price":100,"discountPrice":90,"isDiscount":1,"leftTime":1402123,"isBuy":0},{"coursePackageId":11,"coverPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/5pz33fiG6C1631347114422.png","courseName":"《备考方案1》","courseSecondName":"《备考方案1》副标题","isFree":1,"price":100,"discountPrice":700,"isDiscount":1,"leftTime":1402123,"isBuy":0}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * coursePackageId : 7
         * coverPath : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/H2SWeQSQae1631346990931.png
         * courseName : 《初级会计实务1》
         * courseSecondName : 《初级会计实务1》副标题
         * isFree : 1
         * price : 100
         * discountPrice : 90
         * isDiscount : 1
         * leftTime : 1402123
         * isBuy : 0
         */

        private int coursePackageId;
        private String coverPath;
        private String courseName;
        private String courseSecondName;
        private int isFree;
        private int price;
        private int discountPrice;
        private int isDiscount;
        private int leftTime;
        private int isBuy;

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

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseSecondName() {
            return courseSecondName;
        }

        public void setCourseSecondName(String courseSecondName) {
            this.courseSecondName = courseSecondName;
        }

        public int getIsFree() {
            return isFree;
        }

        public void setIsFree(int isFree) {
            this.isFree = isFree;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getDiscountPrice() {
            return discountPrice;
        }

        public void setDiscountPrice(int discountPrice) {
            this.discountPrice = discountPrice;
        }

        public int getIsDiscount() {
            return isDiscount;
        }

        public void setIsDiscount(int isDiscount) {
            this.isDiscount = isDiscount;
        }

        public int getLeftTime() {
            return leftTime;
        }

        public void setLeftTime(int leftTime) {
            this.leftTime = leftTime;
        }

        public int getIsBuy() {
            return isBuy;
        }

        public void setIsBuy(int isBuy) {
            this.isBuy = isBuy;
        }
    }
}
