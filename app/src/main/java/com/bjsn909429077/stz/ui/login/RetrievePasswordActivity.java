package com.bjsn909429077.stz.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RTextView;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author XuZhuChao
 * @create 2021/9/6
 * @Describe
 */
public class RetrievePasswordActivity extends BaseActivity {


    @BindView(R.id.phoneNum)
    REditText phoneNum;
    @BindView(R.id.et_code)
    REditText etCode;
    @BindView(R.id.but_get_code)
    TextView butGetCode;
    @BindView(R.id.et_password_1)
    REditText etPassword1;
    @BindView(R.id.et_password_2)
    REditText etPassword2;
    @BindView(R.id.login)
    RTextView login;


    private CountDownTimer countDownTimer;
    private long millisInFuture = 60 * 1000;
    private long countDownInterval = 1000;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_retrieve_password;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("找回密码");
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {

        commonDidiver.setVisibility(View.GONE);

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

    @OnClick({R.id.but_get_code, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_get_code:

                phoneCode();
                break;
            case R.id.login:
                setPwd();
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
        NovateUtils.getInstance().Post(mContext, BaseUrl.get_sms_code, map, true,
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
     * 修改密码
     */
    private void setPwd() {
        if (StringUtil.isBlank(phoneNum.getText().toString())) {
            ToastUtils.Toast(mContext, "手机号不能为空");
            return;
        }

        if (StringUtil.isBlank(etCode.getText().toString())) {
            ToastUtils.Toast(mContext, "请输入验证码");
            return;
        }
        if (StringUtil.isBlank(etPassword1.getText().toString())) {
            ToastUtils.Toast(mContext, "请输入新密码");
            return;
        }
        if (StringUtil.isBlank(etPassword2.getText().toString())) {
            ToastUtils.Toast(mContext, "请确认新密码");
            return;
        }
        if (!etPassword2.getText().toString().equals(etPassword1.getText().toString())) {
            ToastUtils.Toast(mContext, "两次输入密码不一样");
            return;
        }


        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phoneNum.getText().toString());
        map.put("smsCode", etCode.getText().toString());
        map.put("newPassword", etPassword2.getText().toString());
        NovateUtils.getInstance().Post(mContext, BaseUrl.setNewPassword, map, false,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(Object response) {

                        showToast("修改成功");
                        finish();

                    }

                });

    }
}