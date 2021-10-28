package com.bjsn909429077.stz.bean;

import java.util.List;

public class MyAnswerBean {

    public int code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        public String answerContent;
        public int id;
        public String intime;
        public List<?> listPics;
        public String questionContent;
        public String uptime;
    }
}
