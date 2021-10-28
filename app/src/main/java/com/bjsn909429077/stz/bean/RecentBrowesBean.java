package com.bjsn909429077.stz.bean;

import java.util.List;

public class RecentBrowesBean {

    public Integer code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        public String coursePrice;
        public String coverPath;
        public Integer firstTypeId;
        public Integer id;
        public Integer isFree;
        public String packageName;
        public String pageageSecondName;
    }
}
