package com.bjsn909429077.stz.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.ui.MainActivity;
import com.example.weblibrary.CallBack.LoginCallback;
import com.example.weblibrary.Manager.VP53Manager;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.AppManager;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.widget.UserAgreementDialog;

public class StartActivity extends Activity implements LifecycleOwner {

    private static final int WHAT_DELAY = 0x11;// 启动页的延时跳转
    //    private static final int DELAY_TIME = 3000;// 延时时间
    private static final int DELAY_TIME = 1000;// 延时时间
    private Context mContext;
    // 创建Handler对象，处理接收的消息
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_DELAY:// 延时3秒跳转
//                    if (UserConfig.isLogin()) {
                        startActivity(new Intent(StartActivity.this, MainActivity.class));//启动MainActivity

//                    } else {
//                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                    }
                    finish();//关闭当前活动
                    break;
            }
        }
    };
    private boolean isLoadFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Android应用第一次安装成功点击“打开”后Home键切出应用后再点击桌面图标返回导致应用重启问题
        setContentView(R.layout.activity_start);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        mContext = this;


        if (StringUtil.isBlank(UserConfig.getString("UserAgreement", ""))) {
            UserAgreementDialog dialog = new UserAgreementDialog(mContext, new UserAgreementDialog.OnDialogClickListener() {
                @Override
                public void clickListener(String password, int whichBtn) {
                    if (whichBtn == 0) {
                        //点击取消按钮的处理逻辑
                        AppManager.getAppManager().AppExit(mContext);
                    } else if (whichBtn == 1) {
                        //点击确定按钮的处理逻辑
                        UserConfig.putString("UserAgreement", "1");
                        startView();
                    }
                }

                @Override
                public void clickUserAgreementListener() {
                    HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "用户协议", NovateUtils.baseUrl + BaseUrl.registerAgreementH5));
                }

                @Override
                public void clickPrivacyPolicyListener() {
                    HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "隐私政策", NovateUtils.baseUrl + BaseUrl.registerAgreementH5));
                }

            }).addBaseDialogLifecycleObserver(this);

            dialog.showDialog();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        } else {
            startView();

            if (UserConfig.isLogin()) {
                loginService();
            }
        }
    }



    /**
     * 登录53客服
     */
    private void loginService() {


        String visitorId = UserConfig.getString("visitorId", "");
        // 当visitorId为空时会被识别成新用户系统会自动分配一个visitorId，此后调用此id会被识别成一个用户
        VP53Manager.getInstance().loginService(visitorId, new LoginCallback() {

            @Override
            public void onLoadFinish() {
                RLog.e("TAG", "onLoadFinish");
                String visitorId = UserConfig.getString("visitorId", "");
                if(!visitorId.isEmpty()) {
                    // 必须调用(messageCallback可为空)
                    VP53Manager.getInstance().loadChatList(visitorId, null);
                } else {
                    // 首次打开并且登录服务未成功返回访客id
                    isLoadFinish = true;
                }
            }

            @Override
            public void onLoginSuccess(String visitorId) {
                RLog.e("TAG", "登录服务成功 visitorId: " + visitorId);
                if (visitorId != null && !visitorId.isEmpty()) { // 首次登录服务
                    // 保存访客id
                    UserConfig.putString("visitorId", visitorId);
                    if (isLoadFinish) { // 已经加载完成
                        // 必须调用(messageCallback可为空)
                        VP53Manager.getInstance().loadChatList(visitorId,null );
                    }
                }
            }

            @Override
            public void onLoginError(String s) {
                RLog.e("TAG", "登录服务失败: " + s);
                Toast.makeText(mContext, "登录服务失败- " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startView() {
        // 调用handler的sendEmptyMessageDelayed方法
        handler.sendEmptyMessageDelayed(WHAT_DELAY, DELAY_TIME);
    }

    final androidx.lifecycle.LifecycleRegistry LifecycleRegistry = new LifecycleRegistry(this);

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return LifecycleRegistry;
    }


    @Override
    protected void onPause() {
        super.onPause();
        LifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);

    }
}

