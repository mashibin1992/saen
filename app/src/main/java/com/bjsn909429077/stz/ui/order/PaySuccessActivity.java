package com.bjsn909429077.stz.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CouponAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CouponBean;
import com.bjsn909429077.stz.bean.ShowFragment;
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.course.SelectCourseActivity;
import com.bjsn909429077.stz.ui.wenda.QuestionInfoActivity;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.AppManager;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.tv_1)
    TextView tv_1;
    private String from;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_pay_success;
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
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if(from.equals("buyVIP")){
            tv_1.setText("继续提问");
        }
    }



    @OnClick({R.id.tv_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                //跳转学习中心
                if(!from.equals("buyVIP")){
                    RxBus.getDefault().post(new ShowFragment(2));
                }else {
                    AppManager.getAppManager().finishActivity(QuestionInfoActivity.class);
                }
                if (from.equals("live")){
                    AppManager.getAppManager().finishActivity(OrderLiveActivity.class);
                }else  if (from.equals("kecheng")){
                    AppManager.getAppManager().finishActivity(SelectCourseActivity.class);
                }

                finish();

                break;

        }
    }
}