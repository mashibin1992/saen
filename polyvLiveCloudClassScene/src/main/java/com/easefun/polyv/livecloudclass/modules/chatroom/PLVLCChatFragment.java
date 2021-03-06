package com.easefun.polyv.livecloudclass.modules.chatroom;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.lifecycle.Observer;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmotionPersonalListAdapter;
import com.easefun.polyv.livecloudclass.modules.chatroom.utils.PLVChatroomUtils;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCBulletinTextView;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVCustomGiftBean;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVSpecialTypeTag;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.holder.PLVChatMessageItemType;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.module.utils.PLVUriPathHelper;
import com.easefun.polyv.livecommon.ui.widget.PLVImagePreviewPopupWindow;
import com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.window.PLVInputFragment;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.custom.PolyvCustomEvent;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendChatImageHelper;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.easefun.polyv.livescenes.model.PLVEmotionImageVO;
import com.easefun.polyv.livescenes.model.bulletin.PolyvBulletinVO;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.utils.PLVSDCardUtils;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import com.plv.socket.event.chat.PLVChatImgEvent;
import com.plv.socket.event.chat.PLVCloseRoomEvent;
import com.plv.socket.event.chat.PLVLikesEvent;
import com.plv.socket.event.chat.PLVSpeakEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.event.login.PLVLogoutEvent;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.StringUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ????????????tab???
 */
public class PLVLCChatFragment extends PLVInputFragment implements View.OnClickListener {
    // <editor-fold defaultstate="collapsed" desc="??????">
    private String TAG = getClass().getSimpleName();

    private static final int REQUEST_SELECT_IMG = 0x01;//????????????????????????
    private static final int REQUEST_OPEN_CAMERA = 0x02;//????????????????????????
    //??????????????????
    private PLVLCChatCommonMessageList chatCommonMessageList;
    //??????????????????view
    private TextView unreadMsgTv;

    //???????????????
    private EditText inputEt;

    //??????????????????
    private ImageView toggleEmojiIv;
    //??????????????????
    private ImageView toggleMoreIv;

    //??????????????????????????????
    private SwipeRefreshLayout swipeLoadView;

    //????????????
    private ViewGroup moreLy;
    //????????????/????????????
    private ViewGroup changeMessageTypeLy;
    private TextView messageTypeTv;
    //????????????
    private ViewGroup sendImgLy;
    //??????
    private ViewGroup openCameraLy;
    //??????
    private ViewGroup showBulletinLy;
    //??????view?????????????????????????????????
    private View moreEmptyView;

    //????????????
    private ViewGroup emojiLy;
    private TextView sendMsgTv;
    private ImageView deleteMsgIv;
    //emoji????????????
    private RecyclerView emojiRv;
    //emoji??????tab
    private ImageView tabEmojiIv;
    private RecyclerView emojiPersonalRv;
    //???????????????tab
    private ImageView tabPersonalIv;
    //???????????????????????????
    private PLVImagePreviewPopupWindow emotionPreviewWindow;

    //????????????
    private ViewGroup likesLy;
    private PLVLCLikeIconView likesView;
    private TextView likesCountTv;
    private long likesCount;

    //?????????
    private PLVLCGreetingTextView greetingTv;
    private boolean isShowGreeting;//?????????????????????

    //??????(???????????????)
    private PLVLCBulletinTextView bulletinTv;

    //?????????presenter
    private IPLVChatroomContract.IChatroomPresenter chatroomPresenter;

    //???????????????????????????
    private File takePictureFilePath;
    private Uri takePictureUri;

