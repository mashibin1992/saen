package com.bjsn909429077.stz.ui.questionbank;

import android.content.Intent;
import android.view.View;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.TypeErrorQuestionAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.TypeErrorQuestionBean;
import com.bjsn909429077.stz.ui.questionbank.activity.QuestionActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyFragment;
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
 * @Date :2021/9/14 22:31
 **/
public class TypeErrorQuestionFragment extends BaseLazyFragment {

    private RecyclerView type_error_question_rv;
    private TypeErrorQuestionAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.type_error_question_fragment;
    }

    @Override
    protected void init() {
        type_error_question_rv = (RecyclerView) findViewById(R.id.type_error_question_rv);
    }

    @Override
    protected void loadData() {
        initnetWork();
    }

    private void initClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                getActivity().startActivity(new Intent(getActivity(), QuestionActivity.class));
            }
        });
    }

    private void initnetWork(){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("secondId", (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1));
        NovateUtils.getInstance().Post(mContext, BaseUrl.errorBookPaper, map, true,
                new NovateUtils.setRequestReturn<TypeErrorQuestionBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(TypeErrorQuestionBean response) {
                        if (response != null && response.getData() != null) {
                            type_error_question_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                            adapter = new TypeErrorQuestionAdapter(R.layout.type_error_question_adapter, response.getData());
                            type_error_question_rv.setAdapter(adapter);
                            initClick();
                        }
                    }
                });
    }

}
