package com.bjsn909429077.stz.ui.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.AddressListAdapter;
import com.bjsn909429077.stz.adapter.AddressListNewAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AddressBean;
import com.bjsn909429077.stz.bean.OrderBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity {


    @BindView(R.id.recycler)
    RecyclerView recycler; @BindView(R.id.tv_add)
    TextView tv_add;


    private OrderBean.DataBean.OrderCoursePackageInfoBean orderCoursePackageInfo;
    private List<AddressBean.DataBean> addressBeanData;
    private AddressListNewAdapter listAdapter;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_address;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        commonTitleView.setTitle("收货地址");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddressActivity.this);
        recycler.setLayoutManager(linearLayoutManager);
        addressBeanData = new ArrayList<>();
        listAdapter = new AddressListNewAdapter(R.layout.item_address, addressBeanData, AddressActivity.this);
        recycler.setAdapter(listAdapter);
        listAdapter.setonItemDeleteListener(new AddressListNewAdapter.OnItemDeleteListener() {
            @Override
            public void delete(int id) {
                //删除地址
                deleteAddress(id);
            }
        });
        listAdapter.setonItemSettingListener(new AddressListNewAdapter.OnItemSettingListener() {
            @Override
            public void setting(AddressBean.DataBean bean) {
                //设置默认地址
                    editAddress(bean);
            }
        });
        listAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (listAdapter.getData()!=null){
                    Intent intent = new Intent();
                    AddressBean.DataBean bean = listAdapter.getData().get(position);
                    intent.putExtra("bean",bean);
                    setResult(200,intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

    private void getAddressList() {
        HashMap<String, Integer> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.addressList, map, true,
                new NovateUtils.setRequestReturn<AddressBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AddressBean addressBean) {
                        if (addressBean != null) {
                            if (addressBean.getData()!=null&&addressBean.getData().size()>0){
                                addressBeanData.clear();
                                recycler.setVisibility(View.VISIBLE);
                                List<AddressBean.DataBean> data = addressBean.getData();
                                addressBeanData.addAll(data);
                                listAdapter.notifyDataSetChanged();
                            }else {
                                recycler.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
    private void deleteAddress(int id) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("id",id);
        NovateUtils.getInstance().Post(mContext, BaseUrl.deleteAddress, map, true,
                new NovateUtils.setRequestReturn<AddressBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AddressBean addressBean) {
                        if (addressBean != null) {
                            getAddressList();
                           ToastUtils.Toast(AddressActivity.this,"刪除成功！");
                        }
                    }
                });
    }
    private void editAddress(AddressBean.DataBean bean) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("address",bean.getAddress());
        map.put("area", bean.getArea());
        map.put("areaId", bean.getAreaId());
        map.put("city", bean.getCity());
        map.put("cityId", bean.getCityId());
        map.put("id",bean.getId());
        map.put("isDefault",bean.getIsDefault());
        map.put("mobile",bean.getMobile());
        map.put("name",bean.getName());
        map.put("province", bean.getProvince());//省份
        map.put("provinceId", bean.getProvinceId());
        NovateUtils.getInstance().Post(mContext, BaseUrl.modAddress, map, true,
                new NovateUtils.setRequestReturn<AddressBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AddressBean addressBean) {
                        if (addressBean != null) {
                            getAddressList();
                        }
                    }
                });
    }
   @OnClick({R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                //添加收货地址
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                intent.putExtra("from","order");
                startActivity(intent);
                break;

            default:
        }
    }
}