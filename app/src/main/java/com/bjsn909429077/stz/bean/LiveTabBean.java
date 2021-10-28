package com.bjsn909429077.stz.bean;

import java.util.List;

public class LiveTabBean {

    public int code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        public int id;
        public String name;
    }
}
