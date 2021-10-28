package com.bjsn909429077.stz.adapter;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageListBean;
import com.bjsn909429077.stz.ui.study.activity.ContinueStudyActivity;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ContinueStudyListAdapter extends BaseQuickAdapter<CoursePackageListBean.DataBean.CourseListBean, BaseViewHolder> {

    private final ContinueStudyActivity continueStudyActivity;
    private OnContinueClickListener listener;

    public ContinueStudyListAdapter(ArrayList<CoursePackageListBean.DataBean.CourseListBean> strings, ContinueStudyActivity continueStudyActivity) {
        super(R.layout.item_continue_study, strings);
        this.continueStudyActivity = continueStudyActivity;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageListBean.DataBean.CourseListBean s) {
        ImageView image_icon = baseViewHolder.getView(R.id.image_icon_a);
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        ProgressBar practice_first_pb = baseViewHolder.getView(R.id.practice_first_pb);
        TextView tv_sub_title = baseViewHolder.getView(R.id.tv_sub_title);
        TextView tv_end_date = baseViewHolder.getView(R.id.tv_end_date);
        TextView tv_continue = baseViewHolder.getView(R.id.tv_continue);

        if (s.effectiveEtime != null && s.effectiveEtime.contains(" ")) {
            tv_end_date.setText("截止：" + s.effectiveEtime.split(" ")[0]);
        } else {
            tv_end_date.setText("截止：" + s.effectiveEtime);
        }

        tv_title.setText(s.name);

        practice_first_pb.setMax(s.studyCourseCount);
        practice_first_pb.setProgress(s.recentlyStudyClassHourId);
        tv_sub_title.setText(s.recentlyStudyClassHourId + "/" + s.studyCourseCount + "课时");

        switch (s.studyState) {
            case 0:
                tv_continue.setText("学习");
                break;
            case 1:
                tv_continue.setText("继续学习");
                break;
            case 2:
                tv_continue.setText("重新学习");
                break;
        }
        switch (s.type) {
            case 0:
                ImageLoaderUtils.loadRes(getContext(),R.drawable.icon_recording,image_icon);
                break;
            case 1:
                ImageLoaderUtils.loadRes(getContext(),R.drawable.icon_live,image_icon);
                break;
        }

        baseViewHolder.findView(R.id.tv_continue).setOnClickListener(v -> {
            if (listener != null) {
                listener.itemClick(s, baseViewHolder.getLayoutPosition());
            }
        });

        tv_end_date.setOnClickListener(v -> {
            if (listener != null) {
                listener.endTimeClick(s, baseViewHolder.getLayoutPosition());
            }
        });
    }

    public interface OnContinueClickListener {
        void itemClick(CoursePackageListBean.DataBean.CourseListBean s, int position);

        //截止时间
        void endTimeClick(CoursePackageListBean.DataBean.CourseListBean s, int position);
    }

    public void setOnContinueClickListener(OnContinueClickListener listener) {

        this.listener = listener;
    }
}
