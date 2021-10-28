package com.bjsn909429077.stz.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bjsn909429077.stz.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jiangjun.library.utils.ImageLoaderUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import cn.finalteam.rxgalleryfinal.imageloader.GlideImageLoader;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/22 22:25
 **/
public class MyAnsweredPicsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;

    public MyAnsweredPicsAdapter(int layoutResId, @Nullable List<String> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        ImageView pic_iv = baseViewHolder.itemView.findViewById(R.id.pic_iv);
        ImageLoaderUtils.loadCircleCropUrl(mContext,s,pic_iv);

//        Glide.with(mContext).load(s)
//                .asBitmap().centerCrop()
//                .into(new BitmapImageViewTarget(pic_iv) {
//                    @Override
//                    protected void setResource(Bitmap resource) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        pic_iv.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
    }
}
