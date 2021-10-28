package com.bjsn909429077.stz.bean;

import java.util.List;

public class LiveListBean {

    public int code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        public int id;
        public String name;
        public String plaTime;
        public String playDate;
        public int state;
    }
}
