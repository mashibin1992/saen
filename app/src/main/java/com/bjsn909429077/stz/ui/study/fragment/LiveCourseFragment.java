package com.bjsn909429077.stz.ui.study.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.LiveCourse2Adapter;
import com.bjsn909429077.stz.bean.PackageListBean2;
import com.bjsn909429077.stz.ui.study.activity.LiveCourse2Activity;
import com.bjsn909429077.stz.ui.study.activity.LiveListActivity;
import com.bjsn909429077.stz.ui.study.contract.LiveCourseFragmentContract;
import com.bjsn909429077.stz.ui.study.model.LiveCourseFragmentModel;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class LiveCourseFragment extends BaseLazyLoadFragment implements LiveCourseFragmentContract {


    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;
    private LiveCourseFragmentModel mLiveCourseFragmentModel;
    private ArrayList<PackageListBean2.DataBean> mDataBeans = new ArrayList<>();

    private int page = 0;
    private LiveCourse2Adapter mLivelistadapter;
    private int coursePackageId;
    private int typeId;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_all_live;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            typeId = getArguments().getInt("typeId", 0);
            coursePackageId = getArguments().getInt("coursePackageId", 0);
        }
        mLiveCourseFragmentModel = new LiveCourseFragmentModel(mContext, this);
        initData();
    }


    @Override
    protected void loadData() {
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        mLivelistadapter = new LiveCourse2Adapter(mDataBeans, this);
        recycler_view.setAdapter(mLivelistadapter);
        initListener();

    }

    private void initListener() {
        mLivelistadapter.setonItemListener((dataBean, position) -> {
            Intent intent = new Intent(mContext, LiveListActivity.class);
            intent.putExtra("courseId", dataBean.id);
            startActivity(intent);
        });
        //刷新
        sml.setOnRefreshListener(refreshLayout ->

        {
            mDataBeans.clear();
            page = 0;
            initData();
        });
        //加载更多
        sml.setOnLoadMoreListener(refreshLayout ->

        {
            page++;
            initData();
        });
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("coursePackageId", coursePackageId);
        map.put("typeId", typeId);
        map.put("page", page);
        mLiveCourseFragmentModel.getCourseListResult(map, sml);
    }

    public static LiveCourseFragment getInstance(Bundle bundle) {
        LiveCourseFragment liveCourseFragment = new LiveCourseFragment();
        liveCourseFragment.setArguments(bundle);
        return liveCourseFragment;
    }

    @Override
    public void liveCourseResult(List<PackageListBean2.DataBean> data) {
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
}

