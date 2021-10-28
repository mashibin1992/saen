package com.bjsn909429077.stz.ui.questionbank;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ExerciseAnalysisJXAdapter;
import com.bjsn909429077.stz.adapter.OptionAdapter;
import com.bjsn909429077.stz.adapter.ShiJuanOptionAdapter;
import com.bjsn909429077.stz.adapter.ZongHeOptionAdapter;
import com.bjsn909429077.stz.adapter.ZongHeShiJuanOptionAdapter;
import com.bjsn909429077.stz.bean.ExerciseAnalysisJXBean;
import com.bjsn909429077.stz.bean.ShiJuanTestBean;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/25 12:29
 **/
public class ZongHeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Object object;
    private int tiMuPosition;
    private View view;
    private RecyclerView zonghe_jx_rv;
    private ExerciseAnalysisJXAdapter exerciseAnalysisJXAdapter;
    private ArrayList<ExerciseAnalysisJXBean> analysisJXBeans = new ArrayList<>();
    private ConstraintLayout zonghe_jianda_cl;
    private EditText zonghe_jianda_et;
    private TextView zonghe_jianda_tv;
    private RecyclerView zonghe_option_rv;
    private OnCheckQuestionListener checkQuestionListener;

    public void setCheckQuestionListene(OnCheckQuestionListener checkQuestionListener) {
        this.checkQuestionListener = checkQuestionListener;
    }

    public interface OnCheckQuestionListener {
        //answerSelect:选中的选项角标，position：第几个题目角标
        void checkQuestionKeGuan(int answerSelect, int position, boolean checked);//客观题

        void checkQuestionJianDa(int position, String content);//主观题
    }

    public static ZongHeFragment newInstance(Object o, int position) {
        ZongHeFragment fragment = new ZongHeFragment();
        Bundle args = new Bundle();
        if (o instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean subDataBean = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) o;
            args.putSerializable(ARG_PARAM1, subDataBean);
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean paperSubjectListBean = (ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean) o;
            args.putSerializable(ARG_PARAM1, paperSubjectListBean);
        }
        args.putSerializable(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable(ARG_PARAM1) instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) {
                object = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) getArguments().getSerializable(ARG_PARAM1);
            } else {
                object = (ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean) getArguments().getSerializable(ARG_PARAM1);
            }
            tiMuPosition = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zonghe, container, false);
        initView();
        return view;
    }

    private void initView() {
        TextView textView = view.findViewById(R.id.zonghe_title);
        zonghe_jianda_cl = view.findViewById(R.id.zonghe_jianda_cl);
        zonghe_option_rv = view.findViewById(R.id.zonghe_option_rv);
        zonghe_jianda_et = view.findViewById(R.id.zonghe_jianda_et);
        zonghe_jianda_tv = view.findViewById(R.id.zonghe_jianda_tv);
        //解析的Rv
        zonghe_jx_rv = view.findViewById(R.id.zonghe_jx_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        zonghe_jx_rv.setLayoutManager(linearLayoutManager);
        if (object instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean paperChapterSubjectListBean = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) object;
            textView.setText(paperChapterSubjectListBean.getSubjectTitle());
            if (paperChapterSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {//多项选择题
                addMultipleOptionButton();
            } else if (paperChapterSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) || paperChapterSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {//单选/判断
                addSingleOptionButton();
            } else if (paperChapterSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)) {//简答
                addJianDaView(tiMuPosition);
            }
            exerciseAnalysisJXAdapter = new ExerciseAnalysisJXAdapter(R.layout.exercise_analysis_jx_item, initJXData(paperChapterSubjectListBean));
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean paperSubjectListBean = (ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean) object;
            textView.setText(paperSubjectListBean.getSubjectTitle());
            if (paperSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_Multiple_Choice)) {//多项选择题
                addMultipleOptionButton();
            } else if (paperSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_Single_Choice) || paperSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_True_OR_False)) {//单选/判断
                addSingleOptionButton();
            } else if (paperSubjectListBean.getClassify().equals(SubjectQuestionBean.TYPE_JIAN_DA)) {//简答
                addJianDaView(tiMuPosition);
            }
            exerciseAnalysisJXAdapter = new ExerciseAnalysisJXAdapter
                    (R.layout.exercise_analysis_jx_item, initShiJuanJXData(paperSubjectListBean));
        }
        zonghe_jx_rv.setAdapter(exerciseAnalysisJXAdapter);
    }

    /**
     * 初始化文字解析，视频解析，考点(练习类)
     *
     * @return
     */
    private ArrayList initJXData(SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean dataBean) {
        analysisJXBeans.add(new ExerciseAnalysisJXBean("文字解析", dataBean.getAnalysisText()));
        analysisJXBeans.add(new ExerciseAnalysisJXBean("视频解析", dataBean.getAnalysisVideo()));
        String kaoDian = "";
        if (dataBean.getKnowledgeList()!=null) {
            for (int i = 1; i <= dataBean.getKnowledgeList().size(); i++) {
                kaoDian += i + "、" + dataBean.getKnowledgeList().get(i - 1).getKnowledgeName() + "  ";
            }
            analysisJXBeans.add(new ExerciseAnalysisJXBean("考点", kaoDian));
        }
        return analysisJXBeans;
    }

    /**
     * 初始化文字解析，视频解析，考点（试卷类）
     *
     * @return
     */
    private ArrayList initShiJuanJXData(ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean dataBean) {
        analysisJXBeans.add(new ExerciseAnalysisJXBean("文字解析", dataBean.getAnalysisText()));
        analysisJXBeans.add(new ExerciseAnalysisJXBean("视频解析", dataBean.getAnalysisVideo()));
        String kaoDian = "";
        if (dataBean.getKnowledgeList()==null)
            return null;
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
        zonghe_jianda_cl.setVisibility(View.VISIBLE);
        zonghe_jianda_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                zonghe_jianda_tv.setText(zonghe_jianda_et.getText().toString().trim().length() + "/" + "1000");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkQuestionListener.checkQuestionJianDa(i, zonghe_jianda_et.getText().toString().trim());
            }
        });
    }

    /**
     * 添加多选按钮
     */
    private void addMultipleOptionButton() {
        if (object instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean paperChapterSubjectListBean = (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) object;
            ZongHeOptionAdapter zongHeOptionAdapter =new ZongHeOptionAdapter(R.layout.item_radio,paperChapterSubjectListBean.getAnswerList());
            zonghe_option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            zonghe_option_rv.setAdapter(zongHeOptionAdapter);
            zongHeOptionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                    paperChapterSubjectListBean.getAnswerList().get(position).setMyselfIsChoose(!paperChapterSubjectListBean.getAnswerList().get(position).isMyselfIsChoose());
                    zongHeOptionAdapter.notifyDataSetChanged();
                    checkQuestionListener.checkQuestionKeGuan(position, tiMuPosition, paperChapterSubjectListBean.getAnswerList().get(position).isMyselfIsChoose());
                }
            });
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean paperSubjectListBean = (ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean) object;
            ZongHeShiJuanOptionAdapter zongHeShiJuanOptionAdapter =new ZongHeShiJuanOptionAdapter(R.layout.item_radio,paperSubjectListBean.getAnswerList());
            zonghe_option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            zonghe_option_rv.setAdapter(zongHeShiJuanOptionAdapter);
            zongHeShiJuanOptionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                    paperSubjectListBean.getAnswerList().get(position).setMyselfIsChoose(!paperSubjectListBean.getAnswerList().get(position).isMyselfIsChoose());
                    zongHeShiJuanOptionAdapter.notifyDataSetChanged();
                    checkQuestionListener.checkQuestionKeGuan(position, tiMuPosition, paperSubjectListBean.getAnswerList().get(position).isMyselfIsChoose());
                }
            });
        }
    }

    /**
     * 添加单选按钮
     */
    private void addSingleOptionButton() {
        if (object instanceof SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) {
            SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean paperChapterSubjectListBean =
                    (SubjectQuestionBean.DataBean.PaperChapterSubjectListBean.SubjectListBean) object;
            ZongHeOptionAdapter zongHeOptionAdapter =new ZongHeOptionAdapter(R.layout.item_radio,paperChapterSubjectListBean.getAnswerList());
            zonghe_option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            zonghe_option_rv.setAdapter(zongHeOptionAdapter);
            zongHeOptionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                    for (int i = 0; i < paperChapterSubjectListBean.getAnswerList().size(); i++) {
                        paperChapterSubjectListBean.getAnswerList().get(i).setMyselfIsChoose(false);
                    }
                    paperChapterSubjectListBean.getAnswerList().get(position).setMyselfIsChoose(true);
                    zongHeOptionAdapter.notifyDataSetChanged();
                    checkQuestionListener.checkQuestionKeGuan(position, tiMuPosition, true);
                }
            });
        } else {
            ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean paperSubjectListBean = (ShiJuanTestBean.DataBean.PaperSubjectListBean.SubjectListBean) object;
            ZongHeShiJuanOptionAdapter zongHeShiJuanOptionAdapter =new ZongHeShiJuanOptionAdapter(R.layout.item_radio,paperSubjectListBean.getAnswerList());
            zonghe_option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            zonghe_option_rv.setAdapter(zongHeShiJuanOptionAdapter);
            zongHeShiJuanOptionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                    for (int i = 0; i < paperSubjectListBean.getAnswerList().size(); i++) {
                        paperSubjectListBean.getAnswerList().get(i).setMyselfIsChoose(false);
                    }
                    paperSubjectListBean.getAnswerList().get(position).setMyselfIsChoose(true);
                    zongHeShiJuanOptionAdapter.notifyDataSetChanged();
                    checkQuestionListener.checkQuestionKeGuan(position, tiMuPosition, true);
                }
            });
        }
    }

    /**
     * 综合题解析的view
     */
    public void setShowZongHeJX() {
        zonghe_jx_rv.setVisibility(View.VISIBLE);
    }

}
