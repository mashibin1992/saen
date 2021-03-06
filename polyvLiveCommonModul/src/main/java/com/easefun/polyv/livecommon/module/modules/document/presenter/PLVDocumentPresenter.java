package com.easefun.polyv.livecommon.module.modules.document.presenter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import android.content.Context;
import android.net.Uri;
import androidx.annotation.Nullable;
import android.util.Log;

import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.document.contract.IPLVDocumentContract;
import com.easefun.polyv.livecommon.module.modules.document.model.PLVDocumentRepository;
import com.easefun.polyv.livecommon.module.modules.document.model.PLVPptUploadLocalRepository;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMode;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVPptUploadStatus;
import com.easefun.polyv.livecommon.module.modules.document.model.vo.PLVPptUploadLocalCacheVO;
import com.easefun.polyv.livescenes.document.PLVSDocumentWebProcessor;
import com.easefun.polyv.livescenes.document.model.PLVSAssistantInfo;
import com.easefun.polyv.livescenes.document.model.PLVSChangePPTInfo;
import com.easefun.polyv.livescenes.document.model.PLVSEditTextInfo;
import com.easefun.polyv.livescenes.document.model.PLVSPPTInfo;
import com.easefun.polyv.livescenes.document.model.PLVSPPTJsModel;
import com.easefun.polyv.livescenes.document.model.PLVSPPTPaintStatus;
import com.easefun.polyv.livescenes.document.model.PLVSPPTStatus;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.easefun.polyv.livescenes.upload.OnPLVSDocumentUploadListener;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.eventbus.ppt.PLVOnSliceStartEventBus;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.socket.user.PLVSocketUserConstant;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author suhongtao
 */
public class PLVDocumentPresenter implements IPLVDocumentContract.Presenter {

    // <editor-fold defaultstate="collapsed" desc="??????">

    private static PLVDocumentPresenter INSTANCE;

    private PLVDocumentPresenter() {
    }

    public static IPLVDocumentContract.Presenter getInstance() {
        if (INSTANCE == null) {
            synchronized (PLVDocumentPresenter.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PLVDocumentPresenter();
                }
            }
        }
        return INSTANCE;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????">

    private static final String TAG = PLVDocumentPresenter.class.getSimpleName();
    //??????auto id
    public static final int AUTO_ID_WHITE_BOARD = 0;

    // ????????? ???????????????????????????
    private boolean isInitialized = false;
    /**
     * MVP - View ?????????
     */
    private final List<WeakReference<IPLVDocumentContract.View>> viewWeakReferenceList = new ArrayList<>();

    /**
     * rx disposables
     */
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * MVP - Model
     */
    @Nullable
    private PLVDocumentRepository plvDocumentRepository;
    @Nullable
    private PLVPptUploadLocalRepository plvPptUploadLocalRepository;

    /**
     * ????????? ??????????????????
     * ????????????????????????????????????
     */
    private boolean isStreamStarted = false;

    //???????????????
    private boolean isGuest = false;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="???????????????">

    @Override
    public void init(LifecycleOwner lifecycleOwner,
                     IPLVLiveRoomDataManager liveRoomDataManager,
                     PLVSDocumentWebProcessor documentWebProcessor) {
        isGuest = liveRoomDataManager.getConfig().getUser().getViewerType().equals(PLVSocketUserConstant.USERTYPE_GUEST);
        initRepository(liveRoomDataManager, documentWebProcessor);
        initSocketListener();

        observeRefreshPptMessage(lifecycleOwner);
        observePptInfo(lifecycleOwner);
        observePptJsModel(lifecycleOwner);
        observePptStatus(lifecycleOwner);
        observePptPaintStatus(lifecycleOwner);
        observePptOnDeleteResponse(lifecycleOwner);

        observeOnSliceStartEvent();

        isInitialized = true;
    }

