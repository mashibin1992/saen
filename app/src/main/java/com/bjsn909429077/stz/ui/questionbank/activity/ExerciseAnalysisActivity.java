package com.bjsn909429077.stz.ui.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.SubjectAnalysisBean;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;
import com.bjsn909429077.stz.bean.lianXiSubjectAnalysisBean;
import com.bjsn909429077.stz.ui.questionbank.ExerciseAnalysisFragment;
import com.bjsn909429077.stz.widget.FragmentPagerAdapterCompat;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.CommonTitleView;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ExerciseAnalysisActivity extends BaseActivity {
    private CommonTitleView commonTitleView;
    private TextView exercise_analysis_type;
    private int subjectId;
    private int nodeId;
    private int nodeType;
    private int jxType;
    private int testPaperId;
    private boolean isPractice;
    private String from;
    private TextView exercise_analysis_jd;
    private ViewPager exercise_analysis_vp;
    private FragmentPagerAdapterCompat fragmentPagerAdapterCompat;
    private List<lianXiSubjectAnalysisBean.DataBean> data;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_exercise_analysis;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(com.jiangjun.library.R.id.common_title);
        exercise_analysis_vp = findViewById(R.id.exercise_analysis_vp);
        exercise_analysis_jd = findViewById(R.id.exercise_analysis_jd);
        exercise_analysis_type = findViewById(R.id.exercise_analysis_type);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if ("ExerciseCollectionActivity".equals(from)) {//单题解析
            subjectId = intent.getIntExtra("subjectId", 0);
        } else {
            isPractice = intent.getBooleanExtra("isPractice", false);
            if (isPractice) {//练习类
                ////节id
                nodeId = intent.getIntExtra("nodeId", -1);
                //类型 1.章节 2.专项
                nodeType = intent.getIntExtra("nodeType", -1);
            } else {//试卷类
                testPaperId = intent.getIntExtra("testPaperId", -1);
            }
            //类型 1.错题解析 2.全部解析
            jxType = intent.getIntExtra("jxType", -1);
        }
        exerciseAnalysis();
        String title = intent.getStringExtra("title");
        commonTitleView.setTitle(TextUtil.isEmpty(title) ? "错题解析" : title);
    }

    /**
     * 初始化vp
     */
    private void initViewPager() {
        exercise_analysis_vp.setOffscreenPageLimit(100);
        fragmentPagerAdapterCompat = new FragmentPagerAdapterCompat(getSupportFragmentManager()) {

            private ExerciseAnalysisFragment exerciseAnalysisFragment;

            @Override
            public Fragment getItem(int position) {
                lianXiSubjectAnalysisBean.DataBean dataBean = data.get(position);
                exerciseAnalysisFragment = ExerciseAnalysisFragment.newInstance(dataBean, position);
                return exerciseAnalysisFragment;
            }

            @Override
            public int getCount() {
                return data.size();
            }
        };
        exercise_analysis_vp.setAdapter(fragmentPagerAdapterCompat);
        exercise_analysis_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                initView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 切换下一题时，显示的变化
     *
     * @param position
     */
    private void initView(int position) {
        if (data == null)
            return;
        int number = position + 1;
        exercise_analysis_jd.setText(number + "/" + data.size());
        if (data.get(position).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice))
            exercise_analysis_type.setText("单选题");
        else if (data.get(position).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice))
            exercise_analysis_type.setText("多选题");
        else if (data.get(position).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False))
            exercise_analysis_type.setText("判断题");
        else if (data.get(position).getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA))
            exercise_analysis_type.setText("简答题");
        else if (data.get(position).getClassify().equals(SubjectQuestionBean.TYPE_ZONG_HE))
            exercise_analysis_type.setText("综合题");
    }

    /**
     * 网络请求
     */
    private void exerciseAnalysis() {
        HashMap<String, Integer> map = new HashMap<>();
        String urlStr = "";
        if ("ExerciseCollectionActivity".equals(from)) {
            map.put("subjectId", subjectId);
            urlStr = BaseUrl.subjectAnalysis;
            NovateUtils.getInstance().Post(mContext, urlStr, map, true,
                    new NovateUtils.setRequestReturn<SubjectAnalysisBean>() {

                        @Override
                        public void onError(Throwable throwable) {
                            ToastUtils.Toast(ExerciseAnalysisActivity.this, throwable.getMessage());
                        }

                        @Override
                        public void onSuccee(SubjectAnalysisBean response) {
                            if (response!=null&&response.getData()!=null){
                                SubjectAnalysisBean.DataBean dataBean = response.getData();
                                lianXiSubjectAnalysisBean.DataBean  lianXiBean=new  lianXiSubjectAnalysisBean.DataBean();
                                lianXiBean.setAccuracy(dataBean.getAccuracy());
                                lianXiBean.setAnalysisText(dataBean.getAnalysisText());
                                lianXiBean.setAnalysisVideo(dataBean.getAnalysisVideo());
                                lianXiBean.setAnswer(dataBean.getAnswer());
                                List<lianXiSubjectAnalysisBean.DataBean.AnswerListBean> answerListBeans=new ArrayList<>();
                                for (int i = 0; i < dataBean.getAnswerList().size(); i++) {
                                    lianXiSubjectAnalysisBean.DataBean.AnswerListBean answerListBean=new lianXiSubjectAnalysisBean.DataBean.AnswerListBean();
                                    answerListBean.setAnswerId(dataBean.getAnswerList().get(i).getAnswerId());
                                    answerListBean.setAnswerName(dataBean.getAnswerList().get(i).getAnswerName());
                                    answerListBean.setIsRight(dataBean.getAnswerList().get(i).getIsRight());
                                    answerListBean.setIndexes(dataBean.getAnswerList().get(i).getIndexes());
                                    answerListBean.setSubjectId(dataBean.getAnswerList().get(i).getSubjectId());
                                    answerListBean.setType(dataBean.getAnswerList().get(i).getType());

                                    answerListBeans.add(answerListBean);
                                }
                                lianXiBean.setAnswerList(answerListBeans);
                                lianXiBean.setClassify(dataBean.getClassify());
                                lianXiBean.setCountNumber(dataBean.getCountNumber());
                                lianXiBean.setDoneAnswer(dataBean.getDoneAnswer());
                                lianXiBean.setDoneAnswerIds(dataBean.getDoneAnswerIds());
                                lianXiBean.setErrorNumber(dataBean.getErrorNumber());
                                lianXiBean.setIsCollect(dataBean.getIsCollect());
                                //lianXiBean.setIsSign(dataBean.getIss);//收藏
                                lianXiBean.setKnowledgeIds(dataBean.getKnowledgeIds());
                                List<lianXiSubjectAnalysisBean.DataBean.KnowledgeListBean> knowledgeListBeans=new ArrayList<>();
                                for (int i = 0; i < dataBean.getKnowledgeList().size(); i++) {
                                    lianXiSubjectAnalysisBean.DataBean.KnowledgeListBean knowledgeListBean=new lianXiSubjectAnalysisBean.DataBean.KnowledgeListBean();
                                    knowledgeListBean.setKnowledgeId(dataBean.getKnowledgeList().get(i).getKnowledgeId());
                                    knowledgeListBean.setKnowledgeName(dataBean.getKnowledgeList().get(i).getKnowledgeName());
                                    knowledgeListBeans.add(knowledgeListBean);
                                }
                                lianXiBean.setKnowledgeList(knowledgeListBeans);
                                lianXiBean.setRightNumber(dataBean.getRightNumber());
                                lianXiBean.setSubjectChapterId(dataBean.getSubjectChapterId());
                                lianXiBean.setSubjectChapterName(dataBean.getSubjectChapterName());
                                lianXiBean.setSubjectId(dataBean.getSubjectId());
                                lianXiBean.setSubjectFirstTypeId(dataBean.getSubjectFirstTypeId());
                                lianXiBean.setSubjectFirstTypeName(dataBean.getSubjectFirstTypeName());
                                lianXiBean.setSubjectNodeId(dataBean.getSubjectNodeId());
                                lianXiBean.setSubjectNodeName(dataBean.getSubjectNodeName());
                                lianXiBean.setSubjectSecondTypeId(dataBean.getSubjectSecondTypeId());
                                lianXiBean.setSubjectSecondTypeName(dataBean.getSubjectSecondTypeName());
                                lianXiBean.setSubjectTitle(dataBean.getSubjectTitle());
                                lianXiBean.setTotalAccuracy(dataBean.getTotalAccuracy());
                                data=new ArrayList<>();
                                data.add(lianXiBean);
                            }
                            initViewPager();
                            //标题赋值
                            if (response != null && response.getData() != null) {
                                if (data.get(0).getClassify().equals("1"))
                                    exercise_analysis_type.setText("单选题");
                                else if (data.get(0).getClassify().equals("2"))
                                    exercise_analysis_type.setText("多选题");
                                else if (data.get(0).getClassify().equals("4"))
                                    exercise_analysis_type.setText("简答题");
                                else if (data.get(0).getClassify().equals("5"))
                                    exercise_analysis_type.setText("综合题");
                            }
                            exercise_analysis_jd.setText("1/" + data.size());
                        }
                    });
        } else {
            if (isPractice) {//练习类
                map.put("nodeId", nodeId);
                map.put("nodeType", nodeType);
                urlStr = BaseUrl.subjectAnalysisList;
            } else {
                map.put("testPaperId", testPaperId);
                urlStr = BaseUrl.testSubjectAnalysisList;
            }
            map.put("type", jxType);
            NovateUtils.getInstance().Post(mContext, urlStr, map, true,
                    new NovateUtils.setRequestReturn<lianXiSubjectAnalysisBean>() {

                        @Override
                        public void onError(Throwable throwable) {
                            ToastUtils.Toast(ExerciseAnalysisActivity.this, throwable.getMessage());
                        }

                        @Override
                        public void onSuccee(lianXiSubjectAnalysisBean response) {
                            if (response != null && response.getData() != null) {
                                data = response.getData();
                                initViewPager();
                                //标题赋值
                                if (response != null && response.getData() != null) {
                                    if (response.getData().get(0).getClassify().equals("1"))
                                        exercise_analysis_type.setText("单选题");
                                    else if (response.getData().get(0).getClassify().equals("2"))
                                        exercise_analysis_type.setText("多选题");
                                    else if (response.getData().get(0).getClassify().equals("4"))
                                        exercise_analysis_type.setText("简答题");
                                    else if (response.getData().get(0).getClassify().equals("5"))
                                        exercise_analysis_type.setText("综合题");
                                }
                                exercise_analysis_jd.setText("1/" + data.size());
                            }
                        }
                    });
        }

    }

}