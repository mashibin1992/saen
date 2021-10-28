package com.jiangjun.library.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creator JiangJun
 * Created by Administrator on 2019/1/14.
 * Describe
 */

public class TimeUtil {
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy");
        }
    };


    public final static String date="yyyy-MM-dd HH:mm:ss";
    public final static String date1="yyyy-MM-dd HH:mm";
    public final static String date2="yyyy-MM-dd";
    public final static String date3="HH:mm";
    public final static String date4="HH";
    public final static String date5="yyyy年MM月dd日 HH:mm";
    public final static String date6="yyyy-MM";

    public static String getStringMMdd(String time) {
        if (StringUtil.isBlank(time)) {
            return null;
        }
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        long l = Long.valueOf(time);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    /**
     * 判断给定字符串时间是否为今日(效率不是很高，不过也是一种方法)
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        String timeDate = getTimeStringType(sdate, "yyyy-MM-dd");
        String nowDate = getTimeStringType(getTimeStame(), "yyyy-MM-dd");

        if (nowDate.equals(timeDate)) {
            b = true;
        }
        return b;

    }


    /**
     * 判断给定字符串时间是否为今年(效率不是很高，不过也是一种方法)
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isYears(String sdate) {
        boolean b = false;
        String timeDate = getTimeStringType(sdate, "yyyy");
        String nowDate = getTimeStringType(getTimeStame(), "yyyy");

        if (nowDate.equals(timeDate)) {
            b = true;
        }
        return b;

    }

    public static String getTimeStringType(String time, String type) {
        if (StringUtil.isBlank(time)) {
            return null;
        }
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        long l = Long.valueOf(time);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }

    //    获取当前时间的时间戳
    public static String getTimeStame() {
        long current_time = System.currentTimeMillis();
        String time_stamp = String.valueOf(current_time);
        return time_stamp;
    }

    /**
     * 时分
     *
     * @param time
     * @return
     */
    public static String getWhenPoints(long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
        return sd.format(date);
    }

    public static String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sd.format(date);
    }

    public static String getStringMMddHHmm(String time) {
        if (StringUtil.isBlank(time)) {
            return null;
        }
        String timeString = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        long l = Long.valueOf(time);
        timeString = sdf.format(new Date(l));//单位秒
        return timeString;
    }
}
