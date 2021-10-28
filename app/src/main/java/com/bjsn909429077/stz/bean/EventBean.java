package com.bjsn909429077.stz.bean;

import java.io.Serializable;
import java.util.List;

public class EventBean<T> implements Serializable {
    private int TYPE;
    private List<T> data;

    public static final int SELECT_COURSE = 0;//选课
    public static final int PURCHASED_COURSE = 1;//已购课程
    public static final int RECENT_BROWSING_COURSE = 2;//最近浏览
    public static final int REFRESH = 0x0009;//刷新

    public EventBean(int type, List<T> courseScheduleListBean) {
        this.TYPE = type;
        this.data = courseScheduleListBean;
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public List<T> getCourseScheduleListBean() {
        return data;
    }

    public void setCourseScheduleListBean(List<T> courseScheduleListBean) {
        this.data = courseScheduleListBean;
    }
}
