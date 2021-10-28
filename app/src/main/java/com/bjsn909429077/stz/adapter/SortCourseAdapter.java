package com.bjsn909429077.stz.adapter;

import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.StudyDirectionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.ui.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SortCourseAdapter extends BaseQuickAdapter<StudyDirectionBean.DataBean, BaseViewHolder> {
    private List<StudyDirectionBean.DataBean> courseList;
    private final BaseActivity sortActivity;
    private OnSoftItemClickListener listener;
    private TextView tv_tab;
    private int mPosition = -1;


    public SortCourseAdapter(int layoutResId, List<StudyDirectionBean.DataBean> courseList, BaseActivity sortActivity) {
        super(layoutResId, courseList);
        this.courseList = courseList;
        this.sortActivity = sortActivity;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StudyDirectionBean.DataBean s) {
        tv_tab = baseViewHolder.getView(R.id.tv_tab);
        baseViewHolder.setText(R.id.tv_tab, s.name);

        if (s.isSelect) {
            tv_tab.setBackground(sortActivity.getResources().getDrawable(R.drawable.sort_tab_bg_blue));
        } else {
            tv_tab.setBackground(sortActivity.getResources().getDrawable(R.drawable.sort_btn_bg_gray));
        }

        tv_tab.setOnClickListener(v -> {
            s.isSelect = true;
            for (int i = 0; i < courseList.size(); i++) {
                if (i != baseViewHolder.getLayoutPosition()) {
                    courseList.get(i).isSelect = false;
                }
            }
            notifyDataSetChanged();
            if (listener != null) {
                listener.onSoftItemClick(baseViewHolder.getLayoutPosition());
            }
        });
    }

    public interface OnSoftItemClickListener {
        void onSoftItemClick(int position);
    }

    public void setOnSoftItemClickListener(OnSoftItemClickListener listener) {
        this.listener = listener;
    }
}
