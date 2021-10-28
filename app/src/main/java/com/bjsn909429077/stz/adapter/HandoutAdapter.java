package com.bjsn909429077.stz.adapter;

import android.view.View;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.CourseWareBean;
import com.bjsn909429077.stz.ui.video.fragment.HandoutFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class HandoutAdapter extends BaseQuickAdapter<CourseWareBean.DataBean, BaseViewHolder> {


    private final BaseLazyLoadFragment handoutFragment;
    private OnItemClickListener listener;

    public HandoutAdapter(ArrayList<CourseWareBean.DataBean> strings, BaseLazyLoadFragment handoutFragment) {
        super(R.layout.item_handout, strings);
        this.handoutFragment = handoutFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CourseWareBean.DataBean s) {
        TextView tv_title = baseViewHolder.getView(R.id.tv_title);
        TextView tv_date = baseViewHolder.getView(R.id.tv_date);
        TextView tv_look = baseViewHolder.getView(R.id.tv_look);

        tv_title.setText(s.coursewareName);
        if (s.intime != null && s.intime.contains(" ")) {
            tv_date.setText("上架时间：" + s.intime.split(" ")[0]);
        } else {
            tv_date.setText("上架时间：" + s.intime);
        }

        baseViewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.itemClick(s);
            }
        });

    }

    public interface OnItemClickListener {
        void itemClick(CourseWareBean.DataBean dataBean);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;
    }
}
