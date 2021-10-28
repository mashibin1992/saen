package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.bean.CoursePackageList;
import com.bjsn909429077.stz.ui.course.contract.CourseArrangeContract;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ArrangeStudyListAdapter extends BaseQuickAdapter<CoursePackageList.DataBean, BaseViewHolder> {


    private final CourseArrangeContract courseArrangeContract;
    private boolean isShow = true;
    private boolean isShowDownload = false;
    private boolean isShowDelete = false;
    private ArrangeStudyListAdapter2 arrangeListAdapter;
    private OnItemDeleteAllClickListener listener;

    public ArrangeStudyListAdapter(int layoutResId, @Nullable List<CoursePackageList.DataBean> data, CourseArrangeContract courseArrangeContract) {
        super(layoutResId, data);
        this.courseArrangeContract = courseArrangeContract;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageList.DataBean s) {
        RecyclerView three_rv = baseViewHolder.getView(R.id.three_rv);
        ImageView tv_arrow_icon = baseViewHolder.getView(R.id.tv_arrow_icon);
        TextView tv_delete_all = baseViewHolder.getView(R.id.tv_delete_all);
        baseViewHolder.setText(R.id.tv_two_title, s.name);

        tv_delete_all.setOnClickListener(v -> {
            if (listener != null) {
                listener.itemDeleteAll(s, baseViewHolder.getLayoutPosition());
            }
        });

        if (isShowDelete) {
            tv_delete_all.setVisibility(View.VISIBLE);
        } else {
            tv_delete_all.setVisibility(View.GONE);
        }

        baseViewHolder.findView(R.id.rl_1).setOnClickListener(v -> {
            if (isShow) {
                three_rv.setVisibility(View.VISIBLE);
                tv_arrow_icon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_arrow_up));
                isShow = false;
            } else {
                three_rv.setVisibility(View.GONE);
                tv_arrow_icon.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_arrow_down));
                isShow = true;
            }
        });
        three_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        arrangeListAdapter = new ArrangeStudyListAdapter2(s.hours, courseArrangeContract);
        arrangeListAdapter.isShowDownload(isShowDownload);
        arrangeListAdapter.isShowDelete(isShowDelete);
        three_rv.setAdapter(arrangeListAdapter);


    }


    public interface OnItemDeleteAllClickListener {
        void itemDeleteAll(CoursePackageList.DataBean data, int position);
    }

    public void setOnItemDeleteAllClickListener(OnItemDeleteAllClickListener listener) {
        this.listener = listener;
    }


    public void setIsShowDownload(boolean isShow) {
        this.isShowDownload = isShow;
    }


    public void isShowDelete(boolean isShow) {
        this.isShowDelete = isShow;
    }

}
