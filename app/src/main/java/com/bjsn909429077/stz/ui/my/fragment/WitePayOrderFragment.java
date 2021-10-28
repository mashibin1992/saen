package com.bjsn909429077.stz.ui.my.fragment;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.AllOrderAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AllOrderBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/20 08:53
 **/
public class WitePayOrderFragment extends BaseLazyLoadFragment {

    private RecyclerView wite_pay_order_rv;

    @Override
    protected int setLayoutId() {
        return R.layout.wite_pay_order_fragment;
    }

    @Override
    protected void init() {
        wite_pay_order_rv = (RecyclerView) findViewById(R.id.wite_pay_order_rv);
    }

    @Override
    protected void loadData() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("page", 0);
        map.put("type", 1);//类型：0、全部 1、待支付 2、已支付；默认全部
        NovateUtils.getInstance().Post(mContext, BaseUrl.AllOrder, map, true,
                new NovateUtils.setRequestReturn<AllOrderBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AllOrderBean response) {
                        if (response != null && response.getData() != null) {
                            List<AllOrderBean.DataBean> data = response.getData();
                            AllOrderAdapter allOrderAdapter = new AllOrderAdapter(R.layout.all_order_adapter, data,getActivity());
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            wite_pay_order_rv.setLayoutManager(linearLayoutManager);
                            wite_pay_order_rv.setAdapter(allOrderAdapter);
                        }
                    }
                });
    }
}
