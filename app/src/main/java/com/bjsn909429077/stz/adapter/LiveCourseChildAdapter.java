package com.bjsn909429077.stz.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.PackageListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ImageLoaderUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LiveCourseChildAdapter extends BaseQuickAdapter<PackageListBean.DataBean, BaseViewHolder> {
    private final BaseLazyLoadFragment noteFragment;
    private OnItemClickListener onItemClickListener;

    public LiveCourseChildAdapter(ArrayList<PackageListBean.DataBean> strings, BaseLazyLoadFragment noteFragment) {
        super(R.layout.item_live_course_2, strings);
        this.noteFragment = noteFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PackageListBean.DataBean s) {
        ImageView img_cover = baseViewHolder.findView(R.id.img_cover);
        TextView tv_title = baseViewHolder.findView(R.id.tv_title);
        if (!TextUtil.isEmpty(s.coverPath)) {
            ImageLoaderUtils.loadUrl(getContext(),s.coverPath,img_cover);
        }
        if (!TextUtil.isEmpty(s.packageName)) {
            tv_title.setText(s.packageName);
        }

        baseViewHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(s, baseViewHolder.getLayoutPosition());
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(PackageListBean.DataBean dataBean, int position);
    }

    public void setonItemListener(OnItemClickListener onItemClickListener) {


        this.onItemClickListener = onItemClickListener;
    }

}