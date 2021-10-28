package com.jiangjun.library.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;

import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by LeBron James on 2018/6/20.
 */

public class UserConfig {
    //默认用包名作为,sp名字
    private static SharedPreferences mSharedPreferences ;
//    private static SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
    private static TelephonyManager tm;
    private static PackageManager manager;
    private static String versionName;
    private static String androidId;//设备ID


    public static void init(Context  context) {
        mSharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 获取TelephonyManager单列
     *
     * @param context
     * @return
     */
    private static TelephonyManager getTelephonyManager(Context context) {
        if (tm == null) {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
        return tm;
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public static String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public static boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public static long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public static void putLong(String key, long value) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void clear() {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.clear();
        edit.commit();
    }

    /**
     * 退出登录,清除用户登录信息
     */
    public static void clearUser(Context context) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove("user");
        editor.remove("user");
        editor.commit();

    }

    /**
     * 获取软件版本
     */
    public static String getVersion(Context context) {
        if (versionName == null) {
            if (manager == null) {
                manager = context.getApplicationContext().getPackageManager();
            }
            try {
                PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
                versionName = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return versionName;
    }

    public static boolean saveStringArray(List<String> sKey) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putInt("Status_size", sKey.size()); /*sKey is an array*/
        for (int i = 0; i < sKey.size(); i++) {
            edit.remove("Status_" + i);
            edit.putString("Status_" + i, sKey.get(i));
        }
        return edit.commit();
    }

    public static List<String> loadArray(Context mContext) {
        List<String> sKey = new ArrayList<>();
        sKey.clear();
        int size = getInt("Status_size");
        for (int i = 0; i < size; i++) {
            sKey.add(getString("Status_" + i, null));
        }
        return sKey;
    }

    public static int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public static String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    /**
     * 判断是否登录
     *
     * @return true:登录，false:未登录
     */
    public static boolean isLogin() {
        boolean result = false;
        if (getUser() != null && !StringUtil.isBlank(getUser().getToken())) {//判断user
            result = true;
        }
        return result;
    }

    /**
     * SharedPreferences取出存储的对象
     */
    public static User.DataDTO getUser() {
        User.DataDTO user = new User.DataDTO();
        String productBase64 = mSharedPreferences.getString("user", "");
        if (TextUtil.isEmpty(productBase64))
            return user;
        //读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes(), 1);
        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                user = (User.DataDTO) bis.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }


    /**
     * SharedPreferences存储对象
     */
    public static void setUser(User.DataDTO user) {
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(user);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encode(baos.toByteArray(), 1));
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString("user", oAuth_Base64);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveSearchArray(List<String> sKey) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();

        edit.putInt("search_size", sKey.size());
        /*sKey is an array*/
        if (sKey.size()>12){
            sKey = sKey.subList(0, 12);
        }
        for (int i = 0; i < sKey.size(); i++) {
            edit.remove("search_size_" + i);
            edit.putString("search_size_" + i, sKey.get(i));
        }
        return edit.commit();
    }

    public static List<String> loadSearchArray(Context mContext) {
        List<String> sKey = new ArrayList<>();
        sKey.clear();
        int size = getInt("search_size");
        for (int i = 0; i < size; i++) {
            if (!TextUtils.isEmpty(getString("search_size_" + i, ""))){
             sKey.add(getString("search_size_" + i, ""));
            }
        }
        return sKey;
    }
    public static void deleteSearchArray(Context mContext) {
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        int size = getInt("search_size");
        for (int i = 0; i < size; i++) {
            edit.remove("search_size_"+ i);
            edit.commit();
        }

    }
}
