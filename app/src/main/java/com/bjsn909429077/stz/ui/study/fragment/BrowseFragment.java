package com.bjsn909429077.stz.ui.study.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.BrowseListAdapter;
import com.bjsn909429077.stz.bean.EventBean;
import com.bjsn909429077.stz.bean.RecentBrowesBean;
import com.bjsn909429077.stz.ui.course.SelectCourseActivity;
import com.bjsn909429077.stz.ui.study.activity.ContinueStudyActivity;
import com.bjsn909429077.stz.ui.study.StudyFragment;
import com.bjsn909429077.stz.ui.study.contract.BrowseListContract;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;

import java.util.ArrayList;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusDisposable;
import io.reactivex.disposables.Disposable;

public class BrowseFragment extends BaseLazyLoadFragment implements BrowseListContract {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private BrowseListAdapter browseListAdapter;
    private ArrayList<RecentBrowesBean.DataBean> dataBeans = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_browse;
    }

    @Override
    protected void init() {
        initRecyclerView();
        initRxBus();
    }

    @Override
    protected void loadData() {

    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycler_view.setLayoutManager(linearLayoutManager);
        browseListAdapter = new BrowseListAdapter(dataBeans, this);
        recycler_view.setAdapter(browseListAdapter);
    }

    /**
     * 去购买
     *
     * @param dataBean
     */
    @Override
    public void goPrice(RecentBrowesBean.DataBean dataBean) {
        Intent intent = new Intent(mContext, SelectCourseActivity.class);
        intent.putExtra("coursePackageId", dataBean.id);
        startActivity(intent);
    }

    /**
     * 去学习
     *
     * @param dataBean
     */
    @Override
    public void goStudy(RecentBrowesBean.DataBean dataBean) {
        Intent intent = new Intent(getActivity(), ContinueStudyActivity.class);
        intent.putExtra("coursePackageId", dataBean.id);
        StudyFragment parentFragment = (StudyFragment) getParentFragment();
        intent.putExtra("secondTypeId", parentFragment.secondTypeId);
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    private void initRxBus() {
        Disposable subscriptionMediaViewPagerChangedEvent = RxBus.getDefault().toObservableSticky(EventBean.class)
                .subscribeWith(new RxBusDisposable<EventBean>() {
                    @Override
                    protected void onEvent(EventBean eventBean) {
                        if (eventBean.getTYPE() == EventBean.RECENT_BROWSING_COURSE) {
                            Log.d("RxBus", "onEvent: "+"请求成功");
                            dataBeans.clear();
                            dataBeans.addAll(eventBean.getCourseScheduleListBean());
                            browseListAdapter.notifyDataSetChanged();
                        }
                    }
                });
        RxBus.getDefault().add(subscriptionMediaViewPagerChangedEvent);
    }

}
