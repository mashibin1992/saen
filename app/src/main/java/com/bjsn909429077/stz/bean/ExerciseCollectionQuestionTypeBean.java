package com.bjsn909429077.stz.bean;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/8 17:41
 **/
public class ExerciseCollectionQuestionTypeBean {
    private String questionType;
    private boolean checked;
    private int classify;//1：单选；2：多选；3：判断题；4：简答；5：综合

    public ExerciseCollectionQuestionTypeBean(String questionType, boolean checked, int classify) {
        this.questionType = questionType;
        this.checked = checked;
        this.classify = classify;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }
}