    //?????????????????????
    private boolean isLiveType;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????">
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.plvlc_chatroom_chat_portrait_fragment, null);
        initView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMG && resultCode == Activity.RESULT_OK) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                String picturePath = PLVUriPathHelper.getPrivatePath(getContext(), selectedUri);
                sendImg(picturePath);
            } else {
                ToastUtils.showShort("cannot retrieve selected image");
            }
        } else if (requestCode == REQUEST_OPEN_CAMERA && resultCode == Activity.RESULT_OK) {//data->null
            if (Build.VERSION.SDK_INT >= 29) {
                String picturePath = PLVUriPathHelper.getPrivatePath(getContext(), takePictureUri);
                sendImg(picturePath);
            } else {
                sendImg(takePictureFilePath.getAbsolutePath());
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="???????????????">
    public void init(PLVLCChatCommonMessageList chatCommonMessageList) {
        this.chatCommonMessageList = chatCommonMessageList;
    }

    //????????????????????????????????????????????????????????????????????????(??????????????????)??????
    public void setIsLiveType(boolean isLiveType) {
        this.isLiveType = isLiveType;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????????view">
    private void initView() {
        if (chatCommonMessageList == null || chatroomPresenter == null) {
            return;
        }
        //????????????view
        unreadMsgTv = findViewById(R.id.unread_msg_tv);
        chatCommonMessageList.addUnreadView(unreadMsgTv);
        chatCommonMessageList.addOnUnreadCountChangeListener(new PLVMessageRecyclerView.OnUnreadCountChangeListener() {
            @Override
            public void onChange(int currentUnreadCount) {
                unreadMsgTv.setText("???" + currentUnreadCount + "???????????????????????????");
            }
        });

        //???????????????
        inputEt = findViewById(R.id.input_et);
        inputEt.addTextChangedListener(inputTextWatcher);

        //?????????????????????
        toggleEmojiIv = findViewById(R.id.toggle_emoji_iv);
        toggleEmojiIv.setOnClickListener(this);
        toggleMoreIv = findViewById(R.id.toggle_more_iv);
        toggleMoreIv.setVisibility(View.VISIBLE);
        toggleMoreIv.setOnClickListener(this);

        //????????????
        swipeLoadView = findViewById(R.id.swipe_load_view);
        swipeLoadView.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeLoadView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (chatroomPresenter == null) {
                    return;
                }
                chatroomPresenter.requestChatHistory(chatroomPresenter.getViewIndex(chatroomView));
            }
        });

        //?????????????????????????????????????????????
        boolean result = chatCommonMessageList.attachToParent(swipeLoadView, false);
        if (result) {
            //???????????????????????????chatroomPresenter.registerView?????????
            chatCommonMessageList.setMsgIndex(chatroomPresenter.getViewIndex(chatroomView));
            //????????????????????????????????????
            if (chatroomPresenter.getChatHistoryTime() == 0) {
                chatroomPresenter.requestChatHistory(chatroomPresenter.getViewIndex(chatroomView));//????????????????????????
            }
        }

        //????????????
        moreLy = findViewById(R.id.more_ly);
        changeMessageTypeLy = findViewById(R.id.change_message_type_ly);
        changeMessageTypeLy.setOnClickListener(this);
        messageTypeTv = findViewById(R.id.message_type_tv);
        sendImgLy = findViewById(R.id.send_img_ly);
        sendImgLy.setOnClickListener(this);
        openCameraLy = findViewById(R.id.open_camera_ly);
        openCameraLy.setOnClickListener(this);
        moreEmptyView = findViewById(R.id.more_empty_view);
        showBulletinLy = findViewById(R.id.show_bulletin_ly);
        showBulletinLy.setOnClickListener(this);
        if (!isLiveType) {
            showBulletinLy.setVisibility(View.INVISIBLE);
        }

        //????????????
        emojiLy = findViewById(R.id.emoji_ly);
        sendMsgTv = findViewById(R.id.send_msg_tv);
        sendMsgTv.setOnClickListener(this);
        deleteMsgIv = findViewById(R.id.delete_msg_iv);
        deleteMsgIv.setOnClickListener(this);
        //emoji????????????
        emojiRv = findViewById(R.id.emoji_rv);
        PLVChatroomUtils.initEmojiList(emojiRv, inputEt);
        //??????????????????
        emojiPersonalRv = findViewById(R.id.emoji_personal_rv);
        //??????????????????
        chatroomPresenter.getChatEmotionImages();
        //??????tab
        tabEmojiIv = findViewById(R.id.plvlc_emoji_tab_emoji_iv);
        tabEmojiIv.setSelected(true);
        tabEmojiIv.setOnClickListener(this);
        tabPersonalIv = findViewById(R.id.plvlc_emoji_tab_personal_iv);
        tabPersonalIv.setVisibility(View.VISIBLE);
        tabPersonalIv.setOnClickListener(this);
        //??????????????????
        emotionPreviewWindow = new PLVImagePreviewPopupWindow(getContext());

        //????????????
        likesLy = findViewById(R.id.likes_ly);
        likesView = findViewById(R.id.likes_view);
        likesView.setOnButtonClickListener(this);
        likesCountTv = findViewById(R.id.likes_count_tv);
        if (likesCount != 0) {
            String likesString = StringUtils.toWString(likesCount);
            likesCountTv.setText(likesString);
        }

        //?????????
        greetingTv = findViewById(R.id.greeting_tv);

        //??????(???????????????)
        bulletinTv = findViewById(R.id.bulletin_tv);

        addPopupButton(toggleEmojiIv);
        addPopupLayout(emojiLy);

        addPopupButton(toggleMoreIv);
        addPopupLayout(moreLy);

        //?????????????????????????????????
        observeFunctionSwitchData();
        //?????????????????????????????????
        observeEmotionImages();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="??????API - ??????PLVInputFragment???????????????">
    @Override
    public int inputLayoutId() {
        return R.id.bottom_input_ly;
    }

    @Override
    public int inputViewId() {
        return R.id.input_et;
    }

    @Override
    public boolean onSendMsg(String message) {
        return sendChatMessage(message);
    }

    @Override
    public int attachContainerViewId() {
        return R.id.plvlc_chatroom_input_layout_container;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - MVP?????????view?????????">
    private IPLVChatroomContract.IChatroomView chatroomView = new PLVAbsChatroomView() {
        @Override
        public void setPresenter(@NonNull IPLVChatroomContract.IChatroomPresenter presenter) {
            super.setPresenter(presenter);
            chatroomPresenter = presenter;
        }

        @Override
        public void onSpeakEvent(@NonNull PLVSpeakEvent speakEvent) {
            super.onSpeakEvent(speakEvent);
            acceptSpeakEvent(speakEvent);
        }

        @Override
        public int getSpeakEmojiSize() {
            return ConvertUtils.dp2px(16);
        }

        @Override
        public void onImgEvent(@NonNull PLVChatImgEvent chatImgEvent) {
            super.onImgEvent(chatImgEvent);
        }

        @Override
        public void onLikesEvent(@NonNull PLVLikesEvent likesEvent) {
            super.onLikesEvent(likesEvent);
            acceptLikesMessage(likesEvent.getCount());
        }

        @Override
        public void onLoginEvent(@NonNull PLVLoginEvent loginEvent) {
            super.onLoginEvent(loginEvent);
            acceptLoginEvent(loginEvent);
        }

        @Override
        public void onLogoutEvent(@NonNull PLVLogoutEvent logoutEvent) {
            super.onLogoutEvent(logoutEvent);
        }

        @Override
        public void onBulletinEvent(@NonNull PolyvBulletinVO bulletinVO) {
            super.onBulletinEvent(bulletinVO);
        }

        @Override
        public void onRemoveBulletinEvent() {
            super.onRemoveBulletinEvent();
        }

        @Override
        public void onCloseRoomEvent(@NonNull final PLVCloseRoomEvent closeRoomEvent) {
            super.onCloseRoomEvent(closeRoomEvent);
            if (chatCommonMessageList != null && chatCommonMessageList.isLandscapeLayout()) {
                return;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showLong(closeRoomEvent.getValue().isClosed() ? R.string.plv_chat_toast_chatroom_close : R.string.plv_chat_toast_chatroom_open);
                }
            });
        }

        @Override
        public void onRemoveMessageEvent(@Nullable String id, boolean isRemoveAll) {
            super.onRemoveMessageEvent(id, isRemoveAll);
            removeChatMessageToList(id, isRemoveAll);
        }

        @Override
        public void onCustomGiftEvent(@NonNull PolyvCustomEvent.UserBean userBean, @NonNull PLVCustomGiftBean customGiftBean) {
            super.onCustomGiftEvent(userBean, customGiftBean);
        }

        @Override
        public void onLocalSpeakMessage(@Nullable PolyvLocalMessage localMessage) {
            super.onLocalSpeakMessage(localMessage);
            if (localMessage == null) {
                return;
            }
            final List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData<>(localMessage, PLVChatMessageItemType.ITEMTYPE_SEND_SPEAK, new PLVSpecialTypeTag()));
            if (!isShowKeyBoard(new OnceHideKeyBoardListener() {
                @Override
                public void call() {
                    //?????????????????????
                    addChatMessageToList(dataList, true);//?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                }
            })) {
                //?????????????????????
                addChatMessageToList(dataList, true);
            }
        }

        @Override
        public void onLoadEmotionMessage(@Nullable PLVChatEmotionEvent emotionEvent) {
            super.onLoadEmotionMessage(emotionEvent);
            if(emotionEvent == null){
                return;
            }
            final List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData(emotionEvent, PLVChatMessageItemType.ITEMTYPE_EMOTION, new PLVSpecialTypeTag()));
            if (!isShowKeyBoard(new OnceHideKeyBoardListener() {
                @Override
                public void call() {
                    //?????????????????????
                    addChatMessageToList(dataList, true);//?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                }
            })) {
                //?????????????????????
                addChatMessageToList(dataList, true);
            }
        }

        @Override
        public void onLocalImageMessage(@Nullable PolyvSendLocalImgEvent localImgEvent) {
            super.onLocalImageMessage(localImgEvent);
            List<PLVBaseViewData> dataList = new ArrayList<>();
            dataList.add(new PLVBaseViewData<>(localImgEvent, PLVChatMessageItemType.ITEMTYPE_SEND_IMG, new PLVSpecialTypeTag()));
            //?????????????????????
            addChatMessageToList(dataList, true);
        }

        @Override
        public void onSpeakImgDataList(List<PLVBaseViewData> chatMessageDataList) {
            super.onSpeakImgDataList(chatMessageDataList);
            //?????????????????????
            addChatMessageToList(chatMessageDataList, false);
        }

        @Override
        public void onHistoryDataList(List<PLVBaseViewData<PLVBaseEvent>> chatMessageDataList, int requestSuccessTime, boolean isNoMoreHistory, int viewIndex) {
            super.onHistoryDataList(chatMessageDataList, requestSuccessTime, isNoMoreHistory, viewIndex);
            if (swipeLoadView != null) {
                swipeLoadView.setRefreshing(false);
                swipeLoadView.setEnabled(true);
            }
            if (!chatMessageDataList.isEmpty()) {
                addChatHistoryToList(chatMessageDataList, requestSuccessTime == 1);
            }
            if (isNoMoreHistory) {
                ToastUtils.showShort(R.string.plv_chat_toast_history_all_loaded);
                if (swipeLoadView != null) {
                    swipeLoadView.setEnabled(false);
                }
            }
        }

        @Override
        public void onHistoryRequestFailed(String errorMsg, Throwable t, int viewIndex) {
            super.onHistoryRequestFailed(errorMsg, t, viewIndex);
            if (swipeLoadView != null) {
                swipeLoadView.setRefreshing(false);
                swipeLoadView.setEnabled(true);
            }
            if (chatroomPresenter != null && viewIndex == chatroomPresenter.getViewIndex(chatroomView)) {
                ToastUtils.showShort(getString(R.string.plv_chat_toast_history_load_failed) + ": " + errorMsg);
            }
        }
    };

    public IPLVChatroomContract.IChatroomView getChatroomView() {
        return chatroomView;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????">
    private void acceptLoginEvent(final PLVLoginEvent loginEvent) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //???????????????
                if (greetingTv != null && isShowGreeting) {
                    greetingTv.acceptLoginEvent(loginEvent);
                }
            }
        });
    }

    private void acceptSpeakEvent(PLVSpeakEvent speakEvent) {
        //??????????????????????????????
        if (PLVSocketUserConstant.USERTYPE_MANAGER.equals(speakEvent.getUser().getUserType())) {
            //?????????????????????(???????????????)
            if (bulletinTv != null) {
                bulletinTv.startMarquee((CharSequence) speakEvent.getObjects()[0]);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ??????????????????">
    private void addChatMessageToList(final List<PLVBaseViewData> chatMessageDataList, final boolean isScrollEnd) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (chatCommonMessageList != null) {
                    chatCommonMessageList.addChatMessageToList(chatMessageDataList, isScrollEnd, false);
                }
            }
        });
    }

    private void addChatHistoryToList(final List<PLVBaseViewData<PLVBaseEvent>> chatMessageDataList, final boolean isScrollEnd) {
        if (chatCommonMessageList != null) {
            chatCommonMessageList.addChatHistoryToList(chatMessageDataList, isScrollEnd, false);
        }
    }

    private void removeChatMessageToList(final String id, final boolean isRemoveAll) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (chatCommonMessageList == null) {
                    return;
                }
                if (isRemoveAll) {
                    chatCommonMessageList.removeAllChatMessage(false);
                } else {
                    chatCommonMessageList.removeChatMessage(id, false);
                }
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ??????????????????">
    private boolean sendChatMessage(String message) {
        if (message.trim().length() == 0) {
            ToastUtils.showLong(R.string.plv_chat_toast_send_text_empty);
            return false;
        } else {
            PolyvLocalMessage localMessage = new PolyvLocalMessage(message);
            if (chatroomPresenter == null) {
                return false;
            }
            Pair<Boolean, Integer> sendResult = chatroomPresenter.sendChatMessage(localMessage);
            if (sendResult.first) {
                //????????????????????????????????????/????????????????????????
                inputEt.setText("");
                hideSoftInputAndPopupLayout();
                return true;
            } else {
                //????????????
                ToastUtils.showShort(getString(R.string.plv_chat_toast_send_msg_failed) + ": " + sendResult.second);
                return false;
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ????????????????????????????????????">
    private void requestSelectImg() {
        ArrayList<String> permissions = new ArrayList<>(1);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PLVFastPermission.getInstance()
                .start((Activity) getContext(), permissions, new PLVOnPermissionCallback() {
                    @Override
                    public void onAllGranted() {
                        selectImg();
                    }

                    @Override
                    public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
                        if (!deniedForeverP.isEmpty()) {
                            showRequestPermissionDialog("???????????????????????????????????????????????????????????????????????????????????????");
                        } else {
                            ToastUtils.showShort("???????????????????????????????????????");
                        }
                    }
                });
    }

    private void selectImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "????????????"), REQUEST_SELECT_IMG);
    }

    private void requestOpenCamera() {
        ArrayList<String> permissions = new ArrayList<>(2);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
        PLVFastPermission.getInstance()
                .start((Activity) getContext(), permissions, new PLVOnPermissionCallback() {
                    @Override
                    public void onAllGranted() {
                        openCamera();
                    }

                    @Override
                    public void onPartialGranted(ArrayList<String> grantedPermissions, ArrayList<String> deniedPermissions, ArrayList<String> deniedForeverP) {
                        if (!deniedForeverP.isEmpty()) {
                            showRequestPermissionDialog("??????????????????????????????????????????????????????????????????????????????????????????");
                        } else {
                            ToastUtils.showShort("??????????????????????????????????????????");
                        }
                    }
                });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String picName = System.currentTimeMillis() + ".jpg";//???????????????
        if (Build.VERSION.SDK_INT >= 29) {
            Environment.getExternalStorageState();
            // ???????????????SD???,????????????SD?????????,?????????SD????????????????????????
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, picName);
            takePictureUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            if (getContext() == null || getContext().getApplicationContext() == null) {
                return;
            }
            String savePath = PLVSDCardUtils.createPath(getContext(), "PLVChatImg");
            takePictureFilePath = new File(savePath, picName);
            takePictureUri = FileProvider.getUriForFile(
                    getContext(),
                    getContext().getApplicationContext().getPackageName() + ".plvfileprovider",
                    takePictureFilePath);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, takePictureUri);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_OPEN_CAMERA);
    }

    private void showRequestPermissionDialog(String message) {
        new AlertDialog.Builder(getContext()).setTitle("??????")
                .setMessage(message)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PLVFastPermission.getInstance().jump2Settings(getContext());
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setCancelable(false).show();
    }

    private void sendImg(String picturePath) {
        PolyvSendLocalImgEvent sendLocalImgEvent = new PolyvSendLocalImgEvent();
        sendLocalImgEvent.setImageFilePath(picturePath);
        int[] pictureWh = PolyvSendChatImageHelper.getPictureWh(picturePath);
        sendLocalImgEvent.setWidth(pictureWh[0]);
        sendLocalImgEvent.setHeight(pictureWh[1]);

        if (chatroomPresenter != null) {
            chatroomPresenter.sendChatImage(sendLocalImgEvent);
        }
        hideSoftInputAndPopupLayout();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ???????????????????????????">
    public boolean isDisplaySpecialType() {
        return changeMessageTypeLy != null && changeMessageTypeLy.isSelected();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - ?????????????????????">
    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s != null && s.length() > 0) {
                sendMsgTv.setEnabled(true);
                sendMsgTv.setSelected(true);
            } else {
                sendMsgTv.setSelected(false);
                sendMsgTv.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="?????? - ?????????????????????">
    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
        String likesString = StringUtils.toWString(likesCount);
        if (likesCountTv != null) {
            likesCountTv.setText(likesString);
        }
    }

    private void acceptLikesMessage(final int likesCount) {
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                startAddLoveIconTask(200, Math.min(5, likesCount));
            }
        });
    }

    private void startAddLoveIconTask(final long ts, final int count) {
        if (count >= 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (likesView != null) {
                        likesView.addLoveIcon(1);
                    }
                    startAddLoveIconTask(ts, count - 1);
                }
            }, ts);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????????TAB">
    private void changeEmojiTab(boolean isEmoji){
        tabEmojiIv.setSelected(isEmoji);
        tabPersonalIv.setSelected(isEmoji);
        int selectColor = Color.parseColor("#FF2B2C35");
        int unSelectColor = Color.parseColor("#FF202127");
        tabEmojiIv.setBackgroundColor(isEmoji ? selectColor : unSelectColor);
        tabPersonalIv.setBackgroundColor(isEmoji ?  unSelectColor : selectColor);
        //??????rv????????????
        if(isEmoji){
            //??????emoji?????????
            emojiRv.setVisibility(View.VISIBLE);
            sendMsgTv.setVisibility(View.VISIBLE);
            deleteMsgIv.setVisibility(View.VISIBLE);
            emojiPersonalRv.setVisibility(View.INVISIBLE);
        } else {
            //?????????????????????
            emojiRv.setVisibility(View.INVISIBLE);
            sendMsgTv.setVisibility(View.INVISIBLE);
            deleteMsgIv.setVisibility(View.INVISIBLE);
            emojiPersonalRv.setVisibility(View.VISIBLE);
        }
    }
    // </editor-fold >

    // <editor-fold defaultstate="collapsed" desc="????????????">
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (chatCommonMessageList == null) {
                return;
            }
            boolean result = chatCommonMessageList.attachToParent(swipeLoadView, false);
            if (result && chatroomPresenter != null) {
                chatCommonMessageList.setMsgIndex(chatroomPresenter.getViewIndex(chatroomView));
                //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                if (chatroomPresenter.getChatHistoryTime() == 0) {
                    chatroomPresenter.requestChatHistory(chatroomPresenter.getViewIndex(chatroomView));
                }
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="???????????? - ????????????????????????">
    private void observeFunctionSwitchData() {
        if (chatroomPresenter == null) {
            return;
        }
        chatroomPresenter.getData().getFunctionSwitchData().observe(this, new Observer<List<PolyvChatFunctionSwitchVO.DataBean>>() {
            @Override
            public void onChanged(@Nullable List<PolyvChatFunctionSwitchVO.DataBean> dataBeans) {
                if (chatroomPresenter != null) {
                    chatroomPresenter.getData().getFunctionSwitchData().removeObserver(this);
                }
                if (dataBeans == null) {
                    return;
                }
                for (PolyvChatFunctionSwitchVO.DataBean dataBean : dataBeans) {
                    boolean isSwitchEnabled = dataBean.isEnabled();
                    switch (dataBean.getType()) {
                        //????????????????????????
                        case PolyvChatFunctionSwitchVO.TYPE_VIEWER_SEND_IMG_ENABLED:
                            sendImgLy.setVisibility(isSwitchEnabled ? View.VISIBLE : View.GONE);
                            openCameraLy.setVisibility(isSwitchEnabled ? View.VISIBLE : View.GONE);
                            moreEmptyView.setVisibility(isSwitchEnabled ? View.GONE : View.VISIBLE);
                            break;
                        //???????????????
                        case PolyvChatFunctionSwitchVO.TYPE_WELCOME:
                            isShowGreeting = isSwitchEnabled;
                            break;
                        //??????/????????????
                        case PolyvChatFunctionSwitchVO.TYPE_SEND_FLOWERS_ENABLED:
                            likesLy.setVisibility(isSwitchEnabled ? View.VISIBLE : View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="???????????? - ????????????">
    private void observeEmotionImages(){
        if(chatroomPresenter == null){
            return;
        }

        chatroomPresenter.getData().getEmotionImages().observe(this, new Observer<List<PLVEmotionImageVO.EmotionImage>>() {
            @Override
            public void onChanged(@Nullable List<PLVEmotionImageVO.EmotionImage> emotionImages) {
                if (chatroomPresenter != null) {
                    chatroomPresenter.getData().getEmotionImages().removeObserver(this);
                }
                //?????????????????????????????????
                if(emotionImages != null && !emotionImages.isEmpty()){
                    PLVChatroomUtils.initEmojiPersonalList(emojiPersonalRv, 5,emotionImages, new PLVLCEmotionPersonalListAdapter.OnViewActionListener() {
                        @Override
                        public void onEmotionViewClick(PLVEmotionImageVO.EmotionImage emotionImage) {
                            if(chatroomPresenter != null){
                                Pair<Boolean, Integer> sendResult = chatroomPresenter.sendChatEmotionImage(new PLVChatEmotionEvent(emotionImage.getId()));

                                if (!sendResult.first) {
                                    //????????????
                                    ToastUtils.showShort(getString(R.string.plv_chat_toast_send_msg_failed) + ": " + sendResult.second);
                                } else {
                                    //????????????
                                    hideSoftInputAndPopupLayout();
                                }
                            }
                        }

                        @Override
                        public void onEmotionViewLongClick(PLVEmotionImageVO.EmotionImage emotionImage, View view) {
                            emotionPreviewWindow.showInTopCenter(emotionImage.getUrl(), view);
                        }
                    });
                } else {
                    Log.e(TAG, "emotionImages is null or empty");
                }
            }
        });
    }
    // </editor-fold >

    // <editor-fold defaultstate="collapsed" desc="????????????">
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.toggle_emoji_iv) {
            togglePopupLayout(toggleEmojiIv, emojiLy);
        } else if (id == R.id.toggle_more_iv) {
            togglePopupLayout(toggleMoreIv, moreLy);
        } else if (id == R.id.delete_msg_iv) {
            PLVChatroomUtils.deleteEmoText(inputEt);
        } else if (id == R.id.send_msg_tv) {
            sendChatMessage(inputEt.getText().toString());
        } else if (id == R.id.change_message_type_ly) {
            changeMessageTypeLy.setSelected(!changeMessageTypeLy.isSelected());
            messageTypeTv.setText(changeMessageTypeLy.isSelected() ? R.string.plv_chat_view_all_message : R.string.plv_chat_view_special_message);
            if (chatCommonMessageList != null) {
                chatCommonMessageList.changeDisplayType(changeMessageTypeLy.isSelected());
            }
        } else if (id == R.id.show_bulletin_ly) {
            hideSoftInputAndPopupLayout();
            if (onViewActionListener != null) {
                onViewActionListener.onShowBulletinAction();
            }
        } else if (id == R.id.send_img_ly) {
            requestSelectImg();
        } else if (id == R.id.open_camera_ly) {
            requestOpenCamera();
        } else if (id == R.id.likes_view) {
            if (chatroomPresenter != null) {
                chatroomPresenter.sendLikeMessage();
            }
            acceptLikesMessage(1);
        } else if(id == R.id.plvlc_emoji_tab_emoji_iv){
            changeEmojiTab(true);
        } else if(id == R.id.plvlc_emoji_tab_personal_iv){
            changeEmojiTab(false);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="????????? - view???????????????">
    private OnViewActionListener onViewActionListener;

    public void setOnViewActionListener(OnViewActionListener listener) {
        this.onViewActionListener = listener;
    }

    public interface OnViewActionListener {
        void onShowBulletinAction();
    }
    // </editor-fold>
}
