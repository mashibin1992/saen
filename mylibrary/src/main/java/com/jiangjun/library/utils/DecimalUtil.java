package com.jiangjun.library.utils;

import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;

/**
 * Created by Administrator姜军 on 2018/6/19.
 */

public class DecimalUtil {

    /**
     * 校验输入的数是否是只保留小数点后两位
     *
     * @param s
     * @return true 表示小数点已经超过了2位数
     */
    public static int decimalFormat(CharSequence s) {

        String str = s.toString();
        if (!StringUtil.isEmpty(str)) {
            int index = str.indexOf('.');
            if (index > 0) {
                if(str.substring(index).length() > 3){
                    return 1;
                } else {
                    return -1;
                }
            } else if(index == 0){
                return 0;
            }
        }
        return -1;
    }

    /**
     * 设置EditText 光标位置到最后
     * @param et
     */
    public static void setEditTextSelection(EditText et) {
        CharSequence text = et.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

}
