package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.ui.course.CourseArrangeFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ArrangeListAdapter extends BaseQuickAdapter<CoursePackageInfoBean.DataBean.CourseScheduleListBean, BaseViewHolder> {


    private final CourseArrangeFragment courseArrangeFragment;
    private boolean isShow = true;
    private boolean isShowDownload = false;
    private boolean isShowDelete = false;
    private ArrangeListAdapter2 arrangeListAdapter;

    public ArrangeListAdapter(int layoutResId, @Nullable List<CoursePackageInfoBean.DataBean.CourseScheduleListBean> data, CourseArrangeFragment courseArrangeFragment) {
        super(layoutResId, data);
        this.courseArrangeFragment = courseArrangeFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageInfoBean.DataBean.CourseScheduleListBean s) {
        RecyclerView two_rv = baseViewHolder.getView(R.id.two_rv);
        ImageView img_arrow = baseViewHolder.findView(R.id.img_arrow);

        baseViewHolder.findView(R.id.rl_1).setOnClickListener(v -> {
            if (isShow) {
                two_rv.setVisibility(View.VISIBLE);
                img_arrow.setImageDrawable(courseArrangeFragment.getResources().getDrawable(R.drawable.icon_arrow_up));
                isShow = false;

            } else {
                two_rv.setVisibility(View.GONE);
                img_arrow.setImageDrawable(courseArrangeFragment.getResources().getDrawable(R.drawable.icon_arrow_down));
                isShow = true;
            }
        });
        baseViewHolder.setText(R.id.tv_one_title, s.courseName);
        two_rv.setLayoutManager(new LinearLayoutManager(courseArrangeFragment.getContext()));
        arrangeListAdapter = new ArrangeListAdapter2(R.layout.item_two_list, s.chapterList, courseArrangeFragment);
        arrangeListAdapter.setIsShowDownload(isShowDownload);
        arrangeListAdapter.isShowDelete(isShowDelete);
        two_rv.setAdapter(arrangeListAdapter);
    }

    public void setIsShowDownload(boolean isShow) {
        this.isShowDownload = isShow;
    }


    public void isShowDelete(boolean isShow) {
        this.isShowDelete = isShow;
    }
}
