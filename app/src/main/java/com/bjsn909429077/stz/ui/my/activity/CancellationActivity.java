package com.bjsn909429077.stz.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.login.HomeWebActivity;
import com.bjsn909429077.stz.ui.login.LoginActivity;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.AppManager;
import com.jiangjun.library.utils.ButtonUtils;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RTextView;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class CancellationActivity extends BaseActivity {


    @BindView(R.id.phoneNum)
    REditText phoneNum;
    @BindView(R.id.et_code)
    REditText etCode;
    @BindView(R.id.but_get_code)
    TextView butGetCode;
    @BindView(R.id.but_queding)
    RTextView butQueding;
    @BindView(R.id.image_agreement)
    ImageView imageAgreement;


    private boolean isXieYi = false;
    private CountDownTimer countDownTimer;
    private long millisInFuture = 60 * 1000;
    private long countDownInterval = 1000;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_cancellation;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("注销");
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {

        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                int RemainingTime = (int) (millisUntilFinished / countDownInterval);
                butGetCode.setText("重新发送" + RemainingTime + "s");
            }

            @Override
            public void onFinish() {
                butGetCode.setText("重新获取");
                butGetCode.setEnabled(true);
            }
        };
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.but_get_code, R.id.but_queding,R.id.image_agreement, R.id.userAgreement, R.id.policyPrivacy})
    public void onClick(View view) {

        if (ButtonUtils.isFastDoubleClick(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.but_get_code:
                phoneCode();
                break;
            case R.id.but_queding:

                if (isXieYi)
                    checkCode();
                else
                    showToast("请阅读并同意用户注销协议");
                break;
            case R.id.image_agreement:
                isXieYi = !isXieYi;
                imageAgreement.setImageResource(isXieYi ? R.drawable.icon_xieyi_yes : R.drawable.icon_xieyi_no);
                break;
            case R.id.userAgreement:
                HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "用户注销协议", NovateUtils.baseUrl + BaseUrl.registerAgreementH5+"用户注销协议"));
                break;
            case R.id.policyPrivacy:
                HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "隐私政策", NovateUtils.baseUrl + BaseUrl.registerAgreementH5+"隐私政策"));
                break;
        }
    }


    /**
     * 获取验证码
     */
    private void phoneCode() {
        if (StringUtil.isBlank(phoneNum.getText().toString())) {
            ToastUtils.Toast(mContext, "请输入手机号");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phoneNum.getText().toString());
        NovateUtils.getInstance().Post(mContext, BaseUrl.get_sms_code, map, false,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(Object response) {
                        butGetCode.setEnabled(false);
                        countDownTimer.start();
                    }
                });

    }

    /**
     * 校验验证码是否正确
     */
    private void checkCode() {

        if (TextUtils.isEmpty(phoneNum.getText().toString())) {
            showToast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(etCode.getText().toString())) {
            showToast("请输入验证码");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phoneNum.getText().toString());
        map.put("smsCode", etCode.getText().toString());

        NovateUtils.getInstance().Post(mContext, BaseUrl.verification_code, map, false,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(Object response) {
                        destroy();
                    }
                });

    }


    /**
     * 获取验证码
     */
    private void destroy() {

        HashMap<String, String> map = new HashMap<>();

        NovateUtils.getInstance().Post(mContext, BaseUrl.destroy, map, false,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(Object response) {
                        showToast("注销成功");
                        UserConfig.clearUser(mContext);
                        AppManager.getAppManager().finishForActivity(MainActivity.class);
                        startActivity(new Intent(mContext, LoginActivity.class));
                    }
                });

    }

}