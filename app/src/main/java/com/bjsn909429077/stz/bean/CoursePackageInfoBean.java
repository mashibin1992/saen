package com.bjsn909429077.stz.bean;

import java.io.Serializable;
import java.util.List;

public class CoursePackageInfoBean implements Serializable {

    public Integer code;
    public DataBean data;
    public String msg;

    public static class DataBean {
        public List<CourseCouponListBean> courseCouponList;
        public String courseName;
        public Integer coursePackageId;
        public List<CoursePackagePriceListBean> coursePackagePriceList;
        public List<CourseScheduleListBean> courseScheduleList;
        public String courseSecondName;
        public String coverPath;
        public String detail;
        public Integer effectiveType;
        public List<String> giveCourseNames;
        public Integer isBuy;
        public Integer isDiscount;
        public Integer isFree;
        public Integer isTextbook;
        public List<String> specialServiceNames;

        public static class CourseCouponListBean {
            public Integer courseCouponId;
            public String courseCouponName;
            public Float effectiveBtime;
            public Integer effectiveDay;
            public Float effectiveEtime;
            public Integer effectiveType;
            public Integer enablePrice;
            public Integer isReceive;
            public Integer price;
        }

        public static class CoursePackagePriceListBean {
            public Integer coursePackagePriceId;
            public Integer discountPrice;
            public Float effectiveEtime;
            public Integer effectiveMonth;
            public Integer price;
        }

        public static class CourseScheduleListBean {
            public List<ChapterListBean> chapterList;
            public Integer courseId;
            public String courseName;

            public static class ChapterListBean {
                public Integer chapterId;
                public String chapterName;
                public List<ClassHourListBean> classHourList;

                public static class ClassHourListBean {
                    public Integer classHourId;
                    public String classHourName;
                    public Integer isAudition;
                    public Integer type;
                    public String videoPath;
                    public String videoTimeLength;
                    public String watchVideoTimeLength;
                }
            }
        }
    }
}
