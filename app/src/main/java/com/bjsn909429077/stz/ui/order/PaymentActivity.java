package com.bjsn909429077.stz.ui.order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alipay.sdk.app.PayTask;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.api.Const;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.bean.CouponBean;
import com.bjsn909429077.stz.bean.OrderBean;
import com.bjsn909429077.stz.bean.PayTypeBean;
import com.bjsn909429077.stz.ui.address.AddAddressActivity;
import com.bjsn909429077.stz.ui.address.AddressActivity;
import com.bjsn909429077.stz.ui.pay.AliPayBean;
import com.bjsn909429077.stz.ui.pay.AuthResult;
import com.bjsn909429077.stz.ui.pay.OrderPayBean;
import com.bjsn909429077.stz.ui.pay.PayResult;
import com.bjsn909429077.stz.ui.pay.WXPayBean;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.RangerEvent;
import com.jiangjun.library.utils.TimeUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity {


    @BindView(R.id.img_address)
    ImageView img_address;
    @BindView(R.id.linear_title)
    LinearLayout linear_title;
    @BindView(R.id.img_header_arrow)
    ImageView img_header_arrow;
    @BindView(R.id.rl_title)
    RelativeLayout rl_title;
    @BindView(R.id.tv_add_address)
    TextView tv_add_address;
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
    @BindView(R.id.tv_course_title)
    TextView tv_course_title;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_phone_num)
    TextView tv_phone_num;
    @BindView(R.id.tv_address1)
    TextView tv_address1;
    @BindView(R.id.iv_cover)
    ImageView iv_cover;
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
    private List<ImageView> imageViews;
    private List<PayTypeBean> payTypeBeans;
    public boolean isSelectJiFen = false;
    private int coursePackageId;
    private int coursePackagePriceId;
    public boolean isSelectXiYi = false;
    private OrderBean.DataBean.OrderCoursePackageInfoBean orderCoursePackageInfo;
    private int courseCouponId;
    private String integralPrice;
    private String couponPrice;
    private String oldPrice;
    private int orderAddressId;

    private final int PermissionsReturn = 122;

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
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //支付成功
                        payState(1);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //支付失败
                        payState(2);
                    }


                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtils.Toast(mContext, "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));

                    } else {
                        // 其他状态值则为授权失败
                        ToastUtils.Toast(mContext, "授权失败" + String.format("authCode:%s", authResult.getAuthCode()));

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
        return R.layout.activity_payment;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        commonTitleView.setTitle("订单信息");

        if (!RangerEvent.getInstance().getEventBus().isRegistered(this)) {
            RangerEvent.getInstance().getEventBus().getDefault().register(this);
        }
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            coursePackageId = intent.getIntExtra("coursePackageId", 0);
            coursePackagePriceId = intent.getIntExtra("coursePackagePriceId", 0);
        }
        getOrderInfo();
        imageViews = new ArrayList<>();
        imageViews.add(iv_alipay);
        imageViews.add(ic_wxpay);
        imageViews.add(iv_jdpay);
        imageViews.add(iv_wypay);
        payTypeBeans = new ArrayList<>();
        payTypeBeans.add(new PayTypeBean("支付宝", true,1));
        payTypeBeans.add(new PayTypeBean("微信", false,2));
        payTypeBeans.add(new PayTypeBean("京东", false,3));
        payTypeBeans.add(new PayTypeBean("网银", false,4));
        //默认选中支付宝支付
        if (payTypeBeans.get(0).isSelect) {
            imageViews.get(0).setImageResource(R.drawable.icon_select1);
        } else {
            imageViews.get(0).setImageResource(R.drawable.icon_select);
        }
    }

    private void getOrderInfo() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("coursePackageId", coursePackageId);
        map.put("coursePackagePriceId", coursePackagePriceId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.orderInfo, map, true,
                new NovateUtils.setRequestReturn<OrderBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(OrderBean orderBean) {
                        if (orderBean != null) {
                            if (orderBean.getData().getOrderAddress() != null) {
                                img_address.setVisibility(View.VISIBLE);
                                linear_title.setVisibility(View.VISIBLE);
                                img_header_arrow.setVisibility(View.VISIBLE);
                                tv_add_address.setVisibility(View.GONE);
                                //img_address
                                OrderBean.DataBean.OrderAddressBean orderAddress = orderBean.getData().getOrderAddress();
                                orderAddressId = orderAddress.getOrderAddressId();
                                if (!TextUtils.isEmpty(orderAddress.getName())) {
                                    tv_user_name.setText(orderAddress.getName());
                                }
                                if (!TextUtils.isEmpty(orderAddress.getMobile())) {
                                    tv_phone_num.setText(orderAddress.getMobile());
                                }
                                if (!TextUtils.isEmpty(orderAddress.getAddress()) && !TextUtils.isEmpty(orderAddress.getProvince()) && !TextUtils.isEmpty(orderAddress.getCity())
                                        && !TextUtils.isEmpty(orderAddress.getArea())) {
                                    tv_address1.setText(orderAddress.getProvince() + orderAddress.getCity() + orderAddress.getArea() + orderAddress.getAddress());
                                }

                            } else {
                                img_address.setVisibility(View.INVISIBLE);
                                linear_title.setVisibility(View.INVISIBLE);
                                img_header_arrow.setVisibility(View.INVISIBLE);
                                tv_add_address.setVisibility(View.VISIBLE);
                                rl_title.setBackgroundResource(R.drawable.bg_order);
                            }

                            //套餐名字
                            orderCoursePackageInfo = orderBean.getData().getOrderCoursePackageInfo();
                            tv_content.setText(orderCoursePackageInfo.getCoursePackageName());

                            String time = TimeUtil.getTime(orderCoursePackageInfo.getEffectiveEtime());
                            tv_time.setText(time);
                            oldPrice = orderBean.getData().getOrderCoursePackageInfo().getPrice();
                            if (!TextUtils.isEmpty(oldPrice)){
                                tv_all_price.setText("¥" + oldPrice);
                                tv_pirce.setText("¥" + oldPrice);
                            }else {
                                tv_pirce.setText("¥" + 0);
                                tv_all_price.setText("¥" + 0);
                            }
                            tv_money.setText("¥" + orderBean.getData().getTotalPrice());
                            //总优惠金额=优惠券金额+积分抵扣
                            if (!TextUtils.isEmpty(orderCoursePackageInfo.getCouponPrice())){
                                couponPrice = orderCoursePackageInfo.getCouponPrice();
                                tv_coupon.setText("-"+couponPrice);
                                tv_coupon_price.setText("¥" + couponPrice);
                            }else {
                                tv_coupon.setText("-"+0);
                                tv_coupon_price.setText("¥" + 0);
                            }


                            Glide.with(PaymentActivity.this).load(orderCoursePackageInfo.getCoverPath()).into(iv_cover);

                            integralPrice = orderBean.getData().getPointDeductionPrice();
                            tv_2.setText("积分抵扣可抵扣¥" + integralPrice);

                            //赠课
                            if (orderCoursePackageInfo.getGiveCourseNames() != null && orderCoursePackageInfo.getGiveCourseNames().size() > 0) {
                                List<String> giveCourseNames = orderCoursePackageInfo.getGiveCourseNames();
                                String courseNames = "";
                                for (int i = 0; i < giveCourseNames.size(); i++) {
                                    if (giveCourseNames.size() == 1) {
                                        courseNames = giveCourseNames.get(i);
                                    } else {
                                        courseNames = giveCourseNames.get(i) + "\n";
                                    }

                                }
                                tv_course_title.setText(courseNames);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick({R.id.rl_title, R.id.linear_coupon, R.id.iv_jifen,R.id.iv_wypay,R.id.iv_jdpay,R.id.ic_wxpay,R.id.iv_alipay,R.id.linear_xieyi,R.id.tv_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_title:
                if (tv_add_address.getVisibility() == View.VISIBLE) {
                    //添加收货地址
                    Intent intent = new Intent(PaymentActivity.this, AddAddressActivity.class);
                    intent.putExtra("from", "order");
                    startActivityForResult(intent, 100);
                } else {
                    //收货人列表
                    Intent intent = new Intent(PaymentActivity.this, AddressActivity.class);
                    startActivityForResult(intent, 100);
                }

                break;
            case R.id.linear_coupon:
                //优惠券
                Intent intent = new Intent(PaymentActivity.this, CouponActivity.class);
                intent.putExtra("coursePackageId", coursePackageId);
                intent.putExtra("from", "kecheng");
                intent.putExtra("coursePackagePriceId", coursePackagePriceId);
                startActivityForResult(intent, 100);


                break;
            case R.id.iv_jifen:

                //选择积分
                if (!isSelectJiFen) {
                    isSelectJiFen = true;
                    iv_jifen.setImageResource(R.drawable.icon_select1);
                    //计算总支付价格  =totalPrice总价格-优惠券价格couponPrice-积分抵扣金额integralPrice
                    int price1 = Integer.parseInt(strToStr(oldPrice)) -  Integer.parseInt(strToStr(couponPrice)) - Integer.parseInt(strToStr(integralPrice));
                    tv_money.setText("¥" + price1);
                    int price2 = Integer.parseInt(strToStr(couponPrice)) + Integer.parseInt(strToStr(integralPrice));
                    tv_coupon_price.setText("¥"+price2);
                } else {
                    isSelectJiFen = false;
                    iv_jifen.setImageResource(R.drawable.icon_select);
                    int price1 = Integer.parseInt(strToStr(oldPrice)) - Integer.parseInt(strToStr(couponPrice));
                    tv_money.setText("¥" + price1);
                    tv_coupon_price.setText("¥"+couponPrice);
                }
                break;
            case R.id.iv_alipay:
                //阿里支付
                for (int i = 0; i <payTypeBeans.size() ; i++) {
                    if (i==0){
                        if (payTypeBeans.get(i).isSelect){
                            payTypeBeans.get(i).isSelect=false;
                            imageViews.get(i).setImageResource(R.drawable.icon_select);
                        }else {
                            payTypeBeans.get(i).isSelect=true;
                            imageViews.get(i).setImageResource(R.drawable.icon_select1);
                        }
                    }else {
                        payTypeBeans.get(i).isSelect=false;
                        imageViews.get(i).setImageResource(R.drawable.icon_select);
                    }
                }

                break;
            case R.id.ic_wxpay:
                //微信支付
                for (int i = 0; i <payTypeBeans.size() ; i++) {
                    if (i==1){
                        if (payTypeBeans.get(i).isSelect){
                            payTypeBeans.get(i).isSelect=false;
                            imageViews.get(i).setImageResource(R.drawable.icon_select);
                        }else {
                            payTypeBeans.get(i).isSelect=true;
                            imageViews.get(i).setImageResource(R.drawable.icon_select1);
                        }
                    }else {
                        payTypeBeans.get(i).isSelect=false;
                        imageViews.get(i).setImageResource(R.drawable.icon_select);
                    }
                }
                break;
            case R.id.iv_jdpay:
                //京东支付
                for (int i = 0; i <payTypeBeans.size() ; i++) {
                    if (i==2){
                        if (payTypeBeans.get(i).isSelect){
                            payTypeBeans.get(i).isSelect=false;
                            imageViews.get(i).setImageResource(R.drawable.icon_select);
                        }else {
                            payTypeBeans.get(i).isSelect=true;
                            imageViews.get(i).setImageResource(R.drawable.icon_select1);
                        }
                    }else {
                        payTypeBeans.get(i).isSelect=false;
                        imageViews.get(i).setImageResource(R.drawable.icon_select);
                    }
                }
                break;
            case R.id.iv_wypay:
                //网银支付
                for (int i = 0; i <payTypeBeans.size() ; i++) {
                    if (i==3){
                        if (payTypeBeans.get(i).isSelect){
                            payTypeBeans.get(i).isSelect=false;
                            imageViews.get(i).setImageResource(R.drawable.icon_select);
                        }else {
                            payTypeBeans.get(i).isSelect=true;
                            imageViews.get(i).setImageResource(R.drawable.icon_select1);
                        }
                    }else {
                        payTypeBeans.get(i).isSelect=false;
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
                //立即支付
                boolean isPay = false;
                for (int i = 0; i < payTypeBeans.size(); i++) {
                    if (payTypeBeans.get(i).isSelect) {
                        isPay = true;
                        break;
                    }
                }
                if (!isPay) {
                    ToastUtils.Toast(PaymentActivity.this, "请选择支付方式");
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
                    ToastUtils.Toast(PaymentActivity.this, "请阅读并勾选课程购买协议");
                }
                break;
            default:
        }
    }
    //支付
    private void pay() {
        String money = tv_money.getText().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("coursePackageId", coursePackageId);//课程套餐Id
        map.put("coursePackagePriceId", coursePackagePriceId);//课程套餐价格Id
        map.put("isPointDeduction", isSelectJiFen ? 1 : 0);//是否积分抵扣：0、否 1、是
     /*   if (money.contains("¥")){
            money=money.substring(1,money.length());
        }
        map.put("payPrice",money);//直播价格//支付价格=总价格-优惠券-积分*/

        map.put("payWay", finalPayType);//支付方式：0未支付；1：支付包；2：微信；3；京东；4：网银
        if (courseCouponId != 0) {
            map.put("couponId", courseCouponId);//优惠卷ID
        }
        map.put("orderAddressId", orderAddressId);//订单地址Id

        NovateUtils.getInstance().Post(mContext, BaseUrl.orderPay, map, true,
                new NovateUtils.setRequestReturn<OrderPayBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(OrderPayBean data) {


                        alipaySign = data.getData().getAliSign();
                        wxSignDTO = data.getData().getWxSign();
                        getPermission();


                    }
                });
    }



    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 检查手机识别码权限
     */
    private void getPermission(){
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ) {
            //申请手机识别码权限
            ActivityCompat.requestPermissions((Activity) mContext, new String[]
                    {Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE}, PermissionsReturn);
        }else {
            switch (finalPayType) {
                case "支付宝":
                    toAliPayApp();
                    break;
                case "微信":
                    wxAPI = WXAPIFactory.createWXAPI(this, Const.WX_APP_ID);
                    toWXApp(wxSignDTO);
                    break;
            }
        }
    }


    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 拉起支付宝
     */
    private void toAliPayApp(){
        //支付宝支付
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PaymentActivity.this);
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
     * @Describe 拉起支付宝
     */
    private void toWXApp(OrderPayBean.DataDTO.WxSignDTO weixinModel ){
        //微信支付
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
            ToastUtils.Toast(mContext, "微信支付错误" + e.getMessage());
        }
    }


    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 支付宝支付，获取支付宝签名
     */
    private void toAliPay(String orderId) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("orderId", orderId);//订单Id

        NovateUtils.getInstance().Post(mContext, BaseUrl.getAliPaySign, map, true,
                new NovateUtils.setRequestReturn<AliPayBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AliPayBean bean) {
                        alipaySign = bean.getData().getAliSign();
                        getPermission();
                    }
                });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionsReturn:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    switch (finalPayType) {
                        case "支付宝":
                            toAliPayApp();
                            break;
                        case "微信":
                            wxAPI = WXAPIFactory.createWXAPI(this, Const.WX_APP_ID);
                            toWXApp(wxSignDTO);
                            break;
                    }
                }else {
                    showToast("没有获取设备标识符权限");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            if (data != null) {
                AddressBean.DataBean orderAddress = (AddressBean.DataBean) data.getSerializableExtra("bean");
                //设置数据
                if (!TextUtils.isEmpty(orderAddress.getName())) {
                    tv_user_name.setText(orderAddress.getName());
                }
                if (!TextUtils.isEmpty(orderAddress.getMobile())) {
                    tv_phone_num.setText(orderAddress.getMobile());
                }
                if (!TextUtils.isEmpty(orderAddress.getAddress()) && !TextUtils.isEmpty(orderAddress.getProvince()) && !TextUtils.isEmpty(orderAddress.getCity())
                        && !TextUtils.isEmpty(orderAddress.getArea())) {
                    tv_address1.setText(orderAddress.getProvince() + orderAddress.getCity() + orderAddress.getArea() + orderAddress.getAddress());
                }
                orderAddressId=orderAddress.getId();
            }
        }  if (requestCode == 100 && resultCode == 300) {
            if (data != null) {
                CouponBean.DataBean bean = (CouponBean.DataBean) data.getSerializableExtra("bean");
                //设置优惠券数据
                couponPrice = bean.getPrice()+"";
                tv_coupon.setText("-" + couponPrice);
                //优惠券id
                courseCouponId = bean.getCourseCouponId();
                //计算总支付价格  =price原价-优惠券价格couponPrice-积分抵扣金额integralPrice
                int price1=0;
                int price2=0;
                if (isSelectJiFen){
                    price1 = Integer.parseInt(strToStr(oldPrice)) - Integer.parseInt(strToStr(couponPrice)) - Integer.parseInt(strToStr(integralPrice));
                    price2=Integer.parseInt(strToStr(couponPrice))+Integer.parseInt(strToStr(integralPrice));
                }else {
                    price1 = Integer.parseInt(strToStr(oldPrice)) - Integer.parseInt(strToStr(couponPrice));
                    price2=Integer.parseInt(strToStr(couponPrice));
                }
                tv_money.setText("¥" + price1);
                tv_coupon_price.setText("¥"+price2);
            }

        } else {
            getOrderInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RangerEvent.WXPayReturn event) {
        if (event.type.equals("1")) {
            //支付成功
            payState(1);

        } else {
            //支付失败
            payState(2);

        }
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe
     * @param pay 1=支付成功 2= 支付失败
     */
    private void payState(int pay){

        if (pay == 1) {
            showToast("支付成功");
            Intent intent = new Intent(PaymentActivity.this,PaySuccessActivity.class);
            intent.putExtra("from","kecheng");
            startActivity(intent);
            finish();
        }else {
            showToast("支付失败");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (RangerEvent.getInstance().getEventBus().isRegistered(this)) {
            RangerEvent.getInstance().getEventBus().getDefault().unregister(this);
        }
    }

    public String strToStr(String price){
        if(price.contains(".")){
            price = (int)Float.parseFloat(price)+"";
        }
        return  price;
    }
}