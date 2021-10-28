package com.bjsn909429077.stz.bean;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/16 14:46
 **/
public class ExerciseAnalysisBean {
    private String option_number;
    private String option_content;
    private boolean isRight;
    private boolean isMyAnswer;

    public ExerciseAnalysisBean(String option_number, String option_content, boolean isRight, boolean isMyAnswer) {
        this.option_number = option_number;
        this.option_content = option_content;
        this.isRight = isRight;
        this.isMyAnswer = isMyAnswer;
    }

    public String getOption_number() {
        return option_number;
    }

    public void setOption_number(String option_number) {
        this.option_number = option_number;
    }

    public String getOption_content() {
        return option_content;
    }

    public void setOption_content(String option_content) {
        this.option_content = option_content;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public boolean isMyAnswer() {
        return isMyAnswer;
    }

    public void setMyAnswer(boolean myAnswer) {
        isMyAnswer = myAnswer;
    }
}
