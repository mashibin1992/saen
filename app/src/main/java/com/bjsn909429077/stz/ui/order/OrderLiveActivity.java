package com.bjsn909429077.stz.ui.order;

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
import com.bjsn909429077.stz.adapter.HomePageFragmentAdapter;
import com.bjsn909429077.stz.adapter.SearchHistoryAdapter;
import com.bjsn909429077.stz.adapter.SearchHotAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.api.Const;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.bean.CouponBean;
import com.bjsn909429077.stz.bean.HomeBannerBean;
import com.bjsn909429077.stz.bean.LiveOrderBean;
import com.bjsn909429077.stz.bean.PayTypeBean;
import com.bjsn909429077.stz.ui.pay.AuthResult;
import com.bjsn909429077.stz.ui.pay.OrderPayBean;
import com.bjsn909429077.stz.ui.pay.PayResult;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderLiveActivity extends BaseActivity {
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_pirce)
    TextView tv_pirce;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_coupon)
    TextView tv_coupon;
    @BindView(R.id.tv_all_price)
    TextView tv_all_price;
    @BindView(R.id.tv_coupon_price)
    TextView tv_coupon_price;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.iv_jifen)
    ImageView iv_jifen;
    @BindView(R.id.iv_alipay)
    ImageView iv_alipay;
    @BindView(R.id.ic_wxpay)
    ImageView ic_wxpay;
    @BindView(R.id.iv_jdpay)
    ImageView iv_jdpay;
    @BindView(R.id.iv_wypay)
    ImageView iv_wypay;
    @BindView(R.id.iv_xieyi)
    ImageView iv_xieyi;
    @BindView(R.id.tv_pay)
    TextView tv_pay;

    private int liveId;
    public boolean isSelectJiFen = false;
    public boolean isSelectXiYi = false;
    private List<ImageView> imageViews;
    private List<PayTypeBean> payTypeBeans;
    private int couponPrice;
    private String integralPrice;
    private int liveOrderId;
    private int courseCouponId;
    private String totalPrice;
    private String oldPrice;


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
        return R.layout.activity_order_live;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("????????????");
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        if (!RangerEvent.getInstance().getEventBus().isRegistered(this)) {
            RangerEvent.getInstance().getEventBus().getDefault().register(this);
        }
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            liveId = getIntent().getIntExtra("liveId", 0);
        }
        getOrderInfo();
        imageViews = new ArrayList<>();
        imageViews.add(iv_alipay);
        imageViews.add(ic_wxpay);
        imageViews.add(iv_jdpay);
        imageViews.add(iv_wypay);
        payTypeBeans = new ArrayList<>();
        payTypeBeans.add(new PayTypeBean("?????????", true,1));
        payTypeBeans.add(new PayTypeBean("??????", false,2));
        payTypeBeans.add(new PayTypeBean("??????", false,3));
        payTypeBeans.add(new PayTypeBean("??????", false,4));

        //???????????????????????????
        if (payTypeBeans.get(0).isSelect) {
            imageViews.get(0).setImageResource(R.drawable.icon_select1);
        } else {
            imageViews.get(0).setImageResource(R.drawable.icon_select);
        }


    }

    /**
     * ??????????????????
     */
    private void getOrderInfo() {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("liveId", liveId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.orderLiveInfo, map, true,
                new NovateUtils.setRequestReturn<LiveOrderBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(LiveOrderBean liveOrderBean) {
                        if (liveOrderBean != null) {
                            if (liveOrderBean.getData() != null) {
                                LiveOrderBean.DataBean data = liveOrderBean.getData();
                                liveOrderId = data.getId();
                                if (!TextUtils.isEmpty(data.getName())) {
                                    tv_content.setText(data.getName());
                                }

                                if (!TextUtils.isEmpty(data.getLiveTime())) {
                                    tv_time.setText(data.getLiveTime());
                                }

                                if (!TextUtils.isEmpty(data.getCouponPrice())) {
                                    couponPrice = Integer.parseInt(strToStr(data.getCouponPrice()));
                                    tv_coupon.setText("-" + couponPrice);
                                    tv_coupon_price.setText("??" + couponPrice);
                                }
                                oldPrice = data.getPrice();
                                if (!TextUtils.isEmpty(oldPrice)) {
                                    tv_all_price.setText("??" + oldPrice);
                                    tv_pirce.setText("??" + oldPrice);

                                }
                                if (!TextUtils.isEmpty(data.getIntegralPrice())) {
                                    integralPrice = data.getIntegralPrice();
                                    tv_1.setText("???????????????????????" + integralPrice);
                                } else {
                                    tv_1.setText("???????????????????????0");
                                }
                                if (!TextUtils.isEmpty(data.getTotalPrice())) {
                                    totalPrice = data.getTotalPrice();
                                    tv_money.setText("??" + totalPrice);
                                } else {
                                    tv_money.setText("??" + "0");
                                }
                            }

                        }
                    }
                });

    }

    @OnClick({R.id.linear_coupon, R.id.iv_jifen, R.id.iv_wypay, R.id.iv_jdpay, R.id.ic_wxpay, R.id.iv_alipay
            , R.id.linear_xieyi, R.id.tv_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_coupon:
                //?????????
                Intent intent = new Intent(OrderLiveActivity.this, CouponActivity.class);
                intent.putExtra("liveId", liveId);
                intent.putExtra("from", "live");
                startActivityForResult(intent, 100);
                break;
            case R.id.iv_jifen:
                //????????????
                if (!isSelectJiFen) {
                    isSelectJiFen = true;
                    iv_jifen.setImageResource(R.drawable.icon_select1);
                    //?????????????????????  =totalPrice?????????-???????????????couponPrice-??????????????????integralPrice
                    int price1 = Integer.parseInt(strToStr(oldPrice)) - couponPrice - Integer.parseInt(strToStr(integralPrice));
                    tv_money.setText("??" + price1);
                    int price2 = couponPrice + Integer.parseInt(strToStr(integralPrice));
                    tv_coupon_price.setText("??" + price2);
                } else {
                    isSelectJiFen = false;
                    iv_jifen.setImageResource(R.drawable.icon_select);
                    int price1 = Integer.parseInt(strToStr(oldPrice)) - couponPrice;
                    tv_money.setText("??" + price1);
                    tv_coupon_price.setText("??" + couponPrice);
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
                    iv_xieyi.setImageResource(R.drawable.icon_xieyi_true);
                } else {
                    isSelectXiYi = false;
                    iv_xieyi.setImageResource(R.drawable.icon_xieyi_false);

                }


                break;
            case R.id.tv_pay:
                //????????????
                boolean isPay = false;
                for (int i = 0; i < payTypeBeans.size(); i++) {
                    if (payTypeBeans.get(i).isSelect) {
                        isPay = true;
                        break;
                    }
                }
                if (!isPay) {
                    ToastUtils.Toast(OrderLiveActivity.this, "?????????????????????");
                    return;
                }
                if (isSelectXiYi) {
                    for (int i = 0; i < payTypeBeans.size(); i++) {
                        if (payTypeBeans.get(i).isSelect) {
                            finalPayType = payTypeBeans.get(i).getType();
                        }
                    }
                    pay();

                } else {
                    ToastUtils.Toast(mContext, "????????????????????????????????????");
                }
                break;


        }
    }


    //??????
    private void pay() {
        String money = tv_money.getText().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("couponPrice", couponPrice);//???????????????
        map.put("integralPrice", integralPrice);//??????????????????
        map.put("isIntegral", isSelectJiFen ? 1 : 0);//?????????????????????0??????1??????
        map.put("liveId",liveId);//??????id
        if (money.contains("??")){
            money=money.substring(1,money.length());
        }
        map.put("payPrice",money);//????????????//????????????=?????????-?????????-??????


        map.put("payWay", finalPayType);//???????????????0????????????1???????????????2????????????3????????????4?????????
        if (couponPrice != 0) {
            map.put("couponId", courseCouponId);//?????????ID
        }
        NovateUtils.getInstance().Post(mContext, BaseUrl.orderLivePay, map, true,
                new NovateUtils.setRequestReturn<OrderPayBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(OrderPayBean data) {

                        alipaySign = data.getData().getAliSign();
                        wxSignDTO = data.getData().getWxSign();

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
                PayTask alipay = new PayTask(OrderLiveActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 300) {
            if (data != null) {
                CouponBean.DataBean bean = (CouponBean.DataBean) data.getSerializableExtra("bean");
                //?????????????????????
                couponPrice = bean.getPrice();
                tv_coupon.setText("-" + couponPrice);
                //?????????id
                courseCouponId = bean.getCourseCouponId();
                //?????????????????????  =price??????-???????????????couponPrice-??????????????????integralPrice
                int price1 = 0;
                int price2 = 0;
                if (isSelectJiFen) {
                    price1 = Integer.parseInt(strToStr(oldPrice)) - couponPrice - Integer.parseInt(strToStr(integralPrice));
                    price2 = couponPrice + Integer.parseInt(strToStr(integralPrice));
                } else {
                    price1 = Integer.parseInt(strToStr(oldPrice)) - couponPrice;
                    price2 = couponPrice;
                }
                tv_money.setText("??" + price1);
                tv_coupon_price.setText("??" + price2);
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
            Intent intent = new Intent(mContext,PaySuccessActivity.class);
            intent.putExtra("from","live");
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

    public String strToStr(String price) {
        if (price.contains(".")) {
            price = (int) Float.parseFloat(price) + "";
        }
        return price;
    }
}