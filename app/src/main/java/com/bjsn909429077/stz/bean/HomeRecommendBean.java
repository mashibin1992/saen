package com.bjsn909429077.stz.bean;

import java.util.List;

public class HomeRecommendBean {


    /**
     * code : 0
     * data : [{"businessType":"","enName":"","firstTypeId":0,"firstTypeName":"","icon":"","id":0,"intime":"","isShow":0,"isdel":0,"level":0,"name":"","secondCount":0,"sort":0,"uptime":""}]
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

    public static class DataBean {
        /**
         * businessType :
         * enName :
         * firstTypeId : 0
         * firstTypeName :
         * icon :
         * id : 0
         * intime :
         * isShow : 0
         * isdel : 0
         * level : 0
         * name :
         * secondCount : 0
         * sort : 0
         * uptime :
         */

        private String businessType;
        private String enName;
        private int firstTypeId;
        private String firstTypeName;
        private String icon;
        private int id;
        private String intime;
        private int isShow;
        private int isdel;
        private int level;
        private String name;
        private int secondCount;
        private int sort;
        private String uptime;

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public int getFirstTypeId() {
            return firstTypeId;
        }

        public void setFirstTypeId(int firstTypeId) {
            this.firstTypeId = firstTypeId;
        }

        public String getFirstTypeName() {
            return firstTypeName;
        }

        public void setFirstTypeName(String firstTypeName) {
            this.firstTypeName = firstTypeName;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIntime() {
            return intime;
        }

        public void setIntime(String intime) {
            this.intime = intime;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public int getIsdel() {
            return isdel;
        }

        public void setIsdel(int isdel) {
            this.isdel = isdel;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSecondCount() {
            return secondCount;
        }

        public void setSecondCount(int secondCount) {
            this.secondCount = secondCount;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }
    }
}
