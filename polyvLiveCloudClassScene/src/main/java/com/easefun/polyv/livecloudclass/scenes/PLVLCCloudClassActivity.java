package com.easefun.polyv.livecloudclass.scenes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;

import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout;
import com.easefun.polyv.livecloudclass.modules.linkmic.IPLVLCLinkMicLayout;
import com.easefun.polyv.livecloudclass.modules.linkmic.PLVLCLinkMicControlBar;
import com.easefun.polyv.livecloudclass.modules.liveroom.PLVLCLiveLandscapeChannelController;
import com.easefun.polyv.livecloudclass.modules.media.IPLVLCMediaLayout;
import com.easefun.polyv.livecloudclass.modules.pagemenu.IPLVLCLivePageMenuLayout;
import com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCFloatingPPTLayout;
import com.easefun.polyv.livecloudclass.modules.ppt.IPLVLCPPTView;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.interact.IPLVInteractLayout;
import com.easefun.polyv.livecommon.module.modules.player.PLVPlayerState;
import com.easefun.polyv.livecommon.module.modules.player.playback.prsenter.data.PLVPlayInfoVO;
import com.easefun.polyv.livecommon.module.utils.PLVDialogFactory;
import com.easefun.polyv.livecommon.module.utils.PLVViewSwitcher;
import com.easefun.polyv.livecommon.module.utils.listener.IPLVOnDataChangedListener;
import com.easefun.polyv.livecommon.module.utils.result.PLVLaunchResult;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;
import com.easefun.polyv.livecommon.ui.window.PLVBaseActivity;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.config.PolyvLiveChannelType;
import com.easefun.polyv.livescenes.linkmic.manager.PolyvLinkMicConfig;
import com.easefun.polyv.livescenes.playback.video.PolyvPlaybackListType;
import com.easefun.polyv.livescenes.video.api.IPolyvLiveListenerEvent;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/**
 * date: 2020/10/12
 * author: HWilliamgo
 * ??????????????????????????? ??????????????????????????? ??? ???????????????
 * ????????????????????????????????????????????????????????????PPT(?????????????????????)???????????????????????????
 * ????????????????????????????????????????????????????????????PPT(?????????????????????)???
 */
public class PLVLCCloudClassActivity extends PLVBaseActivity {

    // <editor-fold defaultstate="collapsed" desc="??????">
    // ?????? - ??????????????????????????????
    private static final String EXTRA_CHANNEL_ID = "channelId";   // ?????????
    private static final String EXTRA_VIEWER_ID = "viewerId";   // ?????????Id
    private static final String EXTRA_VIEWER_NAME = "viewerName";   // ???????????????
    private static final String EXTRA_VIEWER_AVATAR = "viewerAvatar";//?????????????????????
    private static final String EXTRA_VID = "vid";//????????????Id
    private static final String EXTRA_VIDEO_LIST_TYPE = "video_list_type";//??????????????????
    private static final String EXTRA_IS_LIVE = "is_live";//???????????????
    private static final String EXTRA_CHANNEL_TYPE = "channel_type";//????????????

    // ???????????????????????????????????????????????????????????????
    private IPLVLiveRoomDataManager liveRoomDataManager;

    // View
    // ???????????????
    private IPLVLCMediaLayout mediaLayout;
    // ????????????????????????
    private IPLVLCLivePageMenuLayout livePageMenuLayout;
    // ??????PPT??????
    private IPLVLCFloatingPPTLayout floatingPPTLayout;
    // ????????????
    @Nullable
    private IPLVLCLinkMicLayout linkMicLayout;
    // ??????????????????
    @Nullable
    private IPLVInteractLayout interactLayout;

    // ??????PPT?????? ??? ??????????????? ????????????
    private PLVViewSwitcher pptViewSwitcher = new PLVViewSwitcher();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????Activity?????????">

