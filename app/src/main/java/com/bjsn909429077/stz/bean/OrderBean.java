package com.bjsn909429077.stz.bean;

import java.util.List;

public class OrderBean {


    /**
     * code : 0
     * data : {"orderAddress":{"address":"hvhvhv","area":"滨江区","areaId":"330108","city":"杭州市","cityId":"330100","mobile":"13100857098","name":"gyycvy","orderAddressId":9,"province":"浙江省","provinceId":"330000"},"orderCoursePackageInfo":{"couponPrice":0,"coursePackageId":7,"coursePackageName":"《初级会计实务1》","coursePurchaseAgreementId":4,"coverPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/H2SWeQSQae1631346990931.png","effectiveEtime":1631721600000,"giveCourseNames":["课程01"],"price":400},"pointDeductionPrice":0,"totalPrice":400}
     * msg : ok
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
         * orderAddress : {"address":"hvhvhv","area":"滨江区","areaId":"330108","city":"杭州市","cityId":"330100","mobile":"13100857098","name":"gyycvy","orderAddressId":9,"province":"浙江省","provinceId":"330000"}
         * orderCoursePackageInfo : {"couponPrice":0,"coursePackageId":7,"coursePackageName":"《初级会计实务1》","coursePurchaseAgreementId":4,"coverPath":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/H2SWeQSQae1631346990931.png","effectiveEtime":1631721600000,"giveCourseNames":["课程01"],"price":400}
         * pointDeductionPrice : 0
         * totalPrice : 400
         */

        private OrderAddressBean orderAddress;
        private OrderCoursePackageInfoBean orderCoursePackageInfo;
        private String pointDeductionPrice;
        private String totalPrice;

        public OrderAddressBean getOrderAddress() {
            return orderAddress;
        }

        public void setOrderAddress(OrderAddressBean orderAddress) {
            this.orderAddress = orderAddress;
        }

        public OrderCoursePackageInfoBean getOrderCoursePackageInfo() {
            return orderCoursePackageInfo;
        }

        public void setOrderCoursePackageInfo(OrderCoursePackageInfoBean orderCoursePackageInfo) {
            this.orderCoursePackageInfo = orderCoursePackageInfo;
        }

        public String getPointDeductionPrice() {
            return pointDeductionPrice;
        }

        public void setPointDeductionPrice(String pointDeductionPrice) {
            this.pointDeductionPrice = pointDeductionPrice;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public static class OrderAddressBean {
            /**
             * address : hvhvhv
             * area : 滨江区
             * areaId : 330108
             * city : 杭州市
             * cityId : 330100
             * mobile : 13100857098
             * name : gyycvy
             * orderAddressId : 9
             * province : 浙江省
             * provinceId : 330000
             */

            private String address;
            private String area;
            private String areaId;
            private String city;
            private String cityId;
            private String mobile;
            private String name;
            private int orderAddressId;
            private String province;
            private String provinceId;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getOrderAddressId() {
                return orderAddressId;
            }

            public void setOrderAddressId(int orderAddressId) {
                this.orderAddressId = orderAddressId;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getProvinceId() {
                return provinceId;
            }

            public void setProvinceId(String provinceId) {
                this.provinceId = provinceId;
            }
        }

        public static class OrderCoursePackageInfoBean {
            /**
             * couponPrice : 0
             * coursePackageId : 7
             * coursePackageName : 《初级会计实务1》
             * coursePurchaseAgreementId : 4
             * coverPath : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/H2SWeQSQae1631346990931.png
             * effectiveEtime : 1631721600000
             * giveCourseNames : ["课程01"]
             * price : 400
             */

            private String couponPrice;
            private int coursePackageId;
            private String coursePackageName;
            private int coursePurchaseAgreementId;
            private String coverPath;
            private long effectiveEtime;
            private List<String> giveCourseNames;
            private String price;

            public String getCouponPrice() {
                return couponPrice;
            }

            public void setCouponPrice(String couponPrice) {
                this.couponPrice = couponPrice;
            }

            public int getCoursePackageId() {
                return coursePackageId;
            }

            public void setCoursePackageId(int coursePackageId) {
                this.coursePackageId = coursePackageId;
            }

            public String getCoursePackageName() {
                return coursePackageName;
            }

            public void setCoursePackageName(String coursePackageName) {
                this.coursePackageName = coursePackageName;
            }

            public int getCoursePurchaseAgreementId() {
                return coursePurchaseAgreementId;
            }

            public void setCoursePurchaseAgreementId(int coursePurchaseAgreementId) {
                this.coursePurchaseAgreementId = coursePurchaseAgreementId;
            }

            public String getCoverPath() {
                return coverPath;
            }

            public void setCoverPath(String coverPath) {
                this.coverPath = coverPath;
            }

            public long getEffectiveEtime() {
                return effectiveEtime;
            }

            public void setEffectiveEtime(long effectiveEtime) {
                this.effectiveEtime = effectiveEtime;
            }

            public List<String> getGiveCourseNames() {
                return giveCourseNames;
            }

            public void setGiveCourseNames(List<String> giveCourseNames) {
                this.giveCourseNames = giveCourseNames;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
