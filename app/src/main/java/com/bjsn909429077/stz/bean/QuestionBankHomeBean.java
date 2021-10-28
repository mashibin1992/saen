package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/17 18:32
 **/
public class QuestionBankHomeBean {
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
        private String accuracy;
        private String answerNumber;
        private String countNumber;
        private String errorAnswerNumber;
        private String firstId;
        private String firstName;
        private List<RankingListBean> rankingList;
        private String secondId;
        private String secondName;
        private String sumDuration;

        public String getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public String getAnswerNumber() {
            return answerNumber;
        }

        public void setAnswerNumber(String answerNumber) {
            this.answerNumber = answerNumber;
        }

        public String getCountNumber() {
            return countNumber;
        }

        public void setCountNumber(String countNumber) {
            this.countNumber = countNumber;
        }

        public String getErrorAnswerNumber() {
            return errorAnswerNumber;
        }

        public void setErrorAnswerNumber(String errorAnswerNumber) {
            this.errorAnswerNumber = errorAnswerNumber;
        }

        public String getFirstId() {
            return firstId;
        }

        public void setFirstId(String firstId) {
            this.firstId = firstId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public List<RankingListBean> getRankingList() {
            return rankingList;
        }

        public void setRankingList(List<RankingListBean> rankingList) {
            this.rankingList = rankingList;
        }

        public String getSecondId() {
            return secondId;
        }

        public void setSecondId(String secondId) {
            this.secondId = secondId;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        public String getSumDuration() {
            return sumDuration;
        }

        public void setSumDuration(String sumDuration) {
            this.sumDuration = sumDuration;
        }

        public static class RankingListBean {
            private String questionsNumber;
            private String userHead;
            private String userId;
            private String userName;

            public String getQuestionsNumber() {
                return questionsNumber;
            }

            public void setQuestionsNumber(String questionsNumber) {
                this.questionsNumber = questionsNumber;
            }

            public String getUserHead() {
                return userHead;
            }

            public void setUserHead(String userHead) {
                this.userHead = userHead;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
