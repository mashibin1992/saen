package com.bjsn909429077.stz.bean;

import java.util.List;

public class WdInfoBean {


    /**
     * code : 0
     * data : {"isAnswer":1,"isCollect":0,"readCount":0,"wdQuestionAnswerDetailList":[{"answerContent":"不值得购买，现在游戏本溢价太厉害，等待降价再购买","answerPics":[],"answerTime":"2021-09-07 07:57:59","answerUserHeadPic":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png","answerUserId":12,"answerUserNickName":"韩勇","customerHeadPic":"http://localhost:8080/static/img/logo.png","customerId":14,"customerIsVip":1,"customerNickName":"挣脱怨与恨","questionDescribe":"戴尔G15性能怪兽","questionName":"九月份戴尔G15值得购买吗","questionPics":[],"releaseTime":"2021-09-07 07:57:56","wdId":6}]}
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
         * isAnswer : 1
         * isCollect : 0
         * readCount : 0
         * wdQuestionAnswerDetailList : [{"answerContent":"不值得购买，现在游戏本溢价太厉害，等待降价再购买","answerPics":[],"answerTime":"2021-09-07 07:57:59","answerUserHeadPic":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png","answerUserId":12,"answerUserNickName":"韩勇","customerHeadPic":"http://localhost:8080/static/img/logo.png","customerId":14,"customerIsVip":1,"customerNickName":"挣脱怨与恨","questionDescribe":"戴尔G15性能怪兽","questionName":"九月份戴尔G15值得购买吗","questionPics":[],"releaseTime":"2021-09-07 07:57:56","wdId":6}]
         */

        private int isAnswer;
        private int isCollect;
        private int readCount;
        private List<WdQuestionAnswerDetailListBean> wdQuestionAnswerDetailList;

        public int getIsAnswer() {
            return isAnswer;
        }

        public void setIsAnswer(int isAnswer) {
            this.isAnswer = isAnswer;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public List<WdQuestionAnswerDetailListBean> getWdQuestionAnswerDetailList() {
            return wdQuestionAnswerDetailList;
        }

        public void setWdQuestionAnswerDetailList(List<WdQuestionAnswerDetailListBean> wdQuestionAnswerDetailList) {
            this.wdQuestionAnswerDetailList = wdQuestionAnswerDetailList;
        }

        public static class WdQuestionAnswerDetailListBean {
            /**
             * answerContent : 不值得购买，现在游戏本溢价太厉害，等待降价再购买
             * answerPics : []
             * answerTime : 2021-09-07 07:57:59
             * answerUserHeadPic : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png
             * answerUserId : 12
             * answerUserNickName : 韩勇
             * customerHeadPic : http://localhost:8080/static/img/logo.png
             * customerId : 14
             * customerIsVip : 1
             * customerNickName : 挣脱怨与恨
             * questionDescribe : 戴尔G15性能怪兽
             * questionName : 九月份戴尔G15值得购买吗
             * questionPics : []
             * releaseTime : 2021-09-07 07:57:56
             * wdId : 6
             */

            private String answerContent;
            private List<String> answerPics;
            private String answerTime;
            private String answerUserHeadPic;
            private int answerUserId;
            private String answerUserNickName;
            private String customerHeadPic;
            private int customerId;
            private int customerIsVip;
            private String customerNickName;
            private String questionDescribe;
            private String questionName;
            private List<String> questionPics;
            private String releaseTime;
            private int wdId;
            private int isAsk;

            public int getIsAsk() {
                return isAsk;
            }

            public void setIsAsk(int isAsk) {
                this.isAsk = isAsk;
            }

            public String getAnswerContent() {
                return answerContent;
            }

            public void setAnswerContent(String answerContent) {
                this.answerContent = answerContent;
            }

            public List<?> getAnswerPics() {
                return answerPics;
            }

            public void setAnswerPics(List<String> answerPics) {
                this.answerPics = answerPics;
            }

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

            public int getCustomerIsVip() {
                return customerIsVip;
            }

            public void setCustomerIsVip(int customerIsVip) {
                this.customerIsVip = customerIsVip;
            }

            public String getCustomerNickName() {
                return customerNickName;
            }

            public void setCustomerNickName(String customerNickName) {
                this.customerNickName = customerNickName;
            }

            public String getQuestionDescribe() {
                return questionDescribe;
            }

            public void setQuestionDescribe(String questionDescribe) {
                this.questionDescribe = questionDescribe;
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

            public void setQuestionPics(List<String> questionPics) {
                this.questionPics = questionPics;
            }

            public String getReleaseTime() {
                return releaseTime;
            }

            public void setReleaseTime(String releaseTime) {
                this.releaseTime = releaseTime;
            }

            public int getWdId() {
                return wdId;
            }

            public void setWdId(int wdId) {
                this.wdId = wdId;
            }
        }
    }
}
