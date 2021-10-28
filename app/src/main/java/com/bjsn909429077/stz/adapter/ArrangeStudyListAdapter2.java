package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.bean.CoursePackageList;
import com.bjsn909429077.stz.ui.course.contract.CourseArrangeContract;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.download.utils.ToastUtils;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArrangeStudyListAdapter2 extends BaseQuickAdapter<CoursePackageList.DataBean.HoursBean, BaseViewHolder> {


    private final CourseArrangeContract courseArrangeContract;
    private boolean isShow = true;
    private boolean isPlay = true;
    private boolean isShowDownload = false;
    private boolean isShowDelete = false;
    private OnItemDeleteClickListener listener;

    public ArrangeStudyListAdapter2(@Nullable List<CoursePackageList.DataBean.HoursBean> data, CourseArrangeContract courseArrangeContract) {
        super(R.layout.item_three_list, data);
        this.courseArrangeContract = courseArrangeContract;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageList.DataBean.HoursBean s) {
        LinearLayout ll_download = baseViewHolder.getView(R.id.ll_download);//下载
        LinearLayout ll_delete = baseViewHolder.getView(R.id.ll_delete);//删除
        ProgressBar progress = baseViewHolder.getView(R.id.progress);//进度条

        ImageView tv_play = baseViewHolder.getView(R.id.tv_play);//播放按钮
        TextView tv_three_title = baseViewHolder.getView(R.id.tv_three_title);//标题
        TextView tv_date = baseViewHolder.getView(R.id.tv_date);//时间
        TextView tv_show_size = baseViewHolder.getView(R.id.tv_show_size);//大小
        ImageView tv_lock = baseViewHolder.getView(R.id.tv_lock);//锁

        tv_lock.setVisibility(View.GONE);
        tv_play.setVisibility(View.GONE);

        tv_three_title.setText(s.classHourName);
        tv_date.setText(s.videoTimeLength + "/已经" + s.videoTimeLength);
        tv_show_size.setVisibility(View.GONE);

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

        tv_play.setVisibility(View.VISIBLE);
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

        //购买 锁图标
        tv_lock.setOnClickListener(v -> {
            if (courseArrangeContract != null) {
                courseArrangeContract.itemLock(s, baseViewHolder.getLayoutPosition());
            }
        });

        //下载
        ll_download.setOnClickListener(v -> {
            if (courseArrangeContract != null) {
                tv_show_size.setVisibility(View.VISIBLE);
                courseArrangeContract.itemDownload(s, tv_show_size, progress, baseViewHolder.getLayoutPosition());
            }
        });
        //删除
        ll_delete.setOnClickListener(v -> {
            ToastUtils.showToast(getContext(), "删除");
            if (listener != null) {
                listener.itemDelete(s, baseViewHolder.getLayoutPosition());
            }
        });
    }

    public void isShowDownload(boolean isShow) {
        this.isShowDownload = isShow;
    }

    public void isShowDelete(boolean isShow) {
        this.isShowDelete = isShow;
    }

    public interface OnItemDeleteClickListener {
        void itemDelete(CoursePackageList.DataBean.HoursBean data, int position);
    }

    public void setOnItemDeleteClickListener(OnItemDeleteClickListener listener) {
        this.listener = listener;
    }

}
