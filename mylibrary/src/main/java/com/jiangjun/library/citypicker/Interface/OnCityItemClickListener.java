package com.jiangjun.library.citypicker.Interface;


import com.jiangjun.library.citypicker.bean.CityBean;
import com.jiangjun.library.citypicker.bean.DistrictBean;
import com.jiangjun.library.citypicker.bean.ProvinceBean;

/**
 * 作者：liji on 2017/11/16 10:06
 * 邮箱：lijiwork@sina.com
 * QQ ：275137657
 */

public abstract class OnCityItemClickListener {
    
    /**
     * 当选择省市区三级选择器时，需要覆盖此方法
     * @param province
     * @param city
     * @param district
     */
    public abstract void onSelected(ProvinceBean province, CityBean city, DistrictBean district) ;
    
    /**
     * 取消
     */
    public abstract void onCancel() ;
}
