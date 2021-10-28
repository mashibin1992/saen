package com.bjsn909429077.stz.bean;

import java.util.List;

public class HomeBannerBean2 {

    public int code;
    public List<DataDTO> data;
    public String msg;

    public static class DataDTO {
        public int id;
        public String imgPath;
        public String intime;
        public int isShow;
        public int isdel;
        public String linkUrl;
        public int platform;
        public int sort;
        public int type;
        public String uptime;
    }
}
