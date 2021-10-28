package com.bjsn909429077.stz.ui.course.model;

import android.content.Context;
import android.util.Log;

import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HomeBannerBean2;
import com.bjsn909429077.stz.ui.course.contract.SelectCourseListContract;
import com.jiangjun.library.api.NovateUtils;
import com.tamic.novate.Throwable;

import java.util.HashMap;

public class SelectCourseListModel {
    private final Context context;
    private final SelectCourseListContract callback;

    public SelectCourseListModel(Context context, SelectCourseListContract callback) {

        this.context = context;
        this.callback = callback;
    }

    public void getBannerData(HashMap<String, String> map) {
//        NovateUtils.getInstance().Post(context, BaseUrl.home_page_banner, map, true,
//                new NovateUtils.setRequestReturn<HomeBannerBean2>() {
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Log.d("SortHomeModel", "onError: " + throwable);
//                    }
//
//                    @Override
//                    public void onSuccee(HomeBannerBean2 response) {
//                        if (response != null && response.data != null && response.data.size() > 0) {
//                            callback.getHomeBanner(response);
//                        }
//                    }
//                });
    }
}
