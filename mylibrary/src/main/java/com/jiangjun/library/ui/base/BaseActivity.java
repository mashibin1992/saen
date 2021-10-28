package com.jiangjun.library.ui.base;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jiangjun.library.R;
import com.jiangjun.library.utils.AppManager;
import com.jiangjun.library.utils.CommonTitleView;
import com.jiangjun.library.utils.RLog;
import com.jiangjun.library.utils.StatusBarUtil;
import com.jiangjun.library.utils.ToastUtils;

import butterknife.ButterKnife;
//import me.jessyan.autosize.AutoSizeCompat;


/**
 * Created by Administrator on 2018/5/22.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;

    /**
     * Activity instance
     */

    protected CommonTitleView commonTitleView;
    public LinearLayout rootLayout;
    public View commonDidiver;
    public View view_head;
    public String TAG;
    public View inflate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        TAG = getClass().getSimpleName();
        RLog.e("Activity", "This is ------->" + getClass().getSimpleName());
        AppManager.getAppManager().addActivity(this);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        StatusBarUtil.fullScreen(this);
        StatusBarUtil.changStatusIconCollor(this, true);

        initView();
        ButterKnife.bind(this);
        findViews(savedInstanceState);
        initData();
    }




    /**
     * Initialization view
     */
    private void initView() {
        setContentView(R.layout.activity_base_layout);
        rootLayout = (LinearLayout) findViewById(R.id.base_root);
        commonDidiver = findViewById(R.id.common_title_divider);
        view_head = findViewById(R.id.view_head);
        commonTitleView = (CommonTitleView) findViewById(R.id.common_title);

        commonTitleView.setVisibility(isShowTitleView()?View.VISIBLE:View.GONE);
        commonDidiver.setVisibility(isShowTitleView()?View.VISIBLE:View.GONE);
        initTitleView();

        StatusBarUtil.setHeadHighly(mContext, view_head); //设置状态栏高度


        int subclassLayoutId = setLayoutRes();
        inflate = LayoutInflater.from(this).inflate(subclassLayoutId, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        inflate.setLayoutParams(params);
        rootLayout.addView(inflate);
    }


    /**
     * set subclass's layout resource id
     *
     * @return resID
     */
    protected abstract int setLayoutRes();

    /**
     * 是否显示 ShowTitleView
     */
    protected abstract boolean isShowTitleView();

    protected abstract void findViews(@Nullable Bundle savedInstanceState);

    protected abstract void initData();
    /**
     * 点击返回
     */
    protected void initTitleView() {

        commonTitleView.setOnLiftClick(new CommonTitleView.OnTitleClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
            }
        });
    }


    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
//        AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources());//如果没有自定义需求用这个方法
        // AutoSizeCompat.autoConvertDensity(super.getResources(), 667, false);//如果有自定义需求就用这个方法
        return super.getResources();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }


    public void showToast(String msg) {
        ToastUtils.Toast(mContext, msg);
    }
}
