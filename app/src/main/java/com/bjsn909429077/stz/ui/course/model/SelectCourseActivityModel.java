package com.bjsn909429077.stz.ui.course.model;

import android.content.Context;
import android.util.Log;

import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.ui.course.contract.SelectCourseActivityContract;
import com.jiangjun.library.api.NovateUtils;
import com.tamic.novate.Throwable;

import java.util.Map;

public class SelectCourseActivityModel {
    private final Context context;
    private final SelectCourseActivityContract callback;

    public SelectCourseActivityModel(Context context, SelectCourseActivityContract callback) {

        this.context = context;
        this.callback = callback;
    }

    public void getCoursePackageInfo(Map map) {

        NovateUtils.getInstance().Post(context, BaseUrl.coursePackageInfo, map, true,
                new NovateUtils.setRequestReturn<CoursePackageInfoBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(CoursePackageInfoBean response) {
                        if (response != null && response.data != null)
                            callback.coursePackageInfo(response.data);
                    }
                });
    }

    public void receiveCoupon(Map map) {
        NovateUtils.getInstance().Post(context, BaseUrl.receiveCoupon, map, true,
                new NovateUtils.setRequestReturn<CoursePackageInfoBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(CoursePackageInfoBean response) {
                        callback.receiveCoupon(response);
                    }
                });
    }

}