package com.jiangjun.library.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.jiangjun.library.R;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ImageLoaderUtils;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.StatusBarUtil;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.widget.photo.PhotoView;


/**
 * 查看图片
 */
public class PhotoActivity extends BaseActivity {

    private PhotoView photoView;
    public Context mContext;

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        photoView = findViewById(R.id.img);
        ImageView imageView = findViewById(R.id.imageView);
        // 启用图片缩放功能
        photoView.enable();
        mContext = this;
        RLog.e("Activity", "This is ------->" + getClass().getSimpleName());

        String url = getIntent().getStringExtra("url");


        ImageLoaderUtils.loadUrl(mContext, url, photoView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_photo;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
}
