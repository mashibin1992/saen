package com.jiangjun.library.api;

import android.content.Context;
import android.util.ArrayMap;

import com.blankj.utilcode.util.AppUtils;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.SignUtil;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.SystemUtil;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by Administrator姜军 on 2018/3/12.
 * 把这个也改了 InsNovate
 */

public class NovateUtils<T> {


    public static String baseUrl = "http://saen.yaruijie.com/";//正式
//    public static String baseUrl = "http://139.9.141.68:8082/";//外网IP
//    public static String baseUrl = "http://192.168.1.23:8082/";//胡本地
//    public static String baseUrl = "http://192.168.1.35:8082/";//柳本地

    /**
     * true=生产
     * false=开发
     */
    public static boolean isEnvironment = true;
//    public static boolean isEnvironment = false;

    private static NovateUtils instance;
    private Novate novate;

    public static NovateUtils getInstance() {

        if (instance == null) {
            synchronized (NovateUtils.class) {
                if (instance == null) {
                    instance = new NovateUtils();
                }
            }
        }
        return instance;
    }

    public Novate getNovate(Context context) {
        novate = new Novate.Builder(context)
                .readTimeout(300)  //连接时间 可以忽略
                .connectTimeout(300)
                .writeTimeout(300)
                .baseUrl(baseUrl) //base URL
                .addLog(true) //是否开启log
                .tag(baseUrl)
                .build();

        return novate;
    }

    public void Post(final Context context, final String url, Map<String, Object> map, boolean isShow, final setRequestReturn requestReturn) {

        novate = new Novate.Builder(context)
                .addHeader(getHeader(map))
                .readTimeout(30)  //连接时间 可以忽略
                .baseUrl(baseUrl) //base URL
                .addLog(true) //是否开启log
                .tag(url)
                .build();
        if (map == null) {
            map = new ArrayMap<>();
        }


        //打印log
        RLog.e("NovateUtils", "Post:url=" + url  + SignUtil.parseToLog(map));
        novate.post(url, map, new MyBaseSubscriber<T>(context, url, isShow, requestReturn));
    }

    private HashMap getHeader(Map<String, Object> map) {
        HashMap<String, Object> header = new HashMap<>();

        String timeStame = StringUtil.getTimeStame();
        header.put("deviceId", "1");
        header.put("appVersion", AppUtils.getAppVersionName());
        header.put("timestamp", timeStame);
        header.put("deviceModel", SystemUtil.getSystemModel());
        header.put("osName", SystemUtil.getSystemVersion());
        if (UserConfig.isLogin()) {
            header.put("token", UserConfig.getUser().getToken());
        }

        header.put("signature", SignUtil.generateSignature(map, timeStame));

        return header;
    }

    public void get(final Context context, final String url, Map<String, Object> map, boolean isShow, final setRequestReturn requestReturn) {

        novate = new Novate.Builder(context)
                .addHeader(getHeader(map))
                .readTimeout(30)  //连接时间 可以忽略
                .baseUrl(baseUrl) //base URL
                .addLog(true) //是否开启log
                .tag(url)
                .build();
        if (map == null) {
            map = new ArrayMap<>();
        }
        RLog.e("NovateUtils", "Post:url=" + url);
        novate.get(url, map, new MyBaseSubscriber<T>(context, url, isShow, requestReturn));
    }

    public void get2(final Context context, final String url, Map<String, Object> map, boolean isShow, final setResponseBody requestReturn) {

        novate = new Novate.Builder(context)
                .addHeader(getHeader(map))
                .readTimeout(30)  //连接时间 可以忽略
                .baseUrl(baseUrl) //base URL
                .addLog(true) //是否开启log
                .tag(url)
                .build();
        if (map == null) {
            map = new ArrayMap<>();
        }
        RLog.e("NovateUtils", "Post:url=" + url);
        novate.get(url, map, new MyBaseSubscriber2<T>(context, url, isShow, requestReturn));
    }


    public interface setRequestReturn<T> {
        void onError(Throwable throwable);

        void onSuccee(T response);
    }
    public interface setResponseBody<String> {
        void onError(Throwable throwable);

        void onSuccee(String response) throws IOException;
    }

    public static MultipartBody.Part getPart(String name, File file) {
        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData(name, "icon.png", photoRequestBody);
        return photo;
    }

    public static Map<String, RequestBody> getRequestBodyMap(Map<String, Object> map) {
        if (map == null) {
            map = new ArrayMap<>();
        }

        Map<String, RequestBody> requestBodyMap = new HashMap<>();

        for (String key : map.keySet()) {
            String value = (String) map.get(key);
            requestBodyMap.put(key, RequestBody.create(null, value));
            RLog.e("NovateUtils", "key= " + key + " and value= " + value);
        }
        return requestBodyMap;
    }

}
