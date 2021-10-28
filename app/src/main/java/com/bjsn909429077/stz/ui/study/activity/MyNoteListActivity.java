package com.bjsn909429077.stz.ui.study.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.NoteAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.EventBean;
import com.bjsn909429077.stz.bean.NoteListBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusDisposable;
import io.reactivex.disposables.Disposable;

public class MyNoteListActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    private NoteAdapter noteAdapter;
    private Integer page = 0;
    private ArrayList<NoteListBean.DataBean> strings = new ArrayList<>();

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_note_list;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        commonTitleView.setTitle("我的笔记");
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        noteAdapter = new NoteAdapter(R.layout.item_note, strings, null);
        recycler_view.setAdapter(noteAdapter);
        initRxBus();
        initListener();
    }

    private void initListener() {
        //刷新
        sml.setOnRefreshListener(refreshLayout -> {
            strings.clear();
            page = 0;
            initData();
        });
        //加载更多
        sml.setOnLoadMoreListener(refreshLayout -> {
            page++;
            initData();
        });
        noteAdapter.setOnEditListener(new NoteAdapter.OnEditListener() {
            @Override
            public void onEditClick(NoteListBean.DataBean data, int position) {
                Intent intent = new Intent(mContext, EditNoteActivity.class);
                intent.putExtra("content", data.content);
                intent.putExtra("classHourId", data.classHourId);
                intent.putExtra("isEdit", 1);
                intent.putExtra("id", data.id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("page", page);
        NovateUtils.getInstance().Post(mContext, BaseUrl.myNoteList, map, true,
                new NovateUtils.setRequestReturn<NoteListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        closeSml();
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(NoteListBean response) {
                        if (response != null && response.data != null) {
                            closeSml();
                            strings.addAll(response.data);
                            noteAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    @SuppressLint("CheckResult")
    private void initRxBus() {
        Disposable subscriptionMediaViewPagerChangedEvent = RxBus.getDefault().toObservableSticky(EventBean.class)
                .subscribeWith(new RxBusDisposable<EventBean>() {
                    @Override
                    protected void onEvent(EventBean eventBean) {
                        if (eventBean != null && eventBean.getTYPE() == EventBean.REFRESH) {
                            sml.autoRefresh();
                        }
                    }
                });
        RxBus.getDefault().add(subscriptionMediaViewPagerChangedEvent);
    }

    private void closeSml() {
        sml.finishLoadMore();
        sml.finishRefresh();
    }
}