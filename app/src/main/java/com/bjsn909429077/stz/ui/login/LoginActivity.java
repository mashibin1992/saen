package com.bjsn909429077.stz.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.api.Const;
import com.bjsn909429077.stz.bean.LoginBean;
import com.bjsn909429077.stz.bean.SelectMsgFormIdBean;
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.course.SelectCourseActivity;
import com.bjsn909429077.stz.ui.login.um.ExecutorManager;
import com.example.weblibrary.Bean.TitleTheme;
import com.example.weblibrary.CallBack.LoginCallback;
import com.example.weblibrary.CallBack.MessageCallback;
import com.example.weblibrary.Manager.VP53Manager;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.User;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.ButtonUtils;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RLinearLayout;
import com.tamic.novate.Throwable;
import com.umeng.umverify.UMResultCode;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMAuthUIControlClickListener;
import com.umeng.umverify.listener.UMTokenResultListener;
import com.umeng.umverify.model.UMTokenRet;
import com.umeng.umverify.view.UMAuthRegisterViewConfig;
import com.umeng.umverify.view.UMAuthUIConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bjsn909429077.stz.ui.login.um.MockRequest.getPhoneNumber;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.phoneNum)
    REditText etPhoneNum;
    @BindView(R.id.et_password)
    REditText etPassword;
    @BindView(R.id.et_code)
    REditText etCode;
    @BindView(R.id.but_get_code)
    TextView butGetCode;
    @BindView(R.id.image_agreement)
    ImageView imageAgreement;
    @BindView(R.id.but_qh_mmdl)
    TextView butQhMmdl;
    @BindView(R.id.ui_code_login)
    RelativeLayout uiCodeLogin;

    private CountDownTimer countDownTimer;
    private long millisInFuture = 60 * 1000;
    private long countDownInterval = 1000;
    private boolean mIsAgreeChecked = false;
    private boolean mLoginType = false;//false = ???????????? true= ????????????
    private boolean isLoadFinish;


    private UMVerifyHelper mPhoneNumberAuthHelper;
    private UMTokenResultListener mTokenResultListener;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("??????");
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {

        commonDidiver.setVisibility(View.GONE);
        umSdkInit();
        oneKeyLogin();

    }

    @Override
    protected void initData() {

        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                int RemainingTime = (int) (millisUntilFinished / countDownInterval);
                butGetCode.setText("????????????" + RemainingTime + "s");
            }

            @Override
            public void onFinish() {
                butGetCode.setText("????????????");
                butGetCode.setEnabled(true);
            }
        };

    }


    /**
     * ???????????????
     */
    private void codeLogin() {
        if (StringUtil.isBlank(etPhoneNum.getText().toString())) {
            ToastUtils.Toast(mContext, "?????????????????????");
            return;
        }
        if (etPhoneNum.getText().toString().length()> 11) {
            ToastUtils.Toast(mContext, "?????????????????????11???");
            return;
        }
        if (StringUtil.isBlank(etCode.getText().toString())) {
            ToastUtils.Toast(mContext, "??????????????????");
            return;
        }


        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", etPhoneNum.getText().toString());
        map.put("smsCode", etCode.getText().toString());
        NovateUtils.getInstance().Post(mContext, BaseUrl.sms_code_login, map, false,
                new NovateUtils.setRequestReturn<LoginBean>() {
            @Override
            public void onError(Throwable throwable) {
                ToastUtils.Toast(mContext, throwable.getMessage());
            }

            @Override
            public void onSuccee(LoginBean response) {

                User.DataDTO user = new User.DataDTO();
                user.setToken(response.getData().getToken());
                UserConfig.setUser(user);
                getUserInfo();


            }

        });

    }

    /**
     * @author XuZhuChao
     * @create 2021/9/7
     * @Describe ????????????
     */
    private void mobile_login() {
        if (StringUtil.isBlank(etPhoneNum.getText().toString())) {
            showToast("?????????????????????");
            return;
        } else if (etPhoneNum.getText().toString().length() != 11) {
            showToast("?????????????????????11???");
            return;
        } else if (StringUtil.isBlank(etPassword.getText().toString())) {
            showToast("???????????????");
            return;
        } else if (etPassword.getText().toString().length() < 6) {
            showToast("????????????????????????");
            return;
        }


        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", etPhoneNum.getText().toString());
        map.put("password", etPassword.getText().toString());
        NovateUtils.getInstance().Post(mContext, BaseUrl.mobilePwdAppLogin, map, false, new NovateUtils.setRequestReturn<User>() {
            @Override
            public void onError(Throwable throwable) {
                ToastUtils.Toast(mContext, throwable.getMessage());
            }

            @Override
            public void onSuccee(User response) {
                UserConfig.setUser(response.getData());
                getUserInfo();
                customerSelect();
            }

        });

    }

    private void customerSelect(){
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.customerSelect, map, false, new NovateUtils.setRequestReturn<SelectMsgFormIdBean>() {
            @Override
            public void onError(Throwable throwable) {
                RLog.e("TAG", throwable.getMessage());
                SharedPreferencesUtil.saveData(mContext,"secondId",-1);
            }

            @Override
            public void onSuccee(SelectMsgFormIdBean response) {
                if(response.getData().getTestPaperSecondTypeId()==0){
                    SharedPreferencesUtil.saveData(mContext,"secondId",-1);
                }else {
                    SharedPreferencesUtil.saveData(mContext,"secondId",response.getData().getTestPaperSecondTypeId());
                }

            }

        });
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/7
     * @Describe  ??????????????????
     */
    private void getUserInfo() {


        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.getUserInfo, map, false, new NovateUtils.setRequestReturn<User>() {
            @Override
            public void onError(Throwable throwable) {
                ToastUtils.Toast(mContext, throwable.getMessage());
            }

            @Override
            public void onSuccee(User response) {

                User.DataDTO user  = response.getData();
                user.setToken(UserConfig.getUser().getToken());
                UserConfig.setUser(user);
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                showToast("????????????");
                loginService();
                finish();
            }

        });

    }


    /**
     * ??????53??????
     */
    private void loginService() {


        String visitorId = UserConfig.getString("visitorId", "");
        // ???visitorId????????????????????????????????????????????????????????????visitorId??????????????????id???????????????????????????
        VP53Manager.getInstance().loginService(visitorId, new LoginCallback() {

            @Override
            public void onLoadFinish() {
                RLog.e("TAG", "onLoadFinish");
                String visitorId = UserConfig.getString("visitorId", "");
                if(!visitorId.isEmpty()) {
                    // ????????????(messageCallback?????????)
                    VP53Manager.getInstance().loadChatList(visitorId, null);
                } else {
                    // ???????????????????????????????????????????????????id
                    isLoadFinish = true;
                }
            }

            @Override
            public void onLoginSuccess(String visitorId) {
                RLog.e("TAG", "?????????????????? visitorId: " + visitorId);
                if (visitorId != null && !visitorId.isEmpty()) { // ??????????????????
                    // ????????????id
                    UserConfig.putString("visitorId", visitorId);
                    if (isLoadFinish) { // ??????????????????
                        // ????????????(messageCallback?????????)
                        VP53Manager.getInstance().loadChatList(visitorId,null );
                    }
                }
            }

            @Override
            public void onLoginError(String s) {
                RLog.e("TAG", "??????????????????: " + s);
                Toast.makeText(mContext, "??????????????????- " + s, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.login, R.id.userAgreement, R.id.policyPrivacy, R.id.layout_agreement
            , R.id.but_get_code, R.id.but_wjmm, R.id.but_qh_mmdl, R.id.but_wx_login})
    public void onViewClicked(View view) {

        if (ButtonUtils.isFastDoubleClick(view.getId())) {
            return;
        }
        switch (view.getId()) {
            case R.id.userAgreement:
                HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "????????????", NovateUtils.baseUrl + BaseUrl.registerAgreementH5+"????????????"));
                break;
            case R.id.policyPrivacy:
                HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "????????????", NovateUtils.baseUrl + BaseUrl.registerAgreementH5+"????????????"));
                break;
            case R.id.layout_agreement:
                mIsAgreeChecked = !mIsAgreeChecked;

                switchAgreeCheck();
                break;
            case R.id.but_get_code:
                phoneCode();
                break;
            case R.id.but_wjmm://????????????
                startActivity(new Intent(mContext, RetrievePasswordActivity.class));
                break;
            case R.id.but_qh_mmdl://??????????????????
                mLoginType = !mLoginType;
                switchLoginType();
                break;
            case R.id.but_wx_login://????????????
                startActivity(new Intent(mContext, BindPhoneActivity.class));
                break;
            case R.id.login:
                if(mLoginType){
                    mobile_login();
                }else {
                    codeLogin();
                }
                break;
        }
    }


    /**
     * ???????????????
     */
    private void phoneCode() {
        if (StringUtil.isBlank(etPhoneNum.getText().toString())) {
            ToastUtils.Toast(mContext, "??????????????????");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", etPhoneNum.getText().toString());
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

    private void switchAgreeCheck() {

        if (mIsAgreeChecked) {
            imageAgreement.setImageResource(R.drawable.icon_login_select_y);
        } else {
            imageAgreement.setImageResource(R.drawable.icon_login_select_n);
        }
    }

    private void switchLoginType() {

        butQhMmdl.setText(mLoginType?"?????????????????????":"??????????????????");
        etPassword.setVisibility(mLoginType?View.VISIBLE:View.GONE);
        uiCodeLogin.setVisibility(mLoginType?View.GONE:View.VISIBLE);
    }



    /**
     * @author XuZhuChao
     * @create 2021/10/15
     * @Describe ???????????????????????????
     */
    public void umSdkInit() {
        mTokenResultListener = new UMTokenResultListener() {
            @Override
            public void onTokenSuccess(String s) {
                UMTokenRet tokenRet = null;
                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_START_AUTHPAGE_SUCCESS.equals(tokenRet.getCode())) {
                        Log.i("TAG", "????????????????????????" + s);
                    }

                    if (UMResultCode.CODE_GET_TOKEN_SUCCESS.equals(tokenRet.getCode())) {
                        Log.i("TAG", "??????token?????????" + s);
                        getResultWithToken(tokenRet.getToken());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTokenFailed(String s) {
                Log.e(TAG, "??????token?????????" + s);
                UMTokenRet tokenRet = null;
                mPhoneNumberAuthHelper.hideLoginLoading();
                mPhoneNumberAuthHelper.quitLoginPage();

            }
        };
        mPhoneNumberAuthHelper = UMVerifyHelper.getInstance(this, mTokenResultListener);
        mPhoneNumberAuthHelper.setAuthSDKInfo(Const.UM_VERIFY_secret_key);
    }




    /**
     * ??????app??????????????????????????????
     */
    private void oneKeyLogin() {
        mPhoneNumberAuthHelper = UMVerifyHelper.getInstance(getApplicationContext(), mTokenResultListener);
        configLoginTokenPort();
        mPhoneNumberAuthHelper.setAuthListener(mTokenResultListener);
        mPhoneNumberAuthHelper.getLoginToken(this, 5000);

    }


    //??????token
    public void getResultWithToken(final String token) {
        ExecutorManager.run(new Runnable() {
            @Override
            public void run() {
                final String result = getPhoneNumber(token);
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        oneLogin(token);
                    }
                });
            }
        });
    }


    /**
     * ????????????
     * @param umToken
     */
    private void oneLogin(String umToken) {


        HashMap<String, String> map = new HashMap<>();
        map.put("token", umToken);
        map.put("appkey", Const.UM_VERIFY_AppKey);
        NovateUtils.getInstance().Post(mContext, BaseUrl.um_login, map, false,
                new NovateUtils.setRequestReturn<LoginBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        showToast(throwable.getMessage());
                        mPhoneNumberAuthHelper.hideLoginLoading();
                    }

                    @Override
                    public void onSuccee(LoginBean response) {

                        User.DataDTO user = new User.DataDTO();
                        user.setToken(response.getData().getToken());
                        UserConfig.setUser(user);
                        getUserInfo();


                    }

                });
    }


    /**
     * ??????????????????
     */
    private void configLoginTokenPort() {

        mPhoneNumberAuthHelper.setUIClickListener(new UMAuthUIControlClickListener() {
            @Override
            public void onClick(String code, Context context, String jsonString) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);
                    switch (code) {
                        //??????????????????????????????????????????
                        case UMResultCode.CODE_ERROR_USER_CANCEL:
                            RLog.e(TAG, "????????????????????????????????????");
                            mPhoneNumberAuthHelper.quitLoginPage();
                            break;
                        //?????????????????????????????????????????????????????? ??????????????????
                        //????????????????????????????????????setSwitchAccHidden(true)???????????????  ???????????????view???????????????
                        case UMResultCode.CODE_ERROR_USER_SWITCH:
                            RLog.e(TAG, "????????????????????????????????????????????????");
                            break;
                        //??????????????????????????????????????????
                        //??????????????????????????? ????????????????????????toast ?????????????????????????????????????????? setLogBtnToastHidden(true)????????????Toast
                        //???????????????????????????toast
                        case UMResultCode.CODE_ERROR_USER_LOGIN_BTN:
                            if (!jsonObj.getBoolean("isChecked")) {
                                showToast(getString(R.string.custom_toast));
                            }
                            break;
                        //checkbox???????????????????????????
                        case UMResultCode.CODE_ERROR_USER_CHECKBOX:
                            RLog.e(TAG, "checkbox????????????" + jsonObj.getBoolean("isChecked"));
                            break;
                        //??????????????????????????????
                        case UMResultCode.CODE_ERROR_USER_PROTOCOL_CONTROL:
                            RLog.e(TAG, "???????????????" + "name: " + jsonObj.getString("name") + ", url: " + jsonObj.getString("url"));
                            break;
                        default:
                            break;

                    }
                } catch (JSONException e) {

                }
            }
        });

