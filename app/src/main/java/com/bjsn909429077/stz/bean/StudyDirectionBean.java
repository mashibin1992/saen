package com.bjsn909429077.stz.bean;

import java.util.List;

public class StudyDirectionBean {

    public int code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        public boolean isSelect = false;
        public Integer id;
        public String name;
    }
}
