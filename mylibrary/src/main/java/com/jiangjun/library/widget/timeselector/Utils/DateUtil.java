package com.jiangjun.library.widget.timeselector.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuli on 2015/11/27.
 */
public class DateUtil {


    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */

    public static Date parse(String strDate, String pattern) {

        if (TextUtil.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }
    /**
     * yyyy年MM月dd日
     *
     */

    public static String formatYyyyMmDd(Float time) {
        String returnValue = "";
        if (time != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
            returnValue = df.format(time);
        }
        return (returnValue);
    }
    /**
     * yyyy年MM月dd日
     *
     */

    public static String formatHHMMSS(Float time) {
        String returnValue = "";
        if (time != null) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            returnValue = df.format(time);
        }
        return (returnValue);
    }
}
