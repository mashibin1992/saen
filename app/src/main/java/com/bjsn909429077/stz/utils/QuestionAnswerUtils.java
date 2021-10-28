package com.bjsn909429077.stz.utils;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/7 14:15
 **/
public class QuestionAnswerUtils {
    private QuestionAnswerUtils() {//表示不能被实例化
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static String[] answers={"A","B","C","D","E","F","G","H","I","J"};

    public static String getAnswerStr(int select){
        if(select<answers.length){
            return answers[select]+"：";
        }
        return "";
    }
}
