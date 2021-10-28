package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.ui.course.contract.CourseArrangeContract;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArrangeListAdapter2 extends BaseQuickAdapter<CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean, BaseViewHolder> {


    private final CourseArrangeContract courseArrangeFragment;
    private boolean isShow = true;
    private boolean isShowDownload = false;
    private boolean isShowDelete = false;
    private ArrangeListAdapter3 arrangeListAdapter;

    public ArrangeListAdapter2(int layoutResId, @Nullable List<CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean> data, CourseArrangeContract courseArrangeFragment) {
        super(layoutResId, data);
        this.courseArrangeFragment = courseArrangeFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageInfoBean.DataBean.CourseScheduleListBean.ChapterListBean s) {
        RecyclerView three_rv = baseViewHolder.getView(R.id.three_rv);
        ImageView tv_arrow_icon = baseViewHolder.getView(R.id.tv_arrow_icon);
        TextView tv_delete_all = baseViewHolder.findView(R.id.tv_delete_all);
        baseViewHolder.setText(R.id.tv_two_title, s.chapterName);
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
        arrangeListAdapter = new ArrangeListAdapter3(s.classHourList, courseArrangeFragment);
        arrangeListAdapter.isShowDownload(isShowDownload);
        arrangeListAdapter.isShowDelete(isShowDelete);
        tv_delete_all.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
        three_rv.setAdapter(arrangeListAdapter);


    }

    public void setIsShowDownload(boolean isShow) {
        this.isShowDownload = isShow;
    }


    public void isShowDelete(boolean isShow) {
        this.isShowDelete = isShow;
    }

}
