package com.bjsn909429077.stz.ui.my;

import android.os.Bundle;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.MyPointsAdaapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.MyPointBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.StatusBarUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyPointsActivity extends BaseActivity {

    private RecyclerView my_point_rv;
    private TextView my_point;
    private List<MyPointBean.DataBean.PointChildListBean> myPointBean;
    private MyPointsAdaapter myPointsAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected int setLayoutRes() {
        StatusBarUtil.setStatusBarColor(this,R.color.color_5F7DFF);
        return R.layout.activity_my_points;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        my_point_rv = findViewById(R.id.my_point_rv);
        my_point = findViewById(R.id.my_point);
        initNetWork();
    }

    @Override
    protected void initData() {

    }

    private void initNetWork() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("page", 0);
        NovateUtils.getInstance().Post(mContext, BaseUrl.point, map, true,
                new NovateUtils.setRequestReturn<MyPointBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(MyPointsActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(MyPointBean response) {
                        if (response != null && response.getData() != null) {
                            my_point.setText(response.getData().getTotalPointValue() + "");
                            myPointBean = response.getData().getPointChildList();
                            initNetWorkData();
                        }
                    }
                });
    }

    private void initNetWorkData() {
        if (myPointBean != null)
            myPointsAdapter = new MyPointsAdaapter(MyPointsActivity.this,R.layout.my_point_item, myPointBean);
        linearLayoutManager = new LinearLayoutManager(this);
        my_point_rv.setLayoutManager(linearLayoutManager);
        my_point_rv.setAdapter(myPointsAdapter);
    }
}