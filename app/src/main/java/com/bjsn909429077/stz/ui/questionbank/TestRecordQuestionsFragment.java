package com.bjsn909429077.stz.ui.questionbank;

import android.content.Intent;
import android.view.View;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.AnswerAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AnswerListBean;
import com.bjsn909429077.stz.ui.questionbank.activity.QuestionActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/19 11:43
 **/
public class TestRecordQuestionsFragment extends BaseLazyLoadFragment {

    private RecyclerView test_record_rv;
    private AnswerAdapter answerAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.test_record_questions_fragment;
    }

    @Override
    protected void init() {
        test_record_rv = (RecyclerView) findViewById(R.id.test_record_rv);
    }

    @Override
    protected void loadData() {
        initNetWork();
    }

    private void initNetWork() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("secondId", (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1));
        NovateUtils.getInstance().Post(mContext, BaseUrl.recordPaperList, map, true,
                new NovateUtils.setRequestReturn<AnswerListBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AnswerListBean response) {
                        if (response != null && response.getData() != null) {
                            test_record_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            answerAdapter = new AnswerAdapter(R.layout.answer_item, response.getData());
                            test_record_rv.setAdapter(answerAdapter);
                            initClick();
                        }
                    }
                });
    }

    private void initClick(){
        answerAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                intent.putExtra("isPractice", false);
                startActivity(intent);
            }
        });
    }

}