    /**
     * ???????????????
     *
     * @param activity    ?????????Activity
     * @param channelId   ?????????
     * @param channelType ????????????
     * @param viewerId    ??????ID
     * @param viewerName  ????????????
     * @return PLVLaunchResult.isSuccess=true?????????????????????PLVLaunchResult.isSuccess=false??????????????????
     */
    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static PLVLaunchResult launchLive(@NonNull Activity activity,
                                             @NonNull String channelId,
                                             @NonNull PolyvLiveChannelType channelType,
                                             @NonNull String viewerId,
                                             @NonNull String viewerName,
                                             @NonNull String viewerAvatar) {
        if (activity == null) {
            return PLVLaunchResult.error("activity ??????????????????????????????????????????");
        }
        if (TextUtils.isEmpty(channelId)) {
            return PLVLaunchResult.error("channelId ??????????????????????????????????????????");
        }
        if (channelType == null) {
            return PLVLaunchResult.error("channelType ??????????????????????????????????????????");
        }
        if (TextUtils.isEmpty(viewerId)) {
            return PLVLaunchResult.error("viewerId ??????????????????????????????????????????");
        }
        if (TextUtils.isEmpty(viewerName)) {
            return PLVLaunchResult.error("viewerName ??????????????????????????????????????????");
        }

        Intent intent = new Intent(activity, PLVLCCloudClassActivity.class);
        intent.putExtra(EXTRA_CHANNEL_ID, channelId);
        intent.putExtra(EXTRA_CHANNEL_TYPE, channelType);
        intent.putExtra(EXTRA_VIEWER_ID, viewerId);
        intent.putExtra(EXTRA_VIEWER_NAME, viewerName);
        intent.putExtra(EXTRA_VIEWER_AVATAR, viewerAvatar);
        intent.putExtra(EXTRA_IS_LIVE, true);
        activity.startActivity(intent);
        return PLVLaunchResult.success();
    }

    /**
     * ??????????????????
     *
     * @param activity      ?????????Activity
     * @param channelId     ?????????
     * @param channelType   ????????????
     * @param vid           ??????ID
     * @param viewerId      ??????ID
     * @param viewerName    ????????????
     * @param videoListType ???????????????????????????{@link PolyvPlaybackListType}
     * @return PLVLaunchResult.isSuccess=true?????????????????????PLVLaunchResult.isSuccess=false??????????????????
     */
    @SuppressWarnings("ConstantConditions")
    @NonNull
    public static PLVLaunchResult launchPlayback(@NonNull Activity activity,
                                                 @NonNull String channelId,
                                                 @NonNull PolyvLiveChannelType channelType,
                                                 @NonNull String vid,
                                                 @NonNull String viewerId,
                                                 @NonNull String viewerName,
                                                 @NonNull String viewerAvatar,
                                                 int videoListType) {
        if (activity == null) {
            return PLVLaunchResult.error("activity ??????????????????????????????????????????");
        }
        if (TextUtils.isEmpty(channelId)) {
            return PLVLaunchResult.error("channelId ??????????????????????????????????????????");
        }
        if (channelType == null) {
            return PLVLaunchResult.error("channelType ??????????????????????????????????????????");
        }
        if (TextUtils.isEmpty(vid)) {
            return PLVLaunchResult.error("vid ??????????????????????????????????????????");
        }
        if (TextUtils.isEmpty(viewerId)) {
            return PLVLaunchResult.error("viewerId ??????????????????????????????????????????");
        }
        if (TextUtils.isEmpty(viewerName)) {
            return PLVLaunchResult.error("viewerName ??????????????????????????????????????????");
        }

        Intent intent = new Intent(activity, PLVLCCloudClassActivity.class);
        intent.putExtra(EXTRA_CHANNEL_ID, channelId);
        intent.putExtra(EXTRA_CHANNEL_TYPE, channelType);
        intent.putExtra(EXTRA_VIEWER_ID, viewerId);
        intent.putExtra(EXTRA_VIEWER_NAME, viewerName);
        intent.putExtra(EXTRA_VIEWER_AVATAR, viewerAvatar);
        intent.putExtra(EXTRA_VID, vid);
        intent.putExtra(EXTRA_VIDEO_LIST_TYPE, videoListType);
        intent.putExtra(EXTRA_IS_LIVE, false);
        activity.startActivity(intent);
        return PLVLaunchResult.success();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plvlc_cloudclass_activity);
        changStatusIconCollor(this,false);
        setStatusBarColor(this,R.color.black);

