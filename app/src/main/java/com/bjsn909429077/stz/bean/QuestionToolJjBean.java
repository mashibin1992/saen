package com.bjsn909429077.stz.bean;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/10 18:34
 **/
public class QuestionToolJjBean {
    private String subjectTitle;
    private String subjectScore;//得分

    public QuestionToolJjBean(String subjectTitle, String subjectScore) {
        this.subjectTitle = subjectTitle;
        this.subjectScore = subjectScore;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getSubjectScore() {
        return subjectScore;
    }

    public void setSubjectScore(String subjectScore) {
        this.subjectScore = subjectScore;
    }
}
