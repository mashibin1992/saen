package com.bjsn909429077.stz.ui;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import androidx.multidex.MultiDex;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.Const;
import com.easefun.polyv.livecommon.module.config.PLVLiveSDKConfig;
import com.example.weblibrary.Bean.TitleTheme;
import com.example.weblibrary.CallBack.InitCallback;
import com.example.weblibrary.Manager.VP53Manager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.jiangjun.library.ui.base.ActivityLifecycleListener;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.StringUtil;
import com.umeng.commonsdk.UMConfigure;

import imageloader.libin.com.images.loader.ImageLoader;

/**
 * Created by jiangjun on 2020/3/30 15:27
 */
public class MyApplication extends Application {
    private static  MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        RangerEvent.init();


        UserConfig.init(this);


        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);


        //注册生命周期监听
        registerActivityLifecycleCallbacks(new ActivityLifecycleListener());




        initAllSDK();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);

    }

    /**
     * 获取application 对象
     *
     * @return
     */
    public static MyApplication getApplication() {
        return context;
    }

    /**
     * 初始化所有第三方SDK
     */
    public void initAllSDK(){


        if(!StringUtil.isBlank(UserConfig.getString("UserAgreement", ""))){


            initKF53();

            //保利威
            PLVLiveSDKConfig.init(
                    new PLVLiveSDKConfig.Parameter(this)
                            .isOpenDebugLog(true)
                            .isEnableHttpDns(false)
            );


            //初始化友盟一键登录SDK
            UMConfigure.init(context, Const.UM_VERIFY_AppKey,"Umeng",UMConfigure.DEVICE_TYPE_PHONE,null);
        }



    }

    /**
     * 初始化53客服
     */
    private void initKF53(){


        VP53Manager.getInstance().initSDK(Const.KF_53_APP_ID, Const.KF_53_arg, true, this, new InitCallback() {
            @Override
            public void onInitSuccess() {
                RLog.e("TAG", "53客服初始化成功");
                VP53Manager.getInstance().registerPush("","");
            }

            @Override
            public void onInitError(String s) {

                RLog.e("TAG", "53客服初始化失败"+s);
            }
        });

        // 此处会自动去除颜色透明度
        VP53Manager.getInstance()
                .getChatConfigManager()
                .setStatusBarColor(getResources().getColor(R.color.white))
                .setToolbarVisible(true)
                .setTitleBackImage(R.drawable.ic_arrow_back_20dp)
                .setTitleBackgroundColor(getResources().getColor(R.color.white))
                .setTitleTheme(TitleTheme.black)
                .setTitleElevationHeight(1)
                .setSystemTipsBackgroundColor(getResources().getColor(R.color.white))
                .setSystemTipsTextColor(getResources().getColor(R.color.color_999999))
                .setRightChatBubbleBackgroundColor(getResources().getColor(R.color.mainColor))
                .setRightChatTextColor(Color.WHITE)
                .setRightChatBubbleRadius(5)
                .setLeftChatBubbleBackgroundColor(getResources().getColor(R.color.color_D5D5D5))
                .setLeftChatTextColor(Color.BLACK)
                .setLeftChatBubbleRadius(5)
                .setRefreshingHeaderText("下拉刷新")
                .setRefreshingNoMoreHeaderText("没有更多消息啦")
                .setWelcomeText("欢迎您的咨询，期待为您服务")
                .setRefreshingHeaderTextColor(getResources().getColor(R.color.mainColor))
                .apply();
    }



}
