package com.bjsn909429077.stz.bean;

import java.util.List;

public class CourseListBean {
    public int code;
    public List<DataDTO> data;
    public String msg;

    public static class DataDTO {
        public String businessType;
        public String enName;
        public int firstTypeId;
        public String firstTypeName;
        public String icon;
        public int id;
        public String intime;
        public int isShow;
        public int isdel;
        public int level;
        public String name;
        public int secondCount;
        public int sort;
        public String uptime;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "businessType='" + businessType + '\'' +
                    ", enName='" + enName + '\'' +
                    ", firstTypeId=" + firstTypeId +
                    ", firstTypeName='" + firstTypeName + '\'' +
                    ", icon='" + icon + '\'' +
                    ", id=" + id +
                    ", intime='" + intime + '\'' +
                    ", isShow=" + isShow +
                    ", isdel=" + isdel +
                    ", level=" + level +
                    ", name='" + name + '\'' +
                    ", secondCount=" + secondCount +
                    ", sort=" + sort +
                    ", uptime='" + uptime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CourseListBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

}
