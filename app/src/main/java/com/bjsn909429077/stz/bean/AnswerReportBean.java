package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/20 16:27
 **/
public class AnswerReportBean {

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private Object subjectFirstTypeId;
        private String subjectFirstTypeName;
        private Object subjectSecondTypeId;
        private String subjectSecondTypeName;
        private Object subjectChapterId;
        private String subjectChapterName;
        private Object subjectNodeId;
        private String subjectNodeName;
        private String countNumber;
        private String completeNumber;
        private String sumDuration;
        private String accuracy;
        private String totalMaxAccuracy;
        private String totalAveAccuracy;
        private String defeatAccuracy;
        private String rightNumber;
        private String errorNumber;
        private List<ScoreListBean> scoreList;

        public Object getSubjectFirstTypeId() {
            return subjectFirstTypeId;
        }

        public void setSubjectFirstTypeId(Object subjectFirstTypeId) {
            this.subjectFirstTypeId = subjectFirstTypeId;
        }

        public String getSubjectFirstTypeName() {
            return subjectFirstTypeName;
        }

        public void setSubjectFirstTypeName(String subjectFirstTypeName) {
            this.subjectFirstTypeName = subjectFirstTypeName;
        }

        public Object getSubjectSecondTypeId() {
            return subjectSecondTypeId;
        }

        public void setSubjectSecondTypeId(Object subjectSecondTypeId) {
            this.subjectSecondTypeId = subjectSecondTypeId;
        }

        public String getSubjectSecondTypeName() {
            return subjectSecondTypeName;
        }

        public void setSubjectSecondTypeName(String subjectSecondTypeName) {
            this.subjectSecondTypeName = subjectSecondTypeName;
        }

        public Object getSubjectChapterId() {
            return subjectChapterId;
        }

        public void setSubjectChapterId(Object subjectChapterId) {
            this.subjectChapterId = subjectChapterId;
        }

        public String getSubjectChapterName() {
            return subjectChapterName;
        }

        public void setSubjectChapterName(String subjectChapterName) {
            this.subjectChapterName = subjectChapterName;
        }

        public Object getSubjectNodeId() {
            return subjectNodeId;
        }

        public void setSubjectNodeId(Object subjectNodeId) {
            this.subjectNodeId = subjectNodeId;
        }

        public String getSubjectNodeName() {
            return subjectNodeName;
        }

        public void setSubjectNodeName(String subjectNodeName) {
            this.subjectNodeName = subjectNodeName;
        }

        public String getCountNumber() {
            return countNumber;
        }

        public void setCountNumber(String countNumber) {
            this.countNumber = countNumber;
        }

        public String getCompleteNumber() {
            return completeNumber;
        }

        public void setCompleteNumber(String completeNumber) {
            this.completeNumber = completeNumber;
        }

        public String getSumDuration() {
            return sumDuration;
        }

        public void setSumDuration(String sumDuration) {
            this.sumDuration = sumDuration;
        }

        public String getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public String getTotalMaxAccuracy() {
            return totalMaxAccuracy;
        }

        public void setTotalMaxAccuracy(String totalMaxAccuracy) {
            this.totalMaxAccuracy = totalMaxAccuracy;
        }

        public String getTotalAveAccuracy() {
            return totalAveAccuracy;
        }

        public void setTotalAveAccuracy(String totalAveAccuracy) {
            this.totalAveAccuracy = totalAveAccuracy;
        }

        public String getDefeatAccuracy() {
            return defeatAccuracy;
        }

        public void setDefeatAccuracy(String defeatAccuracy) {
            this.defeatAccuracy = defeatAccuracy;
        }

        public String getRightNumber() {
            return rightNumber;
        }

        public void setRightNumber(String rightNumber) {
            this.rightNumber = rightNumber;
        }

        public String getErrorNumber() {
            return errorNumber;
        }

        public void setErrorNumber(String errorNumber) {
            this.errorNumber = errorNumber;
        }

        public List<ScoreListBean> getScoreList() {
            return scoreList;
        }

        public void setScoreList(List<ScoreListBean> scoreList) {
            this.scoreList = scoreList;
        }

        public static class ScoreListBean {
            private double accuracy;

            public double getAccuracy() {
                return accuracy;
            }

            public void setAccuracy(double accuracy) {
                this.accuracy = accuracy;
            }
        }
    }
}
