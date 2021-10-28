package com.jiangjun.library.utils;

import android.net.Uri;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by jiangjun on 2020/9/3 19:52
 */
public class FrescoUtils {

    public static void setImageUrl(SimpleDraweeView image_pic, String url) {
        if (StringUtil.isBlank(url)) {
            return;
        }

        GenericDraweeHierarchy hierarchy = image_pic.getHierarchy();
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
        DraweeController mDraweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(url)//设置uri
                .build();
        //设置Controller
        image_pic.setController(mDraweeController);
    }

    public static void setImageGifUrl(SimpleDraweeView image_pic, int url) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(url))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        image_pic.setController(controller);
    }
}
