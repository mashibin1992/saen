package com.bjsn909429077.stz.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:练习类的bean
 * @Date :2021/9/19 16:13
 **/
public class SubjectQuestionBean {
    /**
     * 单项选择题
     */
    public static final String TYPE_Single_Choice = "1";
    /**
     * 多项选择题
     */
    public static final String TYPE_Multiple_Choice = "2";
    /**
     * 判断题
     */
    public static final String TYPE_True_OR_False = "3";
    /**
     * 简答题
     */
    public static final String TYPE_JIAN_DA = "4";
    /**
     * 综合题
     */
    public static final String TYPE_ZONG_HE = "5";

    public static String getQuestionTypeStr(String type) {
        switch (type) {
            case TYPE_Single_Choice:
                return "单选题";
            case TYPE_True_OR_False:
                return "判断题";
            case TYPE_Multiple_Choice:
                return "多选题";
            case TYPE_JIAN_DA:
                return "简答题";
            case TYPE_ZONG_HE:
                return "综合题";
        }
        return "单选题";
    }

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

    public static class DataBean implements Serializable {
        private String timeLength;
        private Object finallyAnswerId;
        private List<PaperChapterSubjectListBean> paperChapterSubjectList;
        private String countNumber;
        private String completeNumber;

        public String getTimeLength() {
            return timeLength;
        }

        public void setTimeLength(String timeLength) {
            this.timeLength = timeLength;
        }

        public Object getFinallyAnswerId() {
            return finallyAnswerId;
        }

        public void setFinallyAnswerId(Object finallyAnswerId) {
            this.finallyAnswerId = finallyAnswerId;
        }

        public List<PaperChapterSubjectListBean> getPaperChapterSubjectList() {
            return paperChapterSubjectList;
        }

        public void setPaperChapterSubjectList(List<PaperChapterSubjectListBean> paperChapterSubjectList) {
            this.paperChapterSubjectList = paperChapterSubjectList;
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

        public static class PaperChapterSubjectListBean implements Serializable {
            private String subjectId;
            private String subjectTitle;
            private String subjectTitlePic;
            private String classify;
            private Object answer;
            private String analysisText;
            private String analysisVideo;
            private String isCollect;
            private List<AnswerListBean> answerList;
            private List<SubjectListBean> subjectList;
            private String knowledgeIds;
            private List<KnowledgeListBean> knowledgeList;
            private String doneAnswerIds;
            private String doneAnswer;
            private String isSign;//是否标记过这个题目,是否标记 1.是 2.否
            private int question_select;//是否做过这个题目
            private int thisQuestionUseTime;//回答这个题目所用时间
            private boolean isClickJX;//是否点击了解析,用于答题卡页面

            public boolean isClickJX() {
                return isClickJX;
            }

            public void setClickJX(boolean clickJX) {
                isClickJX = clickJX;
            }

            public List<SubjectListBean> getSubjectList() {
                return subjectList;
            }

            public void setSubjectList(List<SubjectListBean> subjectList) {
                this.subjectList = subjectList;
            }

            public static class SubjectListBean implements Serializable{
                private String subjectId;
                private String subjectTitle;
                private String subjectTitlePic;
                private String classify;
                private Object answer;
                private String analysisText;
                private String analysisVideo;
                private String isCollect;
                private Object isSign;
                private List<AnswerListBean> answerList;
                private Object subjectList;
                private String knowledgeIds;
                private List<KnowledgeListBean> knowledgeList;
                private String doneAnswerIds;
                private String doneAnswer;
                private int question_select;//是否做过这个题目
                private int thisQuestionUseTime;//回答这个题目所用时间

                public void setDoneAnswerIds(String doneAnswerIds) {
                    this.doneAnswerIds = doneAnswerIds;
                }

                public void setDoneAnswer(String doneAnswer) {
                    this.doneAnswer = doneAnswer;
                }

                public int getQuestion_select() {
                    return question_select;
                }

                public void setQuestion_select(int question_select) {
                    this.question_select = question_select;
                }

                public int getThisQuestionUseTime() {
                    return thisQuestionUseTime;
                }

                public void setThisQuestionUseTime(int thisQuestionUseTime) {
                    this.thisQuestionUseTime = thisQuestionUseTime;
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

                public String getSubjectTitlePic() {
                    return subjectTitlePic;
                }

                public void setSubjectTitlePic(String subjectTitlePic) {
                    this.subjectTitlePic = subjectTitlePic;
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

                public Object getIsSign() {
                    return isSign;
                }

                public void setIsSign(Object isSign) {
                    this.isSign = isSign;
                }

                public List<AnswerListBean> getAnswerList() {
                    return answerList;
                }

                public void setAnswerList(List<AnswerListBean> answerList) {
                    this.answerList = answerList;
                }

                public Object getSubjectList() {
                    return subjectList;
                }

                public void setSubjectList(Object subjectList) {
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

                public String getDoneAnswer() {
                    return doneAnswer;
                }


                public static class AnswerListBean implements Serializable{
                    private String subjectId;
                    private String answerId;
                    private String indexes;
                    private String answerName;
                    private int type;
                    private int isRight;
                    private boolean myselfIsChoose;//我是否选中该选项
                    private boolean isClickJX;//是否点击了解析

                    public boolean isClickJX() {
                        return isClickJX;
                    }

                    public void setClickJX(boolean clickJX) {
                        isClickJX = clickJX;
                    }

                    public String getSubjectId() {
                        return subjectId;
                    }

                    public boolean isMyselfIsChoose() {
                        return myselfIsChoose;
                    }

                    public void setMyselfIsChoose(boolean myselfIsChoose) {
                        this.myselfIsChoose = myselfIsChoose;
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

                public static class KnowledgeListBean implements Serializable {
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

            public String getIsSign() {
                return isSign;
            }

            public void setIsSign(String isSign) {
                this.isSign = isSign;
            }

            public int getThisQuestionUseTime() {
                return thisQuestionUseTime;
            }

            public void setThisQuestionUseTime(int thisQuestionUseTime) {
                this.thisQuestionUseTime = thisQuestionUseTime;
            }

            public int getQuestion_select() {
                return question_select;
            }

            public void setQuestion_select(int question_select) {
                this.question_select = question_select;
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

            public String getSubjectTitlePic() {
                return subjectTitlePic;
            }

            public void setSubjectTitlePic(String subjectTitlePic) {
                this.subjectTitlePic = subjectTitlePic;
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

            public List<AnswerListBean> getAnswerList() {
                return answerList;
            }

            public void setAnswerList(List<AnswerListBean> answerList) {
                this.answerList = answerList;
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

            public static class AnswerListBean implements Serializable {
                private String subjectId;
                private String answerId;
                private String indexes;
                private String answerName;
                private String answerPic;
                private int type;
                private int isRight;
                private boolean myselfIsChoose;//我是否选中该选项
                private boolean isClickJX;//是否点击了解析

                public boolean isClickJX() {
                    return isClickJX;
                }

                public void setClickJX(boolean clickJX) {
                    isClickJX = clickJX;
                }

                public boolean isMyselfIsChoose() {
                    return myselfIsChoose;
                }

                public void setMyselfIsChoose(boolean myselfIsChoose) {
                    this.myselfIsChoose = myselfIsChoose;
                }

                public String getAnswerPic() {
                    return answerPic;
                }

                public void setAnswerPic(String answerPic) {
                    this.answerPic = answerPic;
                }

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

            public static class KnowledgeListBean implements Serializable {
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
}
