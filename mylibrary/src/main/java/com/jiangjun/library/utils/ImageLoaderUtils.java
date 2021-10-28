package com.jiangjun.library.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jiangjun.library.R;


public class ImageLoaderUtils {

    /***
     * @param Context context
     * @param Url url
     * @param ImageView imageview
     */

    /***
     * 不加缓存
     */

    public static void loadUrl(Context Context, String Url, ImageView ImageView) {
        if (!StringUtil.isBlank(Url)) {
            RequestOptions options = new RequestOptions();
            options.centerCrop();
            Glide.with(Context)
                    .load(Url)
                    .apply(options)
                    .fitCenter()
                    .into(ImageView);
        }
    }


    /**
     * 加载圆形图片
     * @param context
     * @param Url
     * @param ImageView
     */
    public static void loadCircleCropUrl(Context context, String Url, ImageView ImageView) {
        RequestOptions options = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(false);//不做内存缓存

        Glide.with(context).load(Url).apply(options).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                return false;
            }

            @Override
            public boolean onResourceReady(Drawable drawable, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                ImageView.setImageDrawable(drawable);

                return false;
            }
        }).submit();
    }



    /***
     * 不加缓存
     */
    public static void loadUrl(Context Context, String Url, int placeHolder, ImageView ImageView) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(Context)
                .load(Url)
                .apply(options)
                .placeholder(placeHolder)//图片加载出来前，显示的图片
                .error(placeHolder)//图片加载失败后，显示的图片
                .into(ImageView);
    }

    /***
     * 加载资源图片
     */
    public static void loadRes(Context Context, int res, ImageView ImageView) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(Context)
                .load(res)
                .apply(options)
                .load(res)
                .into(ImageView);
    }



    /***
     * 加载资源图片
     */
    public static void loadResGif(Context Context, int res, ImageView ImageView) {
        Glide.with(Context)
                .asGif()
                .load(res)
                .apply(new RequestOptions()
                        .priority(Priority.HIGH)
                        .fitCenter())
                .into(ImageView);
    }
}
