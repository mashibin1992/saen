package com.bjsn909429077.stz.ui.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ExerciseCollectionAdapter;
import com.bjsn909429077.stz.adapter.ExerciseCollectionQuestionTypeAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CommontBean;
import com.bjsn909429077.stz.bean.ExerciseCollectionBean;
import com.bjsn909429077.stz.bean.ExerciseCollectionQuestionTypeBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.CommonTitleView;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ExerciseCollectionActivity extends BaseActivity {

    private RecyclerView exercise_collection_rv;
    private CommonTitleView commonTitleView;
    private ArrayList<ExerciseCollectionQuestionTypeBean> exerciseCollectionQuestionTypeBeans;
    private ExerciseCollectionAdapter exerciseCollectionAdapter;
    private ExerciseCollectionQuestionTypeAdapter exerciseCollectionQuestionTypeAdapter;
    private RecyclerView exercise_collection_question_type_rv;
    private TextView exercise_collection_type;
    private LinearLayout exercise_collection_ll;
    private TextView exercise_collection_question_type_cancel;
    private TextView exercise_collection_question_type_sure;
    private String questionType = "";
    private CheckBox exercise_collection_bottom_cb;
    private ConstraintLayout exercise_collection_bottom_cl;
    private TextView exercise_collection_bottom_del;
    private ArrayList<ExerciseCollectionBean.DataBean> beanArrayList;
    private int classify = 1;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_exercise_collection;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        exercise_collection_rv = findViewById(R.id.exercise_collection_rv);
        exercise_collection_question_type_rv = findViewById(R.id.exercise_collection_question_type_rv);
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        exercise_collection_type = findViewById(R.id.exercise_collection_type);
        exercise_collection_ll = findViewById(R.id.exercise_collection_ll);
        exercise_collection_question_type_cancel = findViewById(R.id.exercise_collection_question_type_cancel);
        exercise_collection_question_type_sure = findViewById(R.id.exercise_collection_question_type_sure);
        //????????????
        exercise_collection_bottom_cl = findViewById(R.id.exercise_collection_bottom_cl);
        exercise_collection_bottom_cb = findViewById(R.id.exercise_collection_bottom_cb);
        exercise_collection_bottom_del = findViewById(R.id.exercise_collection_bottom_del);
    }

    @Override
    protected void initData() {
        commonTitleView.setTitle("??????");
        commonTitleView.setRightString("??????");
        commonTitleView.setRightTextColor(R.color.color_5F7DFF);
        initNetWork();
        questionTypeData();
    }

    /**
     * ????????????
     */
    private void initNetWork() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("classify", classify);//1????????????2????????????3???????????????4????????????5?????????
        map.put("page", 0);
        map.put("pageSize", 10);
        NovateUtils.getInstance().Post(mContext, BaseUrl.collectList, map, true,
                new NovateUtils.setRequestReturn<ExerciseCollectionBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(ExerciseCollectionActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(ExerciseCollectionBean response) {
                        if (response != null && response.getData() != null) {
                            initData(response.getData());
                        }
                    }
                });
    }

    /**
     * ????????????
     */
    private void delCollect(String subjectIds, ArrayList<ExerciseCollectionBean.DataBean> cancelCollectionList) {
        HashMap<String, String> map = new HashMap<>();
        map.put("subjectIds", subjectIds);//???id(?????????????????????)
        NovateUtils.getInstance().Post(mContext, BaseUrl.delCollect, map, true,
                new NovateUtils.setRequestReturn<CommontBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(ExerciseCollectionActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(CommontBean response) {
                        if (response != null) {
                            ToastUtils.Toast(ExerciseCollectionActivity.this, "??????????????????");
                            beanArrayList.removeAll(cancelCollectionList);
                            exerciseCollectionAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * ????????????????????????
     */
    private void initData(List<ExerciseCollectionBean.DataBean> dataBean) {
        beanArrayList = new ArrayList<>();
        for (int i = 0; i < dataBean.size(); i++) {
            dataBean.get(i).setCancelExerciseCollection(false);
            dataBean.get(i).setEditState(false);
        }
        beanArrayList.addAll(dataBean);
        exercise_collection_rv.setLayoutManager(new LinearLayoutManager(this));
        exerciseCollectionAdapter = new ExerciseCollectionAdapter(R.layout.exercise_collection_item, beanArrayList);
        exercise_collection_rv.setAdapter(exerciseCollectionAdapter);
        initOnClick();
    }

    /**
     * ?????????????????????
     */
    private void questionTypeData() {
        exerciseCollectionQuestionTypeBeans = new ArrayList<>();
        ExerciseCollectionQuestionTypeBean exerciseCollectionQuestionTypeBean1 = new ExerciseCollectionQuestionTypeBean("?????????", true, 1);
        ExerciseCollectionQuestionTypeBean exerciseCollectionQuestionTypeBean2 = new ExerciseCollectionQuestionTypeBean("?????????", false, 2);
        ExerciseCollectionQuestionTypeBean exerciseCollectionQuestionTypeBean3 = new ExerciseCollectionQuestionTypeBean("?????????", false, 3);
        ExerciseCollectionQuestionTypeBean exerciseCollectionQuestionTypeBean4 = new ExerciseCollectionQuestionTypeBean("?????????", false, 4);
        ExerciseCollectionQuestionTypeBean exerciseCollectionQuestionTypeBean5 = new ExerciseCollectionQuestionTypeBean("?????????", false, 5);
        exerciseCollectionQuestionTypeBeans.add(exerciseCollectionQuestionTypeBean1);
        exerciseCollectionQuestionTypeBeans.add(exerciseCollectionQuestionTypeBean2);
        exerciseCollectionQuestionTypeBeans.add(exerciseCollectionQuestionTypeBean3);
        exerciseCollectionQuestionTypeBeans.add(exerciseCollectionQuestionTypeBean4);
        exerciseCollectionQuestionTypeBeans.add(exerciseCollectionQuestionTypeBean5);
        exercise_collection_question_type_rv.setLayoutManager(new LinearLayoutManager(this));
        exerciseCollectionQuestionTypeAdapter = new ExerciseCollectionQuestionTypeAdapter(R.layout.exercise_collection_question_type_item, exerciseCollectionQuestionTypeBeans);
        exercise_collection_question_type_rv.setAdapter(exerciseCollectionQuestionTypeAdapter);
    }

    /**
     * ????????????
     */
    private void initOnClick() {
        //???????????????
        exerciseCollectionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                if (commonTitleView.rightText.getText().toString().equals("??????")) {
                    Intent intent = new Intent(ExerciseCollectionActivity.this, ExerciseAnalysisActivity.class);
                    intent.putExtra("from", "ExerciseCollectionActivity");//????????????
                    intent.putExtra("title", "??????");
                    intent.putExtra("subjectId", Integer.parseInt(beanArrayList.get(position).getSubjectId()));
                    startActivity(intent);
                } else {
                    beanArrayList.get(position).setCancelExerciseCollection(!beanArrayList.get(position).isCancelExerciseCollection());
                    exerciseCollectionAdapter.notifyDataSetChanged();
                }
            }
        });
        //????????????
        exerciseCollectionQuestionTypeAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                for (int i = 0; i < exerciseCollectionQuestionTypeBeans.size(); i++) {
                    exerciseCollectionQuestionTypeBeans.get(i).setChecked(false);
                }
                questionType = exerciseCollectionQuestionTypeBeans.get(position).getQuestionType();
                exerciseCollectionQuestionTypeBeans.get(position).setChecked(true);
                exerciseCollectionQuestionTypeAdapter.notifyDataSetChanged();
                classify = exerciseCollectionQuestionTypeBeans.get(position).getClassify();
            }
        });
        exercise_collection_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exercise_collection_ll.getVisibility() == View.VISIBLE)
                    exercise_collection_ll.setVisibility(View.GONE);
                else
                    exercise_collection_ll.setVisibility(View.VISIBLE);
            }
        });
        exercise_collection_question_type_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise_collection_ll.setVisibility(View.GONE);
            }
        });
        exercise_collection_question_type_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise_collection_ll.setVisibility(View.GONE);
                exercise_collection_type.setText(questionType);
                initNetWork();
            }
        });
        //??????/????????????
        commonTitleView.setOnRightClick(new CommonTitleView.OnTitleClickListener() {
            @Override
            public void onClick(View view) {
                if (commonTitleView.rightText.getText().toString().equals("??????")) {
                    commonTitleView.setRightString("??????");
                    for (int i = 0; i < beanArrayList.size(); i++) {
                        beanArrayList.get(i).setEditState(true);
                    }
                    exercise_collection_bottom_cl.setVisibility(View.VISIBLE);
                } else {
                    commonTitleView.setRightString("??????");
                    for (int i = 0; i < beanArrayList.size(); i++) {
                        beanArrayList.get(i).setEditState(false);
                        beanArrayList.get(i).setCancelExerciseCollection(false);
                    }
                    exercise_collection_bottom_cl.setVisibility(View.GONE);
                    exercise_collection_bottom_cb.setChecked(false);
                }
                exerciseCollectionAdapter.notifyDataSetChanged();
            }
        });
        //??????
        exercise_collection_bottom_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (int i = 0; i < beanArrayList.size(); i++) {
                    beanArrayList.get(i).setCancelExerciseCollection(b);
                }
                exerciseCollectionAdapter.notifyDataSetChanged();
            }
        });
        //??????
        exercise_collection_bottom_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //?????????????????????
                ArrayList<ExerciseCollectionBean.DataBean> cancelCollectionList = new ArrayList<>();
                String delCollectionStr = "";
                for (int i = 0; i < beanArrayList.size(); i++) {
                    if (beanArrayList.get(i).isCancelExerciseCollection()) {
                        cancelCollectionList.add(beanArrayList.get(i));
                        delCollectionStr += beanArrayList.get(i).getSubjectId() + ",";
                    }
                }
                if (cancelCollectionList.size() < 1) {
                    ToastUtils.Toast(ExerciseCollectionActivity.this, "?????????????????????");
                } else {
                    delCollect(delCollectionStr.substring(0, delCollectionStr.length() - 1), cancelCollectionList);//???id(?????????????????????)
                }
            }
        });
    }
}