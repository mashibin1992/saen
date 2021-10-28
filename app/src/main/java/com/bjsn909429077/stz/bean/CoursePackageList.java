package com.bjsn909429077.stz.bean;

import java.util.List;

public class CoursePackageList {

    public int code;
    public String msg;
    public List<DataBean> data;
    public static class DataBean {

        public List<HoursBean> hours;
        public int id;
        public String name;

        public static class HoursBean {
            public String classHourName;
            public int courseId;
            public int id;
            public int studyTimeLength;
            public String videoTimeLength;
        }
    }
}
