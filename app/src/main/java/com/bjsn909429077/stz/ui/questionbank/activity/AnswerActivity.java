package com.bjsn909429077.stz.ui.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.AnswerAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AnswerListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.CommonTitleView;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerActivity extends BaseActivity {
    private CommonTitleView commonTitleView;
    private RecyclerView answer_rv;
    private AnswerAdapter answerAdapter;
    private int type;
    private int secondId;
    private List<AnswerListBean.DataBean> data;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_answer;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = (CommonTitleView) findViewById(com.jiangjun.library.R.id.common_title);
        answer_rv = findViewById(R.id.answer_rv);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        type = intent.getIntExtra("type", 0);
        secondId = intent.getIntExtra("secondId", -1);
        commonTitleView.setTitle(title);
        initNetWork();
    }

    private void initOnClick() {
        answerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                Intent intent = new Intent(AnswerActivity.this, QuestionActivity.class);
                intent.putExtra("isPractice", false);
                intent.putExtra("testPaperId", data.get(position).getTestPaperId());
                if (data.get(position).getCountNumber().equals(data.get(position).getCountNumber())){
                    intent.putExtra("type", 2);//类型 2.重新答题 当完成数等于总数是传2 其他情况不传
                }
                startActivity(intent);
            }
        });
    }

    private void initNetWork() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("classify", type);//试卷分类 1.模拟试卷 2.历年真题 3.考前押题
        map.put("secondId", secondId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.paperList, map, true,
                new NovateUtils.setRequestReturn<AnswerListBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(AnswerActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AnswerListBean response) {
                        if (response != null && response.getData() != null) {
                            data = response.getData();
                            answer_rv.setLayoutManager(new LinearLayoutManager(AnswerActivity.this));
                            answerAdapter = new AnswerAdapter(R.layout.answer_item, data);
                            answer_rv.setAdapter(answerAdapter);
                        }
                        initOnClick();
                    }
                });
    }
}