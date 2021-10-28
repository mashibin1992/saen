package com.bjsn909429077.stz.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/24 21:39
 **/
public class lianXiSubjectAnalysisBean {

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

    public static class DataBean implements Serializable {
        private String subjectFirstTypeId;
        private String subjectFirstTypeName;
        private String subjectSecondTypeId;
        private String subjectSecondTypeName;
        private String subjectChapterId;
        private String subjectChapterName;
        private String subjectNodeId;
        private String subjectNodeName;
        private String subjectId;
        private String subjectTitle;
        private String classify;
        private Object answer;
        private String analysisText;
        private String analysisVideo;
        private String isCollect;
        private String isSign;
        private String totalAccuracy;
        private String countNumber;
        private String rightNumber;
        private String errorNumber;
        private String accuracy;
        private List<AnswerListBean> answerList;
        private List<?> subjectList;
        private String knowledgeIds;
        private List<KnowledgeListBean> knowledgeList;
        private String doneAnswerIds;
        private String doneAnswer;

        public String getSubjectFirstTypeId() {
            return subjectFirstTypeId;
        }

        public void setSubjectFirstTypeId(String subjectFirstTypeId) {
            this.subjectFirstTypeId = subjectFirstTypeId;
        }

        public String getSubjectFirstTypeName() {
            return subjectFirstTypeName;
        }

        public void setSubjectFirstTypeName(String subjectFirstTypeName) {
            this.subjectFirstTypeName = subjectFirstTypeName;
        }

        public String getSubjectSecondTypeId() {
            return subjectSecondTypeId;
        }

        public void setSubjectSecondTypeId(String subjectSecondTypeId) {
            this.subjectSecondTypeId = subjectSecondTypeId;
        }

        public String getSubjectSecondTypeName() {
            return subjectSecondTypeName;
        }

        public void setSubjectSecondTypeName(String subjectSecondTypeName) {
            this.subjectSecondTypeName = subjectSecondTypeName;
        }

        public String getSubjectChapterId() {
            return subjectChapterId;
        }

        public void setSubjectChapterId(String subjectChapterId) {
            this.subjectChapterId = subjectChapterId;
        }

        public String getSubjectChapterName() {
            return subjectChapterName;
        }

        public void setSubjectChapterName(String subjectChapterName) {
            this.subjectChapterName = subjectChapterName;
        }

        public String getSubjectNodeId() {
            return subjectNodeId;
        }

        public void setSubjectNodeId(String subjectNodeId) {
            this.subjectNodeId = subjectNodeId;
        }

        public String getSubjectNodeName() {
            return subjectNodeName;
        }

        public void setSubjectNodeName(String subjectNodeName) {
            this.subjectNodeName = subjectNodeName;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getSubjectTitle() {
            return subjectTitle;
        }

        public void setSubjectTitle(String subjectTitle) {
            this.subjectTitle = subjectTitle;
        }

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        public Object getAnswer() {
            return answer;
        }

        public void setAnswer(Object answer) {
            this.answer = answer;
        }

        public String getAnalysisText() {
            return analysisText;
        }

        public void setAnalysisText(String analysisText) {
            this.analysisText = analysisText;
        }

        public String getAnalysisVideo() {
            return analysisVideo;
        }

        public void setAnalysisVideo(String analysisVideo) {
            this.analysisVideo = analysisVideo;
        }

        public String getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(String isCollect) {
            this.isCollect = isCollect;
        }

        public String getIsSign() {
            return isSign;
        }

        public void setIsSign(String isSign) {
            this.isSign = isSign;
        }

        public String getTotalAccuracy() {
            return totalAccuracy;
        }

        public void setTotalAccuracy(String totalAccuracy) {
            this.totalAccuracy = totalAccuracy;
        }

        public String getCountNumber() {
            return countNumber;
        }

        public void setCountNumber(String countNumber) {
            this.countNumber = countNumber;
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

        public String getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public List<AnswerListBean> getAnswerList() {
            return answerList;
        }

        public void setAnswerList(List<AnswerListBean> answerList) {
            this.answerList = answerList;
        }

        public List<?> getSubjectList() {
            return subjectList;
        }

        public void setSubjectList(List<?> subjectList) {
            this.subjectList = subjectList;
        }

        public String getKnowledgeIds() {
            return knowledgeIds;
        }

        public void setKnowledgeIds(String knowledgeIds) {
            this.knowledgeIds = knowledgeIds;
        }

        public List<KnowledgeListBean> getKnowledgeList() {
            return knowledgeList;
        }

        public void setKnowledgeList(List<KnowledgeListBean> knowledgeList) {
            this.knowledgeList = knowledgeList;
        }

        public String getDoneAnswerIds() {
            return doneAnswerIds;
        }

        public void setDoneAnswerIds(String doneAnswerIds) {
            this.doneAnswerIds = doneAnswerIds;
        }

        public String getDoneAnswer() {
            return doneAnswer;
        }

        public void setDoneAnswer(String doneAnswer) {
            this.doneAnswer = doneAnswer;
        }

        public static class AnswerListBean implements Serializable{
            private String subjectId;
            private String answerId;
            private String indexes;
            private String answerName;
            private int type;
            private int isRight;

            public String getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(String subjectId) {
                this.subjectId = subjectId;
            }

            public String getAnswerId() {
                return answerId;
            }

            public void setAnswerId(String answerId) {
                this.answerId = answerId;
            }

            public String getIndexes() {
                return indexes;
            }

            public void setIndexes(String indexes) {
                this.indexes = indexes;
            }

            public String getAnswerName() {
                return answerName;
            }

            public void setAnswerName(String answerName) {
                this.answerName = answerName;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getIsRight() {
                return isRight;
            }

            public void setIsRight(int isRight) {
                this.isRight = isRight;
            }
        }

        public static class KnowledgeListBean implements Serializable{
            private String knowledgeId;
            private String knowledgeName;

            public String getKnowledgeId() {
                return knowledgeId;
            }

            public void setKnowledgeId(String knowledgeId) {
                this.knowledgeId = knowledgeId;
            }

            public String getKnowledgeName() {
                return knowledgeName;
            }

            public void setKnowledgeName(String knowledgeName) {
                this.knowledgeName = knowledgeName;
            }
        }
    }
}
