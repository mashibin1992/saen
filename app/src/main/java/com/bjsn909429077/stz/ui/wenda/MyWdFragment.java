package com.bjsn909429077.stz.ui.wenda;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.MyWDAdapter;
import com.bjsn909429077.stz.adapter.SearchResultWendaAdapter;
import com.bjsn909429077.stz.adapter.WendaAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.SearchWenDaBean;
import com.bjsn909429077.stz.bean.WdListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;


public class MyWdFragment extends BaseLazyLoadFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private String type;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_my_wdhome;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        type = arguments.getString("type");
        String type1 = arguments.getString("type1");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(linearLayoutManager);
        getMyWdList(type,type1);
    }
    private void getMyWdList(String type,String type1) {
        HashMap<String, String> map = new HashMap<>();
        map.put("isAnswer",type);
        map.put("type",type1);
        NovateUtils.getInstance().Post(mContext, BaseUrl.answerList, map, true,
                new NovateUtils.setRequestReturn<WdListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(WdListBean wdListBean) {
                        if (wdListBean != null) {
                            if (wdListBean.getData() != null && wdListBean.getData().size() > 0) {
                                List<WdListBean.DataBean> data = wdListBean.getData();
                                MyWDAdapter adapter = new MyWDAdapter(R.layout.item_my_wenda, data, getActivity(),type);
                                recycler_view.setAdapter(adapter);
                                adapter.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                                        //问答详情
                                        Intent intent = new Intent(getActivity(), WenDaInfoActivity.class);
                                        intent.putExtra("wdId",data.get(position).getWdId()+"");
                                        startActivity(intent);

                                    }
                                });

                            }

                        }
                    }
                });

    }



    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
/*
    @OnClick({R.id.linear_kuaisu, R.id.linear_tiwen, R.id.linear_shoucang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_kuaisu:
                ToastUtils.Toast(getActivity(), "快速提问");
                break;
            case R.id.linear_tiwen:
                ToastUtils.Toast(getActivity(), "我的提问");
                break;
            case R.id.linear_shoucang:
                ToastUtils.Toast(getActivity(), "我的收藏");
                break;
            default:
        }
    }*/


}