//        //???????????????????????????????????????
//        mPhoneNumberAuthHelper.addAuthRegistViewConfig("switch_acc_tv", new UMAuthRegisterViewConfig.Builder()
//                .setView(initDynamicView())
//                .setRootViewId(UMAuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
//                .build());
        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }


        mPhoneNumberAuthHelper.setAuthUIConfig(new UMAuthUIConfig.Builder()
                .setAppPrivacyOne("??????????????????", NovateUtils.baseUrl + BaseUrl.registerAgreementH5+"????????????")
                .setAppPrivacyTwo("??????????????????", NovateUtils.baseUrl + BaseUrl.registerAgreementH5+"????????????")
                .setAppPrivacyColor(Color.GRAY, Color.parseColor("#0B7AFB"))
                //????????????????????????????????????
                .setSwitchAccHidden(false)
                .setSwitchAccText("??????????????????")
                //????????????Toast
                .setLogBtnToastHidden(true)
                .setLogoImgDrawable(getDrawable(R.drawable.logo_saen))
                //??????????????????
                .setStatusBarColor(mContext.getResources().getColor(R.color.white))
                .setStatusBarUIFlag(View.SYSTEM_UI_FLAG_IMMERSIVE)
                .setBottomNavColor(mContext.getResources().getColor(R.color.mainColor))
                .setNavColor(mContext.getResources().getColor(R.color.white))
                .setNavTextColor(mContext.getResources().getColor(R.color.color_333333))
                .setNavReturnImgDrawable(getDrawable(R.drawable.ic_common_arrow_left))
                .setLightColor(true)
                .setWebNavTextSize(17)
                .setWebNavColor(mContext.getResources().getColor(R.color.mainColor))
                .setWebNavTextColor(mContext.getResources().getColor(R.color.white))
                .setWebSupportedJavascript(true)
                //????????????xml????????????????????????????????????????????? ????????????????????????drawable???drawable-xxx????????? in_activity.xml, mytel_app_launcher.png
                .setAuthPageActIn("date_out_anim", "date_put_anim")
                .setAuthPageActOut("date_out_anim", "date_put_anim")
                .setVendorPrivacyPrefix("???")
                .setVendorPrivacySuffix("???")
//                .setPageBackgroundPath("page_background_color")
                .setLogoImgPath("mytel_app_launcher")
                //??????????????????????????????????????????login_btn_bg.xml
//                .setLogBtnBackgroundPath("login_btn_bg")
                .setScreenOrientation(authPageOrientation)
                .create());
    }

}
