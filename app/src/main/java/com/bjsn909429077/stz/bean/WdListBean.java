package com.bjsn909429077.stz.bean;

import java.util.List;

public class WdListBean {


    /**
     * code : 0
     * data : [{"answerTime":"2021-09-07","answerUserHeadPic":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png","answerUserId":12,"answerUserNickName":"韩勇","customerHeadPic":"http://localhost:8080/static/img/logo.png","customerId":14,"questionName":"九月份戴尔G15值得购买吗","questionPics":[],"readCount":0,"wdId":6}]
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
         * answerTime : 2021-09-07
         * answerUserHeadPic : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png
         * answerUserId : 12
         * answerUserNickName : 韩勇
         * customerHeadPic : http://localhost:8080/static/img/logo.png
         * customerId : 14
         * questionName : 九月份戴尔G15值得购买吗
         * questionPics : []
         * readCount : 0
         * wdId : 6
         */

        private String answerTime;
        private String answerUserHeadPic;
        private int answerUserId;
        private String answerUserNickName;
        private String customerHeadPic;
        private int customerId;
        private String questionName;
        private List<?> questionPics;
        private int readCount;
        private int wdId;

        public String getAnswerTime() {
            return answerTime;
        }

        public void setAnswerTime(String answerTime) {
            this.answerTime = answerTime;
        }

        public String getAnswerUserHeadPic() {
            return answerUserHeadPic;
        }

        public void setAnswerUserHeadPic(String answerUserHeadPic) {
            this.answerUserHeadPic = answerUserHeadPic;
        }

        public int getAnswerUserId() {
            return answerUserId;
        }

        public void setAnswerUserId(int answerUserId) {
            this.answerUserId = answerUserId;
        }

        public String getAnswerUserNickName() {
            return answerUserNickName;
        }

        public void setAnswerUserNickName(String answerUserNickName) {
            this.answerUserNickName = answerUserNickName;
        }

        public String getCustomerHeadPic() {
            return customerHeadPic;
        }

        public void setCustomerHeadPic(String customerHeadPic) {
            this.customerHeadPic = customerHeadPic;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getQuestionName() {
            return questionName;
        }

        public void setQuestionName(String questionName) {
            this.questionName = questionName;
        }

        public List<?> getQuestionPics() {
            return questionPics;
        }

        public void setQuestionPics(List<?> questionPics) {
            this.questionPics = questionPics;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public int getWdId() {
            return wdId;
        }

        public void setWdId(int wdId) {
            this.wdId = wdId;
        }
    }
}
