package com.bjsn909429077.stz.ui.study.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.PurchaseListAdapter;
import com.bjsn909429077.stz.bean.EventBean;
import com.bjsn909429077.stz.bean.HasBuyListBean;
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.study.activity.ContinueStudyActivity;
import com.bjsn909429077.stz.ui.study.StudyFragment;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;

import java.util.ArrayList;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusDisposable;
import io.reactivex.disposables.Disposable;

public class PurchaseFragment extends BaseLazyLoadFragment implements PurchaseListAdapter.PurchaseListClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private PurchaseListAdapter purchaseListAdapter;
    private ArrayList<HasBuyListBean.DataBean> dataBeans = new ArrayList<>();


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_purchase;
    }

    @Override
    protected void init() {
        initRecycler();
        initListener();
        initRxBus();
    }


    @Override
    protected void loadData() {

    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycler_view.setLayoutManager(linearLayoutManager);
        purchaseListAdapter = new PurchaseListAdapter(dataBeans);
        recycler_view.setAdapter(purchaseListAdapter);
    }


    private void initListener() {
        purchaseListAdapter.setPurchaseListClickListener(this);
    }

    @SuppressLint("CheckResult")
    private void initRxBus() {
        Disposable subscriptionMediaViewPagerChangedEvent = RxBus.getDefault().toObservableSticky(EventBean.class)
                .subscribeWith(new RxBusDisposable<EventBean>() {
                    @Override
                    protected void onEvent(EventBean eventBean) {
                        if (eventBean.getTYPE() == EventBean.PURCHASED_COURSE) {
                            dataBeans.clear();
                            dataBeans.addAll(eventBean.getCourseScheduleListBean());
                            purchaseListAdapter.notifyDataSetChanged();
                        }

                    }
                });
        RxBus.getDefault().add(subscriptionMediaViewPagerChangedEvent);
    }

    /**
     * 截止日期
     *
     * @param s
     */
    @Override
    public void endDateClick(HasBuyListBean.DataBean s) {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            activity.showFragment(3);
        }
    }

    /**
     * 继续学习
     *
     * @param s
     */
    @Override
    public void continueClick(HasBuyListBean.DataBean s) {
        Intent intent = new Intent(getActivity(), ContinueStudyActivity.class);
        intent.putExtra("coursePackageId",s.id);
        StudyFragment parentFragment = (StudyFragment) getParentFragment();
        intent.putExtra("secondTypeId",parentFragment.secondTypeId);
        startActivity(intent);
    }
}
