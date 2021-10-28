package com.bjsn909429077.stz.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.HomePageFragmentAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;
import com.bjsn909429077.stz.ui.course.SelectCourseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class HomePageFragment extends BaseLazyLoadFragment  {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private HomePageFragmentAdapter homePageFragmentAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        int typeId = arguments.getInt("typeId");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        getRecommendListData(typeId);
    }
    //课程分类列表数据
    private void getRecommendListData(int id) {
        //api/app/v1/index/course/recommend/type/list
        HashMap<String, String> map = new HashMap<>();
        map.put("typeId",id+"");
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeRecommendList, map, true,
                new NovateUtils.setRequestReturn<HomeRecommendListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeRecommendListBean homeRecommendListBean) {
                        if (homeRecommendListBean != null) {
                            if (homeRecommendListBean.getData()!=null&&homeRecommendListBean.getData().size()>0){
                                List<HomeRecommendListBean.DataBean> data = homeRecommendListBean.getData();
                                homePageFragmentAdapter = new HomePageFragmentAdapter(R.layout.item_home_remmcond,data,getActivity());
                                recyclerView.setAdapter(homePageFragmentAdapter);
                                homePageFragmentAdapter.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                                        Intent intent = new Intent(mContext, SelectCourseActivity.class);
                                        intent.putExtra("coursePackageId", data.get(position).getCoursePackageId());
                                        startActivity(intent);
                                    }
                                });
                            }

                        }
                    }
                });

    }


    @Override
    protected void loadData() {

    }

   /* *//**
     * 获取banner
     *//*
    private void getBannerData() {

        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeBanner, map, true,
                new NovateUtils.setRequestReturn<HomeBannerBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeBannerBean response) {
                        if (response.getCode()==200){
                            List<HomeBannerBean.DataBean> data = response.getData();
                        }
                    }
                });

    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
