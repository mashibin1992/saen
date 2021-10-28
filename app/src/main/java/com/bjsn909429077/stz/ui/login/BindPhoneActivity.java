package com.bjsn909429077.stz.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.ui.MainActivity;
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
 * @Describe 绑定手机号
 */
public class BindPhoneActivity extends BaseActivity {


    @BindView(R.id.et_phone)
    REditText etPhone;
    @BindView(R.id.et_code)
    REditText etCode;
    @BindView(R.id.but_get_code)
    TextView butGetCode;
    @BindView(R.id.but_bind)
    RTextView butBind;


    private CountDownTimer countDownTimer;
    private long millisInFuture = 60 * 1000;
    private long countDownInterval = 1000;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("绑定手机");
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

    @OnClick({R.id.but_get_code, R.id.but_bind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_get_code:
                phoneCode();
                break;
            case R.id.but_bind:
                mobile_login();
                break;
        }
    }



    private void mobile_login() {
//        if (StringUtil.isBlank(phoneNum.getText().toString())) {
//            ToastUtils.Toast(mContext, "手机号不能为空");
//            return;
//        } else if (phoneNum.getText().toString().length() != 11) {
//            ToastUtils.Toast(mContext, "手机号不得少于11位");
//            return;
//        } else if (StringUtil.isBlank(password.getText().toString())) {
//            ToastUtils.Toast(mContext, "请输入密码");
//            return;
//        } else if (password.getText().toString().length() < 6) {
//            ToastUtils.Toast(mContext, "请输入正确的密码");
//            return;
//        }
//
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("mobile", phoneNum.getText().toString());
//        map.put("pwd", password.getText().toString());
//        NovateUtils.getInstance().get(mContext, BaseUrl.mobilePwdAppLogin, map, false, new NovateUtils.setRequestReturn<User>() {
//            @Override
//            public void onError(Throwable throwable) {
//                ToastUtils.Toast(mContext, throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccee(User response) {
//                UserConfig.setUser(response.getData());
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
//            }
//
//        });

    }



    /**
     * 获取验证码
     */
    private void phoneCode() {
        if (StringUtil.isBlank(etPhone.getText().toString())) {
            ToastUtils.Toast(mContext, "请输入手机号");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", etPhone.getText().toString());
        NovateUtils.getInstance().get(mContext, BaseUrl.courseList, map, true,
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
}