package com.easefun.polyv.livecommon.module.modules.streamer.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.view.SurfaceView;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicDataMapper;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.contract.IPLVStreamerContract;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.presenter.data.PLVStreamerData;
import com.easefun.polyv.livescenes.streamer.listener.IPLVSStreamerOnLiveStatusChangeListener;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxBaseRetryFunction;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVJoinInfoEvent;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.repository.PLVLinkMicDataRepository;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.livescenes.chatroom.PLVChatApiRequestHelper;
import com.plv.livescenes.chatroom.PLVChatroomManager;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.log.chat.PLVChatroomELog;
import com.plv.livescenes.model.PLVListUsersVO;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.IPLVStreamerManager;
import com.plv.livescenes.streamer.config.PLVStreamerConfig;
import com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender;
import com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender;
import com.plv.livescenes.streamer.listener.IPLVOnGetSessionIdInnerListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveStreamingStartListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnLiveTimingListener;
import com.plv.livescenes.streamer.listener.IPLVStreamerOnServerTimeoutDueToNetBrokenListener;
import com.plv.livescenes.streamer.listener.PLVStreamerEventListener;
import com.plv.livescenes.streamer.listener.PLVStreamerListener;
import com.plv.livescenes.streamer.manager.PLVStreamerManagerFactory;
import com.plv.livescenes.streamer.mix.PLVRTCMixUser;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;
import com.plv.socket.log.PLVELogSender;
import com.plv.socket.socketio.PLVSocketIOClient;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.SPUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * mvp-???????????????presenter?????????????????? IPLVStreamerContract.IStreamerPresenter ??????
 */
public class PLVStreamerPresenter implements IPLVStreamerContract.IStreamerPresenter {
    // <editor-fold defaultstate="collapsed" desc="??????">
    private static final String TAG = "PLVStreamerPresenter";

    /**** ???????????????????????? ****/
    //????????????
    public static final int STREAMER_STATUS_START = 1;
    //????????????
    public static final int STREAMER_STATUS_START_SUCCESS = 2;
    //????????????
    public static final int STREAMER_STATUS_STOP = 3;

    /**** ??????????????????????????? ****/
    //????????????
    private static final int STREAMER_MIC_UNINITIATED = 1;
    //????????????
    private static final int STREAMER_MIC_INITIATING = 2;
    //???????????????
    private static final int STREAMER_MIC_INITIATED = 3;

    /**** ?????? ****/
    //??????????????????
    private static final int TIME_OUT_JOIN_CHANNEL = 20 * 1000;
    //??????????????????
    private static final int TIME_OUT_TO_SHOW_NET_BROKEN = 20;
    //???20s??????????????????
    private static final int INTERVAL_TO_GET_USER_LIST = 20 * 1000;
    //???10s??????????????????
    private static final int INTERVAL_TO_GET_LINK_MIC_LIST = 10 * 1000;
    //???10s??????????????????
    private static final int INTERVAL_TO_POLL_LIVE_STATUS = 10 * 1000;

    /**** ????????? ****/
    private static final int ERROR_GUEST_LINK_TIMEOUT = 1;

    /**** ???????????????????????????????????? ****/
    private static final int DEFAULT_MEMBER_PAGE = 1;
    private static final int DEFAULT_MEMBER_LENGTH = 500;

    /**** View ****/
    //???????????????mvp?????????view
    private List<IPLVStreamerContract.IStreamerView> iStreamerViews;
    /**** Model ****/
    //???????????????????????????
    private final IPLVStreamerManager streamerManager;
    //??????????????????socket???????????????
    private final PLVStreamerMsgHandler streamerMsgHandler;

    /**** ?????? ****/
    //???????????????????????????
    private int streamerInitState = STREAMER_MIC_UNINITIATED;
    //????????????
    private int streamerStatus = STREAMER_STATUS_STOP;

    /**** ???????????? ****/
    //????????????????????????
    private final IPLVLiveRoomDataManager liveRoomDataManager;
    //???????????????????????????
    @Nullable
    private PLVSocketUserBean currentSocketUserBean;
    //?????????????????????????????????????????????????????????????????????
    private final String userType;
    //?????????????????????
    private final PLVStreamerData streamerData;

    /**** ???????????? ****/
    @PLVStreamerConfig.BitrateType
    private int curBitrate = loadBitrate();
    private boolean curCameraFront = true;
    private boolean curEnableRecordingAudioVolume = true;
    private boolean curEnableLocalVideo = true;
    private boolean isFrontMirror = true;
    private int pushPictureResolution = PLVLinkMicConstant.PushPictureResolution.RESOLUTION_LANDSCAPE;

    /**** ?????? ****/
    //?????????????????????
    final List<PLVLinkMicItemDataBean> streamerList = new LinkedList<>();
    //????????????
    List<PLVMemberItemDataBean> memberList = new LinkedList<>();
    //rtc???????????????????????????
    final Map<String, PLVLinkMicItemDataBean> rtcJoinMap = new HashMap<>();

    /**** ?????? ****/
    //?????????????????????
    private final TimerToShowNetBroken timerToShowNetBroken = new TimerToShowNetBroken(TIME_OUT_TO_SHOW_NET_BROKEN);
    //??????????????????
    private Runnable joinChannelRunnable = null;
    //disposable
    private Disposable listUsersDisposable;
    private Disposable listUserTimerDisposable;
    private Disposable linkMicListTimerDisposable;

