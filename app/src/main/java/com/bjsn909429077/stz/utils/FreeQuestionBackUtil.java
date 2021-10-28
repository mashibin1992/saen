package com.bjsn909429077.stz.utils;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/19 15:06
 **/
public class FreeQuestionBackUtil {

    private static ChooseItem mChooseItem;

    public static void sind(){
        mChooseItem.backMethod();
    }

    public static void setChooseItem(ChooseItem chooseItem) {
        mChooseItem = chooseItem;
    }

    public interface ChooseItem {
        void backMethod();
    }
}
