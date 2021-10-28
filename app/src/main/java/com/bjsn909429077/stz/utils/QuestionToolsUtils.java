package com.bjsn909429077.stz.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.bean.ChangeTextSizeBean;
import com.bjsn909429077.stz.bean.ShiJuanTestBean;
import com.bjsn909429077.stz.bean.SubjectQuestionBean;
import com.bjsn909429077.stz.ui.questionbank.activity.AnswerCardActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.QuestionActivity;
import com.bjsn909429077.stz.ui.questionbank.dialogfragment.JJDialogFragment;
import com.bjsn909429077.stz.ui.questionbank.dialogfragment.JSQDialogFragment;
import com.bjsn909429077.stz.widget.ChangeTextSizeView;
import com.jiangjun.library.utils.SharedPreferencesUtil;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.fragment.app.FragmentManager;

/**
 * @Author: Ke.Chen
 * @Description:答题页面小工具的工具类
 * @Date :2021/9/13 11:38
 **/
public class QuestionToolsUtils {

    private CountDownTimer countDownTimer;
    private JJDialogFragment jjDialogFragment;
    private boolean isShowMore = false;
    private JSQDialogFragment jsqDialogFragment;
    public boolean isAutomaticNext = true;
    private CountDownTimer countDownTimer1;
    private static volatile QuestionToolsUtils instance;

    private QuestionToolsUtils() {
        if (instance != null) {
            throw new RuntimeException("instance is exist!");
        }
    }

