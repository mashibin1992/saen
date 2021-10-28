package com.bjsn909429077.stz.bean;

public class GuideInfoBean {

    public int code;
    public DataBean data;
    public String msg;

    public static class DataBean {
        public String collectionBtime;
        public String collectionEtime;
        public int collectionType;
        public String examinationBtime;
        public String examinationEtime;
        public int examinationType;
        public String filePaths;
        public int firstTypeId;
        public String fromExaminationDay;
        public String fromExaminationYear;
        public String inquiryBtime;
        public String inquiryEtime;
        public int inquiryType;
        public String printingBtime;
        public String printingEtime;
        public int printingType;
        public String registrationBtime;
        public String registrationEtime;
        public int registrationType;
    }
}
