package com.bjsn909429077.stz.bean;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/9 18:32
 **/
public class AnswerCardSecondBean extends BaseNode {
    private boolean zuoDa;//是否做过的标记
    private String title;
    private String sign;
    private String subjectId;//题目ID

    public AnswerCardSecondBean(boolean zuoDa, String title, String sign,String subjectId) {
        this.zuoDa = zuoDa;
        this.title = title;
        this.sign = sign;
        this.subjectId = subjectId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isZuoDa() {
        return zuoDa;
    }

    public void setZuoDa(boolean zuoDa) {
        this.zuoDa = zuoDa;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
