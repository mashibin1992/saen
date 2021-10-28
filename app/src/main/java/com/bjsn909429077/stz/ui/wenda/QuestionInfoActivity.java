package com.bjsn909429077.stz.ui.wenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bjsn909429077.stz.Constant;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.WenDaTypeAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.WenDaTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.TakingPicturesPopupWindow;
import com.tamic.novate.Throwable;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;

public class QuestionInfoActivity extends BaseActivity{


    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @Override
    protected int setLayoutRes() {
        return R.layout.activity_question1_info;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        tool_bar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

  /*  private void getWdType() {
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.wdType, map, true,
                new NovateUtils.setRequestReturn<WenDaTypeBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(WenDaTypeBean wenDaTypeBean) {
                        if (wenDaTypeBean != null) {
                            data = wenDaTypeBean.getData();
                        }
                    }
                });
    }*/

    @Override
    protected void initData() {

    }






    @OnClick({R.id.tv_botton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_botton:
                //购买会有
                Intent intent = new Intent(mContext,BuyVipActivity.class);
                startActivity(intent);
                break;


            default:

        }
    }

}