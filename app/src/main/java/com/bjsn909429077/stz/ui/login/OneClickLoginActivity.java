package com.bjsn909429077.stz.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.login.um.ExecutorManager;
import com.example.weblibrary.CallBack.LoginCallback;
import com.example.weblibrary.Manager.VP53Manager;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.User;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.Md5Util;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.umverify.UMResultCode;
import com.umeng.umverify.UMVerifyHelper;
import com.umeng.umverify.listener.UMAuthUIControlClickListener;
import com.umeng.umverify.listener.UMTokenResultListener;
import com.umeng.umverify.model.UMTokenRet;
import com.umeng.umverify.view.UMAuthRegisterViewConfig;
import com.umeng.umverify.view.UMAuthUIConfig;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bjsn909429077.stz.ui.login.um.MockRequest.getPhoneNumber;
import static com.bjsn909429077.stz.utils.UnitConversionUtil.dp2px;


/**
 * @author XuZhuChao
 * @create 2021/10/15
 * @Describe ????????????
 */
public class OneClickLoginActivity extends BaseActivity {


    private UMVerifyHelper mPhoneNumberAuthHelper;
    private UMTokenResultListener mTokenResultListener;
    private boolean isLoadFinish;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_one_click_login;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        sdkInit();
        oneKeyLogin();
    }


    public void sdkInit() {
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
                mPhoneNumberAuthHelper.quitLoginPage();
                try {
                    tokenRet = UMTokenRet.fromJson(s);
                    if (UMResultCode.CODE_ERROR_USER_CANCEL.equals(tokenRet.getCode())) {
                        //???????????????????????? ??????????????????app?????????
                        finish();
                    } else {

//                        Intent pIntent = new Intent(OneClickLoginActivity.this, LoginActivity.class);
                        Intent pIntent = new Intent(OneClickLoginActivity.this, LoginActivity.class);
                        startActivityForResult(pIntent, 1002);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                            Log.e(TAG, "????????????????????????????????????");
                            mPhoneNumberAuthHelper.quitLoginPage();
                            finish();
                            break;
                        //?????????????????????????????????????????????????????? ??????????????????
                        //????????????????????????????????????setSwitchAccHidden(true)???????????????  ???????????????view???????????????
                        case UMResultCode.CODE_ERROR_USER_SWITCH:
                            Log.e(TAG, "????????????????????????????????????????????????");
                            break;
                        //??????????????????????????????????????????
                        //??????????????????????????? ????????????????????????toast ?????????????????????????????????????????? setLogBtnToastHidden(true)????????????Toast
                        //???????????????????????????toast
                        case UMResultCode.CODE_ERROR_USER_LOGIN_BTN:
                            if (!jsonObj.getBoolean("isChecked")) {
                                Toast.makeText(mContext, R.string.custom_toast, Toast.LENGTH_SHORT).show();
                            }
                            break;
                        //checkbox???????????????????????????
                        case UMResultCode.CODE_ERROR_USER_CHECKBOX:
                            Log.e(TAG, "checkbox????????????" + jsonObj.getBoolean("isChecked"));
                            break;
                        //??????????????????????????????
                        case UMResultCode.CODE_ERROR_USER_PROTOCOL_CONTROL:
                            Log.e(TAG, "???????????????" + "name: " + jsonObj.getString("name") + ", url: " + jsonObj.getString("url"));
                            break;
                        default:
                            break;

                    }
                } catch (JSONException e) {

                }
            }
        });

        //???????????????????????????????????????
        mPhoneNumberAuthHelper.addAuthRegistViewConfig("switch_acc_tv", new UMAuthRegisterViewConfig.Builder()
                .setView(initDynamicView())
                .setRootViewId(UMAuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .build());
        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }


        mPhoneNumberAuthHelper.setAuthUIConfig(new UMAuthUIConfig.Builder()
                .setAppPrivacyOne("??????????????????", NovateUtils.baseUrl + BaseUrl.registerAgreementH5+"????????????")
                .setAppPrivacyTwo("??????????????????", NovateUtils.baseUrl + BaseUrl.registerAgreementH5+"????????????")
                .setAppPrivacyColor(Color.GRAY, Color.parseColor("#0B7AFB"))
                //????????????????????????????????????
                .setSwitchAccHidden(true)
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
//                .setAuthPageActIn("in_activity", "out_activity")
//                .setAuthPageActOut("in_activity", "out_activity")
                .setVendorPrivacyPrefix("???")
                .setVendorPrivacySuffix("???")
//                .setPageBackgroundPath("page_background_color")
                .setLogoImgPath("mytel_app_launcher")
                //??????????????????????????????????????????login_btn_bg.xml
//                .setLogBtnBackgroundPath("login_btn_bg")
                .setScreenOrientation(authPageOrientation)
                .create());
    }

    //?????????????????????????????????
    private View initDynamicView() {

        View moreView = View.inflate(mContext, R.layout.view_one_click_login_other, null);
        RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mLayoutParams2.setMargins(0, dp2px(this, 350), 0, 0);

        moreView.setLayoutParams(mLayoutParams2);

        TextView but_code_login = moreView.findViewById(R.id.but_code_login);

        but_code_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "??????????????????", Toast.LENGTH_SHORT).show();
//                Intent pIntent = new Intent(mContext, LoginActivity.class);
                Intent pIntent = new Intent(mContext, LoginActivity.class);
                startActivity(pIntent);
                mPhoneNumberAuthHelper.hideLoginLoading();
                mPhoneNumberAuthHelper.quitLoginPage();
                finish();
            }
        });



        return moreView;
    }


    //??????token
    public void getResultWithToken(final String token) {
        ExecutorManager.run(new Runnable() {
            @Override
            public void run() {
                final String result = getPhoneNumber(token);
               OneClickLoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        oneLogin(token);
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            if (resultCode == 1) {

            }
        }

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
                        ToastUtils.Toast(mContext, throwable.getMessage());
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


//        HashMap<String, String> map = new HashMap<>();
//
//        map.put("umAppkey", Constant.UMAppKey);
//        map.put("umToken",umToken);
//
//
//        OkUtils.getInstance().okPost(mContext, BaseUrl.umeng_verify, map, new OkUtils.OkRequestCall<User>() {
//
//            @Override
//            public void onError(Throwable errorMsg) {
//                RLog.e("----------errorMsg", errorMsg.getMessage());
//                showToast(errorMsg.getMessage()+"----");
//                mPhoneNumberAuthHelper.hideLoginLoading();
//            }
//
//            @Override
//            public void onNext(User data) {
////                RLog.e("----------data", data);
//                UserConfig.setUser(data);
//                Intent intent = new Intent(mContext, MainActivity.class);
//                startActivity(intent);
//                mPhoneNumberAuthHelper.hideLoginLoading();
//                mPhoneNumberAuthHelper.quitLoginPage();
//                finish();
//            }
//        });
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
                mPhoneNumberAuthHelper.hideLoginLoading();
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

                mPhoneNumberAuthHelper.hideLoginLoading();
                mPhoneNumberAuthHelper.quitLoginPage();
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




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}