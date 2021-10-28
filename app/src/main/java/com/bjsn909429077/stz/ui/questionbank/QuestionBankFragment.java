package com.bjsn909429077.stz.ui.questionbank;


import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.QuestionBankHomeBean;
import com.bjsn909429077.stz.ui.login.LoginActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.AnswerActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.ErrorQuestionBookActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.ExerciseCollectionActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.FreeQuestionBankActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.PracticeActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.RankingListActivity;
import com.bjsn909429077.stz.ui.questionbank.activity.RecordQuestionsActivity;
import com.bjsn909429077.stz.utils.FreeQuestionBackUtil;
import com.bjsn909429077.stz.utils.UnitConversionUtil;
import com.bjsn909429077.stz.widget.CircleProgressView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.user.UserConfig;
import com.jiangjun.library.utils.ImageLoaderUtils;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;


public class QuestionBankFragment extends BaseLazyLoadFragment implements FreeQuestionBackUtil.ChooseItem {

    private CircleProgressView question_back_dts;
    private CircleProgressView question_back_cts;
    private CircleProgressView question_back_zql;
    private CircleProgressView question_back_zts;
    private TextView question_back_title;
    private LinearLayout question_back_ll5;
    private TextView question_back_zjlx;
    private TextView question_back_zxlx;
    private TextView question_back_lnzt;
    private TextView question_back_mnsj;
    private TextView question_back_kqyt;
    private TextView question_back_ctb;
    private TextView question_back_xtsc;
    private TextView question_back_ztjl;
    private TextView question_back_first_name;
    private TextView question_back_second_name;
    private TextView question_back_time;
    private TextView question_back_user1;
    private TextView question_back_num1;
    private TextView question_back_user2;
    private TextView question_back_num2;
    private TextView question_back_user3;
    private TextView question_back_num3;
    private ImageView question_back_first_photo;
    private ImageView question_back_second_photo;
    private ImageView question_back_third_photo;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_question_bank;
    }

    @Override
    protected void init() {
        question_back_dts = (CircleProgressView) findViewById(R.id.question_back_dts);
        question_back_cts = (CircleProgressView) findViewById(R.id.question_back_cts);
        question_back_zql = (CircleProgressView) findViewById(R.id.question_back_zql);
        question_back_zts = (CircleProgressView) findViewById(R.id.question_back_zts);
        question_back_title = (TextView) findViewById(R.id.question_back_title);
        question_back_first_name = (TextView) findViewById(R.id.question_back_first_name);
        question_back_second_name = (TextView) findViewById(R.id.question_back_second_name);
        question_back_ll5 = (LinearLayout) findViewById(R.id.question_back_ll5);
        question_back_zjlx = (TextView) findViewById(R.id.question_back_zjlx);
        question_back_zxlx = (TextView) findViewById(R.id.question_back_zxlx);
        question_back_lnzt = (TextView) findViewById(R.id.question_back_lnzt);
        question_back_mnsj = (TextView) findViewById(R.id.question_back_mnsj);
        question_back_kqyt = (TextView) findViewById(R.id.question_back_kqyt);
        question_back_ctb = (TextView) findViewById(R.id.question_back_ctb);
        question_back_xtsc = (TextView) findViewById(R.id.question_back_xtsc);
        question_back_ztjl = (TextView) findViewById(R.id.question_back_ztjl);
        question_back_time = (TextView) findViewById(R.id.question_back_time);
        question_back_user1 = (TextView) findViewById(R.id.question_back_user1);
        question_back_num1 = (TextView) findViewById(R.id.question_back_num1);
        question_back_user2 = (TextView) findViewById(R.id.question_back_user2);
        question_back_num2 = (TextView) findViewById(R.id.question_back_num2);
        question_back_user3 = (TextView) findViewById(R.id.question_back_user3);
        question_back_num3 = (TextView) findViewById(R.id.question_back_num3);
        question_back_first_photo = (ImageView) findViewById(R.id.question_back_first_photo);
        question_back_second_photo = (ImageView) findViewById(R.id.question_back_second_photo);
        question_back_third_photo = (ImageView) findViewById(R.id.question_back_third_photo);
        FreeQuestionBackUtil.setChooseItem(this);
    }

    private void initClick() {
        question_back_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FreeQuestionBankActivity.class));

            }
        });
        question_back_ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RankingListActivity.class));
            }
        });
        //章节练习
        question_back_zjlx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PracticeActivity.class);
                intent.putExtra("title", "章节练习");
                intent.putExtra("type", 1);
                int secondId = (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1);
                intent.putExtra("secondId", secondId);
                startActivity(intent);
            }
        });
        //专项练习
        question_back_zxlx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PracticeActivity.class);
                intent.putExtra("title", "专项练习");
                intent.putExtra("type", 2);
                int secondId = (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1);
                intent.putExtra("secondId", secondId);
                startActivity(intent);
            }
        });
        question_back_lnzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AnswerActivity.class);
                intent.putExtra("title", "历年真题");
                intent.putExtra("type", 2);
                int secondId = (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1);
                intent.putExtra("secondId", secondId);
                startActivity(intent);
            }
        });
        question_back_mnsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AnswerActivity.class);
                intent.putExtra("title", "模拟试卷");
                intent.putExtra("type", 1);
                int secondId = (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1);
                intent.putExtra("secondId", secondId);
                startActivity(intent);
            }
        });
        question_back_kqyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AnswerActivity.class);
                intent.putExtra("title", "考前押题");
                intent.putExtra("type", 3);
                int secondId = (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1);
                intent.putExtra("secondId", secondId);
                startActivity(intent);
            }
        });
        question_back_ztjl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecordQuestionsActivity.class);
                intent.putExtra("title", "做题记录");
                startActivity(intent);
            }
        });
        question_back_ctb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ErrorQuestionBookActivity.class);
                startActivity(intent);
            }
        });
        question_back_xtsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExerciseCollectionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!UserConfig.isLogin()) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }else {
                int secondId = (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1);
                if (-1 == secondId)
                    startActivity(new Intent(getActivity(), FreeQuestionBankActivity.class));
                else
                    initNetWork(secondId);
            }
        }
    }

    /**
     * 首页-根据二级类Id进入题库首页
     */
    private void initNetWork(int secondId) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("secondId", secondId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.questionHome, map, true,
                new NovateUtils.setRequestReturn<QuestionBankHomeBean>() {

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(getActivity(), throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(QuestionBankHomeBean response) {
                        if (response != null && response.getData() != null) {
                            question_back_title.setText(response.getData().getSecondName());
                            question_back_first_name.setText(response.getData().getFirstName());
                            question_back_second_name.setText(response.getData().getSecondName());
                            initData(response.getData().getAnswerNumber(), response.getData().getErrorAnswerNumber(), response.getData().getAccuracy(),
                                    response.getData().getCountNumber());
                            question_back_time.setText(response.getData().getSumDuration());
                            if (response.getData().getRankingList() != null && response.getData().getRankingList().size() > 0 && response.getData().getRankingList().get(0) != null) {
                                if (response.getData().getRankingList().get(0).getUserName() != null)
                                    question_back_user2.setText(response.getData().getRankingList().get(0).getUserName());
                                if (response.getData().getRankingList().get(0).getQuestionsNumber() != null)
                                    question_back_num2.setText(response.getData().getRankingList().get(0).getQuestionsNumber() + "道题");
                                if (response.getData().getRankingList().get(0).getUserHead() != null)
                                    ImageLoaderUtils.loadCircleCropUrl(mContext,response.getData().getRankingList().get(0).getUserHead(),question_back_first_photo);
//                                    Glide.with(getActivity())
//                                            .load(response.getData().getRankingList().get(0).getUserHead())
//                                            .asBitmap().centerCrop()
//                                            .into(new BitmapImageViewTarget(question_back_first_photo) {
//                                                @Override
//                                                protected void setResource(Bitmap resource) {
//                                                    RoundedBitmapDrawable circularBitmapDrawable =
//                                                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
//                                                    circularBitmapDrawable.setCircular(true);
//                                                    question_back_first_photo.setImageDrawable(circularBitmapDrawable);
//                                                }
//                                            });
                            }
                            if (response.getData().getRankingList() != null && response.getData().getRankingList().size() > 1 && response.getData().getRankingList().get(1) != null) {
                                if (response.getData().getRankingList().get(1).getUserName() != null)
                                    question_back_user1.setText(response.getData().getRankingList().get(1).getUserName());
                                if (response.getData().getRankingList().get(1).getQuestionsNumber() != null)
                                    question_back_num1.setText(response.getData().getRankingList().get(1).getQuestionsNumber() + "道题");
                                if (response.getData().getRankingList().get(1).getUserHead() != null)
                                    ImageLoaderUtils.loadCircleCropUrl(mContext,response.getData().getRankingList().get(1).getUserHead(),question_back_second_photo);
//                                    Glide.with(getActivity())
//                                            .load(response.getData().getRankingList().get(1).getUserHead())
//                                            .asBitmap().centerCrop()
//                                            .into(new BitmapImageViewTarget(question_back_second_photo) {
//                                                @Override
//                                                protected void setResource(Bitmap resource) {
//                                                    RoundedBitmapDrawable circularBitmapDrawable =
//                                                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
//                                                    circularBitmapDrawable.setCircular(true);
//                                                    question_back_second_photo.setImageDrawable(circularBitmapDrawable);
//                                                }
//                                            });
                            }
                            if (response.getData().getRankingList() != null && response.getData().getRankingList().size() > 2 && response.getData().getRankingList().get(2) != null) {
                                if (response.getData().getRankingList().get(2).getUserName() != null)
                                    question_back_user3.setText(response.getData().getRankingList().get(2).getUserName());
                                if (response.getData().getRankingList().get(2).getQuestionsNumber() != null)
                                    question_back_num3.setText(response.getData().getRankingList().get(2).getQuestionsNumber() + "道题");
                                if (response.getData().getRankingList().get(2).getUserHead() != null)
                                    ImageLoaderUtils.loadCircleCropUrl(mContext,response.getData().getRankingList().get(2).getUserHead(),question_back_third_photo);
//                                    Glide.with(getActivity())
//                                            .load(response.getData().getRankingList().get(2).getUserHead())
//                                            .asBitmap().centerCrop()
//                                            .into(new BitmapImageViewTarget(question_back_third_photo) {
//                                                @Override
//                                                protected void setResource(Bitmap resource) {
//                                                    RoundedBitmapDrawable circularBitmapDrawable =
//                                                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
//                                                    circularBitmapDrawable.setCircular(true);
//                                                    question_back_third_photo.setImageDrawable(circularBitmapDrawable);
//                                                }
//                                            });
                            }
                        }
                    }
                });
    }

    private void initData(String answerNumber, String errorAnswerNumber, String accuracy, String countNumber) {
        int width = UnitConversionUtil.getScreenWidth(getActivity());
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) question_back_dts.getLayoutParams();
        layoutParams.width = width / 4 - 50;
        layoutParams.height = width / 4 - 50;
        question_back_dts.setLayoutParams(layoutParams);
        question_back_cts.setLayoutParams(layoutParams);
        question_back_zql.setLayoutParams(layoutParams);
        question_back_zts.setLayoutParams(layoutParams);

        int dts = (int) (UnitConversionUtil.circleProgressViewJinDu(answerNumber, countNumber) * 100);
        question_back_dts.setProgress(dts);
        question_back_dts.setText(answerNumber);
        question_back_dts.setRadius(UnitConversionUtil.dp2px(getContext(), 30));//设置半径
        question_back_dts.setStokewidth(UnitConversionUtil.dp2px(getContext(), 5));//设置环宽
        question_back_dts.setTextSize(UnitConversionUtil.dp2px(getContext(), 16));//设置文字进度大小
        question_back_dts.setColor(UnitConversionUtil.color2int("#F2F5FF"), UnitConversionUtil.color2int("#5D7DFF"),
                UnitConversionUtil.color2int("#939393"));//设置颜色（环的颜色，进度条的颜色，文字进度的字体颜色）
        question_back_dts.setSpeed(20);//设置动画速度，这里的数值是每次进度加一所用时间，所以数值越小动画速度越快

        int cts = (int) (UnitConversionUtil.circleProgressViewJinDu(errorAnswerNumber, countNumber) * 100);
        question_back_cts.setProgress(cts);
        question_back_cts.setText(errorAnswerNumber);
        question_back_cts.setRadius(UnitConversionUtil.dp2px(getContext(), 30));//设置半径
        question_back_cts.setStokewidth(UnitConversionUtil.dp2px(getContext(), 5));//设置环宽
        question_back_cts.setTextSize(UnitConversionUtil.dp2px(getContext(), 16));//设置文字进度大小
        question_back_cts.setColor(UnitConversionUtil.color2int("#F2F5FF"), UnitConversionUtil.color2int("#5D7DFF"),
                UnitConversionUtil.color2int("#939393"));//设置颜色（环的颜色，进度条的颜色，文字进度的字体颜色）
        question_back_cts.setSpeed(20);//设置动画速度，这里的数值是每次进度加一所用时间，所以数值越小动画速度越快

        int zql = (int) (Double.parseDouble(accuracy) * 100);
        question_back_zql.setProgress(zql);
        question_back_zql.setText(accuracy);
        question_back_zql.setRadius(UnitConversionUtil.dp2px(getContext(), 30));//设置半径
        question_back_zql.setStokewidth(UnitConversionUtil.dp2px(getContext(), 5));//设置环宽
        question_back_zql.setTextSize(UnitConversionUtil.dp2px(getContext(), 16));//设置文字进度大小
        question_back_zql.setColor(UnitConversionUtil.color2int("#F2F5FF"), UnitConversionUtil.color2int("#5D7DFF"),
                UnitConversionUtil.color2int("#939393"));//设置颜色（环的颜色，进度条的颜色，文字进度的字体颜色）
        question_back_zql.setSpeed(20);//设置动画速度，这里的数值是每次进度加一所用时间，所以数值越小动画速度越快

        question_back_zts.setProgress(100);
        question_back_zts.setText(countNumber);
        question_back_zts.setRadius(UnitConversionUtil.dp2px(getContext(), 30));//设置半径
        question_back_zts.setStokewidth(UnitConversionUtil.dp2px(getContext(), 5));//设置环宽
        question_back_zts.setTextSize(UnitConversionUtil.dp2px(getContext(), 16));//设置文字进度大小
        question_back_zts.setColor(UnitConversionUtil.color2int("#F2F5FF"), UnitConversionUtil.color2int("#5D7DFF"),
                UnitConversionUtil.color2int("#5D7DFF"));//设置颜色（环的颜色，进度条的颜色，文字进度的字体颜色）
        question_back_zts.setSpeed(20);//设置动画速度，这里的数值是每次进度加一所用时间，所以数值越小动画速度越快
        initClick();
    }

    @Override
    public void backMethod() {
        int secondId = (int) SharedPreferencesUtil.getData(getActivity(), "secondId", -1);
        if (-1 == secondId)
            startActivity(new Intent(getActivity(), FreeQuestionBankActivity.class));
        else
            initNetWork(secondId);
    }
}
