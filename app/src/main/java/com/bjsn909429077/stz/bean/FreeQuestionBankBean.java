package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/17 17:05
 **/
public class FreeQuestionBankBean {
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
        private String firstIconUrl;
        private int firstId;
        private String firstName;
        private List<SecondListBean> secondList;

        public boolean isIschecked() {
            return ischecked;
        }

        public void setIschecked(boolean ischecked) {
            this.ischecked = ischecked;
        }

        private boolean ischecked;

        public String getFirstIconUrl() {
            return firstIconUrl;
        }

        public void setFirstIconUrl(String firstIconUrl) {
            this.firstIconUrl = firstIconUrl;
        }

        public int getFirstId() {
            return firstId;
        }

        public void setFirstId(int firstId) {
            this.firstId = firstId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public List<SecondListBean> getSecondList() {
            return secondList;
        }

        public void setSecondList(List<SecondListBean> secondList) {
            this.secondList = secondList;
        }

        public static class SecondListBean {
            private int parentId;
            private String secondIconUrl;
            private int secondId;
            private String secondName;

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getSecondIconUrl() {
                return secondIconUrl;
            }

            public void setSecondIconUrl(String secondIconUrl) {
                this.secondIconUrl = secondIconUrl;
            }

            public int getSecondId() {
                return secondId;
            }

            public void setSecondId(int secondId) {
                this.secondId = secondId;
            }

            public String getSecondName() {
                return secondName;
            }

            public void setSecondName(String secondName) {
                this.secondName = secondName;
            }
        }
    }
}
