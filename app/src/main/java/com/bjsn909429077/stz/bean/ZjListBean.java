package com.bjsn909429077.stz.bean;

import java.util.List;

public class ZjListBean {


    /**
     * code : 0
     * data : [{"answerTime":"","answerUserHeadPic":"","questionName":"","wdId":0}]
     * msg :
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
         * answerTime :
         * answerUserHeadPic :
         * questionName :
         * wdId : 0
         */

        private String answerTime;
        private String answerUserHeadPic;
        private String questionName;
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

        public String getQuestionName() {
            return questionName;
        }

        public void setQuestionName(String questionName) {
            this.questionName = questionName;
        }

        public int getWdId() {
            return wdId;
        }

        public void setWdId(int wdId) {
            this.wdId = wdId;
        }
    }
}