    public static QuestionToolsUtils getInstance() {
        if (instance == null) {
            synchronized (QuestionToolsUtils.class) {
                if (instance == null) {
                    instance = new QuestionToolsUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 计时器
     *
     * @param question_js
     * @param timeLength  累计时长 单位秒
     */
    public void jiShiQi(ChangeTextSizeView question_js, boolean isPractice, String timeLength) {
        if (isPractice) {//练习类
            final int[] totalTime = {Integer.parseInt(timeLength)};
            question_js.setText(UnitConversionUtil.timeConversion(totalTime[0]));
            countDownTimer = new CountDownTimer(7200 * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    totalTime[0]++;
                    question_js.setText(UnitConversionUtil.timeConversion(totalTime[0]));
                }

                @Override
                public void onFinish() {

                }
            }.start();
            return;
        }
        //试卷类
        final int[] totalTime = {7200};
        question_js.setText(UnitConversionUtil.timeConversion(totalTime[0]));
        countDownTimer = new CountDownTimer(totalTime[0] * 1000, 1000) {
            @Override
            public void onTick(long l) {
                totalTime[0]--;
                question_js.setText(UnitConversionUtil.timeConversion(totalTime[0]));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    /**
     * 章节练习，专项练习答题卡
     *
     * @param activity
     */
    public void daTiKa(Activity activity, int nodeId, int nodeType, SubjectQuestionBean.DataBean subjectQuestionBean,boolean isPractice) {
        Intent intent = new Intent(activity, AnswerCardActivity.class);
        intent.putExtra("isPractice",isPractice);
        intent.putExtra("nodeId",nodeId);
        intent.putExtra("nodeType",nodeType);
        intent.putExtra("subjectQuestionBean",subjectQuestionBean);
        activity.startActivity(intent);
    }

    /**
     * 试卷答题卡
     *
     * @param activity
     */
    public void ShiJuanDaTiKa(Activity activity, int testPaperId, ShiJuanTestBean.DataBean shiJuanTestBean,boolean isPractice) {
        Intent intent = new Intent(activity, AnswerCardActivity.class);
        intent.putExtra("isPractice",isPractice);
        intent.putExtra("testPaperId",testPaperId);
        intent.putExtra("shiJuanTestBean",shiJuanTestBean);
        activity.startActivity(intent);
    }

    /**
     * 交卷
     *
     * @param fragmentManager
     */
    public void jiaoJuan(FragmentManager fragmentManager,int nodeId,int nodeType,JJDialogFragment.DismissThis activity) {
        if (jjDialogFragment == null)
            jjDialogFragment = new JJDialogFragment();
        /*Bundle bundle = new Bundle();
        bundle.putBoolean("isPractice", true);
        bundle.putInt("nodeId", nodeId);
        bundle.putInt("nodeType", nodeType);
        jjDialogFragment.setArguments(bundle);*/
        jjDialogFragment.show(fragmentManager, "jjDialogFragment");
        jjDialogFragment.setDismissThis(activity);
    }

    /**
     * 试卷交卷
     *
     * @param fragmentManager
     */
    public void testJiaoJuan(FragmentManager fragmentManager,int testPaperId,JJDialogFragment.DismissThis activity) {
        if (jjDialogFragment == null)
            jjDialogFragment = new JJDialogFragment();
        /*Bundle bundle = new Bundle();
        bundle.putBoolean("isPractice", false);
        bundle.putInt("testPaperId", testPaperId);
        jjDialogFragment.setArguments(bundle);*/
        jjDialogFragment.show(fragmentManager, "jjDialogFragment");
        jjDialogFragment.setDismissThis(activity);
    }

    /**
     * 更多小工具
     *
     * @param question_more_ll
     */
    public void more(LinearLayout question_more_ll) {
        isShowMore = !isShowMore;
        question_more_ll.setVisibility(isShowMore ? View.VISIBLE : View.GONE);
    }

    /**
     * 更多小工具--计算器
     *
     * @param fragmentManager
     */
    public void JiSuanQi(FragmentManager fragmentManager) {
        if (jsqDialogFragment == null)
            jsqDialogFragment = new JSQDialogFragment();
        jsqDialogFragment.show(fragmentManager, "jsqDialogFragment");
    }

    /**
     * 更多小工具--自动切换下一题
     */
    public void AutomaticNext(boolean checked) {
        isAutomaticNext = checked;
    }

    /**
     * 更多小工具--改变字体
     * 0；小，1：中，2：大
     * 现在ui是中号，小号是当前字号小2个，大号是当前字号大4号。
     */
    public void ChangeSize(Context context, int type, ArrayList<ChangeTextSizeBean> changeTextSizeBeans, ChangeTextSizeView small, ChangeTextSizeView in,
                           ChangeTextSizeView large) {
        SharedPreferencesUtil.saveData(context, "sizeType", type);
        if (0 == type) {
            small.setBackground(context.getDrawable(R.drawable.circle_rectangle_5f7dff_2));
            small.setTextColor(context.getResources().getColor(R.color.color_FFFFFF));
            in.setBackground(context.getDrawable(R.drawable.circle_hollow_rectangle_ffffff_dddddd_3));
            in.setTextColor(context.getResources().getColor(R.color.color_999999));
            large.setBackground(context.getDrawable(R.drawable.circle_hollow_rectangle_ffffff_dddddd_3));
            large.setTextColor(context.getResources().getColor(R.color.color_999999));
        } else if (1 == type) {
            in.setBackground(context.getDrawable(R.drawable.circle_rectangle_5f7dff_2));
            in.setTextColor(context.getResources().getColor(R.color.color_FFFFFF));
            small.setBackground(context.getDrawable(R.drawable.circle_hollow_rectangle_ffffff_dddddd_3));
            small.setTextColor(context.getResources().getColor(R.color.color_999999));
            large.setBackground(context.getDrawable(R.drawable.circle_hollow_rectangle_ffffff_dddddd_3));
            large.setTextColor(context.getResources().getColor(R.color.color_999999));
        } else if (2 == type) {
            large.setBackground(context.getDrawable(R.drawable.circle_rectangle_5f7dff_2));
            large.setTextColor(context.getResources().getColor(R.color.color_FFFFFF));
            in.setBackground(context.getDrawable(R.drawable.circle_hollow_rectangle_ffffff_dddddd_3));
            in.setTextColor(context.getResources().getColor(R.color.color_999999));
            small.setBackground(context.getDrawable(R.drawable.circle_hollow_rectangle_ffffff_dddddd_3));
            small.setTextColor(context.getResources().getColor(R.color.color_999999));
        }
        for (int i = 0; i < changeTextSizeBeans.size(); i++) {
            if (0 == type)
                changeTextSizeBeans.get(i).getChangeTextSizeView().setTextSize(changeTextSizeBeans.get(i).getSize() - 2);
            else if (1 == type)
                changeTextSizeBeans.get(i).getChangeTextSizeView().setTextSize(changeTextSizeBeans.get(i).getSize());
            else if (2 == type)
                changeTextSizeBeans.get(i).getChangeTextSizeView().setTextSize(changeTextSizeBeans.get(i).getSize() + 2);
        }
    }


    final int[] useTime = {0};
    public void thisQuestionUseTimeDJS() {
        useTime[0]=0;
        if (countDownTimer1 != null) {
            countDownTimer1.cancel();
            countDownTimer1.onFinish();
            countDownTimer1 = null;
        }
        countDownTimer1 = new CountDownTimer(7200 * 2000, 1000) {
            @Override
            public void onTick(long l) {
                useTime[0]++;
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    /**
     * 这道题所用时间
     *
     * @return
     */
    public int thisQuestionUseTime(){
        return useTime[0];
    }

    /**
     * 销毁页面
     */
    public void finsh() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }

    /*public void setMyAns*/
}
