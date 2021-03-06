package com.bjsn909429077.stz.ui.questionbank;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ExerciseAnalysisAdapter;
import com.bjsn909429077.stz.adapter.ExerciseAnalysisJXAdapter;
import com.bjsn909429077.stz.adapter.InterpretationQuestionAdapter;
import com.bjsn909429077.stz.bean.ExerciseAnalysisJXBean;
import com.bjsn909429077.stz.bean.lianXiSubjectAnalysisBean;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Ke.Chen
 * @Description:
 * @Date :2021/9/24 20:36
 **/
public class ExerciseAnalysisFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view;
    private lianXiSubjectAnalysisBean.DataBean response;
    private int position;
    private TextView exercise_analysis_stem;
    private TextView your_answer;
    private TextView right_answer;
    private TextView all_correct_rate;
    private TextView total_answer_to_this_question;
    private TextView this_question_orrect_rate;
    private TextView number_of_error_answers;
    private TextView number_of_right_answers;
    private RecyclerView exercise_analysis_rv;
    private RecyclerView exercise_analysis_option_rv;
    private LinearLayout objective_question_ll;
    private LinearLayout subjective_question_ll;
    private LinearLayout interpretation_question_ll;
    private RecyclerView interpretation_question_option_rv;
    private TextView interpretation_question_stem;
    private TextView subjective_question_content;
    private LinearLayout objective_question_content;
    private TextView subjective_question_answer;
    private ArrayList<ExerciseAnalysisJXBean> analysisJXBeans = new ArrayList<>();
    private ExerciseAnalysisAdapter exerciseAnalysisAdapter;
    private InterpretationQuestionAdapter interpretationQuestionAdapter;
    private ExerciseAnalysisJXAdapter exerciseAnalysisJXAdapter;
    private ScrollView interpretation_question_sv;

    public static ExerciseAnalysisFragment newInstance(lianXiSubjectAnalysisBean.DataBean subjectAnalysisBean, int position) {
        ExerciseAnalysisFragment fragment = new ExerciseAnalysisFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, subjectAnalysisBean);
        args.putSerializable(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            response = (lianXiSubjectAnalysisBean.DataBean) getArguments().getSerializable(ARG_PARAM1);
            position = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exercise_analysis, container, false);
        interpretation_question_sv = view.findViewById(R.id.interpretation_question_sv);
        exercise_analysis_stem = view.findViewById(R.id.exercise_analysis_stem);//??????
        //??????????????????ll
        objective_question_ll = view.findViewById(R.id.objective_question_ll);
        subjective_question_content = view.findViewById(R.id.subjective_question_content);
        objective_question_content = view.findViewById(R.id.objective_question_content);
        //????????????ll
        subjective_question_ll = view.findViewById(R.id.subjective_question_ll);
        subjective_question_answer = view.findViewById(R.id.subjective_question_answer);
        //?????????
        interpretation_question_ll = view.findViewById(R.id.interpretation_question_ll);
        interpretation_question_stem = view.findViewById(R.id.interpretation_question_stem);

        interpretation_question_option_rv = view.findViewById(R.id.interpretation_question_option_rv);
        your_answer = view.findViewById(R.id.your_answer);
        right_answer = view.findViewById(R.id.right_answer);
        all_correct_rate = view.findViewById(R.id.all_correct_rate);
        total_answer_to_this_question = view.findViewById(R.id.total_answer_to_this_question);//???????????????
        number_of_right_answers = view.findViewById(R.id.number_of_right_answers);
        number_of_error_answers = view.findViewById(R.id.number_of_error_answers);
        this_question_orrect_rate = view.findViewById(R.id.this_question_orrect_rate);
        exercise_analysis_rv = view.findViewById(R.id.exercise_analysis_rv);
        //????????????
        exercise_analysis_option_rv = view.findViewById(R.id.exercise_analysis_option_rv);
        initView();
        return view;
    }

    private void initView() {
        if (response != null) {
            //??????
            if (response.getClassify().equals("1") || response.getClassify().equals("2")
                    || response.getClassify().equals("3")) {//1????????????2????????????3???????????????4????????????5?????????
                String rightAnswer = "";
                String myAnswer = "";
                if (response.getClassify().equals("1") || response.getClassify().equals("2")) {
                    objective_question_ll.setVisibility(View.VISIBLE);
                    objective_question_content.setVisibility(View.VISIBLE);
                    subjective_question_ll.setVisibility(View.GONE);
                    interpretation_question_ll.setVisibility(View.GONE);
                    exercise_analysis_stem.setText(response.getSubjectTitle());
                    //????????????
                    exercise_analysis_option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    exerciseAnalysisAdapter = new ExerciseAnalysisAdapter(getActivity(),
                            R.layout.exercise_analysis_option_item, response.getAnswerList(), response.getDoneAnswerIds());
                    exercise_analysis_option_rv.setAdapter(exerciseAnalysisAdapter);
                    for (int i = 0; i < response.getAnswerList().size(); i++) {
                        if (response.getAnswerList().get(i).getIsRight() == 1)
                            rightAnswer += response.getAnswerList().get(i).getIndexes() + " ";
                    }
                } else {
                    objective_question_ll.setVisibility(View.GONE);
                    subjective_question_ll.setVisibility(View.GONE);
                    interpretation_question_ll.setVisibility(View.VISIBLE);
                    objective_question_content.setVisibility(View.VISIBLE);
                    interpretation_question_stem.setText(response.getSubjectTitle());
                    //????????????
                    interpretation_question_option_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    interpretationQuestionAdapter = new InterpretationQuestionAdapter(R.layout.interpretation_question_option_item,
                            response.getAnswerList(), response.getDoneAnswerIds());
                    interpretation_question_option_rv.setAdapter(interpretationQuestionAdapter);
                    for (int i = 0; i < response.getAnswerList().size(); i++) {
                        if (response.getAnswerList().get(i).getIsRight() == 1)
                            rightAnswer = response.getAnswerList().get(i).getAnswerName();
                    }
                }
                String[] myAnswers=response.getDoneAnswerIds().split(",");
                for (int i = 0; i < myAnswers.length; i++) {
                    for (int j = 0; j < response.getAnswerList().size(); j++) {
                        if (myAnswers[i].equals(response.getAnswerList().get(j).getAnswerId()))
                            myAnswer+=response.getAnswerList().get(j).getIndexes()+" ";
                    }
                }
                //????????????
                your_answer.setText(myAnswer);
                //????????????
                right_answer.setText(rightAnswer);
                //???????????????
                all_correct_rate.setText(response.getTotalAccuracy() + "%");
                total_answer_to_this_question.setText("??????????????????" + response.getCountNumber() + "???");
                number_of_right_answers.setText("?????????" + response.getRightNumber() + "???");
                number_of_error_answers.setText("?????????" + response.getRightNumber() + "???");
                this_question_orrect_rate.setText("????????????" + response.getAccuracy() + "%");
            } else {
                objective_question_ll.setVisibility(View.GONE);
                objective_question_content.setVisibility(View.GONE);
                interpretation_question_ll.setVisibility(View.GONE);
                subjective_question_ll.setVisibility(View.VISIBLE);
                subjective_question_content.setText("????????????" + response.getSubjectTitle());
                subjective_question_answer.setText(response.getDoneAnswer());
            }
            //??????????????????
            exercise_analysis_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
            exerciseAnalysisJXAdapter = new ExerciseAnalysisJXAdapter(R.layout.exercise_analysis_jx_item, initJXData(response));
            exercise_analysis_rv.setAdapter(exerciseAnalysisJXAdapter);
        }
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @return
     */
    private ArrayList initJXData(lianXiSubjectAnalysisBean.DataBean dataBean) {
        analysisJXBeans.add(new ExerciseAnalysisJXBean("????????????", dataBean.getAnalysisText()));
        analysisJXBeans.add(new ExerciseAnalysisJXBean("????????????", dataBean.getAnalysisVideo()));
        String kaoDian = "";
        for (int i = 1; i <= dataBean.getKnowledgeList().size(); i++) {
            kaoDian += i + "???" + dataBean.getKnowledgeList().get(i - 1).getKnowledgeName() + "  ";
        }
        analysisJXBeans.add(new ExerciseAnalysisJXBean("??????", kaoDian));
        return analysisJXBeans;
    }
}
