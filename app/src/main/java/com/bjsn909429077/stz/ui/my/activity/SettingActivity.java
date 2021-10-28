package com.bjsn909429077.stz.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.ui.MainActivity;
import com.example.weblibrary.CallBack.ServiceCallback;
import com.example.weblibrary.Manager.VP53Manager;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.DataCleanManager;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.widget.PromptDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.but_edit_phone)
    TextView butEditPhone;
    @BindView(R.id.but_edit_pwd)
    LinearLayout butEditPwd;
    @BindView(R.id.but_cancellation)
    LinearLayout butCancellation;
    @BindView(R.id.but_bind_wx)
    TextView butBindWx;
    @BindView(R.id.but_empty_cache)
    TextView butEmptyCache;
    @BindView(R.id.but_up_version)
    TextView butUpVersion;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_stting;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("设置");
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {


    }

    @Override
    protected void onResume() {
        super.onResume();
        butUpVersion.setText(UserConfig.getVersion(mContext));
        if (TextUtils.isEmpty(UserConfig.getUser().getOpenId())) {
            butBindWx.setText("未绑定");
        }else {
            butBindWx.setText("解除绑定");
        }
        getCacheSize();
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/28
     * @Describe 设置缓存大小
     */
    private void getCacheSize(){
        try {
            butEmptyCache.setText(DataCleanManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            butEmptyCache.setText("0mb");
            e.printStackTrace();
        }
    }

    @OnClick({R.id.but_edit_phone, R.id.but_edit_pwd, R.id.but_cancellation, R.id.but_bind_wx
            , R.id.but_empty_cache, R.id.but_up_version, R.id.but_outLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_edit_phone:
                startActivity(new Intent(mContext, ModifyMobileActivity_1.class));
                break;
            case R.id.but_edit_pwd:
                startActivity(new Intent(mContext, ChangePasswordActivity.class));
                break;
            case R.id.but_cancellation:
                startActivity(new Intent(mContext, CancellationActivity.class));
                break;
            case R.id.but_bind_wx:
                showLoginOut(2);
                break;
            case R.id.but_empty_cache:
                showLoginOut(3);
                break;
            case R.id.but_up_version:
                break;
            case R.id.but_outLogin:
                showLoginOut(1);
                break;
        }
    }


    /**
     * 退出登录
     */
    private void showLoginOut(int type) {

        String msg = "是否退出登录？";
        switch (type){
            case 1:
                msg = "是否退出登录？";
                break;
            case 2:
                msg = "是否去绑定微信？";
                break;
            case 3:
                msg = "是否清空缓存？";
                break;
        }

        PromptDialog promptDialog = new PromptDialog(mContext,  msg ,new PromptDialog.OnDialogClickListener() {
            @Override
            public void clickListener(String password, int whichBtn) {

                if (whichBtn == 1) {

                    switch (type){
                        case 1:
                            VP53Manager.getInstance().quitService(new ServiceCallback() {
                                @Override
                                public void onQuitSuccess() {
                                    RLog.e("TAG", "服务退出成功");
                                    UserConfig.putString("visitorId", "");
                                    UserConfig.clearUser(mContext);
                                    finish();
                                }

                                @Override
                                public void onQuitFailed() {
                                    Toast.makeText(mContext, "退出失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            });

                            break;
                        case 2:
                            break;
                        case 3:
                            DataCleanManager.clearAllCache(mContext);
                            getCacheSize();
                            break;
                    }
                }

            }
        });
        promptDialog.setTextSureBtn( type==1?"退出":"去绑定");
        promptDialog.showDialog();
    }


}