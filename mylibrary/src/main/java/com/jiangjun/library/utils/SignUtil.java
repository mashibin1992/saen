package com.jiangjun.library.utils;

/**
 * Created by Administrator姜军 on 2018/6/4.
 * <p>
 * 类的用途:
 *
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 * <p>
 * 类的用途:
 * @name 姜军
 * @time 2017/9/16 12:44
 */


/**
 * 类的用途:
 *
 * @name 姜军
 * @time 2017/9/16 12:44
 */

import android.util.Base64;

import com.jiangjun.library.user.UserConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class SignUtil {
    public static String signature = "385CD0C8-4FBC-4BDD-9B62-F6382170A779";

    public static String TAG = "SignUtil";

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @return 签名
     */

    /**
     * 生成签名
     *
     * @return 签名
     */

    public static String generateSignature(Map<String, Object> hashmp, String timeStame) {
        StringBuffer sign = new StringBuffer();

        sign.append(Base64.encodeToString(timeStame.getBytes(), Base64.NO_WRAP));

        //添加当前时间
        if (UserConfig.isLogin()) {
            sign.append(UserConfig.getUser().getToken());
        }

        sign.append(signature);


        if (hashmp.size() > 0) {
            List<String> list = new ArrayList<>();
            Set set = hashmp.keySet();
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                list.add(key.concat(hashmp.get(key).toString()));
            }

            Collections.sort(list);


            for (String s : list) {
                sign.append(s);
            }

            RLog.e(TAG, sign.toString());
            RLog.e(TAG, "sign=" + Md5Util.strToMd5(sign.toString()));

            return Md5Util.strToMd5(sign.toString());
        }
        RLog.e(TAG, sign.toString());
        RLog.e(TAG, "sign=" + Md5Util.strToMd5(sign.toString()));
        return Md5Util.strToMd5(sign.toString());

    }

    /**
     * @return log
     */
    public static String parseToLog(Map<String, Object> hashmp) {
        if (hashmp == null) {
            return "";
        }
        StringBuffer sign = new StringBuffer();
        if (hashmp.size() > 0) {

            Set set = hashmp.keySet();
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                sign.append("\n");
                sign.append(key).append("=").append(hashmp.get(key).toString());
            }

            return sign.toString();
        }
        return sign.toString();
    }


    /**
     * 获取当前时间戳，单位秒
     * @return

     */

    public static long getCurrentTimestamp() {

        return System.currentTimeMillis() / 1000;

    }

    /**
     * 获取当前时间戳，单位毫秒
     * @return

     */

    public static long getCurrentTimestampMs() {

        return System.currentTimeMillis();

    }

    /**
     * 生成 uuid， 即用来标识一笔单，也用做 nonce_str
     * @return

     */

    public static String generateUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);

    }


}