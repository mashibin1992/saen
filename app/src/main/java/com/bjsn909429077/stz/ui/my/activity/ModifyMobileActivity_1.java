package com.bjsn909429077.stz.ui.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.ButtonUtils;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RTextView;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyMobileActivity_1 extends BaseActivity {


    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_code)
    REditText etCode;
    @BindView(R.id.but_get_code)
    TextView butGetCode;
    @BindView(R.id.but_next)
    RTextView butNext;



    private CountDownTimer countDownTimer;
    private long millisInFuture = 60 * 1000;
    private long countDownInterval = 1000;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_modify_mobile_1;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("校验手机");
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
        String phone = UserConfig.getUser().getMobile();
        tvPhone.setText(phone.substring(0, 3) + "****" + phone.substring(7, phone.length()));

    }

    @OnClick({R.id.but_get_code, R.id.but_next})
    public void onClick(View view) {

        if (ButtonUtils.isFastDoubleClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.but_get_code:
                phoneCode();
                break;
            case R.id.but_next:
                checkCode();
                break;
        }
    }



    /**
     * 获取验证码
     */
    private void phoneCode() {

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", UserConfig.getUser().getMobile());
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

        if (TextUtils.isEmpty(etCode.getText().toString())) {
            showToast("请输入验证码");
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", UserConfig.getUser().getMobile());
        map.put("smsCode", etCode.getText().toString());

        NovateUtils.getInstance().Post(mContext, BaseUrl.verification_code, map, false,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(Object response) {
                        startActivity(new Intent(mContext, ModifyMobileActivity_2.class));
                        finish();
                    }
                });

    }
}