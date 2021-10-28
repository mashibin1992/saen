package com.bjsn909429077.stz.bean;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/16 15:08
 **/
public class ExerciseAnalysisJXBean {
    private String jxType;
    private String jxContent;

    public ExerciseAnalysisJXBean(String jxType, String jxContent) {
        this.jxType = jxType;
        this.jxContent = jxContent;
    }

    public String getJxType() {
        return jxType;
    }

    public void setJxType(String jxType) {
        this.jxType = jxType;
    }

    public String getJxContent() {
        return jxContent;
    }

    public void setJxContent(String jxContent) {
        this.jxContent = jxContent;
    }
}
