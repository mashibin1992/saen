package com.jiangjun.library.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator姜军 on 2018/6/21.
 * 关闭软件盘
 */

public class LoginCheck {
    private static String check = null;

    private int textsj = 60;
    public static boolean sj = true;
    private static LoginCheck instance;
    private Runnable runnable;

    public static LoginCheck getInstance() {
        if (instance == null) {
            synchronized (LoginCheck.class) {
                if (instance == null) {
                    instance = new LoginCheck();
                }
            }
        }
        return instance;
    }


    //关闭软键盘
    public void getInputMethodManager(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //显示软键盘
        //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        //如果上面的代码没有弹出软键盘 可以使用下面另一种方式
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }
}
