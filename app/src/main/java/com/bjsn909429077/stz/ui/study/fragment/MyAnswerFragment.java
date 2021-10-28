package com.bjsn909429077.stz.ui.study.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.StudyAnswerAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.MyAnswerBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class MyAnswerFragment extends BaseLazyLoadFragment {
    @BindView(R.id.recycler_answer)
    RecyclerView recycler_answer;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    @BindView(R.id.img_no_data)
    ImageView img_no_data;
    private ArrayList<MyAnswerBean.DataBean> dataBeans = new ArrayList<>();
    private int page = 0;
    private StudyAnswerAdapter studyAnswerAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_my_answer;
    }

    @Override
    protected void init() {

        recycler_answer.setLayoutManager(new LinearLayoutManager(mContext));
        studyAnswerAdapter = new StudyAnswerAdapter(dataBeans, this);
        recycler_answer.setAdapter(studyAnswerAdapter);
        initListener(studyAnswerAdapter);
    }

    private void initListener(StudyAnswerAdapter studyAnswerAdapter) {
        //刷新
        sml.setOnRefreshListener(refreshLayout -> {
            dataBeans.clear();
            page = 0;
            loadData();
        });
        //加载更多
        sml.setOnLoadMoreListener(refreshLayout -> {
            page++;
            loadData();
        });
        //子条目点击事件
        studyAnswerAdapter.setAnswerItemClickListener((dataBean, position) -> {

        });
    }

    @Override
    protected void loadData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        NovateUtils.getInstance().Post(mContext, BaseUrl.myAnswerList, map, true,
                new NovateUtils.setRequestReturn<MyAnswerBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        closeSml();
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(MyAnswerBean response) {
                        closeSml();
                        if (response != null && response.data != null) {
                            dataBeans.addAll(response.data);
                            studyAnswerAdapter.notifyDataSetChanged();
                            if (dataBeans.size() > 0) {
                                img_no_data.setVisibility(View.GONE);
                            } else {
                                img_no_data.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

    }


    private void closeSml() {
        sml.finishLoadMore();
        sml.finishRefresh();
    }
}
