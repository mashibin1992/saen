package com.bjsn909429077.stz.bean;

import java.util.List;

public class PackageListBean2 {

    public int code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean {
        public String coverPhotoPath;
        public int firstTypeId;
        public int id;
        public String name;
        public int secondTypeId;
    }
}
