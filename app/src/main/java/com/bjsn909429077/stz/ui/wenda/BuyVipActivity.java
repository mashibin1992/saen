package com.bjsn909429077.stz.ui.wenda;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.api.Const;
import com.bjsn909429077.stz.bean.PayTypeBean;
import com.bjsn909429077.stz.bean.VipBean;
import com.bjsn909429077.stz.bean.WenDaTypeBean;
import com.bjsn909429077.stz.ui.order.OrderLiveActivity;
import com.bjsn909429077.stz.ui.order.PaySuccessActivity;
import com.bjsn909429077.stz.ui.order.PaymentActivity;
import com.bjsn909429077.stz.ui.pay.AuthResult;
import com.bjsn909429077.stz.ui.pay.OrderPayBean;
import com.bjsn909429077.stz.ui.pay.PayResult;
import com.bjsn909429077.stz.widget.PageView;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.ToastUtils;
import com.stx.xhb.xbanner.XBanner;
import com.tamic.novate.Throwable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class BuyVipActivity extends BaseActivity {
    @BindView(R.id.tv_price_1)
    TextView tv_price_1;
    @BindView(R.id.tv_price_2)
    TextView tv_price_2;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.tv_1_1)
    TextView tv_1_1;
    @BindView(R.id.tv_2_2)
    TextView tv_2_2;
    @BindView(R.id.tv_3_3)
    TextView tv_3_3;
    @BindView(R.id.rely_1)
    RelativeLayout rely_1;
    @BindView(R.id.rely_2)
    RelativeLayout rely_2;
    @BindView(R.id.rely_3)
    RelativeLayout rely_3;
    @BindView(R.id.iv_alipay)
    ImageView iv_alipay;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.ic_wxpay)
    ImageView ic_wxpay;
    @BindView(R.id.iv_jdpay)
    ImageView iv_jdpay;
    @BindView(R.id.iv_wypay)
    ImageView iv_wypay;
    @BindView(R.id.iv_xieyi)
    ImageView iv_xieyi;
    private List<TextView> textViews;
    private List<TextView> text1Views;
    private List<RelativeLayout> relativeLayouts;
    private List<VipBean.DataBean> data;
    private List<ImageView> imageViews;
    private List<PayTypeBean> payTypeBeans;
    public boolean isSelectXiYi = false;


    private IWXAPI wxAPI;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String alipaySign = "";
    private OrderPayBean.DataDTO.WxSignDTO wxSignDTO ;
    private String mOrderId = "";

    private Handler mPayHandler = new Handler(Looper.getMainLooper()) {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                     */
                    String resultInfo = payResult.getResult();// ?????????????????????????????????
                    String resultStatus = payResult.getResultStatus();
                    // ??????resultStatus ???9000?????????????????????
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // ??????????????????????????????????????????????????????????????????????????????
                        //????????????
                        payState(1);
                    } else {
                        // ???????????????????????????????????????????????????????????????????????????
                        //????????????
                        payState(2);
                    }


                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // ??????resultStatus ??????9000??????result_code
                    // ??????200?????????????????????????????????????????????????????????????????????????????????
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // ??????alipay_open_id???????????????????????????extern_token ???value
                        // ??????????????????????????????????????????
                        ToastUtils.Toast(mContext, "????????????\n" + String.format("authCode:%s", authResult.getAuthCode()));

                    } else {
                        // ?????????????????????????????????
                        ToastUtils.Toast(mContext, "????????????" + String.format("authCode:%s", authResult.getAuthCode()));

                    }
                    break;
                }
                default:
                    break;
            }
        }

    };
    private String finalPayType ;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_buy_vip;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {

        if (!RangerEvent.getInstance().getEventBus().isRegistered(this)) {
            RangerEvent.getInstance().getEventBus().getDefault().register(this);
        }

        view_head.setVisibility(View.GONE);
        tv_price_1.bringToFront();
        tv_price_2.bringToFront();
        textViews = new ArrayList<>();
        textViews.add(tv_1);
        textViews.add(tv_2);
        textViews.add(tv_3);
        relativeLayouts = new ArrayList<>();
        relativeLayouts.add(rely_1);
        relativeLayouts.add(rely_2);
        relativeLayouts.add(rely_3);
        text1Views = new ArrayList<>();
        text1Views.add(tv_1_1);
        text1Views.add(tv_2_2);
        text1Views.add(tv_3_3);


        imageViews = new ArrayList<>();
        imageViews.add(iv_alipay);
        imageViews.add(ic_wxpay);
        imageViews.add(iv_jdpay);
        imageViews.add(iv_wypay);
        payTypeBeans = new ArrayList<>();
        payTypeBeans.add(new PayTypeBean("?????????", true, 1));
        payTypeBeans.add(new PayTypeBean("??????", false, 2));
        payTypeBeans.add(new PayTypeBean("??????", false, 3));
        payTypeBeans.add(new PayTypeBean("??????", false, 4));

        //???????????????????????????
        if (payTypeBeans.get(0).isSelect) {
            imageViews.get(0).setImageResource(R.drawable.icon_select1);
        } else {
            imageViews.get(0).setImageResource(R.drawable.icon_select);
        }
    }

    private void buyVip() {
        int memberItemId = -1;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelect()) {
                memberItemId = data.get(i).getMemberItemId();
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("memberItemId", "" + memberItemId);

        map.put("payWay", finalPayType);
        NovateUtils.getInstance().Post(mContext, BaseUrl.buyVip, map, true,
                new NovateUtils.setRequestReturn<OrderPayBean>() {


                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(OrderPayBean vipBean) {


                        alipaySign = vipBean.getData().getAliSign();
                        wxSignDTO = vipBean.getData().getWxSign();
                        switch (finalPayType) {
                            case "?????????":
                                toAliPayApp();
                                break;
                            case "??????":
                                wxAPI = WXAPIFactory.createWXAPI(mContext, Const.WX_APP_ID);
                                toWXApp(wxSignDTO);
                                break;
                        }
                    }
                });
    }



    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe ???????????????
     */
    private void toAliPayApp(){
        //???????????????
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(BuyVipActivity.this);
                Map<String, String> result = alipay.payV2(alipaySign, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mPayHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe ???????????????
     */
    private void toWXApp(OrderPayBean.DataDTO.WxSignDTO weixinModel ){
        //????????????
        try {

            PayReq request = new PayReq();
            request.appId = weixinModel.getAppid();
            request.partnerId = weixinModel.getMch_id();
            request.prepayId = weixinModel.getPrepay_id();
            request.packageValue = "Sign=WXPay";
            request.nonceStr = weixinModel.getNonce_str();
            request.timeStamp = weixinModel.getTimestamp();
            request.sign = weixinModel.getSign();
            wxAPI.sendReq(request);

        } catch (Exception e) {
            ToastUtils.Toast(mContext, "??????????????????" + e.getMessage());
        }
    }


    private void getData() {
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.vipList, map, true,
                new NovateUtils.setRequestReturn<VipBean>() {


                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(VipBean vipBean) {
                        if (vipBean != null) {
                            if (vipBean.getData() != null && vipBean.getData().size() == 3) {
                                data = vipBean.getData();
                                for (int i = 0; i < textViews.size(); i++) {
                                    if (i == 0) {
                                        data.get(i).setSelect(true);
                                    } else {
                                        data.get(i).setSelect(false);
                                    }
                                    textViews.get(i).setText("??" + data.get(i).getPrice());
                                }

                            }
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        getData();
    }


    @OnClick({R.id.tv_botton, R.id.rely_1, R.id.rely_2, R.id.rely_3, R.id.linear_xieyi, R.id.iv_alipay
            , R.id.ic_wxpay, R.id.iv_jdpay, R.id.iv_wypay, R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:

                finish();
                break;
            case R.id.tv_botton:
                //????????????
                boolean isPay = false;
                for (int i = 0; i < payTypeBeans.size(); i++) {
                    if (payTypeBeans.get(i).isSelect) {
                        isPay = true;
                    }
                }
                if (!isPay) {
                    showToast( "?????????????????????");
                    return;
                }
                if (isSelectXiYi) {
                    for (int i = 0; i < payTypeBeans.size(); i++) {
                        if (payTypeBeans.get(i).isSelect) {
                            finalPayType = payTypeBeans.get(i).getType();
                        }
                    }
                    buyVip();

                } else {
                   showToast( "????????????????????????????????????");
                }

                break;
            case R.id.iv_alipay:
                //????????????
                for (int i = 0; i < payTypeBeans.size(); i++) {
                    if (i == 0) {
                        if (payTypeBeans.get(i).isSelect) {
                            payTypeBeans.get(i).isSelect = false;
                            imageViews.get(i).setImageResource(R.drawable.icon_select);
                        } else {
                            payTypeBeans.get(i).isSelect = true;
                            imageViews.get(i).setImageResource(R.drawable.icon_select1);
                        }
                    } else {
                        payTypeBeans.get(i).isSelect = false;
                        imageViews.get(i).setImageResource(R.drawable.icon_select);
                    }
                }

                break;
            case R.id.ic_wxpay:
                //????????????
                for (int i = 0; i < payTypeBeans.size(); i++) {
                    if (i == 1) {
                        if (payTypeBeans.get(i).isSelect) {
                            payTypeBeans.get(i).isSelect = false;
                            imageViews.get(i).setImageResource(R.drawable.icon_select);
                        } else {
                            payTypeBeans.get(i).isSelect = true;
                            imageViews.get(i).setImageResource(R.drawable.icon_select1);
                        }
                    } else {
                        payTypeBeans.get(i).isSelect = false;
                        imageViews.get(i).setImageResource(R.drawable.icon_select);
                    }
                }
                break;
            case R.id.iv_jdpay:
                //????????????
                for (int i = 0; i < payTypeBeans.size(); i++) {
                    if (i == 2) {
                        if (payTypeBeans.get(i).isSelect) {
                            payTypeBeans.get(i).isSelect = false;
                            imageViews.get(i).setImageResource(R.drawable.icon_select);
                        } else {
                            payTypeBeans.get(i).isSelect = true;
                            imageViews.get(i).setImageResource(R.drawable.icon_select1);
                        }
                    } else {
                        payTypeBeans.get(i).isSelect = false;
                        imageViews.get(i).setImageResource(R.drawable.icon_select);
                    }
                }
                break;
            case R.id.iv_wypay:
                //????????????
                for (int i = 0; i < payTypeBeans.size(); i++) {
                    if (i == 3) {
                        if (payTypeBeans.get(i).isSelect) {
                            payTypeBeans.get(i).isSelect = false;
                            imageViews.get(i).setImageResource(R.drawable.icon_select);
                        } else {
                            payTypeBeans.get(i).isSelect = true;
                            imageViews.get(i).setImageResource(R.drawable.icon_select1);
                        }
                    } else {
                        payTypeBeans.get(i).isSelect = false;
                        imageViews.get(i).setImageResource(R.drawable.icon_select);
                    }
                }
                break;
            case R.id.linear_xieyi:
                if (!isSelectXiYi) {
                    isSelectXiYi = true;
                    iv_xieyi.setImageResource(R.drawable.icon_select1);
                } else {
                    isSelectXiYi = false;
                    iv_xieyi.setImageResource(R.drawable.icon_select);

                }


                break;

            case R.id.rely_1:
                if (data != null && data.size() == 3) {
                    for (int i = 0; i < data.size(); i++) {
                        if (i == 0) {
                            data.get(i).setSelect(true);
                        } else {
                            data.get(i).setSelect(false);
                        }
                    }
                    upUI();
                }

                break;
            case R.id.rely_2:
                if (data != null && data.size() == 3) {
                    for (int i = 0; i < data.size(); i++) {
                        if (i == 1) {
                            data.get(i).setSelect(true);
                        } else {
                            data.get(i).setSelect(false);
                        }
                    }
                    upUI();
                }

                break;
            case R.id.rely_3:
                if (data != null && data.size() == 3) {
                    for (int i = 0; i < data.size(); i++) {
                        if (i == 2) {
                            data.get(i).setSelect(true);
                        } else {
                            data.get(i).setSelect(false);
                        }
                    }
                    upUI();
                }

                break;
            default:

        }
    }


    public void upUI() {
        if (data != null && data.size() == 3) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).isSelect()) {
                    relativeLayouts.get(i).setBackgroundResource(R.drawable.circle_rectangle_fef7f0_5);
                    textViews.get(i).setTextColor(Color.parseColor("#ffdc4f35"));
                    text1Views.get(i).setTextColor(Color.parseColor("#ffdc684b"));
                } else {
                    relativeLayouts.get(i).setBackgroundResource(R.drawable.circle_rectangle_f5f5f5_5);
                    textViews.get(i).setTextColor(Color.parseColor("#ff333333"));
                    text1Views.get(i).setTextColor(Color.parseColor("#ff666666"));
                }
            }
        }

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RangerEvent.WXPayReturn event) {
        if (event.type.equals("1")) {
            //????????????
            payState(1);

        } else {
            //????????????
            payState(2);

        }
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe
     * @param pay 1=???????????? 2= ????????????
     */
    private void payState(int pay){

        if (pay == 1) {
            showToast("????????????");
            Intent intent = new Intent(mContext, PaySuccessActivity.class);
            intent.putExtra("from","buyVIP");
            startActivity(intent);
            finish();
        }else {
            showToast("????????????");
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