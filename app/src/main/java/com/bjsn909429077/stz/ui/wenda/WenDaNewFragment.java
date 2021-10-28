package com.bjsn909429077.stz.ui.wenda;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.HomePageFragmentAdapter;
import com.bjsn909429077.stz.adapter.SearchResultWendaAdapter;
import com.bjsn909429077.stz.adapter.WendaAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HomeBannerBean;
import com.bjsn909429077.stz.bean.SearchWenDaBean;
import com.bjsn909429077.stz.bean.WdListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ToastUtils;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class WenDaNewFragment extends BaseLazyLoadFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_wenda_new;
    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(linearLayoutManager);
    }
    //列表数据
    private void getwdListData() {
        HashMap<String, String> map = new HashMap<>();

        NovateUtils.getInstance().Post(mContext, BaseUrl.wdNewList, map, true,
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
                                WendaAdapter adapter = new WendaAdapter(R.layout.item_search_wenda, data, getActivity());
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
        getwdListData();
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
