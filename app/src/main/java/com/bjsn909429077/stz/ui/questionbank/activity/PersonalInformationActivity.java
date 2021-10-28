package com.bjsn909429077.stz.ui.questionbank.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.utils.LoadingImgFrescoUtils;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.User;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.TwoXXPopupWindow;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalInformationActivity extends BaseActivity {


    @BindView(R.id.tv_sex)
    TextView tvSex;

    private User.DataDTO userInfo;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        commonTitleView.setTitle("个人信息");
    }

    @Override
    protected void initData() {
        userInfo = UserConfig.getUser();
        tvSex.setText(userInfo.getSex()==1?"男":"女");


    }

    @OnClick({R.id.personal_information_nickname, R.id.personal_information_name, R.id.personal_information_sex, R.id.personal_information_email})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_information_nickname:
                break;
            case R.id.personal_information_name:
                break;
            case R.id.personal_information_sex:
                showSexPopup();
                break;
            case R.id.personal_information_email:
                break;
        }
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 选择性别弹框
     */
    private void showSexPopup(){

        TwoXXPopupWindow twoXXPopupWindow = new TwoXXPopupWindow(mContext);
        twoXXPopupWindow.setText("男", "女");
        twoXXPopupWindow.setOnCallback(new TwoXXPopupWindow.OnChoiceTypeCallback() {
            @Override
            public void choiceType(int type) {
                if (type == 1) {
                    tvSex.setText("男");
                    upUserData("sex", "1");
                } else if (type == 2) {
                    tvSex.setText("女");
                    upUserData("sex", "2");
                }
            }
        });
        twoXXPopupWindow.show(tvSex);
    }



    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 修改用户信息
     */
    private void upUserData(String key,String v) {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", v);

        NovateUtils.getInstance().Post(mContext, BaseUrl.setUserInfo, map, true,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(Object wenDaTypeBean) {

                        showToast("修改成功");
                        RangerEvent.getInstance().getEventBus().post(RangerEvent.RefreshData.obtain("2"));
                    }
                });
    }
}