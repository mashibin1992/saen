package com.bjsn909429077.stz.adapter;

import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CoursePackageListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TwoLevelAdapter extends BaseQuickAdapter<CoursePackageListBean.DataBean.CourseListBean2, BaseViewHolder> {

    private OnLevelClickListener listener;
    private ArrayList<CoursePackageListBean.DataBean.CourseListBean2> strings;

    public TwoLevelAdapter(ArrayList<CoursePackageListBean.DataBean.CourseListBean2> strings) {
        super(R.layout.item_two_level, strings);
        this.strings = strings;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CoursePackageListBean.DataBean.CourseListBean2 s) {
        TextView tv_branch_class1 = baseViewHolder.getView(R.id.tv_branch_class1);
        baseViewHolder.setText(R.id.tv_branch_class1, s.name);

        if (s.isSelect) {
            tv_branch_class1.setBackground(getContext().getDrawable(R.drawable.shape_class_package_blue));
        } else {
            tv_branch_class1.setBackground(getContext().getDrawable(R.drawable.shape_class_package_gray));
        }

        baseViewHolder.itemView.setOnClickListener(v -> {
            s.isSelect = true;
            for (int i = 0; i < strings.size(); i++) {
                if (i != baseViewHolder.getLayoutPosition()) {
                    strings.get(i).isSelect = false;
                }
            }
            notifyDataSetChanged();
            if (listener != null) {
                listener.itemClick(s, baseViewHolder.getLayoutPosition());
            }
        });
    }

    public interface OnLevelClickListener {
        void itemClick(CoursePackageListBean.DataBean.CourseListBean2 data, int position);
    }

    public void setOnLevelClickListener(OnLevelClickListener listener) {

        this.listener = listener;
    }
}