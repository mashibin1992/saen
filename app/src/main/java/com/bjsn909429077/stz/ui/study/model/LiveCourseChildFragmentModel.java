package com.bjsn909429077.stz.ui.study.model;

import android.content.Context;
import android.util.Log;

import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.BaseQuestionBean;
import com.bjsn909429077.stz.bean.PackageListBean;
import com.bjsn909429077.stz.ui.study.contract.LiveCourseChildFragmentContract;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.download.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.Map;

public class LiveCourseChildFragmentModel {

    private Context context;
    private LiveCourseChildFragmentContract contract;

    public LiveCourseChildFragmentModel(Context context, LiveCourseChildFragmentContract contract) {
        this.context = context;
        this.contract = contract;
    }

    public void getCoursePackageList(Map<String, Object> map, SmartRefreshLayout sml) {
        NovateUtils.getInstance().Post(context, BaseUrl.liveCoursePackageList, map, true,
                new NovateUtils.setRequestReturn<PackageListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        sml.finishLoadMore();
                        sml.finishRefresh();
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(PackageListBean response) {
                        sml.finishLoadMore();
                        sml.finishRefresh();
                        if (response != null && response.data != null) {
                            contract.packageListCallback(response.data);
                        }

                    }
                });
    }
}