    //handler
    private final Handler handler = new Handler(Looper.getMainLooper());
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????">
    public PLVStreamerPresenter(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        streamerData = new PLVStreamerData();

        String viewerId = liveRoomDataManager.getConfig().getUser().getViewerId();
        userType = liveRoomDataManager.getConfig().getUser().getViewerType();
        PLVLinkMicConfig.getInstance().init(viewerId, true);//???????????????????????????manager
        streamerManager = PLVStreamerManagerFactory.createNewStreamerManager();

        streamerMsgHandler = new PLVStreamerMsgHandler(this);
        streamerMsgHandler.run();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????API - ??????IPLVStreamerContract.IStreamerPresenter???????????????">
    @Override
    public void registerView(@NonNull IPLVStreamerContract.IStreamerView v) {
        if (iStreamerViews == null) {
            iStreamerViews = new ArrayList<>();
        }
        if (!iStreamerViews.contains(v)) {
            iStreamerViews.add(v);
        }
        v.setPresenter(this);
    }

    @Override
    public void unregisterView(IPLVStreamerContract.IStreamerView v) {
        if (iStreamerViews != null) {
            iStreamerViews.remove(v);
        }
    }

    @Override
    public void init() {
        streamerInitState = STREAMER_MIC_INITIATING;
        if (PLVSocketUserConstant.USERTYPE_GUEST.equals(userType)) {
            //????????????????????????????????????????????????????????????????????????
            pollLiveStatus();
            //???????????????????????????????????????????????????????????????????????????????????????
            streamerManager.disableAutoJoinChannel();
        }
        streamerManager.initEngine(new PLVStreamerListener() {
            @Override
            public void onStreamerEngineCreatedSuccess() {
                PLVCommonLog.d(TAG, "??????????????????????????????");
                streamerInitState = STREAMER_MIC_INITIATED;

                PLVLinkMicItemDataBean linkMicItemDataBean = new PLVLinkMicItemDataBean();
                linkMicItemDataBean.setMuteAudio(!curEnableRecordingAudioVolume);
                linkMicItemDataBean.setMuteVideo(!curEnableLocalVideo);
                if (PLVSocketUserConstant.USERTYPE_GUEST.equals(userType)) {
                    //????????????????????????????????????????????????????????????
                    linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
                } else {
                    linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_RTC_JOIN);
                }
                linkMicItemDataBean.setLinkMicId(streamerManager.getLinkMicUid());
                linkMicItemDataBean.setActor(liveRoomDataManager.getConfig().getUser().getActor());
                linkMicItemDataBean.setNick(liveRoomDataManager.getConfig().getUser().getViewerName());
                linkMicItemDataBean.setUserId(PLVSocketIOClient.getInstance().getSocketUserId());
                linkMicItemDataBean.setUserType(liveRoomDataManager.getConfig().getUser().getViewerType());
                streamerList.add(0, linkMicItemDataBean);
                Pair<Integer, PLVMemberItemDataBean> item = getMemberItemWithUserId(linkMicItemDataBean.getLinkMicId());
                if (item != null && item.second.getLinkMicItemDataBean() == null) {
                    item.second.setLinkMicItemDataBean(linkMicItemDataBean);
                }

                callbackToView(new ViewRunnable() {
                    @Override
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onStreamerEngineCreatedSuccess(streamerManager.getLinkMicUid(), streamerList);
                    }
                });

                setBitrate(curBitrate);
                setCameraDirection(curCameraFront);
                setPushPictureResolutionType(pushPictureResolution);
                enableLocalVideo(curEnableLocalVideo);
                enableRecordingAudioVolume(curEnableRecordingAudioVolume);
                setFrontCameraMirror(isFrontMirror);

                initStreamerListener();

                if (streamerStatus == STREAMER_STATUS_START) {
                    streamerManager.startLiveStream();
                }
                if (joinChannelRunnable != null) {
                    joinChannelRunnable.run();
                }
            }

            @Override
            public void onStreamerError(final int errorCode, final Throwable throwable) {
                PLVCommonLog.e(TAG, "??????????????????????????????errorCode=" + errorCode);
                PLVCommonLog.exception(throwable);
                if (streamerInitState != STREAMER_MIC_INITIATED) {
                    streamerInitState = STREAMER_MIC_UNINITIATED;
                }

                if (streamerStatus != STREAMER_STATUS_STOP) {
                    stopLiveStream();
                    callbackToView(new ViewRunnable() {
                        @Override
                        public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                            view.onStreamerError(errorCode, throwable);
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getNetworkQuality() {
        return streamerManager.getCurrentNetQuality();
    }

    @Override
    public void setBitrate(int bitrate) {
        curBitrate = Math.min(bitrate, getMaxBitrate());
        if (!isInitStreamerManager()) {
            return;
        }
        streamerManager.setBitrate(curBitrate);
        streamerData.postCurBitrate(curBitrate);
        saveBitrate();
    }

    @Override
    public int getBitrate() {
        return Math.min(curBitrate, getMaxBitrate());
    }

    @Override
    public int getMaxBitrate() {
        return PLVStreamerInnerDataTransfer.getInstance().getSupportedMaxBitrate();
    }

    @Override
    public boolean enableRecordingAudioVolume(final boolean enable) {
        curEnableRecordingAudioVolume = enable;
        if (!isInitStreamerManager()) {
            return false;
        }
        streamerManager.enableLocalMicrophone(enable);
        streamerData.postEnableAudio(enable);
        callUserMuteAudio(streamerManager.getLinkMicUid(), !enable);
        return true;
    }

    @Override
    public boolean enableLocalVideo(final boolean enable) {
        curEnableLocalVideo = enable;
        if (!isInitStreamerManager()) {
            return false;
        }
        streamerManager.enableLocalCamera(enable);
        streamerData.postEnableVideo(enable);
        callUserMuteVideo(streamerManager.getLinkMicUid(), !enable);
        return true;
    }

    @Override
    public boolean enableTorch(boolean enable) {
        if (!isInitStreamerManager()) {
            return false;
        }
        return streamerManager.enableTorch(enable);
    }

    @Override
    public boolean setCameraDirection(final boolean front) {
        curCameraFront = front;
        if (!isInitStreamerManager()) {
            return false;
        }
        if (curCameraFront) {
            streamerManager.setLocalPreviewMirror(isFrontMirror);
            streamerManager.setLocalPushMirror(isFrontMirror);
        } else {
            streamerManager.setLocalPreviewMirror(false);
            streamerManager.setLocalPushMirror(false);
        }
        streamerManager.switchCamera(front);
        streamerData.postIsFrontCamera(front);
        callbackToView(new ViewRunnable() {
            @Override
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                Pair<Integer, PLVMemberItemDataBean> item = getMemberItemWithLinkMicId(streamerManager.getLinkMicUid());
                if (item == null) {
                    return;
                }
                item.second.setFrontCamera(front);
                view.onCameraDirection(front, item.first);
            }
        });
        return true;
    }

    @Override
    public void setFrontCameraMirror(boolean enable) {
        if (!isInitStreamerManager()) {
            return;
        }
        if (curCameraFront) {
            this.isFrontMirror = enable;
            streamerData.postIsFrontMirrorMode(isFrontMirror);
            streamerManager.setLocalPreviewMirror(enable);
            streamerManager.setLocalPushMirror(enable);
        }
    }

    @Override
    public void setPushPictureResolutionType(int type) {
        pushPictureResolution = type;
        if (!isInitStreamerManager()) {
            return;
        }
        streamerManager.setPushPictureResolutionType(pushPictureResolution);
    }

    @Override
    public void setMixLayoutType(int mixLayoutType) {
        if (!isInitStreamerManager()) {
            return;
        }
        streamerManager.setMixLayoutType(mixLayoutType);
    }

    @Override
    public SurfaceView createRenderView(Context context) {
        return streamerManager.createRendererView(context);
    }

    @Override
    public void releaseRenderView(SurfaceView renderView) {
        streamerManager.releaseRenderView(renderView);
    }

    @Override
    public void setupRenderView(SurfaceView renderView, String linkMicId) {
        if (isMyLinkMicId(linkMicId)) {
            streamerManager.setupLocalVideo(renderView);
        } else {
            streamerManager.setupRemoteVideo(renderView, linkMicId);
        }
    }

    @Override
    public void startLiveStream() {
        streamerStatus = STREAMER_STATUS_START;
        switch (streamerInitState) {
            //????????????
            case STREAMER_MIC_UNINITIATED:
                PLVCommonLog.d(TAG, "??????????????????????????????");
                init();
                break;
            //????????????
            case STREAMER_MIC_INITIATING:
                PLVCommonLog.d(TAG, "???????????????????????????");
                return;
            //???????????????
            case STREAMER_MIC_INITIATED:
                streamerManager.startLiveStream();
                break;
            default:
                break;
        }
    }

    @Override
    public void stopLiveStream() {
        streamerStatus = STREAMER_STATUS_STOP;
        streamerManager.stopLiveStream();
        streamerData.postStreamerStatus(false);
        callbackToView(new ViewRunnable() {
            @Override
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onStatesToStreamEnded();
            }
        });
        PLVLinkMicEventSender.getInstance().closeLinkMic();
        PLVLinkMicEventSender.getInstance().emitFinishClassEvent(streamerManager.getLinkMicUid());
    }

    @Override
    public void controlUserLinkMic(int position, boolean isAllowJoin) {
        if (position < 0 || position >= memberList.size()) {
            return;
        }
        final PLVMemberItemDataBean memberItemDataBean = memberList.get(position);
        PLVSocketUserBean socketUserBean = memberItemDataBean.getSocketUserBean();
        @Nullable final PLVLinkMicItemDataBean linkMicItemDataBean = memberItemDataBean.getLinkMicItemDataBean();
        if (isAllowJoin) {
            if (rtcJoinMap.size() >= PLVStreamerInnerDataTransfer.getInstance().getInteractNumLimit()) {
                callbackToView(new ViewRunnable() {
                    @Override
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onReachTheInteractNumLimit();
                    }
                });
                return;
            }
            PLVLinkMicEventSender.getInstance().responseUserLinkMic(socketUserBean, new IPLVLinkMicEventSender.PLVSMainCallAck() {
                @Override
                public void onCall(Object... args) {
                    if (linkMicItemDataBean != null) {
                        linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_JOINING);
                        startJoinTimeoutCount(linkMicItemDataBean);
                        callUpdateSortMemberList();
                    }
                }
            });
        } else {
            if (linkMicItemDataBean != null) {
                PLVLinkMicEventSender.getInstance().closeUserLinkMic(linkMicItemDataBean.getLinkMicId(), null);
            }
        }
    }

    @Override
    public void controlUserLinkMicInLinkMicList(int position, boolean isAllowJoin) {
        if (position < 0 || position >= streamerList.size()) {
            return;
        }
        PLVLinkMicItemDataBean linkMicItemDataBean = streamerList.get(position);
        Pair<Integer, PLVMemberItemDataBean> item = getMemberItemWithLinkMicId(linkMicItemDataBean.getLinkMicId());
        if (item != null) {
            controlUserLinkMic(item.first, isAllowJoin);
        }
    }

    @Override
    public void muteUserMedia(int position, final boolean isVideoType, final boolean isMute) {
        if (position < 0 || position >= memberList.size()) {
            return;
        }
        PLVMemberItemDataBean memberItemDataBean = memberList.get(position);
        PLVSocketUserBean socketUserBean = memberItemDataBean.getSocketUserBean();
        @Nullable final PLVLinkMicItemDataBean linkMicItemDataBean = memberItemDataBean.getLinkMicItemDataBean();
        String sessionId = liveRoomDataManager.getSessionId();
        PLVLinkMicEventSender.getInstance().muteUserMedia(socketUserBean, sessionId, isVideoType, isMute, new IPLVLinkMicEventSender.PLVSMainCallAck() {
            @Override
            public void onCall(Object... args) {
                if (linkMicItemDataBean != null) {
                    if (isVideoType) {
                        linkMicItemDataBean.setMuteVideo(isMute);
                        callUserMuteVideo(linkMicItemDataBean.getLinkMicId(), isMute);
                    } else {
                        linkMicItemDataBean.setMuteAudio(isMute);
                        callUserMuteAudio(linkMicItemDataBean.getLinkMicId(), isMute);
                    }
                }
            }
        });
    }

    @Override
    public void muteUserMediaInLinkMicList(int position, boolean isVideoType, boolean isMute) {
        if (position < 0 || position >= streamerList.size()) {
            return;
        }
        PLVLinkMicItemDataBean linkMicItemDataBean = streamerList.get(position);
        Pair<Integer, PLVMemberItemDataBean> item = getMemberItemWithLinkMicId(linkMicItemDataBean.getLinkMicId());
        if (item != null) {
            muteUserMedia(item.first, isVideoType, isMute);
        }
    }

    @Override
    public void closeAllUserLinkMic() {
        for (Map.Entry<String, PLVLinkMicItemDataBean> linkMicItemDataBeanEntry : rtcJoinMap.entrySet()) {
            String linkMicId = linkMicItemDataBeanEntry.getKey();
            if (!isMyLinkMicId(linkMicId)) {
                PLVLinkMicEventSender.getInstance().closeUserLinkMic(linkMicId, null);
            }
        }
    }

    @Override
    public void muteAllUserAudio(final boolean isMute) {
        for (int i = 0; i < memberList.size(); i++) {
            @Nullable final PLVLinkMicItemDataBean linkMicItemDataBean = memberList.get(i).getLinkMicItemDataBean();
            if (linkMicItemDataBean != null && linkMicItemDataBean.isRtcJoinStatus()) {
                if (!isMyLinkMicId(linkMicItemDataBean.getLinkMicId())) {
                    muteUserMedia(i, false, isMute);
                }
            }
        }
    }

    @Override
    public void requestMemberList() {
        requestListUsersApi();
    }

    @Override
    public int getStreamerStatus() {
        return streamerStatus;
    }

    @NonNull
    @Override
    public PLVStreamerData getData() {
        return streamerData;
    }

    @Override
    public void destroy() {
        streamerInitState = STREAMER_MIC_UNINITIATED;
        streamerStatus = STREAMER_STATUS_STOP;

        handler.removeCallbacksAndMessages(null);
        streamerMsgHandler.destroy();

        dispose(listUsersDisposable);
        dispose(listUserTimerDisposable);
        dispose(linkMicListTimerDisposable);

        streamerList.clear();

        timerToShowNetBroken.destroy();
        if (iStreamerViews != null) {
            iStreamerViews.clear();
        }

        //??????????????????
        if (!PLVSocketUserConstant.USERTYPE_GUEST.equals(userType)) {
            PLVLinkMicEventSender.getInstance().closeLinkMic();
        }

        streamerManager.destroy();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????????????? - ??????????????????">
    private void initStreamerListener() {
        //?????????????????????
        streamerMsgHandler.observeLinkMicData();
        //?????????????????????
        streamerManager.addEventHandler(new PLVStreamerEventListener() {
            //????????????????????????
            @Override
            public void onNetworkQuality(final int quality) {
                streamerData.postNetworkQuality(quality);
                callbackToView(new ViewRunnable() {
                    @Override
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onNetworkQuality(quality);
                    }
                });
                if (quality == PLVStreamerConfig.NetQuality.NET_QUALITY_NO_CONNECTION) {
                    timerToShowNetBroken.invokeTimerWhenNoConnection();
                } else {
                    timerToShowNetBroken.resetWhenHasConnection();
                }
            }
        });

        //????????????
        streamerManager.addOnLiveStreamingStartListener(new IPLVStreamerOnLiveStreamingStartListener() {
            @Override
            public void onLiveStreamingStart() {
                streamerStatus = STREAMER_STATUS_START_SUCCESS;
                streamerData.postStreamerStatus(true);
                callbackToView(new ViewRunnable() {
                    @Override
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onStatesToStreamStarted();
                    }
                });
            }
        });

        //????????????
        streamerManager.setOnLiveTimingListener(new IPLVStreamerOnLiveTimingListener() {
            @Override
            public void onTimePastEachSeconds(final int duration) {
                streamerData.postStreamerTime(duration);
                callbackToView(new ViewRunnable() {
                    @Override
                    public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                        view.onUpdateStreamerTime(duration);
                    }
                });
            }
        });

        //????????????
        streamerManager.addStreamerServerTimeoutListener(new IPLVStreamerOnServerTimeoutDueToNetBrokenListener() {
            @Override
            public void onServerTimeoutDueToNetBroken() {
                //?????????????????????????????????
                if (streamerStatus != STREAMER_STATUS_STOP) {
                    stopLiveStream();
                    callbackToView(new ViewRunnable() {
                        @Override
                        public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                            view.onStreamerError(-1, new Throwable("timeout"));
                        }
                    });
                }
            }
        });

