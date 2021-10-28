package com.bjsn909429077.stz.ui.study.model;

import android.content.Context;
import android.util.Log;

import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.PackageListBean;
import com.bjsn909429077.stz.bean.PackageListBean2;
import com.bjsn909429077.stz.ui.study.contract.LiveCourseChildFragmentContract;
import com.bjsn909429077.stz.ui.study.contract.LiveCourseFragmentContract;
import com.jiangjun.library.api.NovateUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.Map;

public class LiveCourseFragmentModel {
    private Context context;
    private LiveCourseFragmentContract contract;

    public LiveCourseFragmentModel(Context context, LiveCourseFragmentContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void getCourseListResult(Map<String, Object> map, SmartRefreshLayout sml) {
        NovateUtils.getInstance().Post(context, BaseUrl.liveCourseList, map, true,
                new NovateUtils.setRequestReturn<PackageListBean2>() {
                    @Override
                    public void onError(Throwable throwable) {
                        sml.finishLoadMore();
                        sml.finishRefresh();
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(PackageListBean2 response) {
                        sml.finishLoadMore();
                        sml.finishRefresh();
                        if (response != null && response.data != null) {
                            contract.liveCourseResult(response.data);
                        }
                    }
                });
    }
}
