package com.bjsn909429077.stz.utils;

import android.content.res.Resources;
import android.net.Uri;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.bjsn909429077.stz.ui.MyApplication;
import com.jiangjun.library.utils.JiangJunUtils;

/**
 * @Description: java类作用描述
 * @Author: xuzhuchao
 * @CreateDate: 2020/10/23 14:47
 */
public class LoadingImgFrescoUtils {


    /**
     * 加载本地资源图片 ,圆角图片加载
     * @param rid  资源id
     * @param iv  图片控件
     * @param radius  圆角角度
     */
    public static void loadRidImg_round(int rid, SimpleDraweeView iv, int radius){


//        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");

        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setRoundingParams(RoundingParams.fromCornersRadius(JiangJunUtils.dp2px(MyApplication.getApplication(),radius)))
                .setFadeDuration(500)
                .build();
        iv.setHierarchy(hierarchy);
        iv.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(rid)).build());
    }

    /**
     *加载手机内的本地文件, 圆角图片加载
     * @param path  本地或网络图片路径
     * @param iv  图片控件
     * @param radius  圆角角度
     */
    public static void loadFileUrlImg_round(String path, SimpleDraweeView iv, int radius){

        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setRoundingParams(RoundingParams.fromCornersRadius(JiangJunUtils.dp2px(MyApplication.getApplication(),radius)))
                .setFadeDuration(500)
                .build();
        iv.setHierarchy(hierarchy);
        if (path.toLowerCase().startsWith("http://") || path.toLowerCase().startsWith("https://")) {
            Uri uri = Uri.parse(path);
            iv.setImageURI(uri);
        }else {

            iv.setImageURI((new Uri.Builder()).scheme("file").path(String.valueOf(path)).build());
        }
    }


    /**
     * 加载本地资源图片 ,圆形图片加载
     * @param rid  资源id
     * @param iv  图片控件
     */
    public static void loadRidImg_circle( int rid, SimpleDraweeView iv){


        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setRoundingParams(RoundingParams.asCircle())
                .setFadeDuration(500)
                .build();
        iv.setHierarchy(hierarchy);
        iv.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(rid)).build());
    }

    /**
     *加载手机内的本地文件, 圆形图片加载
     * @param path  本地或网络图片路径
     * @param iv  图片控件
     */
    public static void loadFileUrlImg_circle(String path, SimpleDraweeView iv){

        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setRoundingParams(RoundingParams.asCircle())
                .setFadeDuration(500)
                .build();
        iv.setHierarchy(hierarchy);
        if (path.toLowerCase().startsWith("http://") || path.toLowerCase().startsWith("https://")) {
            Uri uri = Uri.parse(path);
            iv.setImageURI(uri);
        }else {

            iv.setImageURI((new Uri.Builder()).scheme("file").path(String.valueOf(path)).build());
        }
    }


    /**
     * 加载本地资源图片 ,圆形带边框图片加载
     * @param rid  资源id
     * @param iv  图片控件
     * @param color  圆圈的颜色, 需要十六进制的色值, 如0xFF333333
     */
    public static void loadRidImg_circle_line(int rid, SimpleDraweeView iv , int color){

        RoundingParams rp = new RoundingParams();
        rp.setRoundAsCircle(true);
        rp.setBorder(color, JiangJunUtils.dp2px(MyApplication.getApplication(), 2));

        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setRoundingParams(rp)
                .setFadeDuration(500)
                .build();
        iv.setHierarchy(hierarchy);
        iv.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(rid)).build());
    }

    /**
     *加载手机内的本地文件, 圆形带边框加载
     * @param path  本地或网络图片路径
     * @param iv  图片控件
     * @param color  圆圈的颜色, 需要十六进制的色值, 如0xFF333333
     */
    public static void loadFileUrlImg_circle_line(String path, SimpleDraweeView iv , int color){

        RoundingParams rp = new RoundingParams();
        rp.setRoundAsCircle(true);
        rp.setBorder(color, JiangJunUtils.dp2px(MyApplication.getApplication(), 2));

        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(Resources.getSystem())
                .setRoundingParams(rp)
                .setFadeDuration(500)
                .build();
        iv.setHierarchy(hierarchy);


        if (path.toLowerCase().startsWith("http://") || path.toLowerCase().startsWith("https://")) {
            Uri uri = Uri.parse(path);
            iv.setImageURI(uri);
        }else {

            iv.setImageURI((new Uri.Builder()).scheme("file").path(String.valueOf(path)).build());
        }
    }

}
