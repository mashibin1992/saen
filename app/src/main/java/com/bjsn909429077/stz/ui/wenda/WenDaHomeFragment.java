package com.bjsn909429077.stz.ui.wenda;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import com.bjsn909429077.stz.adapter.WenDaTypeAdapter;
import com.bjsn909429077.stz.adapter.WendaAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HomeBannerBean;
import com.bjsn909429077.stz.bean.HomeRecommendListBean;
import com.bjsn909429077.stz.bean.SearchWenDaBean;
import com.bjsn909429077.stz.bean.WdListBean;
import com.bjsn909429077.stz.bean.WenDaTypeBean;
import com.bjsn909429077.stz.ui.MainActivity;
import com.bjsn909429077.stz.ui.home.SearchActivity;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class WenDaHomeFragment extends BaseLazyLoadFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.xBanner)
    XBanner xBanner;
    @BindView(R.id.linear_kuaisu)
    LinearLayout linear_kuaisu;
    @BindView(R.id.linear_tiwen)
    LinearLayout linear_tiwen;
    @BindView(R.id.linear_shoucang)
    LinearLayout linear_shoucang;
    private HomePageFragmentAdapter homePageFragmentAdapter;
    public boolean isVisibleToUser;
    private List<WenDaTypeBean.DataBean> data;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_wenda_home;
    }

    @Override
    protected void init() {
        getBanner();
   /*     Bundle arguments = getArguments();
        int typeId = arguments.getInt("typeId");
      */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(linearLayoutManager);
        getwdListData("");
        getWdType();
    }

    private void getBanner() {
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.wdBanner, map, true,
                new NovateUtils.setRequestReturn<HomeBannerBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HomeBannerBean homeBannerBean) {
                        if (homeBannerBean != null) {
                            List<HomeBannerBean.DataBean> data = homeBannerBean.getData();
                            if (data != null && data.size() > 0) {
                                xBanner.removeAllViews();
                                xBanner.setVisibility(View.VISIBLE);
                                initXBanner(data);
                            } else {
                                xBanner.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }
    private void getWdType() {
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.wdType, map, true,
                new NovateUtils.setRequestReturn<WenDaTypeBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(WenDaTypeBean wenDaTypeBean) {
                        if (wenDaTypeBean != null) {
                            data = wenDaTypeBean.getData();
                        }
                    }
                });
    }

    private void initXBanner(List<HomeBannerBean.DataBean> list) {
        // xBanner.removeAllViews();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HomeBannerBean.DataBean dataBean = list.get(i);
            String imgPath = dataBean.getImgPath();
            strings.add(imgPath);
        }
        xBanner.setData(strings, null);
        // XBanner适配数据
        xBanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(getActivity()).load(strings.get(position)).into((ImageView) view);
            }
        });
        xBanner.setPageTransformer(Transformer.Default);
        xBanner.setPageChangeDuration(1000);
        xBanner.startAutoPlay();
        //xbanner的点击事件
        xBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Toast.makeText(getActivity(), "点击了第" + (position + 1) + "张图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //列表数据
    private void getwdListData(String id) {
        //api/app/v1/index/course/recommend/type/list
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(id)){
            map.put("wdTypeId",id);
        }

        NovateUtils.getInstance().Post(mContext, BaseUrl.wdRecommendList, map, true,
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        super.setUserVisibleHint(isVisibleToUser);
    }

    @OnClick({R.id.linear_kuaisu, R.id.linear_tiwen, R.id.linear_shoucang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_kuaisu:
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                intent.putExtra("from","tiwen");
                startActivity(intent);
                break;
            case R.id.linear_tiwen:
                Intent intent1 = new Intent(getActivity(), MyWdActivity.class);
                intent1.putExtra("type","我的提问");
                startActivity(intent1);
                break;
            case R.id.linear_shoucang:
                Intent intent2 = new Intent(getActivity(), MyWdActivity.class);
                intent2.putExtra("type","我的收藏");
                startActivity(intent2);
                break;
            default:
        }
    }

    private PopupWindow mPopWindow;

    //PopupWindow菜单详细内容显示
    //区域
    void showRiskAreaPopupWindow(TabLayout tabLayout) {
        //设置contentView
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_wenda, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //获取实例，设置各个控件的点击响应
        //注意：PopupWindow中各个控件的所在的布局是contentView，而不是在Activity中，所以，要在findViewById(R.id.tv)前指定根布局
        RecyclerView recycler_pop = contentView.findViewById(R.id.recycler_pop);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recycler_pop.setLayoutManager(gridLayoutManager);

        TextView tv_button = contentView.findViewById(R.id.tv_button);
        if (data!=null&&data.size()>0){
            WenDaTypeAdapter wenDaTypeAdapter = new WenDaTypeAdapter(R.layout.item_wenda_type,data);
            recycler_pop.setAdapter(wenDaTypeAdapter);
            wenDaTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    WenDaTypeBean.DataBean dataBean = data.get(position);
                    int id = dataBean.getId();
                    getwdListData(id+"");
                    mPopWindow.dismiss();
                }
            });
        }

        //解决5.0以下版本点击外部不消失问题
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示方式
        mPopWindow.showAsDropDown(tabLayout);

    }
}
