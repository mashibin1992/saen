package com.bjsn909429077.stz.bean;

import java.io.Serializable;
import java.util.List;

public class HasBuyListBean implements Serializable {

    public Integer code;
    public List<DataBean> data;
    public String msg;

    public static class DataBean implements Serializable {
        public Integer classHourCount1;
        public String coverPath;
        public String effectiveEtime;
        public Integer firstTypeId;
        public Integer id;
        public Integer isFree;
        public String packageName;
        public Integer studyCourseCount;
    }
}
