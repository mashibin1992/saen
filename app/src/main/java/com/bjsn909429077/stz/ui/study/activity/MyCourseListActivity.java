package com.bjsn909429077.stz.ui.study.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.PurchaseListAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HasBuyListBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class MyCourseListActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;

    private ArrayList<HasBuyListBean.DataBean> dataBeans = new ArrayList<>();
    private PurchaseListAdapter purchaseListAdapter;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_my_course_list;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        commonTitleView.setTitle(R.string.course_title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycler_view.setLayoutManager(linearLayoutManager);
        purchaseListAdapter = new PurchaseListAdapter(dataBeans);
        recycler_view.setAdapter(purchaseListAdapter);
        initListener();
    }

    private void initListener() {
        sml.setEnableRefresh(true);
        //刷新
        sml.setOnRefreshListener(refreshLayout -> {
        });
        //加载更多
        sml.setOnLoadMoreListener(refreshLayout -> {
        });
        //列表点击事件
        purchaseListAdapter.setPurchaseListClickListener(new PurchaseListAdapter.PurchaseListClickListener() {
            @Override
            public void endDateClick(HasBuyListBean.DataBean s) {

            }

            @Override
            public void continueClick(HasBuyListBean.DataBean s) {
                int firstTypeId = (int) SharedPreferencesUtil.getData(mContext, "firstTypeId", -1);
                Intent intent = new Intent(MyCourseListActivity.this, ContinueStudyActivity.class);
                intent.putExtra("secondTypeId", firstTypeId);
                intent.putExtra("coursePackageId", s.id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        HashMap<String, Integer> map = new HashMap<>();
//        map.put("typeId", "typeId");
        NovateUtils.getInstance().Post(mContext, BaseUrl.myCourseList, map, true,
                new NovateUtils.setRequestReturn<HasBuyListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        finishSml();
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(HasBuyListBean response) {
                        finishSml();
                        if (response != null && response.data != null) {
                            dataBeans.addAll(response.data);
                            purchaseListAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void finishSml() {
        sml.finishLoadMore();
        sml.finishRefresh();
    }
}