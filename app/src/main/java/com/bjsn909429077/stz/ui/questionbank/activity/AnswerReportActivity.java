package com.bjsn909429077.stz.ui.questionbank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.AnswerReportBean;
import com.bjsn909429077.stz.utils.UnitConversionUtil;
import com.bjsn909429077.stz.widget.AchievementCurveView;
import com.bjsn909429077.stz.widget.CircleProgressView;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;

public class AnswerReportActivity extends BaseActivity {

    private LinearLayout achievement_curve_acv_ll;
    private ArrayList yList = new ArrayList<>();
    private CircleProgressView answer_report_cpv;
    private int nodeType;
    private int nodeId;
    private TextView answer_report_first_name;
    private TextView answer_report_second_name;
    private TextView answer_report_answer_number;
    private TextView answer_report_use_time;
    private TextView max_correct_rate;
    private TextView average_correct_rate;
    private TextView exceed_correct_rate;
    private TextView number_of_correct_answers;
    private TextView number_of_wrong_answers;
    private TextView number_of_questions;
    private ArrayList<String> xRawDatas1;
    private boolean isPractice;
    private int testPaperId;
    private TextView again_question;
    private TextView error_jx;
    private TextView all_jx;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_answer_report;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = findViewById(R.id.common_title);
        commonTitleView.setTitle("????????????");
        achievement_curve_acv_ll = findViewById(R.id.achievement_curve_acv_ll);
        answer_report_cpv = findViewById(R.id.answer_report_cpv);
        answer_report_first_name = findViewById(R.id.answer_report_first_name);
        answer_report_second_name = findViewById(R.id.answer_report_second_name);
        answer_report_answer_number = findViewById(R.id.answer_report_answer_number);
        answer_report_use_time = findViewById(R.id.answer_report_use_time);
        max_correct_rate = findViewById(R.id.max_correct_rate);
        average_correct_rate = findViewById(R.id.average_correct_rate);
        exceed_correct_rate = findViewById(R.id.exceed_correct_rate);
        number_of_correct_answers = findViewById(R.id.number_of_correct_answers);
        number_of_wrong_answers = findViewById(R.id.number_of_wrong_answers);
        number_of_questions = findViewById(R.id.number_of_questions);
        again_question = findViewById(R.id.again_question);
        error_jx = findViewById(R.id.error_jx);
        all_jx = findViewById(R.id.all_jx);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        isPractice = intent.getBooleanExtra("isPractice", false);
        if (isPractice) {
            //???id
            nodeId = intent.getIntExtra("nodeId", 0);
            //?????? 1.?????? 2.??????
            nodeType = intent.getIntExtra("nodeType", 0);
            initNetWork();
        } else {//?????????
            //??????ID
            testPaperId = intent.getIntExtra("testPaperId", -1);
            initTestNetWork();
        }
        initClick();
    }

    private void initClick() {
        again_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AnswerReportActivity.this, QuestionActivity.class);
                if (isPractice) {
                    intent1.putExtra("isPractice", true);
                    intent1.putExtra("nodeId", nodeId);//???id
                    intent1.putExtra("nodeType", nodeType);//?????? 1.?????? 2.??????
                    intent1.putExtra("type", 2);//?????? 1.??????or?????? 2.????????????
                } else {//?????????
                    intent1.putExtra("isPractice", false);
                    //??????ID
                    intent1.putExtra("testPaperId", testPaperId);
                    intent1.putExtra("type", 2);//?????? 2.???????????? ??????????????????????????????2 ??????????????????
                }
                startActivity(intent1);
            }
        });
        error_jx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AnswerReportActivity.this, ExerciseAnalysisActivity.class);
                intent1.putExtra("from", "AnswerReportActivity");
                if (isPractice) {
                    intent1.putExtra("isPractice", true);
                    intent1.putExtra("nodeId", nodeId);//???id
                    intent1.putExtra("nodeType", nodeType);//?????? 1.?????? 2.??????
                } else {//?????????
                    intent1.putExtra("isPractice", false);
                    //??????ID
                    intent1.putExtra("testPaperId", testPaperId);
                }
                intent1.putExtra("jxType", 1);//?????? 1.???????????? 2.????????????
                startActivity(intent1);
            }
        });
        all_jx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AnswerReportActivity.this, ExerciseAnalysisActivity.class);
                intent1.putExtra("from", "AnswerReportActivity");
                if (isPractice) {
                    intent1.putExtra("isPractice", true);
                    intent1.putExtra("nodeId", nodeId);//???id
                    intent1.putExtra("nodeType", nodeType);//?????? 1.?????? 2.??????
                } else {//?????????
                    intent1.putExtra("isPractice", false);
                    //??????ID
                    intent1.putExtra("testPaperId", testPaperId);
                }
                intent1.putExtra("title", "??????");
                intent1.putExtra("jxType", 2);//?????? 1.???????????? 2.????????????
                startActivity(intent1);
            }
        });
    }

    /**
     * ?????????
     */
    private void initNetWork() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("nodeId", nodeId);
        map.put("nodeType", nodeType);
        NovateUtils.getInstance().Post(AnswerReportActivity.this, BaseUrl.AnswerReport, map, true,
                new NovateUtils.setRequestReturn<AnswerReportBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(AnswerReportActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AnswerReportBean response) {
                        if (response != null && response.getData() != null) {
                            AnswerReportBean.DataBean data = response.getData();
                            inCpvData((int) Double.parseDouble(data.getAccuracy()));
                            answer_report_first_name.setText(data.getSubjectFirstTypeName());
                            answer_report_second_name.setText(data.getSubjectSecondTypeName());
                            answer_report_answer_number.setText("???????????????" + data.getCompleteNumber() + "/" + data.getCountNumber());
                            answer_report_use_time.setText("?????????" + data.getSumDuration());
                            max_correct_rate.setText(data.getTotalMaxAccuracy());
                            average_correct_rate.setText(data.getTotalAveAccuracy());
                            exceed_correct_rate.setText(data.getDefeatAccuracy());
                            number_of_correct_answers.setText(data.getRightNumber());
                            number_of_wrong_answers.setText(data.getErrorNumber());
                            initACVData(data.getScoreList());
                        }
                    }
                });
    }

    /**
     * ?????????
     */
    private void initTestNetWork() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("testPaperId", testPaperId);
        NovateUtils.getInstance().Post(AnswerReportActivity.this, BaseUrl.testAnswerReport, map, true,
                new NovateUtils.setRequestReturn<AnswerReportBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(AnswerReportActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(AnswerReportBean response) {
                        if (response != null && response.getData() != null) {
                            AnswerReportBean.DataBean data = response.getData();
                            inCpvData((int) Double.parseDouble(data.getAccuracy()));
                            answer_report_first_name.setText(data.getSubjectFirstTypeName());
                            answer_report_second_name.setText(data.getSubjectSecondTypeName());
                            answer_report_answer_number.setText("???????????????" + data.getCompleteNumber() + "/" + data.getCountNumber());
                            answer_report_use_time.setText("?????????" + data.getSumDuration());
                            max_correct_rate.setText(data.getTotalMaxAccuracy());
                            average_correct_rate.setText(data.getTotalAveAccuracy());
                            exceed_correct_rate.setText(data.getDefeatAccuracy());
                            number_of_correct_answers.setText(data.getRightNumber());
                            number_of_wrong_answers.setText(data.getErrorNumber());
                            initACVData(data.getScoreList());
                        }
                    }
                });
    }

    private void initACVData(List<AnswerReportBean.DataBean.ScoreListBean> scoreListBeans) {
        number_of_questions.setText("??????" + scoreListBeans.size() + "???");
        AchievementCurveView lineGraphicView = new AchievementCurveView(AnswerReportActivity.this);
        yList = new ArrayList<>();
        xRawDatas1 = new ArrayList<String>();
        for (int i = 0; i < scoreListBeans.size(); i++) {
            yList.add(scoreListBeans.get(i).getAccuracy());
            xRawDatas1.add(i + "");
        }
        lineGraphicView.setData(yList, xRawDatas1, 100, 20);
        achievement_curve_acv_ll.addView(lineGraphicView);
    }

    private void inCpvData(int accuracy) {
        answer_report_cpv.setProgress(accuracy);
        answer_report_cpv.setRadius(UnitConversionUtil.dp2px(this, 30));//????????????
        answer_report_cpv.setStokewidth(UnitConversionUtil.dp2px(this, 5));//????????????
        answer_report_cpv.setTextSize(UnitConversionUtil.dp2px(this, 16));//????????????????????????
        answer_report_cpv.setColor(UnitConversionUtil.color2int("#F2F5FF"), UnitConversionUtil.color2int("#5D7DFF"),
                UnitConversionUtil.color2int("#939393"));//?????????????????????????????????????????????????????????????????????????????????
        answer_report_cpv.setSpeed(20);//????????????????????????????????????????????????????????????????????????????????????????????????????????????
    }
}