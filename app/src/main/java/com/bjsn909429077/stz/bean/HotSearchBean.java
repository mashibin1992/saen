package com.bjsn909429077.stz.bean;

import java.util.List;

public class HotSearchBean {


    /**
     * code : 0
     * data : [{"businessType":"热门搜索","enName":"会计","icon":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/7D2PZBTras1631954313929.jpg","id":51,"intime":"2021-09-18 ","isShow":0,"isdel":0,"level":1,"name":"会计","secondCount":0,"sort":0},{"businessType":"热门搜索","enName":"备考","icon":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/8n5d2ZZ6Rb1631954327377.jpg","id":52,"intime":"2021-09-18 ","isShow":0,"isdel":0,"level":1,"name":"备考","secondCount":0,"sort":0},{"businessType":"热门搜索","enName":"G15","icon":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/TQNEYKRf7N1631954361277.jpg","id":53,"intime":"2021-09-18 ","isShow":0,"isdel":0,"level":1,"name":"G15","secondCount":0,"sort":0}]
     * msg : ok
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
         * businessType : 热门搜索
         * enName : 会计
         * icon : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/7D2PZBTras1631954313929.jpg
         * id : 51
         * intime : 2021-09-18
         * isShow : 0
         * isdel : 0
         * level : 1
         * name : 会计
         * secondCount : 0
         * sort : 0
         */

        private String businessType;
        private String enName;
        private String icon;
        private int id;
        private String intime;
        private int isShow;
        private int isdel;
        private int level;
        private String name;
        private int secondCount;
        private int sort;

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
    }
}
