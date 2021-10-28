package com.bjsn909429077.stz.bean;

import java.util.List;

public class SearchWenDaBean {


    /**
     * code : 0
     * msg : ok
     * data : [{"wdId":null,"customerId":14,"customerHeadPic":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/DXNNy4MstQ1632639998138.png","answerUserId":12,"answerUserHeadPic":"http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png","answerUserNickName":"韩勇","readCount":0,"answerTime":"2021-09-07","questionName":"九月份戴尔G15值得购买吗","questionPics":[]}]
     */

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
        /**
         * wdId : null
         * customerId : 14
         * customerHeadPic : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/DXNNy4MstQ1632639998138.png
         * answerUserId : 12
         * answerUserHeadPic : http://xiaoxiong-yrj-public.oss-cn-beijing.aliyuncs.com/web/3kHahBYZfA1632405926208.png
         * answerUserNickName : 韩勇
         * readCount : 0
         * answerTime : 2021-09-07
         * questionName : 九月份戴尔G15值得购买吗
         * questionPics : []
         */

        private String wdId;
        private int customerId;
        private String customerHeadPic;
        private int answerUserId;
        private String answerUserHeadPic;
        private String answerUserNickName;
        private int readCount;
        private String answerTime;
        private String questionName;
        private List<String> questionPics;

        public String getWdId() {
            return wdId;
        }

        public void setWdId(String wdId) {
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

        public int getAnswerUserId() {
            return answerUserId;
        }

        public void setAnswerUserId(int answerUserId) {
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
