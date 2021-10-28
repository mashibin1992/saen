package com.bjsn909429077.stz.ui.home.model;

import android.content.Context;
import android.util.Log;

import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.GuideInfoBean;
import com.bjsn909429077.stz.bean.HomeBannerBean2;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;
import com.bjsn909429077.stz.ui.home.contract.SortHomeContract;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.HashMap;

public class SortHomeModel {
    private final Context context;
    private final SortHomeContract callback;

    public SortHomeModel(Context context, SortHomeContract callback) {

        this.context = context;
        this.callback = callback;
    }

    public void getBannerData(HashMap<String, String> map) {
        NovateUtils.getInstance().Post(context, BaseUrl.home_page_banner, map, true,
                new NovateUtils.setRequestReturn<HomeBannerBean2>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(HomeBannerBean2 response) {
                        if (response != null && response.data != null && response.data.size() > 0) {
                            callback.getHomeBanner(response);
                        }
                    }
                });
    }

    public void getInfoGuide(HashMap<String, Object> map) {
        NovateUtils.getInstance().Post(context, BaseUrl.guideInfo, map, true,
                new NovateUtils.setRequestReturn<GuideInfoBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(GuideInfoBean response) {
                        if (response != null && response.data != null) {
                            callback.getInfoGuide(response.data);
                        }
                    }
                });
    }


    public void getListData(HashMap<String, Object> map) {
        NovateUtils.getInstance().Post(context, BaseUrl.homeRecommendList, map, true,
                new NovateUtils.setRequestReturn<HomeRecommendListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(context, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeRecommendListBean homeRecommendListBean) {
                        if (homeRecommendListBean != null && homeRecommendListBean.getData() != null) {
                            callback.getListData(homeRecommendListBean.getData());
                        }
                    }
                });

    }
}
