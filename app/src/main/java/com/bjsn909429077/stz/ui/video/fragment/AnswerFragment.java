package com.bjsn909429077.stz.ui.video.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.StudyAnswerAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.MyAnswerBean;
import com.bjsn909429077.stz.bean.NoteListBean;
import com.bjsn909429077.stz.ui.video.AnswerActivity;
import com.bjsn909429077.stz.ui.video.AnswerInfoActivity;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class AnswerFragment extends BaseLazyLoadFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.img_no_data)
    ImageView img_no_data;
    @BindView(R.id.btn_ask)
    TextView btn_ask;
    private int page = 0;
    private ArrayList<MyAnswerBean.DataBean> dataBeans = new ArrayList<>();
    private StudyAnswerAdapter studyAnswerAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_answer;
    }

    @Override
    protected void init() {

        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        studyAnswerAdapter = new StudyAnswerAdapter(dataBeans, this);
        recycler_view.setAdapter(studyAnswerAdapter);
        initListener();
    }

    private void initListener() {
        //提问
        btn_ask.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), AnswerActivity.class);
            startActivity(intent);
        });
        //详情
        studyAnswerAdapter.setAnswerItemClickListener((dataBean, position) -> {
            Intent intent = new Intent();
            intent.setClass(getContext(), AnswerInfoActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void loadData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("classHourId", page);
        map.put("courseId", page);
        map.put("page", page);
        NovateUtils.getInstance().Post(mContext, BaseUrl.daYiList, map, true,
                new NovateUtils.setRequestReturn<MyAnswerBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(MyAnswerBean response) {
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

    public static AnswerFragment getInstance(Bundle bundle) {
        AnswerFragment answerFragment = new AnswerFragment();
        answerFragment.setArguments(bundle);
        return answerFragment;
    }
}
