package com.bjsn909429077.stz.bean;

public class StudyCenterHomeBean {

    public int code;
    public DataBean data;
    public String msg;

    public static class DataBean {
        public Integer answerCount;
        public Integer answerWrongCount;
        public Integer continuityStudyDays;
        public Integer courseDaYiCount;
        public Integer courseNoteCount;
        public Integer mineCourseCount;
        public Integer mineLiveCount;
        public Integer studyCourseLength;
    }
}
