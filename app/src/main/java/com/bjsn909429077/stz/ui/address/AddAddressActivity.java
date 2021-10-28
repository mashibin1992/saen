package com.bjsn909429077.stz.ui.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AddressBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.citypicker.Interface.OnCityItemClickListener;
import com.jiangjun.library.citypicker.bean.CityBean;
import com.jiangjun.library.citypicker.bean.DistrictBean;
import com.jiangjun.library.citypicker.bean.ProvinceBean;
import com.jiangjun.library.citypicker.citypickerview.CityPickerView;
import com.jiangjun.library.citypicker.citywheel.CityConfig;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;
import com.tamic.novate.Throwable;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_diqu)
    TextView et_diqu;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.iv_select)
    ImageView iv_select;

    public boolean ivType = false;
    public CityPickerView mPicker = new CityPickerView();
    private String id;
    private String name;
    private String id1;
    private String name1;
    private String id2;
    private String name2;
    private String from;
    private AddressBean.DataBean bean;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_add_address;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        commonTitleView.setTitle("添加收货地址");
        mPicker.init(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("addressList")) {
            //修改
            bean = (AddressBean.DataBean) intent.getSerializableExtra("bean");
            et_name.setText(bean.getName());
            et_phone.setText(bean.getMobile());
            et_diqu.setText(bean.getProvince() +"、"+ bean.getCity() +"、"+ bean.getArea());
            et_address.setText(bean.getAddress());
            int isDefault = bean.getIsDefault();
            //1默认
            ivType = isDefault == 1 ? true : false;
            if (!ivType) {
                iv_select.setImageResource(R.drawable.icon_iv_select_false);
            } else {
                iv_select.setImageResource(R.drawable.icon_iv_select_true);
            }
            id=bean.getProvinceId();
            name=bean.getProvince();
            id1=bean.getCityId();
            name1=bean.getCity();
            id2=bean.getAreaId();
            name2=bean.getArea();
        } else {
            //添加

        }


    }


    @OnClick({R.id.tv_add, R.id.iv_select, R.id.et_diqu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                //保存信息
                if (TextUtil.isEmpty(et_name.getText().toString())) {
                    ToastUtils.Toast(AddAddressActivity.this, "请输入名字");
                    return;
                }
                if (TextUtil.isEmpty(et_phone.getText().toString())) {
                    ToastUtils.Toast(AddAddressActivity.this, "请输入手机号");
                    return;
                }
                if (TextUtil.isEmpty(et_diqu.getText().toString()) || et_diqu.getText().toString().equals("省、市、区、街道")) {
                    ToastUtils.Toast(AddAddressActivity.this, "请选择地区");
                    return;
                }
                if (TextUtil.isEmpty(et_address.getText().toString())) {
                    ToastUtils.Toast(AddAddressActivity.this, "请输入详细地址");
                    return;
                }
                if (from.equals("addressList")) {
                    //修改
                    modAddress();
                } else {
                    //添加
                    addAddress();
                }
                break;
            case R.id.iv_select:
                if (!ivType) {
                    iv_select.setImageResource(R.drawable.icon_iv_select_true);
                    ivType = true;
                } else {
                    iv_select.setImageResource(R.drawable.icon_iv_select_false);
                    ivType = false;
                }

                break;
            case R.id.et_diqu:
                InputMethodManager imm = (InputMethodManager) AddAddressActivity.this
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(AddAddressActivity.this.getWindow().getDecorView().getWindowToken(), 0);
                selectAddress();//调用CityPicker选取区域
                break;
            default:
        }
    }
    //添加
    private void addAddress() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("address", et_address.getText().toString());
        map.put("area", name2);
        map.put("areaId", id2);
        map.put("city", name1);
        map.put("cityId", id1);
        map.put("isDefault", ivType ? "1" : "0");
        map.put("mobile", et_phone.getText().toString());
        map.put("name", et_name.getText().toString());
        map.put("province", name);//省份
        map.put("provinceId", id);
        NovateUtils.getInstance().Post(mContext, BaseUrl.addAddress, map, true,
                new NovateUtils.setRequestReturn<AddressBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AddressBean addressBean) {
                        if (addressBean != null) {
                            AddressBean.DataBean dataBean = new AddressBean.DataBean();
                            dataBean.setAddress(et_address.getText().toString());
                            dataBean.setArea(name2);
                            dataBean.setAreaId(id2);
                            dataBean.setCity(name1);
                            dataBean.setCityId(id1);
                            dataBean.setIsDefault(ivType ? 1 : 0);
                            dataBean.setMobile(et_phone.getText().toString());
                            dataBean.setName(et_name.getText().toString());
                            dataBean.setProvince(name);
                            dataBean.setProvinceId(id);
                            Intent intent = new Intent();
                            intent.putExtra("bean",dataBean);
                            setResult(200,intent);
                            finish();
                        }
                    }
                });
    }
    //修改
    private void modAddress() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("address", et_address.getText().toString());
        map.put("area", name2);
        map.put("areaId", id2);
        map.put("city", name1);
        map.put("cityId", id1);
        map.put("id",bean.getId());
        map.put("isDefault", ivType ? "1" : "0");
        map.put("mobile", et_phone.getText().toString());
        map.put("name", et_name.getText().toString());
        map.put("province", name);//省份
        map.put("provinceId", id);
        NovateUtils.getInstance().Post(mContext, BaseUrl.modAddress, map, true,
                new NovateUtils.setRequestReturn<AddressBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AddressBean addressBean) {
                        if (addressBean != null) {
                            finish();
                        }
                    }
                });
    }


    private void selectAddress() {
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜  色
                .titleBackgroundColor("#E9E9E9")//标题栏背景色
                .confirTextColor("#585858")//确认按钮文字颜色
                .confirmText("ok")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#585858")//取消按钮文字颜色
                .cancelText("cancel")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(7)//显示item的数量
                .province("浙江省")//默认显示的省份
                .city("杭州市")//默认显示省份下面的城市
                .district("滨江区")//默认显示省市下面的区县数据
                .provinceCyclic(true)//省份滚轮是否可以循环滚动
                .cityCyclic(true)//城市滚轮是否可以循环滚动
                .districtCyclic(true)//区县滚轮是否循环滚动
                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
                .setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
                .drawShadows(false)//滚轮不显示模糊效果
                .setLineColor("#03a9f4")//中间横线的颜色
                .setLineHeigh(5)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();
        mPicker.setConfig(cityConfig);
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                id = province.getId();
                name = province.getName();
                id1 = city.getId();
                name1 = city.getName();
                id2 = district.getId();
                name2 = district.getName();

                et_diqu.setText(name + "、" + name1 + "、" + name2);
            }

            @Override
            public void onCancel() {
                //    mPicker.hide();
            }
        });
        mPicker.showCityPicker();
    }
}