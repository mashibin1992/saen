package com.bjsn909429077.stz.bean;

import java.util.List;

public class CoursePackageListBean {

    public int code;
    public DataBean data;
    public String msg;

    public static class DataBean {
        public List<CourseListBean> courseList;
        public List<CourseListBean2> types;

        public static class CourseListBean {
            public int courseCount;
            public String effectiveEtime;
            public int id;
            public int isRecently;
            public String name;
            public int recentlyStudyClassHourId;
            public int studyCourseCount;
            public int studyState;
            public int type;
            public boolean isSelect = false;
        }

        public static class CourseListBean2 {
            public int courseCount;
            public String effectiveEtime;
            public int id;
            public int isRecently;
            public String name;
            public int recentlyStudyClassHourId;
            public int studyCourseCount;
            public int studyState;
            public int type;
            public boolean isSelect = false;
        }
    }
}
