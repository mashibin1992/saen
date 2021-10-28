package com.bjsn909429077.stz.bean;

import java.util.List;

public class AllLiveListBean {

    public int code;
    public List<DataBean> data;
    public String msg;

//    public static class DataBean {
//        public int id;
//        public int isBuy;
//        public int isFree;
//        public int liveState;
//        public String liveTime;
//        public String name;
//        public String originalPrive;
//        public String price;
//        public String teacherHeaderPath;
//        public int teacherId;
//        public String teacherName;
//        public String typeName;
//        public String channelId;
//    }


    public static class DataBean {
        /**
         * channelId :
         * id : 0
         * isFree : 0
         * liveTime :
         * name :
         * price :
         * teacherHeaderPath :
         * teacherId : 0
         * teacherName :
         * type : 0
         * typeName :
         */
        public int isBuy;
        public int liveState;
        public String originalPrive;

        public String channelId;
        public Integer id;
        public Integer isFree;
        public String liveTime;
        public String name;
        public String price;
        public String teacherHeaderPath;
        public Integer teacherId;
        public String teacherName;
        public Integer type;
        public String typeName;
    }
}
