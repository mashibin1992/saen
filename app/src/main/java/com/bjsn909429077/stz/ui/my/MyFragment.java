package com.bjsn909429077.stz.ui.my;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.VPBean;
import com.bjsn909429077.stz.ui.login.HomeWebActivity;
import com.bjsn909429077.stz.ui.login.LoginActivity;
import com.bjsn909429077.stz.ui.my.activity.FeedbackActivity;
import com.bjsn909429077.stz.ui.my.activity.SettingActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.PersonalInformationActivity;
import com.bjsn909429077.stz.utils.LoadingImgFrescoUtils;
import com.bjsn909429077.stz.utils.OSSUpDataUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.user.User;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.ButtonUtils;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.TakingPicturesPopupWindow;
import com.tamic.novate.Throwable;

import androidx.cardview.widget.CardView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;


public class MyFragment extends BaseLazyLoadFragment {


    @BindView(R.id.but_in_setting)
    ImageView butInSetting;
    @BindView(R.id.image_pic)
    SimpleDraweeView imagePic;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.tv_vip_date)
    TextView tvVipDate;
    @BindView(R.id.but_in_edit_user_info)
    ImageView butInEditUserInfo;
    @BindView(R.id.but_in_qr_code)
    ImageView butInQrCode;
    @BindView(R.id.tv_msg_num)
    TextView tvMsgNum;
    @BindView(R.id.tv_quan_num)
    TextView tvQuanNum;
    @BindView(R.id.tv_jf_num)
    TextView tvJfNum;
    @BindView(R.id.my_point_lls)
    LinearLayout myPointLls;
    @BindView(R.id.but_homepage)
    CardView butHomepage;
    @BindView(R.id.but_in_order)
    LinearLayout butInOrder;
    @BindView(R.id.but_in_choucang)
    LinearLayout butInChoucang;
    @BindView(R.id.but_in_duihuan)
    LinearLayout butInDuihuan;
    @BindView(R.id.but_in_wd)
    LinearLayout butInWd;
    @BindView(R.id.but_in_yijianfankui)
    LinearLayout butInYijianfankui;
    @BindView(R.id.but_in_xieyi)
    LinearLayout butInXieyi;
    @BindView(R.id.but_in_changjianwenti)
    LinearLayout butInChangjianwenti;
    @BindView(R.id.but_in_yinsi)
    LinearLayout butInYinsi;
    @BindView(R.id.but_in_kefu)
    LinearLayout butInKefu;
    @BindView(R.id.but_in_gywm)
    LinearLayout butInGywm;
    @BindView(R.id.ui_outLogin_layout)
    LinearLayout ui_outLogin_layout;
    @BindView(R.id.ui_login_layout)
    LinearLayout ui_login_layout;


    private User.DataDTO userInfo;
    private TakingPicturesPopupWindow picPopupWindow;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init() {
        if (!RangerEvent.getInstance().getEventBus().isRegistered(this)) {
            RangerEvent.getInstance().getEventBus().getDefault().register(this);
        }

        initPicSelectPopup();
    }


    @Override
    protected void loadData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserConfig.isLogin()) {
            userInfo = UserConfig.getUser();
            setUserData();
            getUserInfo();
        } else {

            ui_login_layout.setVisibility(View.GONE);
            ui_outLogin_layout.setVisibility(View.VISIBLE);
        }

    }

    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 填充用户信息
     */
    private void setUserData() {
        ui_login_layout.setVisibility(View.VISIBLE);
        ui_outLogin_layout.setVisibility(View.GONE);
        tvNickname.setText(userInfo.getNickName());
        tvPhone.setText(userInfo.getMobile());
        ivVip.setVisibility(userInfo.getIsVip() == 0 ? View.GONE : View.VISIBLE);
        if (userInfo.getIsVip() == 0) {
            tvVipDate.setText("");
        } else {
            tvVipDate.setText(userInfo.getVipEndTime() + "到期");
        }
        tvMsgNum.setText(userInfo.getMsgCount() + "");
        tvQuanNum.setText(userInfo.getCouponCount() + "");
        tvJfNum.setText(userInfo.getMyPoints() + "");

        LoadingImgFrescoUtils.loadFileUrlImg_round(userInfo.getHeadPic(), imagePic, 100);

    }

    /**
     * 初始化图片选择弹框
     */
    private void initPicSelectPopup() {
        picPopupWindow = new TakingPicturesPopupWindow(mContext);
        picPopupWindow.setList(false);
        picPopupWindow.setClippingEnabled(true);
        picPopupWindow.setOnCameraClickListener(new TakingPicturesPopupWindow.onCameraClickListener() {
            @Override
            public void onReturnPicture(List<MediaBean> list) {

            }

            @Override
            public void onReturnPicture(String realFilePath) {

                List<VPBean> listPhoto = new ArrayList<>();
                listPhoto.add(new VPBean(1, realFilePath));

                new OSSUpDataUtils().upData(listPhoto, 0, new OSSUpDataUtils.OssUpCallback() {
                    @Override
                    public void onCallback(List<String> picList) {

                        if (picList.size() > 0) {
                            upPicData(picList.get(0));
                        } else {
                            showToast("修改失败");
                        }
                    }
                });

            }
        });
    }


    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 修改头像
     */
    private void upPicData(String pic) {
        HashMap<String, String> map = new HashMap<>();
        map.put("headPath", pic);

        NovateUtils.getInstance().Post(mContext, BaseUrl.setUserInfo, map, true,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(Object wenDaTypeBean) {
                        showToast("修改成功");
                        userInfo.setHeadPic(pic);
                        UserConfig.setUser(userInfo);
                        LoadingImgFrescoUtils.loadFileUrlImg_round(pic, imagePic, 100);
                    }
                });
    }


    @OnClick({R.id.but_in_setting, R.id.but_in_edit_user_info, R.id.but_in_qr_code, R.id.but_homepage
            , R.id.but_in_order, R.id.but_in_choucang, R.id.but_in_duihuan, R.id.but_in_wd
            , R.id.but_in_yijianfankui, R.id.but_in_xieyi, R.id.but_in_changjianwenti, R.id.but_in_yinsi
            , R.id.but_in_kefu, R.id.but_in_gywm, R.id.my_point_lls, R.id.image_pic,R.id.ui_outLogin_layout})
    public void onClick(View view) {
        if (ButtonUtils.isFastDoubleClick(view.getId())) {
            return;
        }
        if (!UserConfig.isLogin()) {
            startActivity(new Intent(mContext, LoginActivity.class));
            return;
        }

        switch (view.getId()) {
            case R.id.but_in_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.my_point_lls:
                startActivity(new Intent(getActivity(), MyPointsActivity.class));
                break;
            case R.id.but_in_edit_user_info:
                startActivity(new Intent(mContext, PersonalInformationActivity.class));
                break;
            case R.id.but_in_qr_code:
                break;
            case R.id.but_homepage:
                break;
            case R.id.but_in_order:
                startActivity(new Intent(mContext, MyOrderListActivity.class));
                break;
            case R.id.but_in_choucang:
                startActivity(new Intent(mContext, MyCollectionActivity.class));
                break;
            case R.id.but_in_duihuan:
                startActivity(new Intent(mContext, MyExchangeActivity.class));
                break;
            case R.id.but_in_wd:
                startActivity(new Intent(mContext, MyQuestionActivity.class));
                break;
            case R.id.but_in_yijianfankui:
                FeedbackActivity.startActivity(mContext);
                break;
            case R.id.but_in_xieyi:
                HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "用户协议", NovateUtils.baseUrl + BaseUrl.registerAgreementH5 + "用户协议"));
                break;
            case R.id.but_in_changjianwenti:
                break;
            case R.id.but_in_yinsi:
                HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "隐私政策", NovateUtils.baseUrl + BaseUrl.registerAgreementH5 + "隐私政策"));
                break;
            case R.id.but_in_kefu:
                break;
            case R.id.but_in_gywm:
                HomeWebActivity.startActivity(mContext, new HomeWebActivity.WebBean("1", "关于我们", NovateUtils.baseUrl + BaseUrl.registerAgreementH5 + "关于我们"));
                break;
            case R.id.image_pic:
                picPopupWindow.show(view, false);
                break;
        }
    }



    /**
     * @author XuZhuChao
     * @create 2021/9/7
     * @Describe  获取用户信息
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
                user.setToken(userInfo.getToken());
                UserConfig.setUser(user);
                userInfo = user;
                setUserData();

            }

        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RangerEvent.RefreshData event) {

        if(event.type.equals("2")){
            getUserInfo();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (RangerEvent.getInstance().getEventBus().isRegistered(this)) {
            RangerEvent.getInstance().getEventBus().getDefault().unregister(this);
        }
    }
}
