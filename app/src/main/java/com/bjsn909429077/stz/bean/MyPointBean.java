package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/17 21:15
 **/
public class MyPointBean {
    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<PointChildListBean> pointChildList;
        private int totalPointValue;

        public List<PointChildListBean> getPointChildList() {
            return pointChildList;
        }

        public void setPointChildList(List<PointChildListBean> pointChildList) {
            this.pointChildList = pointChildList;
        }

        public int getTotalPointValue() {
            return totalPointValue;
        }

        public void setTotalPointValue(int totalPointValue) {
            this.totalPointValue = totalPointValue;
        }

        public static class PointChildListBean {
            private String intime;
            private int pointValue;
            private int status;
            private int type;

            public String getIntime() {
                return intime;
            }

            public void setIntime(String intime) {
                this.intime = intime;
            }

            public int getPointValue() {
                return pointValue;
            }

            public void setPointValue(int pointValue) {
                this.pointValue = pointValue;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
