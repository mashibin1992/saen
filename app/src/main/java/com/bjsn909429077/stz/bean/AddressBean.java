package com.bjsn909429077.stz.bean;

import java.io.Serializable;
import java.util.List;

public class AddressBean {

    /**
     * code : 0
     * msg : ok
     * data : [{"id":10,"customerId":51,"province":"浙江省","provinceId":"330000","city":"杭州市","cityId":"330100","area":"滨江区","areaId":"330108","address":"yccgvhcg","name":"yfgygugu","mobile":"13100845099","isDefault":0,"intime":"2021-09-15T11:41:32.000+0000","uptime":null,"isdel":0},{"id":9,"customerId":51,"province":"浙江省","provinceId":"330000","city":"杭州市","cityId":"330100","area":"滨江区","areaId":"330108","address":"hvhvhv","name":"gyycvy","mobile":"13100857098","isDefault":1,"intime":"2021-09-15T11:38:54.000+0000","uptime":null,"isdel":0},{"id":8,"customerId":51,"province":"浙江省","provinceId":"330000","city":"杭州市","cityId":"330100","area":"滨江区","areaId":"330108","address":"xggcgcgcggc","name":"vygvvy","mobile":"13100845099","isDefault":1,"intime":"2021-09-15T11:37:29.000+0000","uptime":null,"isdel":0}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 10
         * customerId : 51
         * province : 浙江省
         * provinceId : 330000
         * city : 杭州市
         * cityId : 330100
         * area : 滨江区
         * areaId : 330108
         * address : yccgvhcg
         * name : yfgygugu
         * mobile : 13100845099
         * isDefault : 0
         * intime : 2021-09-15T11:41:32.000+0000
         * uptime : null
         * isdel : 0
         */

        private int id;
        private int customerId;
        private String province;
        private String provinceId;
        private String city;
        private String cityId;
        private String area;
        private String areaId;
        private String address;
        private String name;
        private String mobile;
        private int isDefault;
        private String intime;
        private Object uptime;
        private int isdel;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public String getIntime() {
            return intime;
        }

        public void setIntime(String intime) {
            this.intime = intime;
        }

        public Object getUptime() {
            return uptime;
        }

        public void setUptime(Object uptime) {
            this.uptime = uptime;
        }

        public int getIsdel() {
            return isdel;
        }

        public void setIsdel(int isdel) {
            this.isdel = isdel;
        }
    }
}