    /**
     * ????????? MVP - Model
     * ????????????WebView
     *
     * @param liveRoomDataManager
     * @param documentWebProcessor
     */
    private void initRepository(IPLVLiveRoomDataManager liveRoomDataManager, PLVSDocumentWebProcessor documentWebProcessor) {
        plvDocumentRepository = new PLVDocumentRepository(documentWebProcessor);
        plvDocumentRepository.init(liveRoomDataManager);

        PLVSocketUserBean userBean = new PLVSocketUserBean();
        userBean.setUserId(liveRoomDataManager.getConfig().getUser().getViewerId());
        userBean.setNick(liveRoomDataManager.getConfig().getUser().getViewerName());
        userBean.setPic(liveRoomDataManager.getConfig().getUser().getViewerAvatar());

        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.SETUSER, PLVGsonUtil.toJson(userBean));
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.AUTHORIZATION_PPT_PAINT, "{\"userType\":\"speaker\"}");
        if (!isGuest) {
            plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.CHANGEPPT, "{\"autoId\":0,\"isCamClosed\":0}");
        }
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.SETPAINTSTATUS, "{\"status\":\"open\"}");

        plvPptUploadLocalRepository = new PLVPptUploadLocalRepository();
    }

    /**
     * ?????????Socket??????
     * ??????????????????PPT??????socket??????
     */
    private void initSocketListener() {
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(new PLVSocketMessageObserver.OnMessageListener() {
            @Override
            public void onMessage(String listenEvent, String event, String message) {
                if (!PLVEventConstant.Ppt.ON_ASSISTANT_CONTROL.equals(listenEvent)) {
                    return;
                }
                PLVSAssistantInfo assistantInfo = PLVGsonUtil.fromJson(PLVSAssistantInfo.class, message);
                if (assistantInfo == null) {
                    return;
                }
                for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                    IPLVDocumentContract.View view = viewWeakReference.get();
                    if (view != null) {
                        view.onAssistantChangePptPage(assistantInfo.getData().getPageId());
                    }
                }
            }
        }, PLVEventConstant.Ppt.ON_ASSISTANT_CONTROL);
    }

    /**
     * ??????Model???PPT????????????
     * ???PPT????????????????????????socket??????????????????
     *
     * @param lifecycleOwner
     */
    private void observeRefreshPptMessage(LifecycleOwner lifecycleOwner) {
        plvDocumentRepository.getRefreshPptMessageLiveData().observe(lifecycleOwner, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String message) {
                if (isStreamStarted) {
                    PLVSocketWrapper.getInstance().emit(PLVMessageBaseEvent.LISTEN_EVENT, message);
                }
            }
        });
    }

    /**
     * ??????Model?????????PPT??????????????????
     * ???view?????????
     *
     * @param lifecycleOwner
     */
    private void observePptInfo(LifecycleOwner lifecycleOwner) {
        plvDocumentRepository.getPptInfoLiveData().observe(lifecycleOwner, new Observer<PLVStatefulData<PLVSPPTInfo>>() {
            @Override
            public void onChanged(@Nullable PLVStatefulData<PLVSPPTInfo> plvspptInfo) {
                if (plvspptInfo == null || !plvspptInfo.isSuccess()) {
                    return;
                }
                for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                    IPLVDocumentContract.View view = viewWeakReference.get();
                    if (view != null) {
                        view.onPptCoverList(plvspptInfo.getData());
                    }
                }
            }
        });
    }

    /**
     * ??????Model?????????PPT????????????????????????
     * ???view?????????
     *
     * @param lifecycleOwner
     */
    private void observePptJsModel(LifecycleOwner lifecycleOwner) {
        plvDocumentRepository.getPptJsModelLiveData().observe(lifecycleOwner, new Observer<PLVStatefulData<PLVSPPTJsModel>>() {
            @Override
            public void onChanged(@Nullable PLVStatefulData<PLVSPPTJsModel> plvsPptJsModel) {
                if (plvsPptJsModel == null || !plvsPptJsModel.isSuccess()) {
                    return;
                }
                for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                    IPLVDocumentContract.View view = viewWeakReference.get();
                    if (view != null) {
                        view.onPptPageList(plvsPptJsModel.getData());
                    }
                }
            }
        });
    }

    /**
     * ??????Model???Webview PPT????????????
     * ???view????????? PPTID?????? ????????????
     *
     * @param lifecycleOwner
     */
    private void observePptStatus(LifecycleOwner lifecycleOwner) {
        plvDocumentRepository.getPptStatusLiveData().observe(lifecycleOwner, new Observer<PLVSPPTStatus>() {
            @Override
            public void onChanged(@Nullable PLVSPPTStatus plvspptStatus) {
                if (plvspptStatus == null) {
                    return;
                }
                for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                    IPLVDocumentContract.View view = viewWeakReference.get();
                    if (view != null) {
                        view.onPptPageChange(plvspptStatus.getAutoId(), plvspptStatus.getPageId());
                        view.onPptStatusChange(plvspptStatus);
                    }
                }
            }
        });
    }

    /**
     * ??????Model???Webview ??????????????????
     * ???view?????????
     *
     * @param lifecycleOwner
     */
    private void observePptPaintStatus(LifecycleOwner lifecycleOwner) {
        plvDocumentRepository.getPptPaintStatusLiveData().observe(lifecycleOwner, new Observer<PLVSPPTPaintStatus>() {
            @Override
            public void onChanged(@Nullable PLVSPPTPaintStatus plvspptPaintStatus) {
                for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                    IPLVDocumentContract.View view = viewWeakReference.get();
                    if (view != null) {
                        view.onPptPaintStatus(plvspptPaintStatus);
                    }
                }
            }
        });
    }

    /**
     * ??????Model?????????PPT????????????
     *
     * @param lifecycleOwner
     */
    private void observePptOnDeleteResponse(LifecycleOwner lifecycleOwner) {
        plvDocumentRepository.getPptOnDeleteResponseLiveData().observe(lifecycleOwner, new Observer<PLVStatefulData<PLVSPPTInfo.DataBean.ContentsBean>>() {
            @Override
            public void onChanged(@Nullable PLVStatefulData<PLVSPPTInfo.DataBean.ContentsBean> response) {
                if (response == null) {
                    return;
                }

                for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                    IPLVDocumentContract.View view = viewWeakReference.get();
                    if (view != null) {
                        view.onPptDelete(response.isSuccess(), response.getData());
                    }
                }
            }
        });
    }

    /**
     * ??????sliceStart????????????????????????????????????webview
     * ?????????????????????????????????
     */
    private void observeOnSliceStartEvent() {
        Disposable disposable = PLVOnSliceStartEventBus.get()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PLVOnSliceStartEvent>() {
                    @Override
                    public void accept(PLVOnSliceStartEvent plvOnSliceStartEvent) {
                        if (plvDocumentRepository != null) {
                            plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.ONSLICESTART, PLVGsonUtil.toJson(plvOnSliceStartEvent));
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Presenter??????">

    @Override
    public void registerView(IPLVDocumentContract.View view) {
        viewWeakReferenceList.add(new WeakReference<>(view));
    }

    @Override
    public void notifyStreamStatus(boolean isStreamStarted) {
        this.isStreamStarted = isStreamStarted;
    }

    @Override
    public void switchShowMode(PLVDocumentMode showMode) {
        for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
            IPLVDocumentContract.View view = viewWeakReference.get();
            if (view != null) {
                view.onSwitchShowMode(showMode);
            }
        }
    }

    @Override
    public void enableMarkTool(boolean enable) {
        if (!checkInitialized()) {
            return;
        }
        if (enable) {
            plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.SETPAINTSTATUS, "{\"status\":\"open\"}");
        } else {
            plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.SETPAINTSTATUS, "{\"status\":\"close\"}");
        }
    }

    @Override
    public void changeColor(String colorString) {
        if (!checkInitialized()) {
            return;
        }
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.CHANGE_COLOR, colorString);
    }

    @Override
    public void changeMarkToolType(@PLVDocumentMarkToolType.Range String markToolType) {
        if (!checkInitialized()) {
            return;
        }
        if (PLVDocumentMarkToolType.CLEAR.equals(markToolType)) {
            plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.DELETEALLPAINT, "");
        } else if (PLVDocumentMarkToolType.ERASER.equals(markToolType)) {
            plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.ERASE_STATUS, "");
        } else if (PLVDocumentMarkToolType.BRUSH.equals(markToolType)
                || PLVDocumentMarkToolType.ARROW.equals(markToolType)
                || PLVDocumentMarkToolType.TEXT.equals(markToolType)) {
            String message = "{\"type\":\"" + markToolType + "\"}";
            plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.SETDRAWTYPE, message);
        }
    }

    @Override
    public void changeToWhiteBoard() {
        if (!checkInitialized()) {
            return;
        }
        changeWhiteBoardPage(0);
    }

    @Override
    public void changeWhiteBoardPage(int pageId) {
        if (!checkInitialized()) {
            return;
        }
        PLVSChangePPTInfo changePptInfo = new PLVSChangePPTInfo(AUTO_ID_WHITE_BOARD, pageId);
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.CHANGEPPT, PLVGsonUtil.toJson(changePptInfo));
    }

    @Override
    public void changePpt(int autoId) {
        if (!checkInitialized()) {
            return;
        }
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.CHANGEPPT, "{\"autoId\":" + autoId + "}");
        // ??????PPT?????????????????????????????????
        changePptPage(autoId, 0);
    }

    @Override
    public void changePptPage(int autoId, int pageId) {
        if (!checkInitialized()) {
            return;
        }
        PLVSChangePPTInfo changePptInfo = new PLVSChangePPTInfo(autoId, pageId);
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.CHANGEPPT, PLVGsonUtil.toJson(changePptInfo));
    }

    @Override
    public void changePptToLastStep() {
        if (!checkInitialized()) {
            return;
        }
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.CHANGEPPTPAGE, "{\"type\":\"gotoPreviousStep\"}");
    }

    @Override
    public void changePptToNextStep() {
        if (!checkInitialized()) {
            return;
        }
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.CHANGEPPTPAGE, "{\"type\":\"gotoNextStep\"}");
    }

    @Override
    public void changeTextContent(String content) {
        if (!checkInitialized()) {
            return;
        }
        PLVSEditTextInfo textInfo = new PLVSEditTextInfo(content);
        plvDocumentRepository.sendWebMessage(PLVSDocumentWebProcessor.FILLEDITTEXT, PLVGsonUtil.toJson(textInfo));
    }

    @Override
    public void requestGetPptCoverList() {
        requestGetPptCoverList(false);
    }

    @Override
    public void requestGetPptCoverList(boolean forceRefresh) {
        if (!checkInitialized()) {
            return;
        }
        plvDocumentRepository.requestPptCoverList(forceRefresh);
    }

    @Override
    public void requestGetPptPageList(int autoId) {
        if (!checkInitialized()) {
            return;
        }
        plvDocumentRepository.requestGetCachedPptPageList(autoId);
    }

    @Override
    public void onSelectUploadFile(Uri fileUri) {
        for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
            IPLVDocumentContract.View view = viewWeakReference.get();
            if (view != null) {
                boolean consume = view.requestSelectUploadFileConvertType(fileUri);
                if (consume) {
                    return;
                }
            }
        }
    }

    @Override
    public void uploadFile(Context context, File uploadFile, final String convertType, final OnPLVSDocumentUploadListener listener) {
        if (!checkInitialized()) {
            return;
        }

        final PLVPptUploadLocalCacheVO localCacheVO = new PLVPptUploadLocalCacheVO();
        localCacheVO.setStatus(PLVPptUploadStatus.STATUS_UNPREPARED);
        localCacheVO.setFileName(uploadFile.getName());
        localCacheVO.setFilePath(uploadFile.getAbsolutePath());
        localCacheVO.setConvertType(convertType);

        plvDocumentRepository.uploadPptFile(context, uploadFile, convertType, new OnPLVSDocumentUploadListener() {
            @Override
            public void onPrepared(PLVSPPTInfo.DataBean.ContentsBean documentBean) {
                if (listener != null) {
                    listener.onPrepared(documentBean);
                }
                // onPrepared ??????????????????????????????
                localCacheVO.setFileId(documentBean.getFileId());
                localCacheVO.setStatus(PLVPptUploadStatus.STATUS_PREPARED);
                plvPptUploadLocalRepository.saveCache(localCacheVO);
            }

            @Override
            public void onUploadProgress(PLVSPPTInfo.DataBean.ContentsBean documentBean, int progress) {
                if (listener != null) {
                    listener.onUploadProgress(documentBean, progress);
                }
                // ??????????????????????????????
                localCacheVO.setStatus(PLVPptUploadStatus.STATUS_UPLOADING);
                plvPptUploadLocalRepository.saveCache(localCacheVO);
            }

            @Override
            public void onUploadSuccess(PLVSPPTInfo.DataBean.ContentsBean documentBean) {
                if (listener != null) {
                    listener.onUploadSuccess(documentBean);
                }
                // ?????????????????????????????? ???????????? ????????????????????????
                localCacheVO.setStatus(PLVPptUploadStatus.STATUS_UPLOAD_SUCCESS);
                plvPptUploadLocalRepository.saveCache(localCacheVO);
                // ????????????????????????PPT??????????????????
                plvDocumentRepository.requestPptCoverList(true);
            }

            @Override
            public void onUploadFailed(@Nullable PLVSPPTInfo.DataBean.ContentsBean documentBean, int errorCode, String msg, Throwable throwable) {
                if (listener != null) {
                    listener.onUploadFailed(documentBean, errorCode, msg, throwable);
                }
                // ?????????????????????????????? ????????????
                localCacheVO.setStatus(PLVPptUploadStatus.STATUS_UPLOAD_FAILED);
                plvPptUploadLocalRepository.saveCache(localCacheVO);
            }

            @Override
            public void onConvertSuccess(PLVSPPTInfo.DataBean.ContentsBean documentBean) {
                if (listener != null) {
                    listener.onConvertSuccess(documentBean);
                }
                if (convertType.equals(documentBean.getConvertType())) {
                    localCacheVO.setStatus(PLVPptUploadStatus.STATUS_CONVERT_SUCCESS);
                } else {
                    // ??????????????????convertType??????????????????????????? ????????????????????????????????????????????????
                    localCacheVO.setStatus(PLVPptUploadStatus.STATUS_CONVERT_ANIMATE_LOSS);
                }
                plvPptUploadLocalRepository.saveCache(localCacheVO);

                // ???????????? ???View??????
                if (localCacheVO.getStatus() == PLVPptUploadStatus.STATUS_CONVERT_ANIMATE_LOSS) {
                    List<PLVPptUploadLocalCacheVO> convertAnimateLossVOList = new ArrayList<>();
                    convertAnimateLossVOList.add(localCacheVO);
                    for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                        IPLVDocumentContract.View view = viewWeakReference.get();
                        if (view != null) {
                            boolean consume = view.notifyFileConvertAnimateLoss(convertAnimateLossVOList);
                            if (consume) {
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onConvertFailed(PLVSPPTInfo.DataBean.ContentsBean documentBean, int errorCode, String msg, Throwable throwable) {
                if (listener != null) {
                    listener.onConvertFailed(documentBean, errorCode, msg, throwable);
                }
                // ?????????????????????????????? ????????????
                localCacheVO.setStatus(PLVPptUploadStatus.STATUS_CONVERT_FAILED);
                plvPptUploadLocalRepository.saveCache(localCacheVO);
            }

            @Override
            public void onDocumentExist(PLVSPPTInfo.DataBean.ContentsBean documentBean) {
                if (listener != null) {
                    listener.onDocumentExist(documentBean);
                }
                // ??????????????? ??????????????????????????????
                plvPptUploadLocalRepository.removeCache(localCacheVO.getFileId());
            }

            @Override
            public void onDocumentConverting(PLVSPPTInfo.DataBean.ContentsBean documentBean) {
                if (listener != null) {
                    listener.onDocumentConverting(documentBean);
                }
                // ?????????????????????????????? ??????????????????
                localCacheVO.setStatus(PLVPptUploadStatus.STATUS_CONVERTING);
                plvPptUploadLocalRepository.saveCache(localCacheVO);
            }
        });
    }

    @Override
    public void restartUploadFromCache(Context context, String fileId, OnPLVSDocumentUploadListener listener) {
        if (!checkInitialized()) {
            return;
        }
        // ??????fileId??????????????????????????????
        PLVPptUploadLocalCacheVO localCacheVO = plvPptUploadLocalRepository.getCache(fileId);
        if (localCacheVO == null) {
            return;
        }
        File file = new File(localCacheVO.getFilePath());
        if (!file.exists()) {
            Log.w(TAG, "file is not exist.");
            return;
        }
        uploadFile(context, file, localCacheVO.getConvertType(), listener);
    }

    @Override
    public void checkUploadFileStatus() {
        if (!checkInitialized()) {
            return;
        }
        List<PLVPptUploadLocalCacheVO> uploadNotSuccessVOList = new ArrayList<>();
        List<PLVPptUploadLocalCacheVO> convertAnimateLossVOList = new ArrayList<>();
        for (PLVPptUploadLocalCacheVO vo : plvPptUploadLocalRepository.listCache()) {
            if (vo.getStatus() == null) {
                plvPptUploadLocalRepository.removeCache(vo.getFileId());
                continue;
            }
            if (!PLVPptUploadStatus.isStatusUploadSuccess(vo.getStatus())) {
                // ????????????
                File file = new File(vo.getFilePath());
                if (!file.exists()) {
                    Log.i(TAG, "??????????????????????????????????????????");
                    // ??????????????????????????????????????????
                    plvPptUploadLocalRepository.removeCache(vo.getFileId());
                } else {
                    // ???????????????????????????????????????????????????????????????????????????
                    uploadNotSuccessVOList.add(vo);
                    continue;
                }
            }
            if (PLVPptUploadStatus.STATUS_CONVERT_ANIMATE_LOSS == vo.getStatus()) {
                // ????????????????????????????????????????????????????????????
                convertAnimateLossVOList.add(vo);
                continue;
            }

            // ????????????????????????????????????????????????
            plvPptUploadLocalRepository.removeCache(vo.getFileId());
        }

        // ???View?????? ????????????
        if (uploadNotSuccessVOList.size() > 0) {
            for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                IPLVDocumentContract.View view = viewWeakReference.get();
                if (view != null) {
                    boolean consume = view.notifyFileUploadNotSuccess(uploadNotSuccessVOList);
                    if (consume) {
                        break;
                    }
                }
            }
        }

        // ???View?????? ????????????
        if (convertAnimateLossVOList.size() > 0) {
            for (WeakReference<IPLVDocumentContract.View> viewWeakReference : viewWeakReferenceList) {
                IPLVDocumentContract.View view = viewWeakReference.get();
                if (view != null) {
                    boolean consume = view.notifyFileConvertAnimateLoss(convertAnimateLossVOList);
                    if (consume) {
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void removeUploadCache(int autoId) {
        if (!checkInitialized()) {
            return;
        }
        PLVSPPTInfo.DataBean.ContentsBean contentsBean = getPptContentsBeanFromAutoId(autoId);
        if (contentsBean == null) {
            return;
        }
        plvPptUploadLocalRepository.removeCache(contentsBean.getFileId());
    }

    @Override
    public void removeUploadCache(List<PLVPptUploadLocalCacheVO> localCacheVOS) {
        if (!checkInitialized()) {
            return;
        }
        if (localCacheVOS == null) {
            return;
        }
        for (PLVPptUploadLocalCacheVO localCacheVO : localCacheVOS) {
            plvPptUploadLocalRepository.removeCache(localCacheVO.getFileId());
        }
    }

    @Override
    public void deleteDocument(int autoId) {
        if (!checkInitialized()) {
            return;
        }
        plvDocumentRepository.deleteDocument(autoId);
    }

    @Override
    public void deleteDocument(String fileId) {
        if (!checkInitialized()) {
            return;
        }
        plvDocumentRepository.deleteDocument(fileId);
    }

    @Override
    public void destroy() {
        if (plvDocumentRepository != null) {
            plvDocumentRepository.destroy();
        }
        isInitialized = false;
        viewWeakReferenceList.clear();
        compositeDisposable.dispose();
        INSTANCE = null;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????????????????">

    /**
     * ?????????????????????????????????
     * ???????????????????????????
     *
     * @return isInitialized
     */
    private boolean checkInitialized() {
        if (!isInitialized
                || plvPptUploadLocalRepository == null
                || plvDocumentRepository == null) {
            Log.w(TAG, "Call PLVLSDocumentPresenter.init() first!");
        }
        return isInitialized;
    }

    /**
     * ??????autoId??????PPT??????VO
     * ????????????????????????
     *
     * @param autoId ??????id
     * @return ??????PPT??????VO??????????????????????????????autoId???ppt??????vo????????????null
     */
    @Nullable
    private PLVSPPTInfo.DataBean.ContentsBean getPptContentsBeanFromAutoId(int autoId) {
        if (!checkInitialized()) {
            return null;
        }
        PLVSPPTInfo pptInfo = plvDocumentRepository.getCachePptCoverList();
        if (pptInfo == null || pptInfo.getData() == null || pptInfo.getData().getContents() == null) {
            Log.w(TAG, "cache ppt cover list is null.");
            return null;
        }
        List<PLVSPPTInfo.DataBean.ContentsBean> contentsBeans = pptInfo.getData().getContents();
        for (PLVSPPTInfo.DataBean.ContentsBean contentsBean : contentsBeans) {
            if (contentsBean.getAutoId() == autoId) {
                return contentsBean;
            }
        }
        return null;
    }

    // </editor-fold>
}