        //??????sessionId
        streamerManager.addGetSessionIdFromServerListener(new IPLVOnGetSessionIdInnerListener() {
            @Override
            public void onGetSessionId(String sessionId, String channelId, String streamId, boolean isCamClosed) {
                liveRoomDataManager.setSessionId(sessionId);
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="???????????? - ????????????">
    private void requestListUsersApi() {
        dispose(listUsersDisposable);
        dispose(listUserTimerDisposable);
        dispose(linkMicListTimerDisposable);
        String loginRoomId = PLVSocketWrapper.getInstance().getLoginRoomId();//?????????????????????????????????????????????id???????????????????????????
        if (TextUtils.isEmpty(loginRoomId)) {
            loginRoomId = liveRoomDataManager.getConfig().getChannelId();//socket??????????????????????????????
        }
        final String roomId = loginRoomId;
        listUsersDisposable = PLVChatApiRequestHelper.getListUsers(roomId, DEFAULT_MEMBER_PAGE, DEFAULT_MEMBER_LENGTH)
                .retryWhen(new PLVRxBaseRetryFunction(Integer.MAX_VALUE, 3000))
                .subscribe(new Consumer<PLVListUsersVO>() {
                    @Override
                    public void accept(PLVListUsersVO plvsListUsersVO) throws Exception {
                        //???????????????????????????
                        PLVChatroomManager.getInstance().setOnlineCount(plvsListUsersVO.getCount());
                        generateMemberListWithListUsers(plvsListUsersVO.getUserlist(), true);
                        //??????????????????api
                        requestLinkMicListApiTimer();
                        //????????????????????????api
                        requestListUsersApiTimer(roomId);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        PLVCommonLog.exception(throwable);
                        //???????????????????????????????????????
                        PLVELogSender.send(PLVChatroomELog.class, PLVChatroomELog.Event.GET_LISTUSERS_FAIL, throwable);
                    }
                });
    }

    private void requestListUsersApiTimer(final String roomId) {
        dispose(listUserTimerDisposable);
        listUserTimerDisposable = Observable.interval(INTERVAL_TO_GET_USER_LIST, INTERVAL_TO_GET_USER_LIST, TimeUnit.MILLISECONDS, Schedulers.io())
                .flatMap(new Function<Long, Observable<PLVListUsersVO>>() {
                    @Override
                    public Observable<PLVListUsersVO> apply(@NonNull Long aLong) throws Exception {
                        return PLVChatApiRequestHelper.getListUsers(roomId, DEFAULT_MEMBER_PAGE, DEFAULT_MEMBER_LENGTH).retry(1);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PLVListUsersVO>() {
                    @Override
                    public void accept(PLVListUsersVO plvsListUsersVO) throws Exception {
                        //???????????????????????????
                        PLVChatroomManager.getInstance().setOnlineCount(plvsListUsersVO.getCount());
                        generateMemberListWithListUsers(plvsListUsersVO.getUserlist(), false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        PLVCommonLog.exception(throwable);
                        //???????????????????????????????????????
                        PLVELogSender.send(PLVChatroomELog.class, PLVChatroomELog.Event.GET_LISTUSERS_FAIL, throwable);
                    }
                });
    }

    private void generateMemberListWithListUsers(List<PLVSocketUserBean> socketUserBeanList, boolean isResetJoiningStatus) {
        List<PLVMemberItemDataBean> tempMemberList = new LinkedList<>();
        for (int i = 0; i < socketUserBeanList.size(); i++) {
            PLVSocketUserBean socketUserBean = socketUserBeanList.get(i);
            String userId = socketUserBean.getUserId();
            if (userId != null && userId.equals(PLVSocketWrapper.getInstance().getLoginVO().getUserId())) {
                socketUserBeanList.remove(socketUserBean);
                i--;
                continue;//????????????????????????????????????????????????????????????
            }
            PLVMemberItemDataBean memberItemDataBean = new PLVMemberItemDataBean();
            memberItemDataBean.setSocketUserBean(socketUserBean);
            //???????????????????????????????????????????????????????????????????????????
            Pair<Integer, PLVMemberItemDataBean> item = getMemberItemWithUserId(userId);
            if (item != null) {
                PLVLinkMicItemDataBean linkMicItemDataBean = item.second.getLinkMicItemDataBean();
                if (linkMicItemDataBean != null) {
                    if (linkMicItemDataBean.isJoiningStatus() && isResetJoiningStatus) {
                        //??????????????????????????????????????????????????????????????????
                        linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
                    }
                    memberItemDataBean.setLinkMicItemDataBean(linkMicItemDataBean);
                }
            }
            tempMemberList.add(memberItemDataBean);
        }
        memberList = tempMemberList;
        //?????????????????????
        Runnable addTeacherTask = new Runnable() {
            @Override
            public void run() {
                PLVMemberItemDataBean memberItemDataBean = new PLVMemberItemDataBean();
                memberItemDataBean.setFrontCamera(curCameraFront);
                Pair<Integer, PLVLinkMicItemDataBean> item = getLinkMicItemWithLinkMicId(streamerManager.getLinkMicUid());
                if (item != null) {
                    memberItemDataBean.setLinkMicItemDataBean(item.second);
                }
                currentSocketUserBean = PLVSocketWrapper.getInstance().getLoginVO().createSocketUserBean();
                memberItemDataBean.setSocketUserBean(currentSocketUserBean);
                memberList.add(0, memberItemDataBean);
            }
        };
        addTeacherTask.run();

        callUpdateSortMemberList();
    }

    private void requestLinkMicListApiTimer() {
        dispose(linkMicListTimerDisposable);
        linkMicListTimerDisposable = PLVRxTimer.timer(1000, INTERVAL_TO_GET_LINK_MIC_LIST, new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                streamerManager.getLinkStatus(liveRoomDataManager.getSessionId(), new PLVLinkMicDataRepository.IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus>() {
                    @Override
                    public void onSuccess(PLVLinkMicJoinStatus data) {
                        PLVCommonLog.d(TAG, "PLVStreamerPresenter.requestLinkMicListFromServer.onSuccess->\n" + PLVGsonUtil.toJson(data));
                        acceptLinkMicJoinStatus(data);
                    }

                    @Override
                    public void onFail(PLVLinkMicHttpRequestException throwable) {
                        super.onFail(throwable);
                        PLVCommonLog.exception(throwable);
                    }
                });
            }
        });
    }

    private void acceptLinkMicJoinStatus(PLVLinkMicJoinStatus data) {
        List<PLVJoinInfoEvent> joinList = data.getJoinList();
        List<PLVLinkMicJoinStatus.WaitListBean> waitList = data.getWaitList();

        //????????????????????????????????????????????????????????????????????????????????????????????????voice????????????=1??????????????????????????????
        Iterator<PLVJoinInfoEvent> joinInfoEventIterator = joinList.iterator();
        while (joinInfoEventIterator.hasNext()) {
            PLVJoinInfoEvent plvJoinInfoEvent = joinInfoEventIterator.next();
            if (PLVSocketUserConstant.USERTYPE_GUEST.equals(plvJoinInfoEvent.getUserType()) && !plvJoinInfoEvent.getClassStatus().isVoice()) {
                //?????????????????????joinList?????????????????????waitList???
                joinInfoEventIterator.remove();
                waitList.add(PLVLinkMicDataMapper.map2WaitListBean(plvJoinInfoEvent));
                PLVCommonLog.d(TAG, String.format(Locale.US, "guest user [%s] lies in joinList but not join at all, so we move him to waitList manually.", plvJoinInfoEvent.toString()));
            }
        }

        boolean hasChangedMemberList;
        //????????????????????????????????????????????????
        hasChangedMemberList = updateMemberListLinkMicStatus(joinList, waitList);
        //?????????????????????????????????
        for (PLVJoinInfoEvent joinInfoEvent : joinList) {
            PLVLinkMicItemDataBean linkMicItemDataBean = PLVLinkMicDataMapper.map2LinkMicItemData(joinInfoEvent);
            PLVSocketUserBean socketUserBean = PLVLinkMicDataMapper.map2SocketUserBean(joinInfoEvent);
            if (isMyLinkMicId(linkMicItemDataBean.getLinkMicId())) {
                continue;//????????????
            }
            //?????????????????????????????????????????????
            boolean result = updateMemberListItemInfo(socketUserBean, linkMicItemDataBean, true);
            if (result) {
                hasChangedMemberList = true;
            }
        }
        //????????????????????????????????????????????????????????????????????????
        removeLinkMicDataNoExistServer(joinList);
        //?????????????????????????????????
        for (PLVLinkMicJoinStatus.WaitListBean waitListBean : waitList) {
            PLVLinkMicItemDataBean linkMicItemDataBean = PLVLinkMicDataMapper.map2LinkMicItemData(waitListBean);
            PLVSocketUserBean socketUserBean = PLVLinkMicDataMapper.map2SocketUserBean(waitListBean);
            if (isMyLinkMicId(socketUserBean.getUserId())) {
                continue;//????????????
            }
            //?????????????????????????????????????????????
            boolean result = updateMemberListItemInfo(socketUserBean, linkMicItemDataBean, false);
            if (result) {
                hasChangedMemberList = true;
            }
        }
        //????????????????????????
        if (hasChangedMemberList) {
            callUpdateSortMemberList();
        }
    }

    boolean updateMemberListItemInfo(PLVSocketUserBean socketUserBean, PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList) {
        return updateMemberListItemInfo(socketUserBean, linkMicItemDataBean, isJoinList, false);
    }

    boolean updateMemberListItemInfo(PLVSocketUserBean socketUserBean, PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList, boolean isUpdateJoiningStatus) {
        boolean hasChangedMemberList = false;
        //????????????????????????????????????
        Pair<Integer, PLVMemberItemDataBean> memberItem = getMemberItemWithUserId(socketUserBean.getUserId());
        if (memberItem == null || memberItem.second.getLinkMicItemDataBean() == null) {
            //?????????????????????????????????????????????????????????????????????????????????????????????
            PLVMemberItemDataBean memberItemDataBean;
            if (memberItem == null) {
                memberItemDataBean = new PLVMemberItemDataBean();
                memberItemDataBean.setSocketUserBean(socketUserBean);
                memberList.add(memberItemDataBean);
            } else {
                memberItemDataBean = memberItem.second;
            }
            memberItemDataBean.setLinkMicItemDataBean(linkMicItemDataBean);
            updateMemberListLinkMicStatusWithRtcJoinList(memberItemDataBean, linkMicItemDataBean.getLinkMicId());
            hasChangedMemberList = true;
        } else {
            PLVLinkMicItemDataBean linkMicItemDataBeanInMemberList = memberItem.second.getLinkMicItemDataBean();
            boolean isJoiningStatus = linkMicItemDataBeanInMemberList.isJoiningStatus();
            boolean isJoinStatus = linkMicItemDataBeanInMemberList.isJoinStatus();
            boolean isWaitStatus = linkMicItemDataBeanInMemberList.isWaitStatus();
            if (isJoinList) {
                hasChangedMemberList = updateMemberListLinkMicStatusWithRtcJoinList(memberItem.second, linkMicItemDataBeanInMemberList.getLinkMicId());
                if (hasChangedMemberList) {
                    return true;
                }
                boolean isRtcJoinStatus = linkMicItemDataBeanInMemberList.isRtcJoinStatus();
                //????????????????????????
                if (!isRtcJoinStatus && !isJoinStatus && !isJoiningStatus) {
                    linkMicItemDataBeanInMemberList.setStatus(PLVLinkMicItemDataBean.STATUS_JOIN);
                    hasChangedMemberList = true;
                }
            } else {
                //?????????????????????
                if ((!isJoiningStatus || isUpdateJoiningStatus)
                        && !isWaitStatus) {
                    linkMicItemDataBeanInMemberList.setStatus(PLVLinkMicItemDataBean.STATUS_WAIT);
                    hasChangedMemberList = true;
                }
            }
        }
        return hasChangedMemberList;
    }

    boolean updateMemberListLinkMicStatusWithRtcJoinList(PLVMemberItemDataBean item, final String linkMicUid) {
        boolean hasChangedMemberList = false;
        final PLVLinkMicItemDataBean linkMicItemDataBean = item.getLinkMicItemDataBean();
        if (linkMicItemDataBean == null) {
            return false;
        }
        for (Map.Entry<String, PLVLinkMicItemDataBean> linkMicItemDataBeanEntry : rtcJoinMap.entrySet()) {
            String uid = linkMicItemDataBeanEntry.getKey();
            if (linkMicUid != null && linkMicUid.equals(uid)) {
                if (!linkMicItemDataBean.isRtcJoinStatus()) {
                    linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_RTC_JOIN);
                    updateLinkMicMediaStatus(linkMicItemDataBeanEntry.getValue(), linkMicItemDataBean);
                    hasChangedMemberList = true;
                }
                Pair<Integer, PLVLinkMicItemDataBean> linkMicItem = getLinkMicItemWithLinkMicId(linkMicUid);
                if (linkMicItem == null) {
                    streamerList.add(linkMicItemDataBean);
                    if (PLVSocketUserConstant.USERTYPE_GUEST.equals(userType)) {
                        SortGuestLinkMicListUtils.sort(streamerList);
                    }
                    updateMixLayoutUsers();
                    callbackToView(new ViewRunnable() {
                        @Override
                        public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                            //???????????????????????????
                            view.onUsersJoin(Collections.singletonList(linkMicItemDataBean));
                        }
                    });
                }
                break;
            }
        }
        return hasChangedMemberList;
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     * view.onUserLeave
     * view.onUsersJoin
     * view.onUserMuteVideo
     */
    void updateMixLayoutUsers() {
        List<PLVRTCMixUser> mixUserList = new ArrayList<>();
        for (PLVLinkMicItemDataBean plvLinkMicItemDataBean : streamerList) {
            PLVRTCMixUser mixUser = new PLVRTCMixUser();
            mixUser.setUserId(plvLinkMicItemDataBean.getLinkMicId());
            mixUser.setMuteVideo(plvLinkMicItemDataBean.isMuteVideo());
            mixUserList.add(mixUser);
        }
        streamerManager.updateMixLayoutUsers(mixUserList);
    }

    private boolean updateMemberListLinkMicStatus(List<PLVJoinInfoEvent> joinList, List<PLVLinkMicJoinStatus.WaitListBean> waitList) {
        boolean hasChanged = false;
        for (PLVMemberItemDataBean plvMemberItemDataBean : memberList) {
            PLVLinkMicItemDataBean linkMicItemDataBean = plvMemberItemDataBean.getLinkMicItemDataBean();
            if (linkMicItemDataBean == null
                    || linkMicItemDataBean.isIdleStatus()
                    || isMyLinkMicId(linkMicItemDataBean.getLinkMicId())) {
                continue;
            }
            String linkMicId = linkMicItemDataBean.getLinkMicId();
            boolean isExitLinkMicList = false;
            for (PLVJoinInfoEvent joinInfoEvent : joinList) {
                if (linkMicId != null && linkMicId.equals(joinInfoEvent.getUserId())) {
                    isExitLinkMicList = true;
                    break;
                }
            }
            if (!isExitLinkMicList) {
                for (PLVLinkMicJoinStatus.WaitListBean waitListBean : waitList) {
                    if (linkMicId != null && linkMicId.equals(waitListBean.getUserId())) {
                        isExitLinkMicList = true;
                        break;
                    }
                }
            }
            if (!isExitLinkMicList) {
                linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
                rtcJoinMap.remove(linkMicItemDataBean.getLinkMicId());
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    private void removeLinkMicDataNoExistServer(List<PLVJoinInfoEvent> joinList) {
        final List<PLVLinkMicItemDataBean> willRemoveStreamerList = new ArrayList<>();
        Iterator<PLVLinkMicItemDataBean> linkMicItemDataBeanIterator = streamerList.iterator();
        while (linkMicItemDataBeanIterator.hasNext()) {
            PLVLinkMicItemDataBean linkMicItemDataBean = linkMicItemDataBeanIterator.next();
            String linkMicId = linkMicItemDataBean.getLinkMicId();
            boolean isExistServerList = false;
            for (PLVJoinInfoEvent joinInfoEvent : joinList) {
                if (linkMicId != null && linkMicId.equals(joinInfoEvent.getUserId())) {
                    isExistServerList = true;
                    break;
                }
            }
            if (!isExistServerList && !isMyLinkMicId(linkMicId)) {
                linkMicItemDataBeanIterator.remove();
                willRemoveStreamerList.add(linkMicItemDataBean);
            }
        }
        if (!willRemoveStreamerList.isEmpty()) {
            updateMixLayoutUsers();
            callbackToView(new ViewRunnable() {
                @Override
                public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                    view.onUsersLeave(willRemoveStreamerList);
                }
            });
        }
    }

    void updateLinkMicMediaStatus(PLVLinkMicItemDataBean rtcJoinLinkMicItem, PLVLinkMicItemDataBean linkMicItemDataBean) {
        if (rtcJoinLinkMicItem == null || linkMicItemDataBean == null) {
            return;
        }
        if (rtcJoinLinkMicItem.getMuteVideoInRtcJoinList() != null) {
            //???????????????????????????????????????????????????????????????
            linkMicItemDataBean.setMuteVideo(rtcJoinLinkMicItem.isMuteVideo());
        } else {
            if (!linkMicItemDataBean.isGuest()) {//?????????????????????????????????????????????
                //???????????????????????????????????????????????????muteVideo??????
                linkMicItemDataBean.setMuteVideo(!PLVLinkMicEventSender.getInstance().isVideoLinkMicType());
            } else {
                linkMicItemDataBean.setMuteVideo(false);
            }
        }
        if (rtcJoinLinkMicItem.getMuteAudioInRtcJoinList() != null) {
            //???????????????????????????????????????????????????????????????
            linkMicItemDataBean.setMuteAudio(rtcJoinLinkMicItem.isMuteAudio());
        } else {
            //???????????????muteAudio?????????false
            linkMicItemDataBean.setMuteAudio(false);
        }
    }

    private void startJoinTimeoutCount(final PLVLinkMicItemDataBean linkMicItemDataBean) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                linkMicItemDataBean.setStatusMethodCallListener(null);
                linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_WAIT);
                callUpdateSortMemberList();
            }
        };
        handler.postDelayed(runnable, TIME_OUT_JOIN_CHANNEL);
        linkMicItemDataBean.setStatusMethodCallListener(new Runnable() {
            @Override
            public void run() {
                if (!linkMicItemDataBean.isJoiningStatus()) {
                    handler.removeCallbacks(runnable);
                }
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????????????????">
    private void pollLiveStatus() {
        streamerManager.listenLiveStatusChange(0, INTERVAL_TO_POLL_LIVE_STATUS, new IPLVSStreamerOnLiveStatusChangeListener() {
            @Override
            public void onLiveStatusChange(final boolean isLive) {
                if (isLive) {
                    //?????????
                    guestTryJoinLinkMic();
                } else {
                    //????????????

                    dispose(linkMicListTimerDisposable);
                    streamerManager.leaveChannel(true);

                    PLVLinkMicItemDataBean guestDataBean = null;
                    String myLinkMicID = streamerManager.getLinkMicUid();
                    for (int i = 0; i < streamerList.size(); i++) {
                        PLVLinkMicItemDataBean dataBean = streamerList.get(i);
                        if (myLinkMicID.equals(dataBean.getLinkMicId())) {
                            guestDataBean = dataBean;
                            break;
                        }
                    }
                    rtcJoinMap.clear();
                    streamerList.clear();
                    if (guestDataBean != null) {
                        streamerList.add(guestDataBean);
                    }
                    callbackToView(new ViewRunnable() {
                        @Override
                        public void run(@NonNull @NotNull IPLVStreamerContract.IStreamerView view) {
                            view.onUsersLeave(streamerList);
                        }
                    });
                    callUpdateGuestStatus(false);
                }
                streamerData.postStreamerStatus(isLive);
                callbackToView(new ViewRunnable() {
                    @Override
                    public void run(@NonNull @NotNull IPLVStreamerContract.IStreamerView view) {
                        view.onStreamLiveStatusChanged(isLive);
                    }
                });
            }
        });
    }

    //????????????
    private void guestTryJoinLinkMic() {
        boolean isGuestAutoLinkMic = liveRoomDataManager.getConfig().isAutoLinkToGuest();
        if (isGuestAutoLinkMic) {
            //????????????
            PLVLinkMicEventSender.getInstance().guestAutoLinkMic(3, new IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener() {
                @Override
                public void onAutoLinkMic() {
                    if (streamerInitState == STREAMER_MIC_INITIATED) {
                        streamerManager.joinChannel();
                    } else {
                        joinChannelRunnable = new Runnable() {
                            @Override
                            public void run() {
                                streamerManager.joinChannel();
                            }
                        };
                    }
                    requestLinkMicListApiTimer();
                }

                @Override
                public void onTimeout() {
                    final String msg = "?????????????????????";
                    PLVCommonLog.e(TAG, msg);
                    callbackToView(new ViewRunnable() {
                        @Override
                        public void run(@NonNull @NotNull IPLVStreamerContract.IStreamerView view) {
                            view.onStreamerError(ERROR_GUEST_LINK_TIMEOUT, new Exception(msg));
                        }
                    });
                }

                @Override
                public void onHangupByTeacher() {
                    streamerManager.switchRoleToAudience();
                    callUpdateGuestStatus(false);
                }

                @Override
                public void onInviteByTeacher() {
                    streamerManager.switchRoleToBroadcaster();
                    callUpdateGuestStatus(true);
                }
            });
        } else {
            //????????????
            PLVCommonLog.d(TAG, "?????????????????????????????????");
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    private void saveBitrate() {
        SPUtils.getInstance().put("plv_key_bitrate", curBitrate);
    }

    private int loadBitrate() {
        return SPUtils.getInstance().getInt("plv_key_bitrate", PLVStreamerConfig.Bitrate.BITRATE_SUPER);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private boolean isInitStreamerManager() {
        return streamerInitState == STREAMER_MIC_INITIATED;
    }

    private boolean isMyLinkMicId(String linkMicId) {
        return linkMicId != null && linkMicId.equals(streamerManager.getLinkMicUid());
    }

    Pair<Integer, PLVLinkMicItemDataBean> getLinkMicItemWithLinkMicId(String linkMicId) {
        for (int i = 0; i < streamerList.size(); i++) {
            PLVLinkMicItemDataBean linkMicItemDataBean = streamerList.get(i);
            String linkMicIdForIndex = linkMicItemDataBean.getLinkMicId();
            if (linkMicId != null && linkMicId.equals(linkMicIdForIndex)) {
                return new Pair<>(i, linkMicItemDataBean);
            }
        }
        return null;
    }

    Pair<Integer, PLVMemberItemDataBean> getMemberItemWithLinkMicId(String linkMicId) {
        for (int i = 0; i < memberList.size(); i++) {
            PLVMemberItemDataBean memberItemDataBean = memberList.get(i);
            PLVLinkMicItemDataBean linkMicItemDataBean = memberItemDataBean.getLinkMicItemDataBean();
            if (linkMicItemDataBean != null) {
                String linkMicIdForIndex = linkMicItemDataBean.getLinkMicId();
                if (linkMicId != null && linkMicId.equals(linkMicIdForIndex)) {
                    return new Pair<>(i, memberItemDataBean);
                }
            }
        }
        return null;
    }

    Pair<Integer, PLVMemberItemDataBean> getMemberItemWithUserId(String userId) {
        for (int i = 0; i < memberList.size(); i++) {
            PLVMemberItemDataBean memberItemDataBean = memberList.get(i);
            PLVSocketUserBean socketUserBean = memberItemDataBean.getSocketUserBean();
            if (socketUserBean != null) {
                String userIdForIndex = socketUserBean.getUserId();
                if (userId != null && userId.equals(userIdForIndex)) {
                    return new Pair<>(i, memberItemDataBean);
                }
            }
        }
        return null;
    }

    void callUpdateSortMemberList() {
        callbackToView(new ViewRunnable() {
            @Override
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onUpdateMemberListData(SortMemberListUtils.sort(memberList));
            }
        });
    }

    void callUserMuteAudio(final String linkMicId, final boolean isMute) {
        Pair<Integer, PLVLinkMicItemDataBean> item = getLinkMicItemWithLinkMicId(linkMicId);
        if (item == null) {
            return;
        }
        item.second.setMuteAudio(isMute);
        final int streamerListPos = item.first;
        Pair<Integer, PLVMemberItemDataBean> memberItem = getMemberItemWithLinkMicId(linkMicId);
        if (memberItem == null) {
            return;
        }
        final int memberListPos = memberItem.first;
        callbackToView(new ViewRunnable() {
            @Override
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onUserMuteAudio(linkMicId, isMute, streamerListPos, memberListPos);
            }
        });
    }

    void callUserMuteVideo(final String linkMicId, final boolean isMute) {
        Pair<Integer, PLVLinkMicItemDataBean> item = getLinkMicItemWithLinkMicId(linkMicId);
        if (item == null) {
            return;
        }
        item.second.setMuteVideo(isMute);
        final int streamerListPos = item.first;
        Pair<Integer, PLVMemberItemDataBean> memberItem = getMemberItemWithLinkMicId(linkMicId);
        if (memberItem == null) {
            return;
        }
        final int memberListPos = memberItem.first;
        callbackToView(new ViewRunnable() {
            @Override
            public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                view.onUserMuteVideo(linkMicId, isMute, streamerListPos, memberListPos);
            }
        });
        updateMixLayoutUsers();
    }

    void callUpdateGuestStatus(boolean joinRTC) {
        String myLinkMicID = streamerManager.getLinkMicUid();
        int myIndex = 0;
        for (int i = 0; i < streamerList.size(); i++) {
            PLVLinkMicItemDataBean dataBean = streamerList.get(i);
            if (myLinkMicID.equals(dataBean.getLinkMicId())) {
                dataBean.setStatus(joinRTC ? PLVLinkMicItemDataBean.STATUS_RTC_JOIN : PLVLinkMicItemDataBean.STATUS_IDLE);
                myIndex = i;
                break;
            }
        }
        final int finalMyIndex = myIndex;
        callbackToView(new ViewRunnable() {
            @Override
            public void run(@NonNull @NotNull IPLVStreamerContract.IStreamerView view) {
                view.onGuestRTCStatusChanged(finalMyIndex);
            }
        });
    }

    void callUpdateGuestMediaStatus(boolean isMute, boolean isAudio) {
        String myLinkMicID = streamerManager.getLinkMicUid();
        if (isAudio) {
            enableRecordingAudioVolume(!isMute);
        } else {
            enableLocalVideo(!isMute);
            streamerManager.enableLocalCamera(!isMute);
        }
    }

    IPLVLiveRoomDataManager getLiveRoomDataManager() {
        return liveRoomDataManager;
    }

    IPLVStreamerManager getStreamerManager() {
        return streamerManager;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - view??????">
    void callbackToView(ViewRunnable runnable) {
        if (iStreamerViews != null) {
            for (IPLVStreamerContract.IStreamerView view : iStreamerViews) {
                if (view != null && runnable != null) {
                    runnable.run(view);
                }
            }
        }
    }

    interface ViewRunnable {
        void run(@NonNull IPLVStreamerContract.IStreamerView view);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ???????????????20s??????">
    public class TimerToShowNetBroken {
        // <editor-fold defaultstate="collapsed" desc="??????">
        //?????????
        private Disposable timerDisposable;

        //????????????????????????
        private final int secondsToShow;

        //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        private boolean hasShownDuringOneNetBroken = false;
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="?????????">
        TimerToShowNetBroken(int secondsToShow) {
            this.secondsToShow = secondsToShow;
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="??????">
        //?????????????????????????????????????????????
        void invokeTimerWhenNoConnection() {
            //??????????????????????????????
            if (streamerStatus == STREAMER_STATUS_STOP) {
                return;
            }
            //?????????????????????????????????
            if (isOngoing()) {
                return;
            }
            if (hasShownDuringOneNetBroken) {
                return;
            }
            dispose(timerDisposable);
            timerDisposable = PLVRxTimer.timer(1000, new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    if (aLong >= secondsToShow) {
                        //?????????????????????
                        dispose(timerDisposable);
                        hasShownDuringOneNetBroken = true;

                        //??????????????????????????????????????????????????????????????????????????????????????????
                        if (!streamerManager.isLiveStreaming()) {
                            stopLiveStream();
                            callbackToView(new ViewRunnable() {
                                @Override
                                public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                                    view.onStreamerError(-1, new Throwable("network disconnect"));
                                }
                            });
                        } else {
                            //call
                            streamerData.postShowNetBroken();
                            callbackToView(new ViewRunnable() {
                                @Override
                                public void run(@NonNull IPLVStreamerContract.IStreamerView view) {
                                    view.onShowNetBroken();
                                }
                            });
                        }
                    } else {
                        //?????????????????????????????????????????????,????????????????????????????????????????????????????????????
                        int netQuality = streamerManager.getCurrentNetQuality();
                        if (netQuality != PLVStreamerConfig.NetQuality.NET_QUALITY_NO_CONNECTION) {
                            dispose(timerDisposable);
                        }
                    }
                }
            });
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="??????">
        //???????????????????????????????????????flag
        void resetWhenHasConnection() {
            hasShownDuringOneNetBroken = false;
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="??????">
        void destroy() {
            hasShownDuringOneNetBroken = false;
            dispose(timerDisposable);
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="?????????????????????">
        private void dispose(Disposable disposable) {
            if (disposable != null) {
                disposable.dispose();
            }
        }

        private boolean isOngoing() {
            if (timerDisposable != null) {
                return !timerDisposable.isDisposed();
            }
            return false;
        }
        // </editor-fold>
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ??????????????????">
    public static class SortMemberListUtils {
        //?????????>?????????>??????>??????>???????????????>??????????????????????????????
        private static final String SELF = "??????";
        private static final String REAL = "?????????";
        private static final String REAL_LINK_MIC_RTC_JOIN = REAL + PLVLinkMicItemDataBean.STATUS_RTC_JOIN;
        private static final String REAL_LINK_MIC_JOIN = REAL + PLVLinkMicItemDataBean.STATUS_JOIN;
        private static final String REAL_LINK_MIC_JOINING = REAL + PLVLinkMicItemDataBean.STATUS_JOINING;
        private static final String REAL_LINK_MIC_WAIT = REAL + PLVLinkMicItemDataBean.STATUS_WAIT;
        private static final List<String> SORT_INDEX = Arrays.asList(
                SELF,
                PLVSocketUserConstant.USERTYPE_MANAGER,
                PLVSocketUserConstant.USERTYPE_TEACHER,
                PLVSocketUserConstant.USERTYPE_GUEST,
                PLVSocketUserConstant.USERTYPE_VIEWER,
                PLVSocketUserConstant.USERTYPE_ASSISTANT,
                REAL_LINK_MIC_WAIT,
                REAL_LINK_MIC_JOINING,
                REAL_LINK_MIC_JOIN,
                REAL_LINK_MIC_RTC_JOIN,
                REAL,
                PLVSocketUserConstant.USERTYPE_DUMMY
        );

        private static String getSortType(PLVMemberItemDataBean item) {
            PLVSocketUserBean data = item.getSocketUserBean();
            String type = data.getUserType();
            String myUserId = PLVSocketWrapper.getInstance().getLoginVO().getUserId();
            if (myUserId.equals(data.getUserId())) {
                type = SELF;
                return type;
            }
            if (!PLVSocketUserConstant.USERTYPE_MANAGER.equals(type)
                    && !PLVSocketUserConstant.USERTYPE_TEACHER.equals(type)
                    && !PLVSocketUserConstant.USERTYPE_GUEST.equals(type)
                    && !PLVSocketUserConstant.USERTYPE_VIEWER.equals(type)
                    && !PLVSocketUserConstant.USERTYPE_ASSISTANT.equals(type)
                    && !PLVSocketUserConstant.USERTYPE_DUMMY.equals(type)) {
                @Nullable
                PLVLinkMicItemDataBean linkMicItemDataBean = item.getLinkMicItemDataBean();
                if (linkMicItemDataBean != null) {
                    if (linkMicItemDataBean.isRtcJoinStatus()) {
                        type = REAL_LINK_MIC_RTC_JOIN;
                        return type;
                    } else if (linkMicItemDataBean.isJoinStatus()) {
                        type = REAL_LINK_MIC_JOIN;
                        return type;
                    } else if (linkMicItemDataBean.isJoiningStatus()) {
                        type = REAL_LINK_MIC_JOINING;
                        return type;
                    } else if (linkMicItemDataBean.isWaitStatus()) {
                        type = REAL_LINK_MIC_WAIT;
                        return type;
                    }
                }
                type = REAL;
            }
            return type;
        }

        public static List<PLVMemberItemDataBean> sort(List<PLVMemberItemDataBean> memberList) {
            Collections.sort(memberList, new Comparator<PLVMemberItemDataBean>() {
                @Override
                public int compare(PLVMemberItemDataBean o1, PLVMemberItemDataBean o2) {
                    int io1 = SORT_INDEX.indexOf(getSortType(o1));
                    int io2 = SORT_INDEX.indexOf(getSortType(o2));
                    return io1 - io2;
                }
            });
            return memberList;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????">
    public static class SortGuestLinkMicListUtils {
        //???????????????????????????????????????????????????????????????????????????????????????
        private static final String OTHER_TYPE = "SortGuestLinkMicListUtils-other";
        private static final List<String> SORT_INDEX = Arrays.asList(
                PLVSocketUserConstant.USERTYPE_TEACHER,
                PLVSocketUserConstant.USERTYPE_GUEST,
                OTHER_TYPE
        );

        private static String getSortType(PLVLinkMicItemDataBean itemDataBean) {
            String type = itemDataBean.getUserType();
            if (!SORT_INDEX.contains(type)) {
                type = OTHER_TYPE;
            }
            return type;
        }

        public static List<PLVLinkMicItemDataBean> sort(List<PLVLinkMicItemDataBean> input) {
            Collections.sort(input, new Comparator<PLVLinkMicItemDataBean>() {
                @Override
                public int compare(PLVLinkMicItemDataBean o1, PLVLinkMicItemDataBean o2) {
                    try {
                        if (PLVSocketUserConstant.USERTYPE_GUEST.equals(o1.getUserType()) && PLVSocketUserConstant.USERTYPE_GUEST.equals(o2.getUserType())) {
                            return Integer.parseInt(o1.getUserId()) - Integer.parseInt(o2.getUserId());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                    int io1 = SORT_INDEX.indexOf(getSortType(o1));
                    int io2 = SORT_INDEX.indexOf(getSortType(o2));
                    return io1 - io2;
                }
            });
            return input;
        }
    }
// </editor-fold>
}
