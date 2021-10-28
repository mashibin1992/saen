package com.bjsn909429077.stz.ui.home.contract;

import com.bjsn909429077.stz.bean.GuideInfoBean;
import com.bjsn909429077.stz.bean.HomeBannerBean2;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;

import java.util.List;


public interface SortHomeContract {
    void getHomeBanner(HomeBannerBean2 homeBannerBean);

    void getInfoGuide(GuideInfoBean.DataBean response);

    void getListData(List<HomeRecommendListBean.DataBean> data);
}
