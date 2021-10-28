package com.bjsn909429077.stz.ui.wenda;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import com.bjsn909429077.stz.adapter.WenDaZhuanjiaAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HomeBannerBean;
import com.bjsn909429077.stz.bean.SearchWenDaBean;
import com.bjsn909429077.stz.bean.WenDaZhuanJiaBean;
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


public class WenDaZhuanjiaFragment extends BaseLazyLoadFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_wenda_zhuanjia;
    }

    @Override
    protected void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void loadData() {
        getList();
    }

    private void getList() {
        HashMap<String, String> map = new HashMap<>();

        NovateUtils.getInstance().Post(mContext, BaseUrl.wdZhuanJiaList, map, true,
                new NovateUtils.setRequestReturn<WenDaZhuanJiaBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(WenDaZhuanJiaBean wenDaZhuanJiaBean) {
                        if (wenDaZhuanJiaBean != null) {
                            if (wenDaZhuanJiaBean.getData() != null && wenDaZhuanJiaBean.getData().size() > 0) {
                                List<WenDaZhuanJiaBean.DataBean> data = wenDaZhuanJiaBean.getData();
                                WenDaZhuanjiaAdapter adapter = new WenDaZhuanjiaAdapter(R.layout.item_zhuanjia_wenda, data, getActivity());
                                recycler_view.setAdapter(adapter);
                                adapter.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                                        Intent intent = new Intent(getActivity(), ZuanJiaInfoActivity.class);
                                        WenDaZhuanJiaBean.DataBean dataBean = data.get(position);
                                        intent.putExtra("bean",dataBean);
                                        startActivity(intent);
                                    }
                                });
                            }

                        }
                    }
                });
    }


}
