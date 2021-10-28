package com.bjsn909429077.stz.bean;

import java.util.List;

public class CoursePackageBean {

    public int code;
    public List<DataDTO> data;
    public String msg;

    public static class DataDTO {
        public String courseName;
        public int coursePackageId;
        public String courseSecondName;
        public String coverPath;
        public int discountPrice;
        public int isBuy;
        public int isDiscount;
        public int isFree;
        public int leftTime;
        public int price;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "courseName='" + courseName + '\'' +
                    ", coursePackageId=" + coursePackageId +
                    ", courseSecondName='" + courseSecondName + '\'' +
                    ", coverPath='" + coverPath + '\'' +
                    ", discountPrice=" + discountPrice +
                    ", isBuy=" + isBuy +
                    ", isDiscount=" + isDiscount +
                    ", isFree=" + isFree +
                    ", leftTime=" + leftTime +
                    ", price=" + price +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CoursePackageBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
