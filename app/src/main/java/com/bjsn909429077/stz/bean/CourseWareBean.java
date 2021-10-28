package com.bjsn909429077.stz.bean;

import java.util.List;

public class CourseWareBean {

    public int code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        public String coursewareName;
        public String filePath;
        public int id;
        public String intime;
    }
}
