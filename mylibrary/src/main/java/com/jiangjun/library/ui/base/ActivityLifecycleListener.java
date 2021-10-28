package com.jiangjun.library.ui.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jiangjun.library.utils.RLog;

/**
 * @author XuZhuChao
 * @create 2021/9/6
 * @Describe 全局生命周期监听
 */
public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "生命周期监听";

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        RLog.i(TAG, "onActivityCreated =" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

        RLog.i(TAG, "onActivityResumed =" + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

        RLog.i(TAG, "onActivityPaused =" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

        RLog.i(TAG, "onActivityDestroyed =" + activity.getLocalClassName());
    }
}
