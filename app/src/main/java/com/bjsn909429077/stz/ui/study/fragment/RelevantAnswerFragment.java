package com.bjsn909429077.stz.ui.study.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.StudyAnswerAdapter;
import com.bjsn909429077.stz.bean.MyAnswerBean;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

public class RelevantAnswerFragment extends BaseLazyLoadFragment {
    @BindView(R.id.recycler_answer)
    RecyclerView recycler_answer;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    @BindView(R.id.img_no_data)
    ImageView img_no_data;
    private ArrayList<MyAnswerBean.DataBean> strings;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_relevant_answer;
    }

    @Override
    protected void init() {
        strings = new ArrayList<>();
        recycler_answer.setLayoutManager(new LinearLayoutManager(mContext));
        StudyAnswerAdapter studyAnswerAdapter = new StudyAnswerAdapter(strings, this);
        recycler_answer.setAdapter(studyAnswerAdapter);
        initListener(studyAnswerAdapter);
    }

    private void initListener(StudyAnswerAdapter studyAnswerAdapter) {
        //刷新
        sml.setOnRefreshListener(refreshLayout -> {
        });
        //加载更多
        sml.setOnLoadMoreListener(refreshLayout -> {
        });
        //子条目点击事件
        studyAnswerAdapter.setAnswerItemClickListener((object, position) -> {

        });
    }


    @Override
    protected void loadData() {
        if (strings.size() > 0) {
            img_no_data.setVisibility(View.GONE);
            recycler_answer.setVisibility(View.VISIBLE);
        } else {
            img_no_data.setVisibility(View.VISIBLE);
            recycler_answer.setVisibility(View.GONE);
        }
    }
}
