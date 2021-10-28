package com.bjsn909429077.stz.bean;

import java.util.List;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/8 16:20
 **/
public class ExerciseCollectionBean {
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
        private String classify;
        private String subjectId;
        private String subjectTitle;
        private boolean cancelExerciseCollection;//是否选中取消习题收藏
        private boolean editState;//编辑状态，true：编辑

        public boolean isCancelExerciseCollection() {
            return cancelExerciseCollection;
        }

        public void setCancelExerciseCollection(boolean cancelExerciseCollection) {
            this.cancelExerciseCollection = cancelExerciseCollection;
        }

        public boolean isEditState() {
            return editState;
        }

        public void setEditState(boolean editState) {
            this.editState = editState;
        }

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
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
    }

}
