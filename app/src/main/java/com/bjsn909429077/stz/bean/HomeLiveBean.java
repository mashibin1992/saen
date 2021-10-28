package com.bjsn909429077.stz.bean;

import java.util.List;

public class HomeLiveBean {


    /**
     * code : 0
     * data : [{"id":4,"isBuy":0,"isFree":1,"liveState":1,"liveTime":"2021-09-09 00:00-00:00","name":"中秋晚会","originalPrive":"2","price":"2","teacherId":2,"teacherName":"王晓","typeName":"注会"},{"id":2,"isBuy":0,"isFree":1,"liveState":1,"liveTime":"2021-09-15 00:00-00:00","name":"43531","originalPrive":"34","price":"34","teacherId":3,"teacherName":"胡歌","typeName":"注会"}]
     * msg : ok
     */

    private int code;
    private List<DataBean> data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * id : 4
         * isBuy : 0
         * isFree : 1
         * liveState : 1
         * liveTime : 2021-09-09 00:00-00:00
         * name : 中秋晚会
         * originalPrive : 2
         * price : 2
         * teacherId : 2
         * teacherName : 王晓
         * typeName : 注会
         */

        private int id;
        private int isBuy;
        private int isFree;
        private int liveState;
        private String liveTime;
        private String name;
        private String originalPrive;
        private String price;
        private int teacherId;
        private String teacherName;
        private String typeName;
        private String teacherHeaderPath;
        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTeacherHeaderPath() {
            return teacherHeaderPath;
        }

        public void setTeacherHeaderPath(String teacherHeaderPath) {
            this.teacherHeaderPath = teacherHeaderPath;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsBuy() {
            return isBuy;
        }

        public void setIsBuy(int isBuy) {
            this.isBuy = isBuy;
        }

        public int getIsFree() {
            return isFree;
        }

        public void setIsFree(int isFree) {
            this.isFree = isFree;
        }

        public int getLiveState() {
            return liveState;
        }

        public void setLiveState(int liveState) {
            this.liveState = liveState;
        }

        public String getLiveTime() {
            return liveTime;
        }

        public void setLiveTime(String liveTime) {
            this.liveTime = liveTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOriginalPrive() {
            return originalPrive;
        }

        public void setOriginalPrive(String originalPrive) {
            this.originalPrive = originalPrive;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(int teacherId) {
            this.teacherId = teacherId;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
