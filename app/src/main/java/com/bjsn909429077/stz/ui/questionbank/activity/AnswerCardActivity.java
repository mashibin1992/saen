package com.bjsn909429077.stz.ui.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.AnswerCardAdapter;
import com.bjsn909429077.stz.bean.AnswerCardFirstBean;
import com.bjsn909429077.stz.bean.AnswerCardSecondBean;
import com.bjsn909429077.stz.bean.ShiJuanTestBean;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.AppManager;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerCardActivity extends BaseActivity {

    private RecyclerView answer_card_rv;
    private AnswerCardAdapter answerCardAdapter;
    private TextView answer_card_jj;
    private int nodeId;
    private int nodeType;
    private SubjectQuestionBean.DataBean dataBean;
    private ShiJuanTestBean.DataBean shiJuanData;
    private int testPaperId;
    private boolean isPractice;
    private TextView yida_num;
    private TextView dacuo_num;
    private TextView weida_num;
    private TextView biaoji_num;
    private ImageView dacuo_iv;
    private int yiDa = 0;
    private int weiDa = 0;
    private int biaoJi = 0;
    //本地答错的集合
    public static ArrayList<SubjectQuestionBean.DataBean.PaperChapterSubjectListBean> cuoTiList = new ArrayList<>();

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_answer_card;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        commonTitleView.setTitle("答题卡");
        answer_card_rv = findViewById(R.id.answer_card_rv);
        answer_card_jj = findViewById(R.id.answer_card_jj);
        yida_num = findViewById(R.id.yida_num);
        dacuo_num = findViewById(R.id.dacuo_num);
        dacuo_iv = findViewById(R.id.dacuo_iv);
        weida_num = findViewById(R.id.weida_num);
        biaoji_num = findViewById(R.id.biaoji_num);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        isPractice = intent.getBooleanExtra("isPractice", true);
        //练习类
        if (isPractice) {
            //节id
            nodeId = intent.getIntExtra("nodeId", 0);
            //类型 1.章节 2.专项
            nodeType = intent.getIntExtra("nodeType", 0);
            dataBean = (SubjectQuestionBean.DataBean) intent.getSerializableExtra("subjectQuestionBean");
            for (int i = 0; i < dataBean.getPaperChapterSubjectList().size(); i++) {
                if (!TextUtil.isEmpty(dataBean.getPaperChapterSubjectList().get(i).getDoneAnswer()) ||
                        !TextUtil.isEmpty(dataBean.getPaperChapterSubjectList().get(i).getDoneAnswerIds()))
                    yiDa++;
                if (dataBean.getPaperChapterSubjectList().get(i).getIsSign().equals("1"))//是否标记 1.是 2.否
                    biaoJi++;
                //答错只判断客观题
                if (dataBean.getPaperChapterSubjectList().get(i).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                        dataBean.getPaperChapterSubjectList().get(i).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False) ||
                        dataBean.getPaperChapterSubjectList().get(i).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {
                    if (dataBean.getPaperChapterSubjectList().get(i).isClickJX()
                            && !TextUtil.isEmpty(dataBean.getPaperChapterSubjectList().get(i).getDoneAnswerIds())) {
                        String doAnswers = dataBean.getPaperChapterSubjectList().get(i).getDoneAnswerIds();
                        String rightAnswers = "";
                        for (int j = 0; j < dataBean.getPaperChapterSubjectList().get(i).getAnswerList().size(); j++) {
                            if (dataBean.getPaperChapterSubjectList().get(i).getAnswerList().get(j).getIsRight() == 1) {//0不正确；1：正确
                                rightAnswers += dataBean.getPaperChapterSubjectList().get(i).getAnswerList().get(j).getAnswerId() + ",";
                            }
                        }
                        if (!doAnswers.equals(rightAnswers.substring(0, doAnswers.length() - 1))) {
                            cuoTiList.add(dataBean.getPaperChapterSubjectList().get(i));
                        }
                    }
                }
            }
            weiDa = dataBean.getPaperChapterSubjectList().size() - yiDa;
            dacuo_num.setText("答错" + cuoTiList.size() + "道");
        } else {//试卷类
            ////试卷ID
            testPaperId = intent.getIntExtra("testPaperId", 0);
            shiJuanData = (ShiJuanTestBean.DataBean) intent.getSerializableExtra("shiJuanTestBean");
            dacuo_iv.setVisibility(View.GONE);
            dacuo_num.setVisibility(View.GONE);
            for (int i = 0; i < shiJuanData.getPaperSubjectList().size(); i++) {
                if ((!TextUtil.isEmpty(shiJuanData.getPaperSubjectList().get(i).getDoneAnswer()) && shiJuanData.getPaperSubjectList().get(i).getDoneAnswer() != null) ||
                        (!TextUtil.isEmpty(shiJuanData.getPaperSubjectList().get(i).getDoneAnswerIds()) && shiJuanData.getPaperSubjectList().get(i).getDoneAnswerIds() != null))
                    yiDa++;
                if (shiJuanData.getPaperSubjectList().get(i).getIsSign().equals("1"))//是否标记 1.是 2.否
                    biaoJi++;
            }
            weiDa = shiJuanData.getPaperSubjectList().size() - yiDa;
        }
        answer_card_rv.setLayoutManager(new GridLayoutManager(this, 7));
        answerCardAdapter = new AnswerCardAdapter();
        answer_card_rv.setAdapter(answerCardAdapter);
        answerCardAdapter.setList(getEntity());
        yida_num.setText("已答题" + yiDa + "道");
        biaoji_num.setText("标记" + biaoJi + "道");
        weida_num.setText("未答" + weiDa + "道");
        initClick();
    }

    private void initClick() {
        answer_card_jj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswerCardActivity.this, AnswerReportActivity.class);
                if (isPractice) {
                    intent.putExtra("isPractice", true);
                    intent.putExtra("nodeId", nodeId);
                    intent.putExtra("nodeType", nodeType);
                } else {
                    intent.putExtra("isPractice", false);
                    intent.putExtra("testPaperId", testPaperId);
                }
                startActivity(intent);
                AppManager.getAppManager().finishActivity(QuestionActivity.class);
                finish();
            }
        });
    }

    private List<BaseNode> getEntity() {
        List<BaseNode> list = new ArrayList<>();
        List<BaseNode> secondNodeListDan = new ArrayList<>();
        List<BaseNode> secondNodeListDuo = new ArrayList<>();
        List<BaseNode> secondNodeListPan = new ArrayList<>();
        List<BaseNode> secondNodeListJian = new ArrayList<>();
        List<BaseNode> secondNodeListZong = new ArrayList<>();
        if (isPractice) {//练习类
            for (int i = 0; i < dataBean.getPaperChapterSubjectList().size(); i++) {
                SubjectQuestionBean.DataBean.PaperChapterSubjectListBean bean = dataBean.getPaperChapterSubjectList().get(i);
                boolean zuoda = false;
                if (!TextUtil.isEmpty(bean.getDoneAnswer()) || !TextUtil.isEmpty(bean.getDoneAnswerIds())) {
                    zuoda = true;
                }
                if (bean.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListDan.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListDan.add(secondBean);
                } else if (bean.getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListDuo.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListDuo.add(secondBean);
                } else if (bean.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListPan.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListPan.add(secondBean);
                } else if (bean.getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListJian.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListJian.add(secondBean);
                } else if (bean.getClassify().equals(SubjectQuestionBean.TYPE_ZONG_HE)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListZong.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListZong.add(secondBean);
                }
            }
        } else {//试卷类
            for (int i = 0; i < shiJuanData.getPaperSubjectList().size(); i++) {
                ShiJuanTestBean.DataBean.PaperSubjectListBean bean = shiJuanData.getPaperSubjectList().get(i);
                boolean zuoda = false;
                if (!TextUtil.isEmpty(bean.getDoneAnswer()) || !TextUtil.isEmpty(bean.getDoneAnswerIds())) {
                    zuoda = true;
                }
                if (bean.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListDan.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListDan.add(secondBean);
                } else if (bean.getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListDuo.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListDuo.add(secondBean);
                } else if (bean.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListPan.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListPan.add(secondBean);
                } else if (bean.getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListJian.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListJian.add(secondBean);
                } else if (bean.getClassify().equals(SubjectQuestionBean.TYPE_ZONG_HE)) {
                    AnswerCardSecondBean secondBean = new AnswerCardSecondBean(zuoda, (secondNodeListZong.size() + 1) + "", bean.getIsSign(), bean.getSubjectId());
                    secondNodeListZong.add(secondBean);
                }
            }
        }
        if (secondNodeListDan.size() > 0)
            list.add(new AnswerCardFirstBean(secondNodeListDan, "单选题"));
        if (secondNodeListDuo.size() > 0)
            list.add(new AnswerCardFirstBean(secondNodeListDuo, "多选题"));
        if (secondNodeListPan.size() > 0)
            list.add(new AnswerCardFirstBean(secondNodeListPan, "判断题"));
        if (secondNodeListJian.size() > 0)
            list.add(new AnswerCardFirstBean(secondNodeListJian, "简答题"));
        if (secondNodeListZong.size() > 0)
            list.add(new AnswerCardFirstBean(secondNodeListZong, "综合题"));
        return list;
    }
}