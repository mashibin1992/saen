package com.bjsn909429077.stz.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.PackageListBean;
import com.bjsn909429077.stz.bean.PackageListBean2;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ImageLoaderUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LiveCourse2Adapter extends BaseQuickAdapter<PackageListBean2.DataBean, BaseViewHolder> {
    private final BaseLazyLoadFragment noteFragment;
    private OnItemClickListener onItemClickListener;

    public LiveCourse2Adapter(ArrayList<PackageListBean2.DataBean> strings, BaseLazyLoadFragment noteFragment) {
        super(R.layout.item_live_course, strings);
        this.noteFragment = noteFragment;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PackageListBean2.DataBean s) {
        ImageView img_cover = baseViewHolder.findView(R.id.img_cover);
        TextView tv_title = baseViewHolder.findView(R.id.textView);
        if (!TextUtil.isEmpty(s.coverPhotoPath)) {
            ImageLoaderUtils.loadUrl(getContext(),s.coverPhotoPath,img_cover);
        }
        if (!TextUtil.isEmpty(s.name)) {
            tv_title.setText(s.name);
        }

        baseViewHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(s, baseViewHolder.getLayoutPosition());
            }
        });
    }

    public interface OnItemClickListener {
        void onClick(PackageListBean2.DataBean dataBean, int position);
    }

    public void setonItemListener(OnItemClickListener onItemClickListener) {


        this.onItemClickListener = onItemClickListener;
    }

}