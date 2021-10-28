package com.bjsn909429077.stz.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.Const;
import com.easefun.polyv.livecloudclass.scenes.PLVLCCloudClassActivity;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.config.PLVLiveScene;
import com.easefun.polyv.livecommon.module.utils.result.PLVLaunchResult;
import com.easefun.polyv.livescenes.config.PolyvLiveChannelType;
import com.easefun.polyv.livescenes.feature.login.IPLVSceneLoginManager;
import com.easefun.polyv.livescenes.feature.login.PLVSceneLoginManager;
import com.easefun.polyv.livescenes.feature.login.PolyvLiveLoginResult;
import com.easefun.polyv.livescenes.feature.login.PolyvPlaybackLoginResult;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackListType;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.ToastUtils;
import com.plv.foundationsdk.log.PLVCommonLog;

import static cn.finalteam.rxgalleryfinal.utils.Logger.TAG;

/**
 * @author XuZhuChao
 * @create 2021/9/29
 * @Describe
 */
public class InLiveUtils {

    private Activity mContext;
    private String channelId;
    private String vId;
    //manager
    private static IPLVSceneLoginManager loginLiveManager;
    private static ProgressDialog loginProgressDialog;

    public IPLVSceneLoginManager getLoginLiveManager(){

        if (loginLiveManager == null) {
            //初始化登录管理器
            loginLiveManager = new PLVSceneLoginManager();
        }
        return loginLiveManager;
    }

    /**
     *
     * @param mContext
     * @param channelId 直播间ID
     * @param vId  回放ID
     *             看直播时，回放ID 不需要传
     */
    public InLiveUtils(Activity mContext, String channelId, String vId) {
        this.mContext = mContext;
        this.channelId = channelId;
        this.vId = vId;
        getLoginLiveManager();
        initDialog();
    }


    /**
     * @author XuZhuChao
     * @create 2021/9/29
     * @Describe 进入直播间
     */
    public void inLive(){

        loginProgressDialog.show();
        loginLiveManager.loginLive(Const.BLW_APP_ID, Const.BLW_APP_SECRET, Const.BLW_USER_ID, channelId, new IPLVSceneLoginManager.OnLoginListener<PolyvLiveLoginResult>() {
            @Override
            public void onLoginSuccess(PolyvLiveLoginResult polyvLiveLoginResult) {
                loginProgressDialog.dismiss();
                PLVLiveChannelConfigFiller.setupAccount(Const.BLW_USER_ID, Const.BLW_APP_ID, Const.BLW_APP_SECRET);
                PolyvLiveChannelType channelType = polyvLiveLoginResult.getChannelType();
                if (PLVLiveScene.isCloudClassSceneSupportType(channelType)) {
                    PLVLaunchResult launchResult = PLVLCCloudClassActivity.launchLive(mContext, channelId, channelType, UserConfig.getUser().getId(), UserConfig.getUser().getNickName(), UserConfig.getUser().getHeadPic());
                    if (!launchResult.isSuccess()) {
                        ToastUtils.Toast(mContext,launchResult.getErrorMessage());
                    }

                } else {
                    ToastUtils.Toast(mContext,mContext.getString(R.string.plv_scene_login_toast_cloudclass_no_support_type));
                }
            }

            @Override
            public void onLoginFailed(String msg, java.lang.Throwable throwable) {
                loginProgressDialog.dismiss();
                ToastUtils.Toast(mContext,msg);
                PLVCommonLog.e(TAG,"loginLive onLoginFailed:"+throwable.getMessage());
            }
        });
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/29
     * @Describe 进入直播回放
     */
    public void inPlayback(){

        loginProgressDialog.show();
        loginLiveManager.loginPlayback(Const.BLW_APP_ID, Const.BLW_APP_SECRET, Const.BLW_USER_ID, channelId, vId, new IPLVSceneLoginManager.OnLoginListener<PolyvPlaybackLoginResult>() {
            @Override
            public void onLoginSuccess(PolyvPlaybackLoginResult polyvPlaybackLoginResult) {
                loginProgressDialog.dismiss();
                PLVLiveChannelConfigFiller.setupAccount( Const.BLW_USER_ID, Const.BLW_APP_ID, Const.BLW_APP_SECRET);
                PolyvLiveChannelType channelType = polyvPlaybackLoginResult.getChannelType();
                if (PLVLiveScene.isCloudClassSceneSupportType(channelType)) {
                    PLVLaunchResult launchResult = PLVLCCloudClassActivity.launchPlayback(mContext, channelId, channelType,
                            vId,  UserConfig.getUser().getId(), UserConfig.getUser().getNickName(), UserConfig.getUser().getHeadPic(),
                            false ? PolyvPlaybackListType.VOD : PolyvPlaybackListType.PLAYBACK
                    );
                    if (!launchResult.isSuccess()) {
                        ToastUtils.Toast(mContext,launchResult.getErrorMessage());
                    }
                } else {
                    ToastUtils.Toast(mContext,mContext.getString(R.string.plv_scene_login_toast_cloudclass_no_support_type));
                }
            }

            @Override
            public void onLoginFailed(String msg, Throwable throwable) {
                loginProgressDialog.dismiss();
                com.plv.thirdpart.blankj.utilcode.util.ToastUtils.showShort(msg);
                PLVCommonLog.e(TAG,"loginPlayback onLoginFailed:"+throwable.getMessage());
            }
        });
    }



    private void initDialog() {

        if (loginProgressDialog == null) {
            loginProgressDialog = new ProgressDialog(mContext);
            loginProgressDialog.setMessage("正在进入，请稍等...");
            loginProgressDialog.setCanceledOnTouchOutside(false);
            loginProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    loginLiveManager.destroy();
                }
            });
        }

    }

    public static void destroy(){
        if (loginLiveManager != null) {
            loginLiveManager.destroy();
        }
        if (loginProgressDialog != null) {
            loginProgressDialog.dismiss();
            loginProgressDialog = null;
        }
    }

}
