package com.bjsn909429077.stz.ui.study.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.LiveCourseChildAdapter;
import com.bjsn909429077.stz.bean.PackageListBean;
import com.bjsn909429077.stz.ui.study.activity.LiveCourse2Activity;
import com.bjsn909429077.stz.ui.study.contract.LiveCourseChildFragmentContract;
import com.bjsn909429077.stz.ui.study.model.LiveCourseChildFragmentModel;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class LiveCourseChildFragment extends BaseLazyLoadFragment implements LiveCourseChildFragmentContract {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;
    private LiveCourseChildFragmentModel mLiveCourseFragmentModel;
    private ArrayList<PackageListBean.DataBean> mDataBeans = new ArrayList<>();

    private int page = 0;
    private LiveCourseChildAdapter mLivelistadapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_all_live;
    }

    @Override
    protected void init() {
        mLiveCourseFragmentModel = new LiveCourseChildFragmentModel(mContext, this);
        initData();
    }


    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        mLivelistadapter = new LiveCourseChildAdapter(mDataBeans, this);
        recycler_view.setAdapter(mLivelistadapter);
        initListener();

    }

    private void initListener() {
        mLivelistadapter.setonItemListener((dataBean, position) -> {
            Intent intent = new Intent(mContext, LiveCourse2Activity.class);
            intent.putExtra("coursePackageId", dataBean.id);
            startActivity(intent);
        });
        //刷新
        sml.setOnRefreshListener(refreshLayout -> {
            mDataBeans.clear();
            page = 0;
            initData();
        });
        //加载更多
        sml.setOnLoadMoreListener(refreshLayout -> {
            page++;
            initData();
        });
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        mLiveCourseFragmentModel.getCoursePackageList(map, sml);
    }


    @Override
    public void packageListCallback(List<PackageListBean.DataBean> data) {
        if (data.size() > 0) {
            rl_no_data.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mDataBeans.addAll(data);
            mLivelistadapter.notifyDataSetChanged();
        } else {
            if (mDataBeans.size() == 0) {
                rl_no_data.setVisibility(View.VISIBLE);
                recycler_view.setVisibility(View.GONE);
            }
        }
    }


    public static LiveCourseChildFragment getInstance(Bundle bundle) {
        LiveCourseChildFragment liveCourseChildFragment = new LiveCourseChildFragment();
        liveCourseChildFragment.setArguments(bundle);
        return liveCourseChildFragment;
    }

}