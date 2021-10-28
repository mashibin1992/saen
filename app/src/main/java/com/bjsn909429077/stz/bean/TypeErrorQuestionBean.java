package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/15 17:43
 **/
public class TypeErrorQuestionBean {

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
        private String completeNumber;
        private String countNumber;
        private String errorNumber;
        private int testPaperId;
        private String testPaperName;

        public String getCompleteNumber() {
            return completeNumber;
        }

        public void setCompleteNumber(String completeNumber) {
            this.completeNumber = completeNumber;
        }

        public String getCountNumber() {
            return countNumber;
        }

        public void setCountNumber(String countNumber) {
            this.countNumber = countNumber;
        }

        public String getErrorNumber() {
            return errorNumber;
        }

        public void setErrorNumber(String errorNumber) {
            this.errorNumber = errorNumber;
        }

        public int getTestPaperId() {
            return testPaperId;
        }

        public void setTestPaperId(int testPaperId) {
            this.testPaperId = testPaperId;
        }

        public String getTestPaperName() {
            return testPaperName;
        }

        public void setTestPaperName(String testPaperName) {
            this.testPaperName = testPaperName;
        }
    }
}
