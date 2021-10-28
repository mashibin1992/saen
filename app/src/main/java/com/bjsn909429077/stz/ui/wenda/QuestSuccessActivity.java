package com.bjsn909429077.stz.ui.wenda;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.PayTypeBean;
import com.bjsn909429077.stz.bean.VipBean;
import com.bumptech.glide.Glide;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;

public class QuestSuccessActivity extends BaseActivity {
    @BindView(R.id.tv_finish)
    TextView tv_finish;
    @BindView(R.id.tv_show)
    TextView tv_show; @BindView(R.id.iv_code)
    ImageView iv_code;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    private String qrCode;
    private int wdId;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_quest_success;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {


        Intent intent = getIntent();
        qrCode = intent.getStringExtra("qrCode");
        wdId = intent.getIntExtra("wdId", 0);
        Glide.with(this).load(qrCode).into(iv_code);


        tool_bar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void initData(){
    }


    @OnClick({R.id.tv_finish,R.id.tv_show})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show:
                //返回问题详情

                //问答详情
                Intent intent = new Intent(QuestSuccessActivity.this, WenDaInfoActivity.class);
                intent.putExtra("wdId",wdId);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_finish:
                Intent intent1 = new Intent(QuestSuccessActivity.this, WenDaActivity.class);
                intent1.putExtra("from",1);
                startActivity(intent1);
                finish();


                break;
            default:

        }
    }
}