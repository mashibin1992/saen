package com.bjsn909429077.stz.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import java.text.DecimalFormat;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/5 11:56
 **/
public class UnitConversionUtil {

    /**
     * 将dp转换为与之相等的px
     */
    public static int dp2px(Context context, float dipValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将十六进制颜色转换为int类型的数值
     */
    public static int color2int(String colorStr) {
        return Color.parseColor(colorStr);
    }

    /**
     * 屏幕宽
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        return width;
    }

    /**
     * 屏幕高
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        return height;
    }

    /**
     * 秒转换为00:00:00 的时间格式
     *
     * @return
     */
    public static String timeConversion(int time) {
        int hour = 0;
        int minutes = 0;
        int sencond = 0;
        int temp = time % 3600;
        if (time > 3600) {
            hour = time / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    minutes = temp / 60;
                    if (temp % 60 != 0) {
                        sencond = temp % 60;
                    }
                } else {
                    sencond = temp;
                }
            }
        } else {
            minutes = time / 60;
            if (time % 60 != 0) {
                sencond = time % 60;
            }
        }
        return (hour < 10 ? ("0" + hour) : hour) + ":" + (minutes < 10 ? ("0" + minutes) : minutes) + ":" + (sencond < 10 ? ("0" + sencond) : sencond);
    }

    /**
     * CircleProgressView的进度算法
     *
     * @param chuShu
     * @param beiChuShu
     * @return
     */
    public static double circleProgressViewJinDu(String chuShu, String beiChuShu) {
        if (TextUtil.isEmpty(chuShu) || TextUtil.isEmpty(beiChuShu) || "0".equals(chuShu) || "0".equals(beiChuShu))
            return 0;
        DecimalFormat df = new DecimalFormat("0.00");
        int chu = Integer.parseInt(chuShu);
        int bei = Integer.parseInt(beiChuShu);
        double jieGuo = Double.parseDouble(df.format((float) chu / bei));
        return jieGuo;
    }
}
