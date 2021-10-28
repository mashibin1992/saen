package com.bjsn909429077.stz.ui.study.fragment;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.AllLiveAdapter;
import com.bjsn909429077.stz.adapter.LiveListAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AllLiveListBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.download.utils.ToastUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class PlaybackLiveFragment extends BaseLazyLoadFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;
    private int page = 0;
    private ArrayList<AllLiveListBean.DataBean> dataBeans = new ArrayList<>();
    private AllLiveAdapter livelistadapter;

    @Override
    protected int setLayoutId() {

        return R.layout.fragment_playe_back;
    }

    @Override
    protected void init() {
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        livelistadapter = new AllLiveAdapter(R.layout.item_all_live_list, dataBeans, this);
        recycler_view.setAdapter(livelistadapter);
        livelistadapter.setIsShowButton(false);
        initData();
        initListener();
    }

    private void initData() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("page", page);
        NovateUtils.getInstance().Post(mContext, BaseUrl.playback, map, true,
                new NovateUtils.setRequestReturn<AllLiveListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        sml.finishLoadMore();
                        sml.finishRefresh();
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(AllLiveListBean response) {
                        sml.finishLoadMore();
                        sml.finishRefresh();
                        if (response != null && response.data != null && response.data.size() > 0) {
                            recycler_view.setVisibility(View.VISIBLE);
                            dataBeans.addAll(response.data);
                            livelistadapter.notifyDataSetChanged();
                            rl_no_data.setVisibility(View.GONE);
                        } else {
                            if (dataBeans.size() == 0) {
                                rl_no_data.setVisibility(View.VISIBLE);
                                recycler_view.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    private void initListener() {
        sml.setOnRefreshListener(v -> {
            dataBeans.clear();
            page = 0;
            initData();
        });
        sml.setOnLoadMoreListener(v -> {
            page++;
            initData();
        });

        livelistadapter.setOnBackPlayClickListener((dataBean, position) -> {
            ToastUtils.showToast(mContext, "回放");
        });

    }

    @Override
    protected void loadData() {

    }
}