        initParams();
        initLiveRoomManager();
        initView();

        observeMediaLayout();
        observeLinkMicLayout();
        observePageMenuLayout();
        observePPTView();
    }

    /**
     * ????????????????????????????????????
     * true:?????? false?????????
     * @param activity
     * @param setDark
     */

    public void changStatusIconCollor(Activity activity,boolean setDark) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            View decorView = activity.getWindow().getDecorView();
            if(decorView != null){
                int vis = decorView.getSystemUiVisibility();
                if(setDark){
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else{
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * ??????????????????????????????4.4????????????
     * @param activity
     * @param colorId
     */
    public void setStatusBarColor(Activity activity, int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaLayout != null) {
            mediaLayout.destroy();
        }
        if (linkMicLayout != null) {
            linkMicLayout.destroy();
        }
        if (livePageMenuLayout != null) {
            livePageMenuLayout.destroy();
        }
        if (interactLayout != null) {
            interactLayout.destroy();
        }
        if (floatingPPTLayout != null) {
            floatingPPTLayout.destroy();
        }
        if (liveRoomDataManager != null) {
            liveRoomDataManager.destroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (interactLayout != null && interactLayout.onBackPress()) {
            return;
        } else if (mediaLayout != null && mediaLayout.onBackPressed()) {
            return;
        } else if (livePageMenuLayout != null && livePageMenuLayout.onBackPressed()) {
            return;
        }

        //?????????????????????????????????
        PLVDialogFactory.createConfirmDialog(
                this,
                getResources().getString(
                        liveRoomDataManager.getConfig().isLive()
                                ? R.string.plv_live_room_dialog_exit_confirm_ask
                                : R.string.plv_playback_room_dialog_exit_confirm_ask
                ),
                getResources().getString(R.string.plv_common_dialog_exit),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PLVLCCloudClassActivity.super.onBackPressed();
                    }
                }
        ).show();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????">
    private void initParams() {
        // ??????????????????
        Intent intent = getIntent();
        boolean isLive = intent.getBooleanExtra(EXTRA_IS_LIVE, true);
        PolyvLiveChannelType channelType = (PolyvLiveChannelType) intent.getSerializableExtra(EXTRA_CHANNEL_TYPE);
        String channelId = intent.getStringExtra(EXTRA_CHANNEL_ID);
        String viewerId = intent.getStringExtra(EXTRA_VIEWER_ID);
        String viewerName = intent.getStringExtra(EXTRA_VIEWER_NAME);
        String viewerAvatar = intent.getStringExtra(EXTRA_VIEWER_AVATAR);

        // ??????Config??????
        PLVLiveChannelConfigFiller.setIsLive(isLive);
        PLVLiveChannelConfigFiller.setChannelType(channelType);
        PLVLiveChannelConfigFiller.setupUser(viewerId, viewerName, viewerAvatar,
                PolyvLinkMicConfig.getInstance().getLiveChannelType() == PolyvLiveChannelType.PPT
                        ? PLVSocketUserConstant.USERTYPE_SLICE : PLVSocketUserConstant.USERTYPE_STUDENT);
        PLVLiveChannelConfigFiller.setupChannelId(channelId);

        // ???????????????????????????????????????
        if (!isLive) { // ????????????
            String vid = intent.getStringExtra(EXTRA_VID);
            int videoListType = intent.getIntExtra(EXTRA_VIDEO_LIST_TYPE, PolyvPlaybackListType.PLAYBACK);
            PLVLiveChannelConfigFiller.setupVid(vid);
            PLVLiveChannelConfigFiller.setupVideoListType(videoListType);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????">
    private void initLiveRoomManager() {
        // ??????PLVLiveChannelConfigFiller?????????????????????????????????????????????????????????????????????
        liveRoomDataManager = new PLVLiveRoomDataManager(PLVLiveChannelConfigFiller.generateNewChannelConfig());
        // ?????????????????????????????????????????????
        liveRoomDataManager.requestPageViewer();

        // ?????????????????????????????????????????????
        liveRoomDataManager.requestChannelDetail();

        // ?????????????????????????????????????????????
        liveRoomDataManager.requestChannelSwitch();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ??????UI">
    private void initView() {
        // ?????????ViewStub
        ViewStub videoLyViewStub = findViewById(R.id.plvlc_video_viewstub);

        // ??????????????????
        livePageMenuLayout = findViewById(R.id.plvlc_live_page_menu_layout);
        livePageMenuLayout.init(liveRoomDataManager);

        // ??????PPT??????
        floatingPPTLayout = findViewById(R.id.plvlc_ppt_floating_ppt_layout);

        if (liveRoomDataManager.getConfig().isLive()) {
            // ?????????????????????
            ViewStub landscapeChannelControllerViewStub = findViewById(R.id.plvlc_ppt_landscape_channel_controller);
            PLVLCLiveLandscapeChannelController liveLandscapeChannelController = (PLVLCLiveLandscapeChannelController) landscapeChannelControllerViewStub.inflate();

            // ???????????????
            videoLyViewStub.setLayoutResource(R.layout.plvlc_live_media_layout_view_stub);
            mediaLayout = (IPLVLCMediaLayout) videoLyViewStub.inflate();
            mediaLayout.init(liveRoomDataManager);
            mediaLayout.setLandscapeControllerView(liveLandscapeChannelController);
            mediaLayout.startPlay();

            // ???????????????
            ViewStub linkmicControllerViewStub = findViewById(R.id.plvlc_ppt_linkmic_controller);
            PLVLCLinkMicControlBar linkMicControlBar = (PLVLCLinkMicControlBar) linkmicControllerViewStub.inflate();

            // ????????????
            ViewStub linkmicLayoutViewStub = findViewById(R.id.plvlc_linkmic_viewstub);
            linkMicLayout = (IPLVLCLinkMicLayout) linkmicLayoutViewStub.inflate();
            linkMicLayout.init(liveRoomDataManager, linkMicControlBar);
            linkMicLayout.hideAll();

            // ??????????????????
            ViewStub interactLayoutViewStub = findViewById(R.id.plvlc_ppt_interact_layout);
            interactLayout = (IPLVInteractLayout) interactLayoutViewStub.inflate();
            interactLayout.init();
        } else {
            // ???????????????
            videoLyViewStub.setLayoutResource(R.layout.plvlc_playback_media_layout_view_stub);
            mediaLayout = (IPLVLCMediaLayout) videoLyViewStub.inflate();
            mediaLayout.init(liveRoomDataManager);
            mediaLayout.setPPTView(floatingPPTLayout.getPPTView().getPlaybackPPTViewToBindInPlayer());
            mediaLayout.startPlay();
        }

        // ??????????????????????????????
        PLVLCChatLandscapeLayout chatLandscapeLayout = mediaLayout.getChatLandscapeLayout();
        chatLandscapeLayout.init(livePageMenuLayout.getChatCommonMessageList());
        livePageMenuLayout.getChatroomPresenter().registerView(chatLandscapeLayout.getChatroomView());

        // ?????? ??????PPT?????? ??? ??????????????? ????????????
        pptViewSwitcher.registerSwitchVew(floatingPPTLayout.getPPTSwitchView(), mediaLayout.getPlayerSwitchView());

        // ????????? ????????????
        if (ScreenUtils.isPortrait()) {
            PLVScreenUtils.enterPortrait(this);
        } else {
            PLVScreenUtils.enterLandscape(this);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????????????? - ?????????">
    private void observeMediaLayout() {
        //??????view???????????????
        mediaLayout.setOnViewActionListener(new IPLVLCMediaLayout.OnViewActionListener() {
            @Override
            public void onClickShowOrHideSubTab(boolean toShow) {
                if (liveRoomDataManager.getConfig().isLive()) {
                    if (linkMicLayout == null) {
                        return;
                    }
                    if (linkMicLayout.isJoinChannel()) {
                        if (toShow) {
                            linkMicLayout.showAll();
                        } else {
                            linkMicLayout.hideLinkMicList();
                        }
                    } else {
                        if (toShow) {
                            floatingPPTLayout.show();
                        } else {
                            floatingPPTLayout.hide();
                        }
                    }
                } else {
                    if (toShow) {
                        floatingPPTLayout.show();
                    } else {
                        floatingPPTLayout.hide();
                    }
                }
            }

            @Override
            public void onShowMediaController(boolean show) {
                if (liveRoomDataManager.getConfig().isLive()) {
                    if (linkMicLayout == null) {
                        return;
                    }
                    if (show) {
                        linkMicLayout.showControlBar();
                    } else {
                        linkMicLayout.hideControlBar();
                    }
                }
            }

            @Override
            public Pair<Boolean, Integer> onSendChatMessageAction(String message) {
                PolyvLocalMessage localMessage = new PolyvLocalMessage(message);
                return livePageMenuLayout.getChatroomPresenter().sendChatMessage(localMessage);
            }

            @Override
            public void onShowBulletinAction() {
                if (liveRoomDataManager.getConfig().isLive() && interactLayout != null) {
                    interactLayout.showBulletin();
                }
            }

            @Override
            public void onSendLikesAction() {
                livePageMenuLayout.getChatroomPresenter().sendLikeMessage();
            }
        });

        //???????????? ?????? ?????????????????????PPT??????????????????
        mediaLayout.addOnPPTShowStateListener(new IPLVOnDataChangedListener<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isPptVisible) {
                if (isPptVisible == null) {
                    return;
                }
                floatingPPTLayout.setServerEnablePPT(isPptVisible);
            }
        });
        //??????????????????
        mediaLayout.addOnPlayerStateListener(new IPLVOnDataChangedListener<PLVPlayerState>() {
            @Override
            public void onChanged(@Nullable PLVPlayerState playerState) {
                if (playerState == null) {
                    return;
                }
                if (liveRoomDataManager.getConfig().isLive()) {
                    //????????????????????????
                    switch (playerState) {
                        case PREPARED:
                            floatingPPTLayout.show();
                            livePageMenuLayout.updateLiveStatusWithLive();
                            if (linkMicLayout != null) {
                                linkMicLayout.showAll();
                            }
                            break;
                        case LIVE_STOP:
                        case NO_LIVE:
                        case LIVE_END:
                            if (liveRoomDataManager.getConfig().isPPTChannelType()) {
                                //??????????????????????????????????????????PPT??????????????????????????????????????????
                                if (!floatingPPTLayout.isPPTInFloatingLayout()) {
                                    pptViewSwitcher.switchView();
                                }
                            }
                            floatingPPTLayout.hide();
                            livePageMenuLayout.updateLiveStatusWithNoLive();
                            if (linkMicLayout != null) {
                                linkMicLayout.setLiveEnd();
                                linkMicLayout.hideAll();
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    //????????????????????????
                    switch (playerState) {
                        case PREPARED:
                            floatingPPTLayout.show();
                            break;
                        case IDLE:
                            floatingPPTLayout.hide();
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        if (liveRoomDataManager.getConfig().isLive()) {
            //?????????????????????

            //???????????? ?????? ??????????????????????????????->?????????????????????????????????
            mediaLayout.addOnLinkMicStateListener(new IPLVOnDataChangedListener<Pair<Boolean, Boolean>>() {
                @Override
                public void onChanged(@Nullable Pair<Boolean/*??????????????????*/, Boolean/*?????????????????????*/> linkMicState) {
                    if (linkMicState == null) {
                        return;
                    }
                    boolean isLinkMicOpen = linkMicState.first;
                    boolean isAudio = linkMicState.second;
                    if (linkMicLayout == null) {
                        return;
                    }
                    linkMicLayout.setIsTeacherOpenLinkMic(isLinkMicOpen);
                    linkMicLayout.setIsAudio(isAudio);
                }
            });
            //???????????? ?????? ?????????????????????sei??????
            mediaLayout.addOnSeiDataListener(new IPLVOnDataChangedListener<Long>() {
                @Override
                public void onChanged(@Nullable Long aLong) {
                    if (aLong == null) {
                        return;
                    }
                    floatingPPTLayout.getPPTView().sendSEIData(aLong);
                }
            });
            mediaLayout.setOnRTCPlayEventListener(new IPolyvLiveListenerEvent.OnRTCPlayEventListener() {
                @Override
                public void onRTCLiveStart() {
                    if (linkMicLayout != null) {
                        linkMicLayout.setLiveStart();
                    }
                }

                @Override
                public void onRTCLiveEnd() {
                    if (linkMicLayout != null) {
                        linkMicLayout.setLiveEnd();
                    }
                }
            });
        } else {
            //?????????????????????

            mediaLayout.addOnPlayInfoVOListener(new IPLVOnDataChangedListener<PLVPlayInfoVO>() {
                @Override
                public void onChanged(@Nullable PLVPlayInfoVO plvPlayInfoVO) {
                    if (plvPlayInfoVO == null) {
                        return;
                    }
                    //???????????????????????????????????????PPT???
                    floatingPPTLayout.getPPTView().setPlaybackCurrentPosition(plvPlayInfoVO.getPosition());
                }
            });
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????????????? - ????????????">
    private void observePageMenuLayout() {
        //??????view???????????????
        livePageMenuLayout.setOnViewActionListener(new IPLVLCLivePageMenuLayout.OnViewActionListener() {
            @Override
            public void onShowBulletinAction() {
                if (interactLayout != null) {
                    interactLayout.showBulletin();
                }
            }

            @Override
            public void onSendDanmuAction(CharSequence message) {
                mediaLayout.sendDanmaku(message);
            }
        });
        //???????????? ?????? ???????????????????????????????????????
        livePageMenuLayout.addOnViewerCountListener(new IPLVOnDataChangedListener<Long>() {
            @Override
            public void onChanged(@Nullable Long l) {
                if (l == null) {
                    return;
                }
                mediaLayout.updateViewerCount(l);
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????????????? - PPT">
    private void observePPTView() {
        //??????????????????????????????
        floatingPPTLayout.setOnFloatingViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pptViewSwitcher.switchView();
            }
        });
        //???????????????????????????????????????
        floatingPPTLayout.setOnClickCloseListener(new IPLVLCFloatingPPTLayout.IPLVOnClickCloseFloatingView() {
            @Override
            public void onClickCloseFloatingView() {
                mediaLayout.updateOnClickCloseFloatingView();
            }
        });
        if (liveRoomDataManager.getConfig().isLive()) {
            //????????????PPT???????????????
            floatingPPTLayout.getPPTView().initLivePPT(new IPLVLCPPTView.OnPLVLCLivePPTViewListener() {

                @Override
                public void onLiveSwitchPPTViewLocation(boolean toMainScreen) {
                    if (!liveRoomDataManager.getConfig().isPPTChannelType()) {
                        return;
                    }
                    if (linkMicLayout == null || !linkMicLayout.isJoinChannel()) {
                        if (toMainScreen) {
                            if (!pptViewSwitcher.isViewSwitched()) {
                                pptViewSwitcher.switchView();
                            }
                        } else {
                            if (pptViewSwitcher.isViewSwitched()) {
                                pptViewSwitcher.switchView();
                            }
                        }
                    }
                }

                @Override
                public void onLiveChangeToLandscape(boolean toLandscape) {
                    if (toLandscape) {
                        PLVOrientationManager.getInstance().setLandscape(PLVLCCloudClassActivity.this);
                    } else {
                        PLVOrientationManager.getInstance().setPortrait(PLVLCCloudClassActivity.this);
                    }
                }

                @Override
                public void onLiveStartOrPauseVideoView(boolean toStart) {
                    if (toStart) {
                        mediaLayout.startPlay();
                    } else {
                        mediaLayout.stop();
                    }
                }

                @Override
                public void onLiveRestartVideoView() {
                    mediaLayout.startPlay();
                }

                @Override
                public void onLiveBackTopActivity() {
                    if (ScreenUtils.isLandscape()) {
                        PLVOrientationManager.getInstance().setPortrait(PLVLCCloudClassActivity.this);
                    } else {
                        finish();
                    }
                }
            });
        } else {
            //????????????PPT????????????
            floatingPPTLayout.getPPTView().initPlaybackPPT(new IPLVLCPPTView.OnPLVLCPlaybackPPTViewListener() {
                @Override
                public void onPlaybackSwitchPPTViewLocation(boolean toMainScreen) {
                    pptViewSwitcher.switchView();
                }
            });
        }

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????????????? - ??????">
    private void observeLinkMicLayout() {
        if (!liveRoomDataManager.getConfig().isLive() || linkMicLayout == null) {
            return;
        }
        //?????? View ????????????????????????
        final PLVViewSwitcher linkMicItemSwitcher = new PLVViewSwitcher();
        //???????????????????????????
        linkMicLayout.setOnPLVLinkMicLayoutListener(new IPLVLCLinkMicLayout.OnPLVLinkMicLayoutListener() {
            @Override
            public void onJoinChannelSuccess() {
                if (liveRoomDataManager.getConfig().isPPTChannelType()) {
                    //??????????????????????????????PPT??????????????????????????????PPT?????????????????????????????????????????????????????????????????????
                    if (floatingPPTLayout.isPPTInFloatingLayout()) {
                        pptViewSwitcher.switchView();
                    }
                }
                //???????????????
                floatingPPTLayout.hide();
                //??????PPT??????????????????0
                floatingPPTLayout.getPPTView().removeDelayTime();
                //?????????????????????
                mediaLayout.updateWhenJoinRTC(linkMicLayout.getLandscapeWidth());
            }

            @Override
            public void onLeaveChannel() {
                //???????????????
                floatingPPTLayout.show();
                //??????PPT????????????
                floatingPPTLayout.getPPTView().recoverDelayTime();
                //?????????????????????
                mediaLayout.updateWhenLeaveRTC();
            }

            @Override
            public void onShowLandscapeRTCLayout(boolean show) {
                if (show) {
                    mediaLayout.setShowLandscapeRTCLayout();
                } else {
                    mediaLayout.setHideLandscapeRTCLayout();
                }
            }

            @Override
            public void onChangeTeacherLocation(PLVViewSwitcher viewSwitcher, PLVSwitchViewAnchorLayout switchView) {
                viewSwitcher.registerSwitchVew(switchView, mediaLayout.getPlayerSwitchView());
                viewSwitcher.switchView();
                mediaLayout.getPlayerSwitchView().post(new Runnable() {
                    @Override
                    public void run() {
                        if(mediaLayout != null && mediaLayout.getPlayerSwitchView() != null) {
                            //?????? constraint-layout ????????? 2.0.0+ ??????????????????????????????
                            mediaLayout.getPlayerSwitchView().requestLayout();
                        }
                    }
                });
            }

            @Override
            public void onClickSwitchWithMediaOnce(PLVSwitchViewAnchorLayout switchView) {
                linkMicItemSwitcher.registerSwitchVew(switchView, mediaLayout.getPlayerSwitchView());
                linkMicItemSwitcher.switchView();
            }

            @Override
            public void onClickSwitchWithMediaTwice(PLVSwitchViewAnchorLayout switchViewHasMedia, PLVSwitchViewAnchorLayout switchViewGoMainScreen) {
                //??????PPT??????????????????????????????
                linkMicItemSwitcher.registerSwitchVew(switchViewHasMedia, mediaLayout.getPlayerSwitchView());
                linkMicItemSwitcher.switchView();

                //???????????????????????????item???PPT????????????
                linkMicItemSwitcher.registerSwitchVew(switchViewGoMainScreen, mediaLayout.getPlayerSwitchView());
                linkMicItemSwitcher.switchView();
            }

            @Override
            public void onRTCPrepared() {
                mediaLayout.notifyRTCPrepared();
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????????????????">
    @Override
    protected boolean enableRotationObserver() {
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            PLVScreenUtils.enterLandscape(this);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } else {
            PLVScreenUtils.enterPortrait(this);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }
    // </editor-fold>

}
