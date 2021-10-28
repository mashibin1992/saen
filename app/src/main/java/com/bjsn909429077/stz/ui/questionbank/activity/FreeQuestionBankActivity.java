package com.bjsn909429077.stz.ui.questionbank.activity;

import android.os.Bundle;
import android.view.View;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.FreeQuestionBankLeftAdapter;
import com.bjsn909429077.stz.adapter.FreeQuestionBankRightAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.FreeQuestionBankBean;
import com.bjsn909429077.stz.utils.FreeQuestionBackUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.CommonTitleView;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FreeQuestionBankActivity extends BaseActivity {

    private CommonTitleView commonTitleView;
    private RecyclerView free_right;
    private RecyclerView free_left;
    private FreeQuestionBankLeftAdapter freeQuestionBankLeftAdapter;
    private FreeQuestionBankRightAdapter freeQuestionBankRightAdapter;
    private FreeQuestionBankBean freeQuestionBankBean;
    private GridLayoutManager gridLayoutManager;
    private List<FreeQuestionBankBean.DataBean.SecondListBean> secondListBean = new ArrayList<>();

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_free_question_bank;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        commonTitleView.setTitle("免费题库");
        free_left = findViewById(R.id.free_left);
        free_right = findViewById(R.id.free_right);
    }

    @Override
    protected void initData() {
        initNetWork();
    }

    /**
     * 左侧rv数据
     */
    private void initLeftData(FreeQuestionBankBean freeQuestionBankBean) {
        for (int i = 0; i < freeQuestionBankBean.getData().size(); i++) {
            if (0 == i)
                freeQuestionBankBean.getData().get(i).setIschecked(true);
            else
                freeQuestionBankBean.getData().get(i).setIschecked(false);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        free_left.setLayoutManager(linearLayoutManager);
        freeQuestionBankLeftAdapter = new FreeQuestionBankLeftAdapter(R.layout.freequestionbankleftitem, freeQuestionBankBean.getData(), this);
        free_left.setAdapter(freeQuestionBankLeftAdapter);
    }

    /**
     * 右侧rv数据
     */
    private void initRightData() {
        gridLayoutManager = new GridLayoutManager(this, 2);
        free_right.setLayoutManager(gridLayoutManager);
        freeQuestionBankRightAdapter = new FreeQuestionBankRightAdapter(R.layout.free_question_bank_right_item, secondListBean, this);
        free_right.setAdapter(freeQuestionBankRightAdapter);
        initClick();
    }

    private void initNetWork() {
        HashMap<String, Integer> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.firstOrSecondLists, map, true,
                new NovateUtils.setRequestReturn<FreeQuestionBankBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(FreeQuestionBankActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(FreeQuestionBankBean response) {
                        if (response != null && response.getData() != null) {
                            freeQuestionBankBean = response;
                            initLeftData(response);
                            for (int i = 0; i < freeQuestionBankBean.getData().get(0).getSecondList().size(); i++) {
                                secondListBean.add(freeQuestionBankBean.getData().get(0).getSecondList().get(i));
                            }
                            initRightData();
                        }
                    }
                });
    }

    private void initClick() {
        //左侧
        freeQuestionBankLeftAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                secondListBean.clear();
                for (int i = 0; i < freeQuestionBankBean.getData().size(); i++) {
                    freeQuestionBankBean.getData().get(i).setIschecked(false);
                }
                freeQuestionBankBean.getData().get(position).setIschecked(true);
                freeQuestionBankLeftAdapter.notifyDataSetChanged();
                secondListBean.addAll(freeQuestionBankBean.getData().get(position).getSecondList());
                if (freeQuestionBankRightAdapter != null)
                    freeQuestionBankRightAdapter.notifyDataSetChanged();
            }
        });
        //右侧
        freeQuestionBankRightAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                SharedPreferencesUtil.saveData(FreeQuestionBankActivity.this,"secondId",secondListBean.get(position).getSecondId());
                FreeQuestionBackUtil.sind();
                finish();
            }
        });
    }

    @Override
    protected void initTitleView() {
        super.initTitleView();
    }
}