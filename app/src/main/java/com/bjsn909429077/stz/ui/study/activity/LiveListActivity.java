package com.bjsn909429077.stz.ui.study.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.LiveListAdapter;
import com.bjsn909429077.stz.bean.LiveListBean;
import com.bjsn909429077.stz.ui.course.model.SelectCourseActivityModel;
import com.bjsn909429077.stz.ui.study.contract.LiveListActivityContract;
import com.bjsn909429077.stz.ui.study.model.LiveListActivityModel;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class LiveListActivity extends BaseActivity
        implements LiveListActivityContract,
        LiveListAdapter.OnIntoButtonClickListener,
        LiveListAdapter.OnPlayButtonClickListener,
        LiveListAdapter.OnWaitButtonClickListener {
    @BindView(R.id.recycler_live)
    RecyclerView recycler_live;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    private int page = 0;
    private LiveListActivityModel liveListActivityModel;
    private int courseId;
    private ArrayList<LiveListBean.DataBean> dataBeans = new ArrayList<>();
    private LiveListAdapter livelistadapter;


    @Override
    protected int setLayoutRes() {
        return R.layout.fragment_live_list;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null) {
            courseId = getIntent().getIntExtra("courseId", 0);
        }
        liveListActivityModel = new LiveListActivityModel(mContext, this);
        commonTitleView.setTitle("直播课包列表");
        initRecyclerView();
        initListener();
    }

    private void initRecyclerView() {
        recycler_live.setLayoutManager(new LinearLayoutManager(mContext));
        livelistadapter = new LiveListAdapter(dataBeans);
        recycler_live.setAdapter(livelistadapter);
        livelistadapter.setOnIntoButtonClickListener(this);
        livelistadapter.setOnPlayButtonClickListener(this);
        livelistadapter.setOnWaitButtonClickListener(this);
    }

    private void initListener() {
        //刷新
        sml.setOnRefreshListener(refreshLayout -> {
            dataBeans.clear();
            page = 0;
            initData();
        });
        //加载更多
        sml.setOnLoadMoreListener(refreshLayout -> {
            page++;
            initData();
        });
    }

    @Override
    protected void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseId", courseId);
        map.put("page", page);
        liveListActivityModel.getLiveListResult(map, sml);
    }

    @Override
    public void getLiveListResult(List<LiveListBean.DataBean> dataBean) {
        dataBeans.addAll(dataBean);
        livelistadapter.notifyDataSetChanged();
    }

    /**
     * 进入
     *
     * @param dataBean
     */
    @Override
    public void intoClick(LiveListBean.DataBean dataBean) {

    }

    /**
     * 回播
     *
     * @param dataBean
     */
    @Override
    public void playClick(LiveListBean.DataBean dataBean) {

    }

    /**
     * 待开播
     *
     * @param dataBean
     */
    @Override
    public void waitClick(LiveListBean.DataBean dataBean) {

    }
}