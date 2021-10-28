package com.bjsn909429077.stz.ui.study.model;

import android.content.Context;
import android.util.Log;

import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.LiveListBean;
import com.bjsn909429077.stz.ui.study.contract.LiveListActivityContract;
import com.jiangjun.library.api.NovateUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.Map;

public class LiveListActivityModel {
    private final Context mContext;
    private final LiveListActivityContract callback;

    public LiveListActivityModel(Context mContext, LiveListActivityContract callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    public void getLiveListResult(Map<String, Object> map, SmartRefreshLayout sml) {
        NovateUtils.getInstance().Post(mContext, BaseUrl.liveList2, map, true,
                new NovateUtils.setRequestReturn<LiveListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        sml.finishLoadMore();
                        sml.finishRefresh();
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(LiveListBean response) {
                        sml.finishLoadMore();
                        sml.finishRefresh();
                        if (response != null && response.data != null) {
                            callback.getLiveListResult(response.data);
                        }
                    }
                });
    }
}
