package com.bjsn909429077.stz.ui.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.TopicAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.ChangeTextSizeBean;
import com.bjsn909429077.stz.bean.CommontBean;
import com.bjsn909429077.stz.bean.ShiJuanTestBean;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;
import com.bjsn909429077.stz.ui.questionbank.QuestionFragment;
import com.bjsn909429077.stz.ui.questionbank.ZongHeCommitInterface;
import com.bjsn909429077.stz.ui.questionbank.dialogfragment.JJDialogFragment;
import com.bjsn909429077.stz.utils.QuestionToolsUtils;
import com.bjsn909429077.stz.widget.ChangeTextSizeView;
import com.bjsn909429077.stz.widget.FragmentPagerAdapterCompat;
import com.bjsn909429077.stz.widget.QuestionViewPager;
import com.bjsn909429077.stz.widget.SlidingUpPanelLayout;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.AppManager;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class QuestionActivity extends BaseActivity implements QuestionFragment.OnModifyQuestionListener, JJDialogFragment.DismissThis, ZongHeCommitInterface {

    private SlidingUpPanelLayout mLayout;
    private TopicAdapter topicAdapter;
    private RecyclerView recyclerView;
    private ImageView shadowView;
    private QuestionViewPager questionViewPager;
    private ChangeTextSizeView question_jx;
    private ChangeTextSizeView question_jj;
    private ChangeTextSizeView question_dtk;
    private ImageView question_more;
    private ChangeTextSizeView question_js;
    private LinearLayout question_more_ll;
    private LinearLayout dragView;
    private ChangeTextSizeView question_more_jsq;
    private ChangeTextSizeView question_more_small;
    private ChangeTextSizeView question_more_in;
    private ChangeTextSizeView question_more_large;
    private ChangeTextSizeView question_more_automatic_tv;
    private CheckBox question_more_automatic;
    private int prePosition2;
    private int curPosition2;
    private int prePosition;
    private int curPosition;
    private GridLayoutManager gridLayoutManager;
    private ChangeTextSizeView question_type;
    private ChangeTextSizeView question_jd;
    private ArrayList<ChangeTextSizeBean> arrayList;
    private boolean isPractice;
    private ImageView question_collectioned;
    private ImageView question_uncollection;
    private ImageView question_tag;
    private ImageView question_untag;
    private int nodeId;
    private int nodeType;
    private int type;
    private SubjectQuestionBean.DataBean data;
    private ShiJuanTestBean.DataBean shiJuanData;
    private int curPageNum = 0;//当前页面的pageNum
    private int testPaperId;
    private FragmentPagerAdapterCompat fragmentPagerAdapterCompat;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_question;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        shadowView = findViewById(R.id.shadowView);
        questionViewPager = findViewById(R.id.readerViewPager);
        recyclerView = findViewById(R.id.list);
        mLayout = findViewById(R.id.sliding_layout);
        dragView = findViewById(R.id.dragView);
        question_collectioned = findViewById(R.id.question_collectioned);
        question_uncollection = findViewById(R.id.question_uncollection);
        question_tag = findViewById(R.id.question_tag);
        question_untag = findViewById(R.id.question_unTag);
        //小工具
        question_jx = findViewById(R.id.question_jx);
        question_jj = findViewById(R.id.question_jj);
        question_js = findViewById(R.id.question_js);
        question_dtk = findViewById(R.id.question_dtk);
        question_more = findViewById(R.id.question_more);

        question_type = findViewById(R.id.question_type);
        question_jd = findViewById(R.id.question_jd);
        //更多工具里面
        question_more_ll = findViewById(R.id.question_more_ll);
        question_more_jsq = findViewById(R.id.question_more_jsq);
        question_more_small = findViewById(R.id.question_more_small);
        question_more_in = findViewById(R.id.question_more_in);
        question_more_large = findViewById(R.id.question_more_large);
        question_more_automatic_tv = findViewById(R.id.question_more_automatic_tv);
        question_more_automatic = findViewById(R.id.question_more_automatic);
        initOnClick();
        initChangeTextSize();
    }

    private List<SubjectQuestionBean.DataBean.PaperChapterSubjectListBean> paperChapterSubjectList;
    private List<ShiJuanTestBean.DataBean.PaperSubjectListBean> paperSubjectListBeans;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        //章节/专项练习的标记
        isPractice = intent.getBooleanExtra("isPractice", false);
        if (isPractice) {
            //节id
            nodeId = intent.getIntExtra("nodeId", 0);
            //类型 1.章节 2.专项
            nodeType = intent.getIntExtra("nodeType", 0);
            ////类型 1.答题or继续 2.重新答题
            type = intent.getIntExtra("type", 1);
            question_jx.setVisibility(View.VISIBLE);
            //获取练习题目列表
            getQuestionBankList();
        } else {//试卷类的隐藏解析按钮
            question_jx.setVisibility(View.GONE);
            testPaperId = intent.getIntExtra("testPaperId", -1);//试卷ID
            type = intent.getIntExtra("type", -1);//类型 2.重新答题 当完成数等于总数是传2 其他情况不传
            //获取试卷题目列表
            getShiJuanList();
        }
    }

    private void initOnClick() {
        //取消收藏按钮
        question_collectioned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect(false);
            }
        });
        //收藏按钮
        question_uncollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect(true);
            }
        });
        //取消标记
        question_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign(2);
                question_untag.setVisibility(View.VISIBLE);
                question_tag.setVisibility(View.GONE);
            }
        });
        //标记
        question_untag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign(1);
                question_untag.setVisibility(View.GONE);
                question_tag.setVisibility(View.VISIBLE);
            }
        });
        //答题卡
        question_dtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPractice)
                    QuestionToolsUtils.getInstance().daTiKa(QuestionActivity.this, nodeId, nodeType, data, true);
                    //questionToolsUtils.daTiKa(QuestionActivity.this, nodeId, nodeType, data, true);
                else
                    QuestionToolsUtils.getInstance().ShiJuanDaTiKa(QuestionActivity.this, testPaperId, shiJuanData, false);
            }
        });
        //交卷
        question_jj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPractice) {
                    QuestionToolsUtils.getInstance().jiaoJuan(getSupportFragmentManager(), nodeId, nodeType, QuestionActivity.this);
                    if (!paperChapterSubjectList.get(curPosition2).getClassify().equals(SubjectQuestionBean.TYPE_ZONG_HE)){
                        saveSubject(curPosition2);
                    }else {
                        QuestionFragment fragment = (QuestionFragment) fragmentPagerAdapterCompat.getFragment(curPosition2);
                        fragment.jiaoJuanSave();
                    }
                } else {
                    QuestionToolsUtils.getInstance().testJiaoJuan(getSupportFragmentManager(), testPaperId, QuestionActivity.this);
                    if (!paperSubjectListBeans.get(curPosition2).getClassify().equals(SubjectQuestionBean.TYPE_ZONG_HE)){
                        testSaveSubject(curPosition2,"");
                    }else {
                        QuestionFragment fragment = (QuestionFragment) fragmentPagerAdapterCompat.getFragment(curPosition2);
                        fragment.jiaoJuanSave();
                    }
                }
            }
        });
        //只有练习类的试题有解析
        question_jx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionFragment fragment = (QuestionFragment) fragmentPagerAdapterCompat.getFragment(curPosition2);
                fragment.setShowJX(true, data.getPaperChapterSubjectList().get(curPageNum).getClassify());
                if (data != null) {
                    data.getPaperChapterSubjectList().get(curPosition).setClickJX(true);
                }
            }
        });
        //更多
        question_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionToolsUtils.getInstance().more(question_more_ll);
            }
        });
        //更多--计算器
        question_more_jsq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionToolsUtils.getInstance().JiSuanQi(getSupportFragmentManager());
                question_more_ll.setVisibility(View.GONE);
            }
        });
        //更多--自动切换下一题
        question_more_automatic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                QuestionToolsUtils.getInstance().AutomaticNext(b);
                question_more_ll.setVisibility(View.GONE);
            }
        });
        //更多--小字体
        question_more_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionToolsUtils.getInstance().ChangeSize(QuestionActivity.this, 0, arrayList, question_more_small, question_more_in, question_more_large);
                question_more_ll.setVisibility(View.GONE);
            }
        });
        //更多--中字体
        question_more_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionToolsUtils.getInstance().ChangeSize(QuestionActivity.this, 1, arrayList, question_more_small, question_more_in, question_more_large);
                question_more_ll.setVisibility(View.GONE);
            }
        });
        //更多--大字体
        question_more_large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionToolsUtils.getInstance().ChangeSize(QuestionActivity.this, 2, arrayList, question_more_small, question_more_in, question_more_large);
                question_more_ll.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 切换字体大中小的方法
     */
    private void initChangeTextSize() {
        if (arrayList == null)
            arrayList = new ArrayList<>();
        ChangeTextSizeBean changeTextSizeBeanJS = new ChangeTextSizeBean(question_js, 10);
        ChangeTextSizeBean changeTextSizeBeanJX = new ChangeTextSizeBean(question_jx, 10);
        ChangeTextSizeBean changeTextSizeBeanDTK = new ChangeTextSizeBean(question_dtk, 10);
        ChangeTextSizeBean changeTextSizeBeanJJ = new ChangeTextSizeBean(question_jj, 10);
        ChangeTextSizeBean changeTextSizeBeanTYPE = new ChangeTextSizeBean(question_type, 15);
        ChangeTextSizeBean changeTextSizeBeanJD = new ChangeTextSizeBean(question_jd, 15);
        ChangeTextSizeBean changeTextSizeBeanJSQ = new ChangeTextSizeBean(question_more_jsq, 15);
        ChangeTextSizeBean changeTextSizeBeanSMALL = new ChangeTextSizeBean(question_more_small, 15);
        ChangeTextSizeBean changeTextSizeBeanIN = new ChangeTextSizeBean(question_more_in, 15);
        ChangeTextSizeBean changeTextSizeBeanLARGE = new ChangeTextSizeBean(question_more_large, 15);
        ChangeTextSizeBean changeTextSizeBeanAUTOMATICTV = new ChangeTextSizeBean(question_more_automatic_tv, 15);
        arrayList.add(changeTextSizeBeanJS);
        arrayList.add(changeTextSizeBeanJX);
        arrayList.add(changeTextSizeBeanDTK);
        arrayList.add(changeTextSizeBeanJJ);
        arrayList.add(changeTextSizeBeanTYPE);
        arrayList.add(changeTextSizeBeanJD);
        arrayList.add(changeTextSizeBeanJSQ);
        arrayList.add(changeTextSizeBeanSMALL);
        arrayList.add(changeTextSizeBeanIN);
        arrayList.add(changeTextSizeBeanLARGE);
        arrayList.add(changeTextSizeBeanAUTOMATICTV);
    }

    private void initReadViewPager() {
        questionViewPager.setOffscreenPageLimit(100);
        fragmentPagerAdapterCompat = new FragmentPagerAdapterCompat(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (paperChapterSubjectList != null) {
                    SubjectQuestionBean.DataBean.PaperChapterSubjectListBean subDataBean = paperChapterSubjectList.get(position);
                    QuestionFragment fragment = QuestionFragment.newInstance(subDataBean, position, nodeType, testPaperId);
                    fragment.setZongHeCommitInterface(QuestionActivity.this);
                    fragment.setModifyQuestionListener(QuestionActivity.this);
                    return fragment;
                } else {
                    ShiJuanTestBean.DataBean.PaperSubjectListBean paperSubjectListBean = paperSubjectListBeans.get(position);
                    QuestionFragment fragment = QuestionFragment.newInstance(paperSubjectListBean, position, nodeType, testPaperId);
                    fragment.setZongHeCommitInterface(QuestionActivity.this);
                    fragment.setModifyQuestionListener(QuestionActivity.this);
                    return fragment;
                }
            }

            @Override
            public int getCount() {
                if (paperChapterSubjectList != null)
                    return paperChapterSubjectList.size();
                else
                    return paperSubjectListBeans.size();
            }
        };
        questionViewPager.setAdapter(fragmentPagerAdapterCompat);
        questionViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                shadowView.setTranslationX(questionViewPager.getWidth() - positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPosition2 = position;
                topicAdapter.notifyCurPosition(curPosition2);
                topicAdapter.notifyPrePosition(prePosition2);
                prePosition2 = curPosition2;
                MoveToPosition();
                initView(position);
                if (curPageNum > position) {//是切换上一题
                    if (data != null)
                        saveSubject(position);
                    else
                        testSaveSubject(position, "");
                } else {//是切换下一题
                    if (data != null)
                        saveSubject(position - 1);
                    else
                        testSaveSubject(position - 1, "");
                }
                curPageNum = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 切换下一题时，显示的变化
     *
     * @param position 完成题目数量
     */
    private void initView(int position) {
        int number = position + 1;
        question_jd.setText(number + "/" + (data != null ? data.getPaperChapterSubjectList().size() : shiJuanData.getPaperSubjectList().size()));
        if (data != null ? data.getPaperChapterSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice)
                : shiJuanData.getPaperSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice))
            question_type.setText("单选题");
        else if (data != null ? data.getPaperChapterSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)
                : shiJuanData.getPaperSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice))
            question_type.setText("多选题");
        else if (data != null ? data.getPaperChapterSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)
                : shiJuanData.getPaperSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False))
            question_type.setText("判断题");
        else if (data != null ? data.getPaperChapterSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)
                : shiJuanData.getPaperSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA))
            question_type.setText("简答题");
        else if (data != null ? data.getPaperChapterSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_ZONG_HE)
                : shiJuanData.getPaperSubjectList().get(position).getClassify().equals(SubjectQuestionBean.TYPE_ZONG_HE))
            question_type.setText("综合题");
        question_uncollection.setVisibility(data != null ? data.getPaperChapterSubjectList().get(position).getIsCollect().equals("1") ? View.GONE : View.VISIBLE
                : shiJuanData.getPaperSubjectList().get(position).getIsCollect().equals("1") ? View.GONE : View.VISIBLE);
        question_collectioned.setVisibility(data != null ? data.getPaperChapterSubjectList().get(position).getIsCollect().equals("1") ? View.VISIBLE : View.GONE
                : shiJuanData.getPaperSubjectList().get(position).getIsCollect().equals("1") ? View.VISIBLE : View.GONE);
        question_untag.setVisibility(data != null ? data.getPaperChapterSubjectList().get(position).getIsSign().equals("1") ? View.GONE : View.VISIBLE
                : shiJuanData.getPaperSubjectList().get(position).getIsCollect().equals("1") ? View.GONE : View.VISIBLE);
        question_tag.setVisibility(data != null ? data.getPaperChapterSubjectList().get(position).getIsSign().equals("1") ? View.VISIBLE : View.GONE
                : shiJuanData.getPaperSubjectList().get(position).getIsCollect().equals("1") ? View.VISIBLE : View.GONE);
        if (position != 0) {
            questionViewPager.setCurrentItem(position);
        }
    }

    private void initList() {
        gridLayoutManager = new GridLayoutManager(this, 6);
        topicAdapter = new TopicAdapter(this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(topicAdapter);
        topicAdapter.setOnTopicClickListener(new TopicAdapter.OnTopicClickListener() {
            @Override
            public void onClick(TopicAdapter.TopicViewHolder holder, int position) {
                curPosition = position;
                if (mLayout != null &&
                        (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                questionViewPager.setCurrentItem(position);
                topicAdapter.notifyCurPosition(curPosition);
                topicAdapter.notifyPrePosition(prePosition);
                prePosition = curPosition;
            }
        });
    }

    private void initSlidingUoPanel() {
        int height = getWindowManager().getDefaultDisplay().getHeight();
        SlidingUpPanelLayout.LayoutParams params = new SlidingUpPanelLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (height * 0.8f));
        dragView.setLayoutParams(params);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    MoveToPosition();
                }
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    /**
     * 滚动 recycleView 到当前选择的位置
     */
    private void MoveToPosition() {
        gridLayoutManager.scrollToPositionWithOffset(curPosition2, 0);
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 下一题
     */
    private synchronized void nextQuestion() {
        int currentItem = questionViewPager.getCurrentItem();
        currentItem = currentItem + 1;
        if (currentItem < 0) {
            currentItem = 0;
        }
        questionViewPager.setCurrentItem(currentItem);
    }

    //客观题回调
    @Override
    public void modifyQuestion(int selectId, final int position, boolean checked) {
        //保存这个题目的答案
        if (isPractice) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean dataBeanTemp = paperChapterSubjectList.get(position);
            //未做过的题目 单项选择题选择后直接选择答案后延时进入下一题 ； 多项选择/题目选择后修改的需要自行滑动活着点击下一题
            if (dataBeanTemp.getQuestion_select() == -1 && (dataBeanTemp.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                    dataBeanTemp.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) && QuestionToolsUtils.getInstance().isAutomaticNext) {
                //延时下一题
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (position == curPosition2) {
                            nextQuestion();
                        }
                    }
                }, 500);
            }
            dataBeanTemp.setQuestion_select(selectId);
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean dataBeanTemp = paperSubjectListBeans.get(position);
            //未做过的题目 单项选择题选择后直接选择答案后延时进入下一题 ； 多项选择/题目选择后修改的需要自行滑动活着点击下一题
            if (dataBeanTemp.getQuestion_select() == -1 && (dataBeanTemp.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                    dataBeanTemp.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) && QuestionToolsUtils.getInstance().isAutomaticNext) {
                //延时下一题
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (position == curPosition2) {
                            nextQuestion();
                        }
                    }
                }, 500);
            }
            dataBeanTemp.setQuestion_select(selectId);
        }

    }

    //主观题回调
    @Override
    public void modifyQuestionJianDa(int position, String content) {
        //保存这个题目的答案
        if (isPractice) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean dataBeanTemp = paperChapterSubjectList.get(position);
            dataBeanTemp.setDoneAnswer(content);
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean dataBeanTemp = paperSubjectListBeans.get(position);
            dataBeanTemp.setDoneAnswer(content);
        }
    }

    /**
     * 收藏
     *
     * @param isAddCollect 是否是添加收藏
     */
    private void collect(boolean isAddCollect) {
        String url = "";
        String key = "";
        if (isAddCollect) {
            url = BaseUrl.addCollect;
            key = "subjectId";
        } else {
            url = BaseUrl.cancelCollect;
            key = "subjectIds";//题id(多个用逗号分隔)
        }
        HashMap<String, Integer> map = new HashMap<>();
        if (isPractice) {
            map.put(key, Integer.parseInt(data.getPaperChapterSubjectList().get(curPosition2).getSubjectId()));
        } else {
            map.put(key, Integer.parseInt(shiJuanData.getPaperSubjectList().get(curPosition2).getSubjectId()));
        }
        NovateUtils.getInstance().Post(QuestionActivity.this, url, map, true,
                new NovateUtils.setRequestReturn<CommontBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(QuestionActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(CommontBean response) {
                        if (response != null) {
                            ToastUtils.Toast(QuestionActivity.this, isAddCollect ? "添加收藏成功" : "取消收藏成功");
                            question_collectioned.setVisibility(isAddCollect ? View.VISIBLE : View.GONE);
                            question_uncollection.setVisibility(isAddCollect ? View.GONE : View.VISIBLE);
                        }
                    }
                });
    }

    /**
     * 标记
     *
     * @param isAddSign 是否是添加标记
     */
    private void sign(int isAddSign) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("type", isAddSign);//类型 1.添加 2.删除
        if (isPractice) {
            map.put("nodeId", nodeId);//节id
            map.put("subjectId", Integer.parseInt(data.getPaperChapterSubjectList().get(curPosition2).getSubjectId()));
        } else {
            map.put("testPaperId", testPaperId);//试卷ID
            map.put("subjectId", Integer.parseInt(shiJuanData.getPaperSubjectList().get(curPosition2).getSubjectId()));
        }
        NovateUtils.getInstance().Post(QuestionActivity.this, BaseUrl.addSign, map, true,
                new NovateUtils.setRequestReturn<CommontBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(QuestionActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(CommontBean response) {
                        if (response != null) {
                            ToastUtils.Toast(QuestionActivity.this, isAddSign == 1 ? "添加标记成功" : "取消标记成功");
                        }
                    }
                });
    }

    /**
     * 获试卷列表
     */
    private void getShiJuanList() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("testPaperId", testPaperId);//试卷ID
        if (2 == type) {
            map.put("type", type);//类型 2.重新答题 当完成数等于总数是传2 其他情况不传
        }
        NovateUtils.getInstance().Post(QuestionActivity.this, BaseUrl.subjectList, map, true,
                new NovateUtils.setRequestReturn<ShiJuanTestBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(QuestionActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(ShiJuanTestBean response) {
                        if (response != null && response.getData() != null) {
                            shiJuanData = response.getData();
                            //答题进度
                            question_jd.setText(shiJuanData.getCompleteNumber() + "/" + shiJuanData.getPaperSubjectList().size());
                            //累计时长
                            QuestionToolsUtils.getInstance().jiShiQi(question_js, isPractice, shiJuanData.getTimeLength());
                            //倒计时该题目时间
                            QuestionToolsUtils.getInstance().thisQuestionUseTimeDJS();
                            //题目类别：1：单选；2：多选；3：判断题；4：简答；5：综合
                            paperSubjectListBeans = shiJuanData.getPaperSubjectList();
                            for (int i = 0; i < paperSubjectListBeans.size(); i++) {
                                //添加是否做过的标记
                                paperSubjectListBeans.get(i).setQuestion_select(-1);
                                //把我的答案数据赋值给网络数据
                                //dan,duo,判断
                                if (paperSubjectListBeans.get(i).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                                        paperSubjectListBeans.get(i).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice) ||
                                        paperSubjectListBeans.get(i).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {
                                    if (!TextUtil.isEmpty(paperSubjectListBeans.get(i).getDoneAnswerIds())) {
                                        String[] doneAnswerIds = paperSubjectListBeans.get(i).getDoneAnswerIds().split(",");//回答的答案选项ID 多个逗号分割
                                        List<ShiJuanTestBean.DataBean.PaperSubjectListBean.AnswerListBean> answerList = paperSubjectListBeans.get(i).getAnswerList();
                                        for (int j = 0; j < answerList.size(); j++) {
                                            for (int k = 0; k < doneAnswerIds.length; k++) {
                                                answerList.get(j).setMyselfIsChoose(answerList.get(j).getAnswerId().equals(doneAnswerIds[k]));
                                            }
                                        }
                                    }
                                }
                            }
                            initSlidingUoPanel();
                            initList();
                            if (topicAdapter != null) {
                                topicAdapter.setDataNum(paperSubjectListBeans.size());
                                topicAdapter.setDatas(paperSubjectListBeans);
                            }
                            initReadViewPager();
                            //跳转到之前答题的页面
                            if (!TextUtil.isEmpty(response.getData().getCompleteNumber()))
                                initView(Integer.parseInt(response.getData().getCompleteNumber()));
                            else
                                initView(0);
                        }
                    }
                });
    }

    /**
     * 获取题列表
     */
    private void getQuestionBankList() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("nodeId", nodeId);
        map.put("nodeType", nodeType);//类型 1.章节 2.专项
        map.put("type", type);//类型 1.答题or继续 2.重新答题
        NovateUtils.getInstance().Post(QuestionActivity.this, BaseUrl.getQuestionBankList, map, true,
                new NovateUtils.setRequestReturn<SubjectQuestionBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(QuestionActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(SubjectQuestionBean response) {
                        if (response != null && response.getData() != null&&response.getData().getPaperChapterSubjectList()!=null) {
                            data = response.getData();
                            //答题进度
                            question_jd.setText(response.getData().getCompleteNumber() + "/" + response.getData().getPaperChapterSubjectList().size());
                            //累计时长
                            QuestionToolsUtils.getInstance().jiShiQi(question_js, isPractice, response.getData().getTimeLength());
                            //倒计时该题目时间
                            QuestionToolsUtils.getInstance().thisQuestionUseTimeDJS();
                            //题目类别：1：单选；2：多选；3：判断题；4：简答；5：综合
                            paperChapterSubjectList = response.getData().getPaperChapterSubjectList();
                            for (int i = 0; i < paperChapterSubjectList.size(); i++) {
                                //添加是否做过的标记
                                paperChapterSubjectList.get(i).setQuestion_select(-1);
                                //把我的答案数据赋值给网络数据
                                //dan,duo,判断
                                if (paperChapterSubjectList.get(i).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                                        paperChapterSubjectList.get(i).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice) ||
                                        paperChapterSubjectList.get(i).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {
                                    if (!TextUtil.isEmpty(paperChapterSubjectList.get(i).getDoneAnswerIds())) {
                                        String[] doneAnswerIds = paperChapterSubjectList.get(i).getDoneAnswerIds().split(",");//回答的答案选项ID 多个逗号分割
                                        List<SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.AnswerListBean> answerList = paperChapterSubjectList.get(i).getAnswerList();
                                        for (int j = 0; j < answerList.size(); j++) {
                                            for (int k = 0; k < doneAnswerIds.length; k++) {
                                                answerList.get(j).setMyselfIsChoose(answerList.get(j).getAnswerId().equals(doneAnswerIds[k]));
                                            }
                                        }
                                    }
                                }
                            }
                            initSlidingUoPanel();
                            initList();
                            if (topicAdapter != null) {
                                topicAdapter.setDataNum(paperChapterSubjectList.size());
                                topicAdapter.setDatas(paperChapterSubjectList);
                            }
                            initReadViewPager();
                            //跳转到之前答题的页面
                            if (!TextUtil.isEmpty(response.getData().getCompleteNumber()))
                                initView(Integer.parseInt(response.getData().getCompleteNumber()));
                            else
                                initView(0);
                        }
                    }
                });
    }

    /**
     * 练习类切换下一题的时候，保存答案
     */
    private void saveSubject(int saveQuestionPotion) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("answerTimeLength", QuestionToolsUtils.getInstance().thisQuestionUseTime());//这道题所用的时间
        map.put("nodeType", nodeType);//类型 1.章节 2.专项
        map.put("subjectId", data.getPaperChapterSubjectList().get(saveQuestionPotion).getSubjectId());
        if (data.getPaperChapterSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False) ||
                data.getPaperChapterSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                data.getPaperChapterSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {
            String answerId = "";
            for (int i = 0; i < data.getPaperChapterSubjectList().get(saveQuestionPotion).getAnswerList().size(); i++) {
                if (data.getPaperChapterSubjectList().get(saveQuestionPotion).getAnswerList().get(i).isMyselfIsChoose())
                    answerId += data.getPaperChapterSubjectList().get(saveQuestionPotion).getAnswerList().get(i).getAnswerId() + ",";
            }
            if (TextUtil.isEmpty(answerId))//没有作答不提交答案
                return;
            map.put("answerId", answerId.substring(0, answerId.length() - 1));
        } else if (data.getPaperChapterSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)) {//简答
            String doneAnswer = data.getPaperChapterSubjectList().get(saveQuestionPotion).getDoneAnswer();
            map.put("answerText", doneAnswer);
            if (TextUtil.isEmpty(doneAnswer))//没有作答不提交答案
                return;
        }
        NovateUtils.getInstance().Post(QuestionActivity.this, BaseUrl.saveSubject, map, true,
                new NovateUtils.setRequestReturn<CommontBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(QuestionActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(CommontBean response) {
                        if (response != null) {
                            //从新倒计时该题目时间
                            QuestionToolsUtils.getInstance().thisQuestionUseTimeDJS();
                        }
                    }
                });
    }

    /**
     * 试卷类切换下一题的时候，保存答案
     */
    private void testSaveSubject(int saveQuestionPotion, String fraction) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("answerTimeLength", QuestionToolsUtils.getInstance().thisQuestionUseTime());//这道题所用的时间
        map.put("testPaperId", testPaperId);//试卷ID
        map.put("subjectId", shiJuanData.getPaperSubjectList().get(saveQuestionPotion).getSubjectId());
        if (!TextUtil.isEmpty(fraction)) {
                map.put("score", Integer.parseInt(fraction));//自评分
        } else {
            if (shiJuanData.getPaperSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False) ||
                    shiJuanData.getPaperSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                    shiJuanData.getPaperSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {
                String answerId = "";
                for (int i = 0; i < shiJuanData.getPaperSubjectList().get(saveQuestionPotion).getAnswerList().size(); i++) {
                    if (shiJuanData.getPaperSubjectList().get(saveQuestionPotion).getAnswerList().get(i).isMyselfIsChoose())
                        answerId += shiJuanData.getPaperSubjectList().get(saveQuestionPotion).getAnswerList().get(i).getAnswerId() + ",";
                }
                if (TextUtil.isEmpty(answerId))//没有作答不提交答案
                    return;
                map.put("answerId", answerId.substring(0, answerId.length() - 1));
            } else {
                String doneAnswer = shiJuanData.getPaperSubjectList().get(saveQuestionPotion).getDoneAnswer();
                if (TextUtil.isEmpty(doneAnswer))//没有作答不提交答案
                    return;
                map.put("answerText", doneAnswer);
            }
        }
        NovateUtils.getInstance().Post(QuestionActivity.this, BaseUrl.TestSaveSubject, map, true,
                new NovateUtils.setRequestReturn<CommontBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(QuestionActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(CommontBean response) {
                        if (response != null) {
                            //从新倒计时该题目时间
                            QuestionToolsUtils.getInstance().thisQuestionUseTimeDJS();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QuestionToolsUtils.getInstance().finsh();
    }

    @Override
    public void stopDo() {
        finish();
    }

    @Override
    public void jiaoJuan() {
        Intent intent = new Intent(QuestionActivity.this, AnswerReportActivity.class);
        if (isPractice) {
            intent.putExtra("isPractice", true);
            intent.putExtra("nodeId", nodeId);
            intent.putExtra("nodeType", nodeType);
        } else {
            intent.putExtra("isPractice", false);
            intent.putExtra("testPaperId", testPaperId);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void OnCommitClick(String fraction) {
        testSaveSubject(curPageNum, fraction);
    }
    /*private JiaoJuanSave jiaoJuanSave;
    public void setjiaoJuanSave(JiaoJuanSave jiaoJuanSave) {
        this.jiaoJuanSave = jiaoJuanSave;
    }

    public interface JiaoJuanSave{
        void baoCun();
    }*/
}