package com.bjsn909429077.stz.ui.course;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ArrangeListAdapter;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.bean.EventBean;
import com.bjsn909429077.stz.ui.course.contract.CourseArrangeContract;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ToastUtils;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusDisposable;
import cn.finalteam.rxgalleryfinal.rxbus.event.MediaViewPagerChangedEvent;
import io.reactivex.disposables.Disposable;
import rx.functions.Action1;

import static io.reactivex.internal.util.NotificationLite.subscription;

public class CourseArrangeFragment extends BaseLazyLoadFragment implements CourseArrangeContract<CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean.ClassHourListBean> {
    @BindView(R.id.rv_arrange)
    RecyclerView rv_arrange;
    private ArrangeListAdapter arrangeListAdapter;
    private static List<CoursePackageInfoBean.DataBean.CourseScheduleListBean> courseScheduleListBean = new ArrayList();
    private List<CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean> data;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_course_arrange;
    }

    @Override
    protected void init() {
        rv_arrange.setLayoutManager(new LinearLayoutManager(mContext));
        arrangeListAdapter = new ArrangeListAdapter(R.layout.item_arrange_course,
                courseScheduleListBean, this);
        rv_arrange.setAdapter(arrangeListAdapter);
        //是否显示进度条 下载等按钮
        arrangeListAdapter.setIsShowDownload(false);
        arrangeListAdapter.isShowDelete(false);

        initRxBus();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void courseArrangeItemCallback(String data, int position) {
        ToastUtils.Toast(mContext, position);
    }

    @Override
    public void itemPlay(CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean.ClassHourListBean data, int position) {
        ToastUtils.Toast(mContext, "播放");
    }

    @Override
    public void itemSpend(CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean.ClassHourListBean data, int position) {
        ToastUtils.Toast(mContext, "暂停");
    }

    @Override
    public void itemDownload(CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean.ClassHourListBean data, TextView view, ProgressBar progressBar, int position) {

    }

    @Override
    public void itemLock(CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean.ClassHourListBean s, int layoutPosition) {
        ToastUtils.Toast(mContext, "锁");
    }

    @SuppressLint("CheckResult")
    private void initRxBus() {
        Disposable subscriptionMediaViewPagerChangedEvent = RxBus.getDefault().toObservableSticky(EventBean.class)
                .subscribeWith(new RxBusDisposable<EventBean>() {
                    @Override
                    protected void onEvent(EventBean eventBean) {
                        if (eventBean.getTYPE() == EventBean.SELECT_COURSE) {
                            courseScheduleListBean.clear();
                            courseScheduleListBean.addAll(eventBean.getCourseScheduleListBean());
                            arrangeListAdapter.notifyDataSetChanged();
                        }
                    }
                });
        RxBus.getDefault().add(subscriptionMediaViewPagerChangedEvent);
    }
}
