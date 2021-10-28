package com.bjsn909429077.stz.adapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageBean;
import com.bjsn909429077.stz.ui.course.SelectCourseListFragment;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SelectCourseListAdapter extends BaseQuickAdapter<CoursePackageBean.DataDTO, BaseViewHolder> {
    private final SelectCourseListFragment selectCourseListFragment;

    public SelectCourseListAdapter(ArrayList<CoursePackageBean.DataDTO> strings, SelectCourseListFragment selectCourseListFragment) {
        super(R.layout.item_select_course, strings);
        this.selectCourseListFragment = selectCourseListFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageBean.DataDTO courseSelectBean) {
        if (baseViewHolder.getLayoutPosition() == 0) {
            baseViewHolder.getView(R.id.line2).setVisibility(View.VISIBLE);
        }

        ImageView img_bit = baseViewHolder.getView(R.id.img_bit);//图片
        TextView is_free = baseViewHolder.getView(R.id.is_free);//左上角免费
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);//标题
        TextView tv_sub_title = baseViewHolder.getView(R.id.tv_sub_title);//说明
        TextView tv_discount = baseViewHolder.getView(R.id.tv_discount);//价格
        TextView tv_original_price = baseViewHolder.getView(R.id.tv_original_price);//原价

        tv_title.setText(courseSelectBean.courseName);
        tv_sub_title.setText(courseSelectBean.courseSecondName);

        ImageLoaderUtils.loadUrl(getContext(),courseSelectBean.coverPath,img_bit);
        tv_original_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        tv_original_price.setText("¥" + courseSelectBean.price);

        switch (courseSelectBean.isFree) {
            case 0:
                is_free.setVisibility(View.VISIBLE);
                tv_original_price.setVisibility(View.GONE);
                tv_discount.setText("免费");
                break;
            case 1:
                is_free.setVisibility(View.GONE);
                tv_discount.setText("¥" + courseSelectBean.discountPrice);
                break;
        }

        baseViewHolder.itemView.setOnClickListener(v -> {
            selectCourseListFragment.listItemOnClick(baseViewHolder.getLayoutPosition());
        });

    }


}
