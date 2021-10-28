package com.bjsn909429077.stz.ui.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.GuideInfoBean;
import com.bjsn909429077.stz.bean.HasBuyListBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.StatusBarUtil;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import butterknife.BindView;

public class SortGuideActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.tv_sign_up)
    TextView tv_sign_up;
    @BindView(R.id.tv_print)
    TextView tv_print;
    @BindView(R.id.examination)
    TextView examination;
    @BindView(R.id.tv_inquiry_type)
    TextView tv_inquiry_type;
    @BindView(R.id.collectionType)
    TextView collectionType;
    @BindView(R.id.tv_number_1)
    TextView tv_number_1;
    @BindView(R.id.tv_number_2)
    TextView tv_number_2;
    @BindView(R.id.tv_number_3)
    TextView tv_number_3;
    @BindView(R.id.tv_number_4)
    TextView tv_number_4;
    @BindView(R.id.tv_number_5)
    TextView tv_number_5;
    @BindView(R.id.tv_register_date)
    TextView tv_register_date;
    @BindView(R.id.tv_print_date)
    TextView tv_print_date;
    @BindView(R.id.examination_time)
    TextView examination_time;
    @BindView(R.id.tv_inquiry_type_time)
    TextView tv_inquiry_type_time;
    @BindView(R.id.collection_time)
    TextView collection_time;
    private int id;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_sort_guide;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        view_head.setVisibility(View.GONE);
        initListener();
    }

    private void initListener() {
        if (getIntent() != null) {
            id = getIntent().getIntExtra("id", 0);
        }
        img_back.setOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {

        HashMap<String, Integer> map = new HashMap<>();
        map.put("firstTypeId", id);
        NovateUtils.getInstance().Post(mContext, BaseUrl.guideInfo, map, true,
                new NovateUtils.setRequestReturn<GuideInfoBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(GuideInfoBean response) {
                        if (response != null && response.data != null) {
                            GuideInfoBean.DataBean data = response.data;
                            tv_register_date.setText(data.registrationBtime + "~" + data.registrationEtime);
                            tv_print_date.setText(data.printingBtime + "~" + data.printingEtime);
                            examination_time.setText(data.examinationBtime + "~" + data.examinationEtime);
                            tv_inquiry_type_time.setText(data.inquiryBtime + "~" + data.inquiryEtime);
                            collection_time.setText(data.collectionBtime + "~" + data.collectionEtime);

//                            报名类型：0、未开始 1、进行中 2、已结束
                            switch (data.registrationType) {
                                case 0:
                                    tv_sign_up.setText(R.string.tv_not_started);
                                    tv_number_1.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    tv_sign_up.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 1:
                                    tv_sign_up.setText(R.string.have_in_hand);
                                    tv_number_1.setBackground(getDrawable(R.drawable.sort_guide_sequence_blue));
                                    tv_sign_up.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 2:
                                    tv_sign_up.setText(R.string.has_ended);
                                    tv_number_1.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    tv_sign_up.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_gray));
                                    break;
                            }
                            //准考证类型：0、未开始 1、进行中 2、已结束
                            switch (data.printingType) {
                                case 0:
                                    tv_print.setText(R.string.tv_not_started);
                                    tv_number_2.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    tv_print.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 1:
                                    tv_print.setText(R.string.have_in_hand);
                                    tv_number_2.setBackground(getDrawable(R.drawable.sort_guide_sequence_blue));
                                    tv_print.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 2:
                                    tv_print.setText(R.string.has_ended);
                                    tv_number_2.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    tv_print.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_gray));
                                    break;
                            }
                            //考试类型：0、未开始 1、进行中 2、已结束
                            switch (data.examinationType) {
                                case 0:
                                    examination.setText(R.string.tv_not_started);
                                    tv_number_3.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    examination.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 1:
                                    examination.setText(R.string.have_in_hand);
                                    tv_number_3.setBackground(getDrawable(R.drawable.sort_guide_sequence_blue));
                                    examination.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 2:
                                    examination.setText(R.string.has_ended);
                                    tv_number_3.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    examination.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_gray));
                                    break;
                            }

                            //成绩查询类型：0、未开始 1、进行中 2、已结束
                            switch (data.examinationType) {
                                case 0:
                                    tv_inquiry_type.setText(R.string.tv_not_started);
                                    tv_number_4.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    tv_inquiry_type.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 1:
                                    tv_inquiry_type.setText(R.string.have_in_hand);
                                    tv_number_4.setBackground(getDrawable(R.drawable.sort_guide_sequence_blue));
                                    tv_inquiry_type.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 2:
                                    tv_inquiry_type.setText(R.string.has_ended);
                                    tv_number_4.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    tv_inquiry_type.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_gray));
                                    break;
                            }
                            //领取合格证类型：0、未开始 1、进行中 2、已结束
                            switch (data.examinationType) {
                                case 0:
                                    collectionType.setText(R.string.tv_not_started);
                                    tv_number_5.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    collectionType.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 1:
                                    collectionType.setText(R.string.have_in_hand);
                                    tv_number_5.setBackground(getDrawable(R.drawable.sort_guide_sequence_blue));
                                    collectionType.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_blue));
                                    break;
                                case 2:
                                    collectionType.setText(R.string.has_ended);
                                    tv_number_5.setBackground(getDrawable(R.drawable.sort_guide_sequence_gray));
                                    collectionType.setBackground(getDrawable(R.drawable.sort_guide_btn_bg_gray));
                                    break;
                            }


                        }
                    }
                });
    }
}