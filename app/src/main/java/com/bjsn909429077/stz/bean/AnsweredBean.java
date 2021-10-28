package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/22 21:49
 **/
public class AnsweredBean {

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int wdId;
        private int customerId;
        private String customerHeadPic;
        private String answerUserId;
        private String answerUserHeadPic;
        private String answerUserNickName;
        private int readCount;
        private String answerTime;
        private String questionName;
        private List<String> questionPics;

        public int getWdId() {
            return wdId;
        }

        public void setWdId(int wdId) {
            this.wdId = wdId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getCustomerHeadPic() {
            return customerHeadPic;
        }

        public void setCustomerHeadPic(String customerHeadPic) {
            this.customerHeadPic = customerHeadPic;
        }

        public String getAnswerUserId() {
            return answerUserId;
        }

        public void setAnswerUserId(String answerUserId) {
            this.answerUserId = answerUserId;
        }

        public String getAnswerUserHeadPic() {
            return answerUserHeadPic;
        }

        public void setAnswerUserHeadPic(String answerUserHeadPic) {
            this.answerUserHeadPic = answerUserHeadPic;
        }

        public String getAnswerUserNickName() {
            return answerUserNickName;
        }

        public void setAnswerUserNickName(String answerUserNickName) {
            this.answerUserNickName = answerUserNickName;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public String getAnswerTime() {
            return answerTime;
        }

        public void setAnswerTime(String answerTime) {
            this.answerTime = answerTime;
        }

        public String getQuestionName() {
            return questionName;
        }

        public void setQuestionName(String questionName) {
            this.questionName = questionName;
        }

        public List<String> getQuestionPics() {
            return questionPics;
        }

        public void setQuestionPics(List<String> questionPics) {
            this.questionPics = questionPics;
        }
    }
}
