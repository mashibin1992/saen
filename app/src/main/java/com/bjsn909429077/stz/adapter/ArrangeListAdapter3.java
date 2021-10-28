package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.ui.course.contract.CourseArrangeContract;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArrangeListAdapter3 extends BaseQuickAdapter<CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean.ClassHourListBean, BaseViewHolder> {


    private final CourseArrangeContract courseArrangeContract;
    private boolean isShow = true;
    private boolean isPlay = true;
    private boolean isShowDownload = false;
    private boolean isShowDelete = false;

    public ArrangeListAdapter3(@Nullable List<CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean.ClassHourListBean> data, CourseArrangeContract courseArrangeContract) {
        super(R.layout.item_three_list, data);
        this.courseArrangeContract = courseArrangeContract;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean.ClassHourListBean s) {
        LinearLayout ll_download = baseViewHolder.getView(R.id.ll_download);//下载
        LinearLayout ll_delete = baseViewHolder.getView(R.id.ll_delete);//删除
        ProgressBar progress = baseViewHolder.getView(R.id.progress);//进度条

        ImageView tv_play = baseViewHolder.getView(R.id.tv_play);//播放按钮
        TextView tv_three_title = baseViewHolder.getView(R.id.tv_three_title);//标题
        TextView tv_date = baseViewHolder.getView(R.id.tv_date);//时间
        TextView tv_show_size = baseViewHolder.getView(R.id.tv_show_size);//大小
        ImageView tv_lock = baseViewHolder.getView(R.id.tv_lock);//锁

        tv_play.setVisibility(View.GONE);
        tv_lock.setVisibility(View.GONE);

        tv_three_title.setText(s.classHourName);
        tv_date.setText(s.videoTimeLength + "/已经" + s.videoTimeLength);

        if (!isShowDownload) {
            ll_download.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
            ll_delete.setVisibility(View.GONE);
        } else {
            //显示条件  如果显示根据状态判断显示哪几个按钮
            if (isShowDelete) {
                ll_delete.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                ll_download.setVisibility(View.GONE);
            } else {
                ll_delete.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                ll_download.setVisibility(View.VISIBLE);
            }
        }

        //是否试看
        if (s.isAudition == 1) {
            tv_play.setVisibility(View.VISIBLE);
            tv_lock.setVisibility(View.GONE);
            tv_play.setOnClickListener(v -> {
                if (isPlay) {
                    ImageLoaderUtils.loadRes(getContext(),R.drawable.icon_suspend,tv_play);
                    courseArrangeContract.itemPlay(s, baseViewHolder.getLayoutPosition());
                    isPlay = false;
                } else {
                    ImageLoaderUtils.loadRes(getContext(),R.drawable.icon_paly,tv_play);
                    courseArrangeContract.itemSpend(s, baseViewHolder.getLayoutPosition());
                    isPlay = true;
                }
            });
        } else {
            tv_lock.setVisibility(View.VISIBLE);
            tv_play.setVisibility(View.GONE);
            tv_lock.setOnClickListener(v -> {
                courseArrangeContract.itemLock(s, baseViewHolder.getLayoutPosition());
            });
        }

        ll_download.setOnClickListener(v -> {
            courseArrangeContract.itemDownload(s, tv_show_size, progress, baseViewHolder.getLayoutPosition());
        });
    }

    public void isShowDownload(boolean isShow) {
        this.isShowDownload = isShow;
    }

    public void isShowDelete(boolean isShow) {
        this.isShowDelete = isShow;
    }

}
