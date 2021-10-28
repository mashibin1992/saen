package com.bjsn909429077.stz.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.SearchHistoryAdapter;
import com.bjsn909429077.stz.adapter.SearchHotAdapter;
import com.bjsn909429077.stz.adapter.SearchResultAdapter;
import com.bjsn909429077.stz.adapter.SearchResultWendaAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.HotSearchBean;
import com.bjsn909429077.stz.bean.SearchBean;
import com.bjsn909429077.stz.bean.SearchWenDaBean;
import com.bjsn909429077.stz.ui.course.SelectCourseActivity;
import com.bjsn909429077.stz.ui.wenda.WenDaInfoActivity;
import com.bjsn909429077.stz.ui.wenda.ZuanJiaInfoActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.recyclerView_hot)
    RecyclerView recyclerView_hot;
    @BindView(R.id.recyclerView_history)
    RecyclerView recyclerView_history;
    @BindView(R.id.iv_delete_hoistory)
    ImageView iv_delete_hoistory;
    @BindView(R.id.rely_hot)
    RelativeLayout rely_hot;
    @BindView(R.id.rely_history)
    RelativeLayout rely_history;
    @BindView(R.id.tv_kecheng)
    TextView tv_kecheng;
    @BindView(R.id.tv_wenda)
    TextView tv_wenda;
    @BindView(R.id.tv_sousuo)
    TextView tv_sousuo;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.view_kecheng)
    View view_kecheng;
    @BindView(R.id.view_wenda)
    View view_wenda;
    @BindView(R.id.linear_result)
    LinearLayout linear_result;
    @BindView(R.id.rely_tab)
    RelativeLayout rely_tab;
    @BindView(R.id.linear_empty)
    LinearLayout linear_empty;
    @BindView(R.id.linear_empty_home)
    LinearLayout linear_empty_home;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SearchHotAdapter adapterHot;
    private SearchResultAdapter searchResultAdapter;
    private String from;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) { //避免setOnKeyListener 执行两次
                    getSearch();

                    return true;
                }
                return false;

            }

        });
    }

    private void getSearch() {
        // 先隐藏键盘
        if (SearchActivity.this.getCurrentFocus()!=null){
            View currentFocus = SearchActivity.this.getCurrentFocus();
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(currentFocus
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        if (from.equals("home")) {
            //获取问答数据
            getWenDaList(et_search.getText().toString());
        }
        //保存历史数据
        List<String> list = UserConfig.loadSearchArray(SearchActivity.this);
        list.add(0,et_search.getText().toString());
        UserConfig.saveSearchArray(list);
        getSearchList(et_search.getText().toString());
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("kecheng")) {
            rely_tab.setVisibility(View.GONE);
        } else if (from.equals("home")) {
            rely_tab.setVisibility(View.VISIBLE);
        }
        initHistoryData();


        //课程搜索结果
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 4);
        recyclerView_hot.setLayoutManager(layoutManager2);
        List<HotSearchBean.DataBean> list = new ArrayList<>();
        adapterHot = new SearchHotAdapter(R.layout.item_search_hot, list);
        recyclerView_hot.setAdapter(adapterHot);

        getHotList();
    }

    private void initHistoryData() {
        List<String> searchHist = UserConfig.loadSearchArray(SearchActivity.this);
        //历史搜索
        if (searchHist.size() > 0) {
            rely_history.setVisibility(View.VISIBLE);
        } else {
            rely_history.setVisibility(View.GONE);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_history.setLayoutManager(layoutManager);
        SearchHistoryAdapter adapter = new SearchHistoryAdapter(R.layout.item_search_history, searchHist);
        recyclerView_history.setAdapter(adapter);


        List<String> finalSearchHist = searchHist;
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //历史搜索
                et_search.setText(finalSearchHist.get(position));
                getSearch();
            }
        });
    }

    private void getHotList() {
        //api/app/v1/index/hot/search/list
        HashMap<String, Integer> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.hotList, map, true,
                new NovateUtils.setRequestReturn<HotSearchBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(HotSearchBean hotSearchBean) {
                        if (hotSearchBean != null) {
                            if (hotSearchBean.getData() != null && hotSearchBean.getData().size() > 0) {
                                rely_hot.setVisibility(View.VISIBLE);
                                List<HotSearchBean.DataBean> data = hotSearchBean.getData();
                                adapterHot.setNewData(data);
                                adapterHot.notifyDataSetChanged();
                                adapterHot.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                                        //热门搜索
                                        et_search.setText(data.get(position).getName());
                                        getSearch();
                                    }
                                });
                            } else {
                                rely_hot.setVisibility(View.GONE);
                            }

                        }
                    }
                });
    }

    @OnClick({R.id.tv_kecheng, R.id.tv_wenda, R.id.tv_sousuo, R.id.iv_delete_hoistory,R.id.iv_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_kecheng:
                tv_kecheng.setTextColor(Color.parseColor("#3557E8"));
                tv_wenda.setTextColor(Color.parseColor("#141414"));
                view_kecheng.setVisibility(View.VISIBLE);
                view_wenda.setVisibility(View.GONE);
                getSearchList(et_search.getText().toString());


                break;
            case R.id.tv_wenda:
                tv_kecheng.setTextColor(Color.parseColor("#141414"));
                tv_wenda.setTextColor(Color.parseColor("#3557E8"));
                view_kecheng.setVisibility(View.GONE);
                view_wenda.setVisibility(View.VISIBLE);
                //获取问答数据
                getWenDaList(et_search.getText().toString());
                break;
            case R.id.tv_sousuo:
                //搜索
                getSearch();
                break;
            case R.id.iv_finish:

                finish();
                break;
            case R.id.iv_delete_hoistory:
                //删除历史搜索
                AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
                builder.setTitle("提示"); //设置标题
                builder.setMessage("是否全部删除历史搜索?"); //设置内容
                builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //关闭dialog
                        UserConfig.deleteSearchArray(SearchActivity.this);
                        initHistoryData();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //参数都设置完成了，创建并显示出来
                builder.create().show();
                break;

        }
    }

    // 获取课程搜索结果
    private void getSearchList(String search) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("search", search);
        NovateUtils.getInstance().Post(mContext, BaseUrl.searchList, map, true,
                new NovateUtils.setRequestReturn<SearchBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(SearchBean searchBean) {
                        if (searchBean != null) {
                            if (searchBean.getData() != null && searchBean.getData().size() > 0) {
                                List<SearchBean.DataBean> data = searchBean.getData();
                                searchResultAdapter = new SearchResultAdapter(R.layout.item_home_remmcond, data, SearchActivity.this);
                                recyclerView.setAdapter(searchResultAdapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                linear_result.setVisibility(View.VISIBLE);
                                linear_empty.setVisibility(View.GONE);
                                linear_empty_home.setVisibility(View.GONE);
                                searchResultAdapter.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                                        Intent intent = new Intent(mContext, SelectCourseActivity.class);
                                        intent.putExtra("coursePackageId", data.get(position).getCoursePackageId());
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                if (from.equals("home")) {
                                    linear_result.setVisibility(View.VISIBLE);
                                    linear_empty.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.GONE);
                                    linear_empty_home.setVisibility(View.VISIBLE);
                                } else if (from.equals("kecheng")) {
                                    linear_result.setVisibility(View.GONE);
                                    linear_empty.setVisibility(View.VISIBLE);
                                    linear_empty_home.setVisibility(View.GONE);
                                }
                            }
                            rely_hot.setVisibility(View.GONE);
                            rely_history.setVisibility(View.GONE);
                        }
                    }
                });
    }

    // 获取问答搜索结果
    private void getWenDaList(String search) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("search", search);
        NovateUtils.getInstance().Post(mContext, BaseUrl.wendaSearchList, map, true,
                new NovateUtils.setRequestReturn<SearchWenDaBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(SearchWenDaBean searchBean) {
                        if (searchBean != null) {
                            if (searchBean.getData() != null && searchBean.getData().size() > 0) {
                                List<SearchWenDaBean.DataBean> data = searchBean.getData();
                                SearchResultWendaAdapter   adapter = new SearchResultWendaAdapter(R.layout.item_search_wenda, data, SearchActivity.this);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setVisibility(View.VISIBLE);
                                linear_result.setVisibility(View.VISIBLE);
                                linear_empty.setVisibility(View.GONE);
                                linear_empty_home.setVisibility(View.GONE);
                                adapter.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                                        //问答详情
                                        Intent intent = new Intent(SearchActivity.this, WenDaInfoActivity.class);
                                        intent.putExtra("wdId",data.get(position).getWdId()+"");
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                if (from.equals("home")) {
                                    linear_result.setVisibility(View.VISIBLE);
                                    linear_empty.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.GONE);
                                    linear_empty_home.setVisibility(View.VISIBLE);
                                } else if (from.equals("kecheng")) {
                                    linear_result.setVisibility(View.GONE);
                                    linear_empty.setVisibility(View.VISIBLE);
                                    linear_empty_home.setVisibility(View.GONE);
                                }
                            }
                            rely_hot.setVisibility(View.GONE);
                            rely_history.setVisibility(View.GONE);
                        }
                    }
                });
    }

}