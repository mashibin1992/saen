package com.bjsn909429077.stz.ui.order;

import android.content.Intent;
import android.os.Bundle;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.CouponAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.bean.CouponBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CouponActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private int coursePackageId;
    private int coursePackagePriceId;
    private CouponAdapter couponAdapter;
    private List<CouponBean.DataBean> list;
    private int liveId;
    private String from;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_coupon;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("选择优惠券");
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CouponActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        couponAdapter = new CouponAdapter(R.layout.item_coupon, list, CouponActivity.this);
        recyclerView.setAdapter(couponAdapter);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from!=null&&from.equals("live")){
            liveId = intent.getIntExtra("liveId", 0);
        }else {
            coursePackageId = intent.getIntExtra("coursePackageId", 0);
            coursePackagePriceId = intent.getIntExtra("coursePackagePriceId", 0);
        }
        getCouponList();

        couponAdapter.setonItemListener(new CouponAdapter.OnItemClickListener() {
            @Override
            public void onClick(CouponBean.DataBean dataBean) {
                //使用优惠券
                Intent intent = new Intent();
                intent.putExtra("bean",dataBean);
                setResult(300,intent);
                finish();
            }
        });
    }

    private void getCouponList() {
        HashMap<String, Object> map = new HashMap<>();
        if (from.equals("live")){
            map.put("liveId",liveId);
        }else {
            map.put("coursePackageId",coursePackageId);
            map.put("coursePackagePriceId",coursePackagePriceId);
        }

        NovateUtils.getInstance().Post(mContext, BaseUrl.couponLst, map, true,
                new NovateUtils.setRequestReturn<CouponBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(CouponBean couponBean) {
                        List<CouponBean.DataBean> data = couponBean.getData();
                        list.clear();
                        list.addAll(data);
                        couponAdapter.notifyDataSetChanged();
                    }
                });
    }

  /*  @OnClick({R.id.tv_kecheng, R.id.tv_wenda})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_kecheng:
                tv_kecheng.setTextColor(Color.parseColor("#3557E8"));
                tv_wenda.setTextColor(Color.parseColor("#141414"));
                view_kecheng.setVisibility(View.VISIBLE);
                view_wenda.setVisibility(View.GONE);


                HomePageFragmentAdapter homePageFragmentAdapter = new HomePageFragmentAdapter(R.layout.item_home_remmcond, list1);
                recyclerView.setAdapter(homePageFragmentAdapter);
                break;
            case R.id.tv_wenda:
                tv_kecheng.setTextColor(Color.parseColor("#141414"));
                tv_wenda.setTextColor(Color.parseColor("#3557E8"));
                view_kecheng.setVisibility(View.GONE);
                view_wenda.setVisibility(View.VISIBLE);


             *//*   HomePageFragmentAdapter homePageFragmentAdapter = new HomePageFragmentAdapter(R.layout.item_home_remmcond, list1);
                recyclerView.setAdapter(homePageFragmentAdapter);*//*
                break;

        }
    }*/
}