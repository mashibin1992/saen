package com.bjsn909429077.stz.bean;

import java.util.List;

public class WdZhuiWenBean {


    /**
     * code : 0
     * data : {"answerPics":[],"customerHeadPic":"http://localhost:8080/static/img/logo.png","customerId":13,"customerIsVip":1,"questionDescribe":"减肥好烦好烦发货","questionPics":[],"wdId":15}
     * msg : ok
     */

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
        /**
         * answerPics : []
         * customerHeadPic : http://localhost:8080/static/img/logo.png
         * customerId : 13
         * customerIsVip : 1
         * questionDescribe : 减肥好烦好烦发货
         * questionPics : []
         * wdId : 15
         */

        private List<?> answerPics;
        private String customerHeadPic;
        private int customerId;
        private int customerIsVip;
        private String questionDescribe;
        private List<?> questionPics;
        private int wdId;

        public List<?> getAnswerPics() {
            return answerPics;
        }

        public void setAnswerPics(List<?> answerPics) {
            this.answerPics = answerPics;
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

        public int getCustomerIsVip() {
            return customerIsVip;
        }

        public void setCustomerIsVip(int customerIsVip) {
            this.customerIsVip = customerIsVip;
        }

        public String getQuestionDescribe() {
            return questionDescribe;
        }

        public void setQuestionDescribe(String questionDescribe) {
            this.questionDescribe = questionDescribe;
        }

        public List<?> getQuestionPics() {
            return questionPics;
        }

        public void setQuestionPics(List<?> questionPics) {
            this.questionPics = questionPics;
        }

        public int getWdId() {
            return wdId;
        }

        public void setWdId(int wdId) {
            this.wdId = wdId;
        }
    }
}
