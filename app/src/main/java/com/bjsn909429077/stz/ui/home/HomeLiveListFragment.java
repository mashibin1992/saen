package com.bjsn909429077.stz.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.LiveList_2Adapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AllLiveListBean;
import com.bjsn909429077.stz.bean.HomeBannerBean;
import com.bjsn909429077.stz.ui.order.OrderLiveActivity;
import com.bjsn909429077.stz.utils.InLiveUtils;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.download.utils.ToastUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;


public class HomeLiveListFragment extends BaseLazyLoadFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    @BindView(R.id.rl_no_data)
    RelativeLayout rl_no_data;


    private int page = 0;
    private ArrayList<AllLiveListBean.DataBean> dataBeans = new ArrayList<>();
    private LiveList_2Adapter livelistadapter;
    private int type;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_live;
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            type = getArguments().getInt("type", 0);
        }

        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycler_view.setLayoutManager(linearLayoutManager);
        livelistadapter = new LiveList_2Adapter(R.layout.item_all_live_list, dataBeans, this);
        recycler_view.setAdapter(livelistadapter);
        initListener();
    }

    private void initData() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        NovateUtils.getInstance().Post(mContext, BaseUrl.liveList, map, true,
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


    @Override
    protected void loadData() {

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

        livelistadapter.setOnInputBtnClickListener((dataBean, position) -> {
            ToastUtils.showToast(mContext, "进入按钮");
            //进入直播间
            new InLiveUtils(getActivity(), livelistadapter.getItem(position).channelId, "").inLive();
        });

        livelistadapter.setOnSleepPlayClickListener((dataBean, position) -> {
            ToastUtils.showToast(mContext, "待开播");
        });

        livelistadapter.setOnPurchaseClickListener((dataBean, position) -> {
            Intent intent1 = new Intent(getActivity(), OrderLiveActivity.class);
            intent1.putExtra("liveId", dataBean.id);
            startActivity(intent1);
            ToastUtils.showToast(mContext, "购买");
        });

        livelistadapter.setYuYueClickListener((dataBean, position) -> {
            showDialog(dataBean.id);
            ToastUtils.showToast(mContext, "预约");
        });

//        livelistadapter.setOnBackPlayClickListener((dataBean, position) -> {
//            ToastUtils.showToast(mContext, "回放");
//        });

//
//        livelistadapter.setOnItemClickListener((adapter, view, position) -> {
//            //进入直播间
//            new InLiveUtils(getActivity(), livelistadapter.getItem(position).channelId, "").inLive();
//
//        });

    }

    public void showDialog(int liveId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认预约?"); //设置内容
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); //关闭dialog
                //预约
                reserve(liveId);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.create().show();

    }

    public void reserve(int liveId){
        HashMap<String, String> map = new HashMap<>();
        map.put("liveId",liveId+"");
        NovateUtils.getInstance().Post(mContext, BaseUrl.homeReserve, map, true,
                new NovateUtils.setRequestReturn<HomeBannerBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        com.jiangjun.library.utils.ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeBannerBean homeBannerBean) {
                        if (homeBannerBean != null) {
//                            getLiveData();
                            sml.autoRefresh();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        InLiveUtils.destroy();
    }

    public static HomeLiveListFragment getInstance(Bundle bundle) {
        HomeLiveListFragment homeLiveListFragment = new HomeLiveListFragment();
        homeLiveListFragment.setArguments(bundle);
        return homeLiveListFragment;
    }
}
