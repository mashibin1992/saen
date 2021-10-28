package com.jiangjun.library.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator姜军 on 2018/3/9.
 */

public class ToastUtils {

    private static Toast toast;

    public static void Toast(Context mContext, String mag) {
        try {
            synchronized (ToastUtils.class) {

                if (toast != null) {
                    toast.cancel();//注销之前显示的那条信息
                    toast = null;//这里要注意上一步相当于隐藏了信息，mtoast并没有为空，我们强制是他为空
                }
                if (toast == null) {
                    toast = Toast.makeText(mContext, mag, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        } catch (Exception e) {

        }
    }

    public static void Toast(Context mContext, int mag) {
        if (toast == null) {
            toast = Toast.makeText(mContext, mag, Toast.LENGTH_SHORT);
        } else {
            toast.setText(mag);
        }
        toast.show();
    }

    public static void ToastMiddle(Context mContext, String mag) {
        if (toast == null) {
            toast = Toast.makeText(mContext, mag, Toast.LENGTH_SHORT);
        } else {
            toast.setText(mag);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
