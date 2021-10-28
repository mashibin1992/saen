package com.bjsn909429077.stz.ui.questionbank;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ExerciseAnalysisJXAdapter;
import com.bjsn909429077.stz.adapter.OptionAdapter;
import com.bjsn909429077.stz.adapter.ShiJuanOptionAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CommontBean;
import com.bjsn909429077.stz.bean.ExerciseAnalysisJXBean;
import com.bjsn909429077.stz.bean.ShiJuanTestBean;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;
import com.bjsn909429077.stz.ui.questionbank.activity.QuestionActivity;
import com.bjsn909429077.stz.utils.QuestionToolsUtils;
import com.bjsn909429077.stz.utils.UnitConversionUtil;
import com.bjsn909429077.stz.widget.WrapContentHeightViewPager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.utils.ImageLoaderUtils;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.timeselector.Utils.TextUtil;
import com.tamic.novate.Throwable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/7 14:56
 **/
public class QuestionFragment extends Fragment implements View.OnClickListener, ZongHeFragment.OnCheckQuestionListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private Object object;
    private int tiMuPosition;
    private int nodeType;
    private int testPaperId;
    private View view;
    private OnModifyQuestionListener modifyQuestionListener;
    private RecyclerView question_rv;
    private ExerciseAnalysisJXAdapter exerciseAnalysisJXAdapter;
    private ArrayList<ExerciseAnalysisJXBean> analysisJXBeans = new ArrayList<>();
    private ConstraintLayout jianda_cl;
    private EditText jianda_et;
    private TextView jianda_tv;
    private WrapContentHeightViewPager zong_he_vp;
    private LinearLayout zong_he_ti_num;
    private RecyclerView option_rv;
    private OptionAdapter optionAdapter;
    private ShiJuanOptionAdapter shiJuanOptionAdapter;
    private ShiJuanTestBean.DataBean.PaperSubjectListBean paperSubjectListBean;//试卷
    private SubjectQuestionBean.DataBean.PaperChapterSubjectListBean paperChapterSubjectListBean;//练习
    private TextView zong_he_tv;
    private ScrollView zong_he_sv;
    private View zong_he_view;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ArrayList<ZongHeFragment> zongHeFragments;
    private int curPageNum = 0;//当前页面的pageNum
    private LinearLayout shi_juan_ziping;
    private TextView question_commit;
    private ZongHeCommitInterface zongHeCommitInterface;
    private EditText question_fraction;
    private boolean isPractice;

    public void setModifyQuestionListener(OnModifyQuestionListener modifyQuestionListener) {
        this.modifyQuestionListener = modifyQuestionListener;
    }

    public interface OnModifyQuestionListener {
        //answerSelect:选中的选项角标，position：第几个题目角标
        void modifyQuestion(int answerSelect, int position, boolean checked);//客观题

        void modifyQuestionJianDa(int position, String content);//主观题
    }

    public QuestionFragment() {
    }

    public static QuestionFragment newInstance(Object o, int position/*, QuestionToolsUtils questionToolsUtils*/, int nodeType, int testPaperId) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        if (o instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean subDataBean = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) o;
            args.putSerializable(ARG_PARAM1, subDataBean);
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean paperSubjectListBean = (ShiJuanTestBean.DataBean.PaperSubjectListBean) o;
            args.putSerializable(ARG_PARAM1, paperSubjectListBean);
        }
        args.putSerializable(ARG_PARAM2, position);
        //args.putSerializable(ARG_PARAM3, questionToolsUtils);
        args.putSerializable(ARG_PARAM4, nodeType);
        args.putSerializable(ARG_PARAM5, testPaperId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable(ARG_PARAM1) instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) {
                object = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) getArguments().getSerializable(ARG_PARAM1);
                isPractice = true;
            } else {
                object = (ShiJuanTestBean.DataBean.PaperSubjectListBean) getArguments().getSerializable(ARG_PARAM1);
                isPractice = false;
            }
            tiMuPosition = getArguments().getInt(ARG_PARAM2);
            nodeType = getArguments().getInt(ARG_PARAM4);
            testPaperId = getArguments().getInt(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question, container, false);
        initView();
        return view;
    }

    private void initView() {
        TextView textView = view.findViewById(R.id.tv_title);
        LinearLayout linearLayout = view.findViewById(R.id.ll_title);
        TextView tv_title_number = view.findViewById(R.id.tv_title_number);
        ImageView imageView = view.findViewById(R.id.iv_title);
        jianda_cl = view.findViewById(R.id.jianda_cl);
        option_rv = view.findViewById(R.id.option_rv);
        jianda_et = view.findViewById(R.id.jianda_et);
        jianda_tv = view.findViewById(R.id.jianda_tv);
        zong_he_tv = view.findViewById(R.id.zong_he_tv);
        zong_he_sv = view.findViewById(R.id.zong_he_sv);
        zong_he_view = view.findViewById(R.id.zong_he_view);
        zong_he_ti_num = view.findViewById(R.id.zong_he_ti_num);
        zong_he_vp = view.findViewById(R.id.zong_he_vp);
        shi_juan_ziping = view.findViewById(R.id.shi_juan_ziping);//自评分
        question_fraction = view.findViewById(R.id.question_fraction);
        question_commit = view.findViewById(R.id.question_commit);
        //解析的Rv
        question_rv = view.findViewById(R.id.question_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        question_rv.setLayoutManager(linearLayoutManager);
        initClick();
        if (object instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) {
            paperChapterSubjectListBean = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) object;
            if (TextUtils.isEmpty(paperChapterSubjectListBean.getSubjectTitlePic())) {
                linearLayout.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText((tiMuPosition + 1) + ". " + paperChapterSubjectListBean.getSubjectTitle());
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                tv_title_number.setText((tiMuPosition + 1) + ". ");
                ImageLoaderUtils.loadUrl(getActivity(), paperChapterSubjectListBean.getSubjectTitlePic(), imageView);
            }
            if (paperChapterSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {//多项选择题
                addMultipleOptionButton();
            } else if (paperChapterSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) || paperChapterSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {//单选/判断
                addSingleOptionButton();
            } else if (paperChapterSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)) {//简答
                addJianDaView(tiMuPosition);
            } else {
                initZongHe(tiMuPosition, paperChapterSubjectListBean, true);
            }
            exerciseAnalysisJXAdapter = new ExerciseAnalysisJXAdapter(R.layout.exercise_analysis_jx_item, initJXData(paperChapterSubjectListBean));
        } else {
            paperSubjectListBean = (ShiJuanTestBean.DataBean.PaperSubjectListBean) object;
            if (TextUtils.isEmpty(paperSubjectListBean.getSubjectTitlePic())) {
                linearLayout.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText((tiMuPosition + 1) + ". " + paperSubjectListBean.getSubjectTitle());
            } else {
                linearLayout.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                tv_title_number.setText((tiMuPosition + 1) + ". ");
                ImageLoaderUtils.loadUrl(getActivity(), paperSubjectListBean.getSubjectTitlePic(), imageView);
            }
            if (paperSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {//多项选择题
                addMultipleOptionButton();
            } else if (paperSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) || paperSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {//单选/判断
                addSingleOptionButton();
            } else if (paperSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)) {//简答
                addJianDaView(tiMuPosition);
                shi_juan_ziping.setVisibility(View.VISIBLE);
            } else {
                initZongHe(tiMuPosition, paperSubjectListBean, false);
                shi_juan_ziping.setVisibility(View.VISIBLE);
            }
            exerciseAnalysisJXAdapter = new ExerciseAnalysisJXAdapter
                    (R.layout.exercise_analysis_jx_item, initShiJuanJXData(paperSubjectListBean));
        }
        question_rv.setAdapter(exerciseAnalysisJXAdapter);
    }

    //试卷类综合题的提交
    private void initClick() {
        question_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtil.isEmpty(question_fraction.getText().toString().trim())) {
                    ToastUtils.Toast(getActivity(), "请输入自评分后，再提交");
                    return;
                }
                zongHeCommitInterface.OnCommitClick(question_fraction.getText().toString().trim());
            }
        });
    }

    public void setZongHeCommitInterface(ZongHeCommitInterface zongHeCommitInterface) {
        this.zongHeCommitInterface = zongHeCommitInterface;
    }

    /**
     * 初始化文字解析，视频解析，考点(练习类)
     *
     * @return
     */
    private ArrayList initJXData(SubjectQuestionBean.DataBean.PaperChapterSubjectListBean dataBean) {
        analysisJXBeans.add(new ExerciseAnalysisJXBean("文字解析", dataBean.getAnalysisText()));
        analysisJXBeans.add(new ExerciseAnalysisJXBean("视频解析", dataBean.getAnalysisVideo()));
        String kaoDian = "";
        for (int i = 1; i <= dataBean.getKnowledgeList().size(); i++) {
            kaoDian += i + "、" + dataBean.getKnowledgeList().get(i - 1).getKnowledgeName() + "  ";
        }
        analysisJXBeans.add(new ExerciseAnalysisJXBean("考点", kaoDian));
        return analysisJXBeans;
    }

    /**
     * 初始化文字解析，视频解析，考点（试卷类）
     *
     * @return
     */
    private ArrayList initShiJuanJXData(ShiJuanTestBean.DataBean.PaperSubjectListBean dataBean) {
        analysisJXBeans.add(new ExerciseAnalysisJXBean("文字解析", dataBean.getAnalysisText()));
        analysisJXBeans.add(new ExerciseAnalysisJXBean("视频解析", dataBean.getAnalysisVideo()));
        String kaoDian = "";
        for (int i = 1; i <= dataBean.getKnowledgeList().size(); i++) {
            kaoDian += i + "、" + dataBean.getKnowledgeList().get(i - 1).getKnowledgeName() + "  ";
        }
        analysisJXBeans.add(new ExerciseAnalysisJXBean("考点", kaoDian));
        return analysisJXBeans;
    }

    /**
     * 初始化简答题
     *
     * @param i
     */
    private void addJianDaView(int i) {
        jianda_cl.setVisibility(View.VISIBLE);
        if (paperChapterSubjectListBean != null && !TextUtil.isEmpty(paperChapterSubjectListBean.getDoneAnswer()))
            jianda_et.setText(paperChapterSubjectListBean.getDoneAnswer());
        if (paperSubjectListBean != null && !TextUtil.isEmpty(paperSubjectListBean.getDoneAnswer()))
            jianda_et.setText(paperSubjectListBean.getDoneAnswer());
        jianda_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                jianda_tv.setText(jianda_et.getText().toString().trim().length() + "/" + "1000");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                modifyQuestionListener.modifyQuestionJianDa(i, jianda_et.getText().toString().trim());
            }
        });
    }

    /**
     * 初始化综合View
     *
     * @param i
     */
    private void initZongHe(int i, Object object, boolean lianxi) {
        zong_he_tv.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = zong_he_sv.getLayoutParams();
        layoutParams.height = UnitConversionUtil.getScreenHeight(getActivity()) / 2;
        zong_he_sv.setLayoutParams(layoutParams);
        //弹出
        zong_he_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zong_he_sv.setVisibility(View.VISIBLE);
                zong_he_tv.setVisibility(View.GONE);
            }
        });
        //收起
        zong_he_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zong_he_sv.setVisibility(View.GONE);
                zong_he_tv.setVisibility(View.VISIBLE);
            }
        });
        zongHeFragments = new ArrayList<>();
        if (lianxi) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean bean = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) object;
            for (int j = 0; j < bean.getSubjectList().size(); j++) {
                TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.zong_he_ti_num, null);
                if (0 == j) {
                    textView.setTextColor(getActivity().getResources().getColor(R.color.color_FFFFFF));
                    textView.setBackgroundColor(getActivity().getResources().getColor(R.color.color_5F7DFF));
                }
                textView.setText(j + 1 + "");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(15, 0, 0, 0);
                textView.setLayoutParams(lp);
                zong_he_ti_num.addView(textView);
                textView.setOnClickListener(this);
                ZongHeFragment zongHeFragment = ZongHeFragment.newInstance(bean.getSubjectList().get(j), i);
                zongHeFragments.add(zongHeFragment);
            }
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean bean = (ShiJuanTestBean.DataBean.PaperSubjectListBean) object;
            for (int j = 0; j < bean.getSubjectList().size(); j++) {
                TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.zong_he_ti_num, null);
                if (0 == j) {
                    textView.setTextColor(getActivity().getResources().getColor(R.color.color_FFFFFF));
                    textView.setBackgroundColor(getActivity().getResources().getColor(R.color.color_5F7DFF));
                }
                textView.setText(j + 1 + "");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(15, 0, 0, 0);
                textView.setLayoutParams(lp);
                zong_he_ti_num.addView(textView);
                textView.setOnClickListener(this);
                ZongHeFragment zongHeFragment = ZongHeFragment.newInstance(bean.getSubjectList().get(j), i);
                zongHeFragments.add(zongHeFragment);
            }
        }
        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @NotNull
            @Override
            public Fragment getItem(int position) {
                if (isPractice) {
                    if (paperChapterSubjectListBean != null && paperChapterSubjectListBean.getSubjectList() != null && paperChapterSubjectListBean.getSubjectList().size() > 0) {
                        SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean subjectList = paperChapterSubjectListBean.getSubjectList().get(position);
                        ZongHeFragment zongHeFragment = ZongHeFragment.newInstance(subjectList, position);
                        zongHeFragment.setCheckQuestionListene(QuestionFragment.this);
                        return zongHeFragment;
                    } else {
                        return null;
                    }
                } else {
                    if (paperSubjectListBean != null && paperSubjectListBean.getSubjectList() != null && paperSubjectListBean.getSubjectList().size() > 0) {
                        ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean subjectList = paperSubjectListBean.getSubjectList().get(position);
                        ZongHeFragment zongHeFragment = ZongHeFragment.newInstance(subjectList, position);
                        zongHeFragment.setCheckQuestionListene(QuestionFragment.this);
                        return zongHeFragment;
                    } else {
                        return null;
                    }
                }
            }

            @Override
            public int getCount() {
                return zongHeFragments.size();
            }
        };
        zong_he_vp.setOffscreenPageLimit(10);
        zong_he_vp.setAdapter(fragmentPagerAdapter);
        zong_he_vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int posisiton) {
                curPageNum = posisiton;
                for (int i = 0; i < zong_he_ti_num.getChildCount(); i++) {
                    TextView textView = (TextView) zong_he_ti_num.getChildAt(i);
                    if (posisiton == i) {
                        textView.setTextColor(getActivity().getResources().getColor(R.color.color_FFFFFF));
                        textView.setBackgroundColor(getActivity().getResources().getColor(R.color.color_5F7DFF));
                    } else {
                        textView.setTextColor(getActivity().getResources().getColor(R.color.color_141414));
                        textView.setBackground(getActivity().getResources().getDrawable(R.drawable.rectangle_hollow_dadada_ffffff));
                    }
                }
                if (curPageNum > posisiton) {//是切换上一题
                    if (paperChapterSubjectListBean != null)
                        saveSubject(posisiton);
                    else
                        testSaveSubject(posisiton, "");
                } else {//是切换下一题
                    if (paperChapterSubjectListBean != null)
                        saveSubject(posisiton - 1);
                    else
                        testSaveSubject(posisiton - 1, "");
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < zong_he_ti_num.getChildCount(); i++) {
            TextView tv = (TextView) zong_he_ti_num.getChildAt(i);
            if (view == tv) {
                tv.setTextColor(getActivity().getResources().getColor(R.color.color_FFFFFF));
                tv.setBackgroundColor(getActivity().getResources().getColor(R.color.color_5F7DFF));
                zong_he_vp.setCurrentItem(i);
            } else {
                tv.setTextColor(getActivity().getResources().getColor(R.color.color_141414));
                tv.setBackground(getActivity().getResources().getDrawable(R.drawable.rectangle_hollow_dadada_ffffff));
            }
        }
    }

    /**
     * 添加多选按钮
     */
    private void addMultipleOptionButton() {
        if (object instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) {
            optionAdapter = new OptionAdapter(R.layout.item_radio, paperChapterSubjectListBean.getAnswerList());
            option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            option_rv.setAdapter(optionAdapter);
            optionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                    paperChapterSubjectListBean.getAnswerList().get(position).setMyselfIsChoose(!paperChapterSubjectListBean.getAnswerList().get(position).isMyselfIsChoose());
                    optionAdapter.notifyDataSetChanged();
                    modifyQuestionListener.modifyQuestion(position, tiMuPosition, paperChapterSubjectListBean.getAnswerList().get(position).isMyselfIsChoose());
                }
            });
        } else {
            shiJuanOptionAdapter = new ShiJuanOptionAdapter(R.layout.item_radio, paperSubjectListBean.getAnswerList());
            option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            option_rv.setAdapter(shiJuanOptionAdapter);
            shiJuanOptionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                    paperSubjectListBean.getAnswerList().get(position).setMyselfIsChoose(!paperSubjectListBean.getAnswerList().get(position).isMyselfIsChoose());
                    shiJuanOptionAdapter.notifyDataSetChanged();
                    modifyQuestionListener.modifyQuestion(position, tiMuPosition, paperSubjectListBean.getAnswerList().get(position).isMyselfIsChoose());
                }
            });
        }
    }

    /**
     * 添加单选按钮
     */
    private void addSingleOptionButton() {
        if (object instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean) {
            optionAdapter = new OptionAdapter(R.layout.item_radio, paperChapterSubjectListBean.getAnswerList());
            option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            option_rv.setAdapter(optionAdapter);
            optionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                    for (int i = 0; i < paperChapterSubjectListBean.getAnswerList().size(); i++) {
                        paperChapterSubjectListBean.getAnswerList().get(i).setMyselfIsChoose(false);
                    }
                    paperChapterSubjectListBean.getAnswerList().get(position).setMyselfIsChoose(true);
                    optionAdapter.notifyDataSetChanged();
                    modifyQuestionListener.modifyQuestion(position, tiMuPosition, true);
                }
            });
        } else {
            shiJuanOptionAdapter = new ShiJuanOptionAdapter(R.layout.item_radio, paperSubjectListBean.getAnswerList());
            option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            option_rv.setAdapter(shiJuanOptionAdapter);
            shiJuanOptionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                    for (int i = 0; i < paperSubjectListBean.getAnswerList().size(); i++) {
                        paperSubjectListBean.getAnswerList().get(i).setMyselfIsChoose(false);
                    }
                    paperSubjectListBean.getAnswerList().get(position).setMyselfIsChoose(true);
                    shiJuanOptionAdapter.notifyDataSetChanged();
                    modifyQuestionListener.modifyQuestion(position, tiMuPosition, true);
                }
            });
        }
    }

    /**
     * 是否显示解析
     *
     * @param show
     */
    public void setShowJX(boolean show, String tiXing) {
        //综合类
        if (tiXing.equals(SubjectQuestionBean.TYPE_ZONG_HE)) {
            if (zongHeFragments == null || zongHeFragments.size() <= 0) {
                ToastUtils.Toast(getActivity(), "数据为空");
                return;
            }
            ZongHeFragment fragment = (ZongHeFragment) fragmentPagerAdapter.getItem(curPageNum);
            fragment.setShowZongHeJX();
        } else {//非综合类
            if (shiJuanOptionAdapter != null) {
                for (int i = 0; i < paperSubjectListBean.getAnswerList().size(); i++) {
                    paperSubjectListBean.getAnswerList().get(i).setClickJX(true);
                }
                shiJuanOptionAdapter.notifyDataSetChanged();
                question_rv.setVisibility(show ? View.VISIBLE : View.GONE);
            }
            if (optionAdapter != null) {
                for (int i = 0; i < paperChapterSubjectListBean.getAnswerList().size(); i++) {
                    paperChapterSubjectListBean.getAnswerList().get(i).setClickJX(true);
                }
                optionAdapter.notifyDataSetChanged();
                question_rv.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        }
    }

    @Override
    public void checkQuestionKeGuan(int answerSelect, int position, boolean checked) {
        //保存这个题目的答案
        if (isPractice) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean dataBeanTemp = paperChapterSubjectListBean.getSubjectList().get(position);
            //未做过的题目 单项选择题选择后直接选择答案后延时进入下一题 ； 多项选择/题目选择后修改的需要自行滑动活着点击下一题
            if (dataBeanTemp.getQuestion_select() == -1 && (dataBeanTemp.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                    dataBeanTemp.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) && QuestionToolsUtils.getInstance().isAutomaticNext) {
                //延时下一题
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (position == curPageNum) {
                            nextQuestion();
                        }
                    }
                }, 500);
            }
            dataBeanTemp.setQuestion_select(answerSelect);
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean dataBeanTemp = paperSubjectListBean.getSubjectList().get(position);
            //未做过的题目 单项选择题选择后直接选择答案后延时进入下一题 ； 多项选择/题目选择后修改的需要自行滑动活着点击下一题
            if (dataBeanTemp.getQuestion_select() == -1 && (dataBeanTemp.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                    dataBeanTemp.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) && QuestionToolsUtils.getInstance().isAutomaticNext) {
                //延时下一题
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (position == curPageNum) {
                            nextQuestion();
                        }
                    }
                }, 500);
            }
            dataBeanTemp.setQuestion_select(answerSelect);
        }
    }

    /**
     * 下一题
     */
    private synchronized void nextQuestion() {
        int currentItem = zong_he_vp.getCurrentItem();
        currentItem = currentItem + 1;
        if (currentItem < 0) {
            currentItem = 0;
        }
        zong_he_vp.setCurrentItem(currentItem);
    }

    @Override
    public void checkQuestionJianDa(int position, String content) {
        //保存这个题目的答案
        if (isPractice) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean dataBeanTemp = paperChapterSubjectListBean.getSubjectList().get(position);
            dataBeanTemp.setDoneAnswer(content);
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean dataBeanTemp = paperSubjectListBean.getSubjectList().get(position);
            dataBeanTemp.setDoneAnswer(content);
        }
    }

    /**
     * 练习类切换下一题的时候，保存答案
     */
    private void saveSubject(int saveQuestionPotion) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("answerTimeLength", QuestionToolsUtils.getInstance().thisQuestionUseTime());//这道题所用的时间
        map.put("nodeType", nodeType);//类型 1.章节 2.专项
        map.put("subjectId", paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getSubjectId());
        if (paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False) ||
                paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {
            String answerId = "";
            for (int i = 0; i < paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getAnswerList().size(); i++) {
                if (paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getAnswerList().get(i).isMyselfIsChoose())
                    answerId += paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getAnswerList().get(i).getAnswerId() + ",";
            }
            if (TextUtil.isEmpty(answerId))//没有作答不提交答案
                return;
            map.put("answerId", answerId.substring(0, answerId.length() - 1));
        } else if (paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)) {//简答
            String doneAnswer = paperChapterSubjectListBean.getSubjectList().get(saveQuestionPotion).getDoneAnswer();
            map.put("answerText", doneAnswer);
            if (TextUtil.isEmpty(doneAnswer))//没有作答不提交答案
                return;
        }
        NovateUtils.getInstance().Post(getActivity(), BaseUrl.saveSubject, map, true,
                new NovateUtils.setRequestReturn<CommontBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
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
        map.put("subjectId", paperSubjectListBean.getSubjectList().get(saveQuestionPotion).getSubjectId());
        if (paperSubjectListBean.getSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False) ||
                paperSubjectListBean.getSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) ||
                paperSubjectListBean.getSubjectList().get(saveQuestionPotion).getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {
            String answerId = "";
            for (int i = 0; i < paperSubjectListBean.getSubjectList().get(saveQuestionPotion).getAnswerList().size(); i++) {
                if (paperSubjectListBean.getSubjectList().get(saveQuestionPotion).getAnswerList().get(i).isMyselfIsChoose())
                    answerId += paperSubjectListBean.getSubjectList().get(saveQuestionPotion).getAnswerList().get(i).getAnswerId() + ",";
            }
            if (TextUtil.isEmpty(answerId))//没有作答不提交答案
                return;
            map.put("answerId", answerId.substring(0, answerId.length() - 1));
        } else {
            String doneAnswer = paperSubjectListBean.getSubjectList().get(saveQuestionPotion).getDoneAnswer();
            if (!TextUtil.isEmpty(fraction))
                map.put("score", Integer.parseInt(fraction));//自评分
            if (TextUtil.isEmpty(doneAnswer))//没有作答不提交答案
                return;
            map.put("answerText", doneAnswer);
        }
        NovateUtils.getInstance().Post(getActivity(), BaseUrl.TestSaveSubject, map, true,
                new NovateUtils.setRequestReturn<CommontBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
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

    public void jiaoJuanSave() {
        if (isPractice)
            saveSubject(curPageNum);
        else
            testSaveSubject(curPageNum, "");
    }

}