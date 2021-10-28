package com.bjsn909429077.stz.ui.my;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.DoExchangeBean;
import com.bjsn909429077.stz.bean.MyExchangeBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import androidx.annotation.Nullable;

public class MyExchangeActivity extends BaseActivity {

    private EditText my_exchange_et;
    private TextView my_exchange_query;
    private LinearLayout exchange_content;
    private TextView exchange_button;
    private TextView exchange_time;
    private TextView exchange_end_time;
    private TextView exchange_title;
    private TextView exchange_second_title;
    private TextView exchange_price;
    private TextView exchange_give_course_names;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_my_exchange;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(R.id.common_title);
        commonTitleView.setTitle("我要兑换");
        my_exchange_et = findViewById(R.id.my_exchange_et);
        my_exchange_query = findViewById(R.id.my_exchange_query);
        exchange_content = findViewById(R.id.exchange_content);
        exchange_button = findViewById(R.id.exchange_button);
        exchange_time = findViewById(R.id.exchange_time);
        exchange_end_time = findViewById(R.id.exchange_end_time);
        exchange_title = findViewById(R.id.exchange_title);
        exchange_second_title = findViewById(R.id.exchange_second_title);
        exchange_price = findViewById(R.id.exchange_price);
        exchange_give_course_names = findViewById(R.id.exchange_give_course_names);
    }

    @Override
    protected void initData() {
        my_exchange_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtil.isEmpty(my_exchange_et.getText().toString().trim())) {
                    ToastUtils.Toast(MyExchangeActivity.this, "请输入兑换码");
                    return;
                }
                initNetWork();
            }
        });
        //我要兑换
        exchange_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doExchange();
            }
        });
    }

    /**
     * 我要兑换/获取兑换信息
     * Wee9UI8wK4G
     * b939066n22N
     * P9jS1Y6hNku
     * 9eJgmBF78gI
     * 3l25I4363d2
     * rL5Z5Fckm2o
     */
    private void initNetWork() {
        HashMap<String, String> map = new HashMap<>();
        map.put("redemptionCode", my_exchange_et.getText().toString().trim());
        NovateUtils.getInstance().Post(mContext, BaseUrl.exchange, map, true,
                new NovateUtils.setRequestReturn<MyExchangeBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(MyExchangeActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(MyExchangeBean response) {
                        if (response != null && response.getData() != null) {
                            exchange_content.setVisibility(View.VISIBLE);
                            exchange_button.setVisibility(View.VISIBLE);
                            exchange_time.setText("兑换码有效期：" + response.getData().getExchangeEfficientBTime() + "~" + response.getData().getExchangeEfficientETime());
                            exchange_end_time.setText(response.getData().getCoursePackageEfficientETime());
                            exchange_title.setText(response.getData().getCourseName());
                            exchange_second_title.setText(response.getData().getCourseSecondName());
                            exchange_price.setText("¥"+response.getData().getCoursePackagePrice());
                            String giveNames="";
                            for (int i = 0; i < response.getData().getGiveCourseNames().size(); i++) {
                                giveNames+=response.getData().getGiveCourseNames().get(i);
                            }
                            exchange_give_course_names.setText(giveNames);
                        }
                    }
                });
    }

    /**
     * 我要兑换/获取兑换信息
     */
    private void doExchange() {
        HashMap<String, String> map = new HashMap<>();
        map.put("redemptionCode", my_exchange_et.getText().toString().trim());
        NovateUtils.getInstance().Post(mContext, BaseUrl.doExchange, map, true,
                new NovateUtils.setRequestReturn<DoExchangeBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(MyExchangeActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(DoExchangeBean response) {
                        ToastUtils.Toast(MyExchangeActivity.this, response.getMsg());
                        finish();
                    }
                });
    }

}