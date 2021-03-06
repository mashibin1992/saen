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
        commonTitleView.setTitle("??????????????????");
        mPicker.init(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("addressList")) {
            //??????
            bean = (AddressBean.DataBean) intent.getSerializableExtra("bean");
            et_name.setText(bean.getName());
            et_phone.setText(bean.getMobile());
            et_diqu.setText(bean.getProvince() +"???"+ bean.getCity() +"???"+ bean.getArea());
            et_address.setText(bean.getAddress());
            int isDefault = bean.getIsDefault();
            //1??????
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
            //??????

        }


    }


    @OnClick({R.id.tv_add, R.id.iv_select, R.id.et_diqu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                //????????????
                if (TextUtil.isEmpty(et_name.getText().toString())) {
                    ToastUtils.Toast(AddAddressActivity.this, "???????????????");
                    return;
                }
                if (TextUtil.isEmpty(et_phone.getText().toString())) {
                    ToastUtils.Toast(AddAddressActivity.this, "??????????????????");
                    return;
                }
                if (TextUtil.isEmpty(et_diqu.getText().toString()) || et_diqu.getText().toString().equals("????????????????????????")) {
                    ToastUtils.Toast(AddAddressActivity.this, "???????????????");
                    return;
                }
                if (TextUtil.isEmpty(et_address.getText().toString())) {
                    ToastUtils.Toast(AddAddressActivity.this, "?????????????????????");
                    return;
                }
                if (from.equals("addressList")) {
                    //??????
                    modAddress();
                } else {
                    //??????
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
                selectAddress();//??????CityPicker????????????
                break;
            default:
        }
    }
    //??????
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
        map.put("province", name);//??????
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
    //??????
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
        map.put("province", name);//??????
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
                .title("????????????")//??????
                .titleTextSize(18)//??????????????????
                .titleTextColor("#585858")//???????????????  ???
                .titleBackgroundColor("#E9E9E9")//??????????????????
                .confirTextColor("#585858")//????????????????????????
                .confirmText("ok")//??????????????????
                .confirmTextSize(16)//????????????????????????
                .cancelTextColor("#585858")//????????????????????????
                .cancelText("cancel")//??????????????????
                .cancelTextSize(16)//????????????????????????
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//?????????????????????????????????????????????????????????????????????????????????
                .showBackground(true)//???????????????????????????
                .visibleItemsCount(7)//??????item?????????
                .province("?????????")//?????????????????????
                .city("?????????")//?????????????????????????????????
                .district("?????????")//???????????????????????????????????????
                .provinceCyclic(true)//????????????????????????????????????
                .cityCyclic(true)//????????????????????????????????????
                .districtCyclic(true)//??????????????????????????????
                .setCustomItemLayout(R.layout.item_city)//?????????item?????????
                .setCustomItemTextViewId(R.id.item_city_name_tv)//?????????item???????????????textViewid
                .drawShadows(false)//???????????????????????????
                .setLineColor("#03a9f4")//?????????????????????
                .setLineHeigh(5)//?????????????????????
                .setShowGAT(true)//?????????????????????????????????????????????
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

                et_diqu.setText(name + "???" + name1 + "???" + name2);
            }

            @Override
            public void onCancel() {
                //    mPicker.hide();
            }
        });
        mPicker.showCityPicker();
    }
}