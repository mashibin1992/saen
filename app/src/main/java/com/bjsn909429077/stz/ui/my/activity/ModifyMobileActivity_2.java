package com.bjsn909429077.stz.ui.my.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.User;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RTextView;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyMobileActivity_2 extends BaseActivity {


    @BindView(R.id.phoneNum)
    REditText phoneNum;
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
        return R.layout.activity_modify_mobile_2;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("修改手机号");
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

    @OnClick({R.id.but_get_code, R.id.but_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.but_get_code:
                phoneCode();
                break;
            case R.id.but_next:
                bindingMobile();
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
     * 绑定手机号
     */
    private void bindingMobile() {
        if (StringUtil.isBlank(phoneNum.getText().toString())) {
            ToastUtils.Toast(mContext, "请输入手机号");
            return;
        }
        if (StringUtil.isBlank(etCode.getText().toString())) {
            ToastUtils.Toast(mContext, "请输入验证码");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phoneNum.getText().toString());
        map.put("smsCode", etCode.getText().toString());
        NovateUtils.getInstance().Post(mContext, BaseUrl.binding_mobile, map, false,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(Object response) {
                        User.DataDTO user = UserConfig.getUser();
                        user.setMobile(phoneNum.getText().toString());
                        UserConfig.setUser(user);
                        showToast("修改成功");
                        RangerEvent.getInstance().getEventBus().post(RangerEvent.RefreshData.obtain("2"));
                        finish();
                    }
                });

    }


}