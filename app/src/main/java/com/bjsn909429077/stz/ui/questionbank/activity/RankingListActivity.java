package com.bjsn909429077.stz.ui.questionbank.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.RankingListAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.RankingListBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.CommonTitleView;
import com.jiangjun.library.utils.ImageLoaderUtils;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RankingListActivity extends BaseActivity {

    private CommonTitleView commonTitleView;
    private RecyclerView raanking_list_rv;
    private RankingListAdapter rankingListAdapter;
    private ImageView user_photo1;
    private TextView user_phone1;
    private TextView user_desc1;
    private ImageView user_photo2;
    private TextView user_phone2;
    private TextView user_desc2;
    private ImageView user_photo3;
    private TextView user_phone3;
    private TextView user_desc3;
    private ArrayList<RankingListBean.DataBean> dataBeanArrayList=new ArrayList<>();

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_ranking_list;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        commonTitleView = (CommonTitleView) findViewById(com.jiangjun.library.R.id.common_title);
        commonTitleView.setTitle("答案排行榜");
        raanking_list_rv = findViewById(R.id.raanking_list_rv);
        user_photo1 = findViewById(R.id.user_photo1);
        user_phone1 = findViewById(R.id.user_phone1);
        user_desc1 = findViewById(R.id.user_desc1);
        user_photo2 = findViewById(R.id.user_photo2);
        user_phone2 = findViewById(R.id.user_phone2);
        user_desc2 = findViewById(R.id.user_desc2);
        user_photo3 = findViewById(R.id.user_photo3);
        user_phone3 = findViewById(R.id.user_phone3);
        user_desc3 = findViewById(R.id.user_desc3);
    }

    @Override
    protected void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("secondId", (int) SharedPreferencesUtil.getData(RankingListActivity.this, "secondId", -1));
        NovateUtils.getInstance().Post(RankingListActivity.this, BaseUrl.rankingList, map, true,
                new NovateUtils.setRequestReturn<RankingListBean>() {

                    private LinearLayoutManager linearLayoutManager;

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(RankingListActivity.this, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(RankingListBean response) {
                        if (response != null && response.getData() != null && response.getData().size() > 0) {


                            switch (response.getData().size()) {
                                case 1:
                                    if (response.getData().get(0) != null) {
                                        user_phone2.setText(response.getData().get(0).getUserName());
                                        user_desc2.setText(response.getData().get(0).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(0).getUserHead(), user_photo2);
                                    }
                                    break;
                                case 2:
                                    if (response.getData().get(1) != null) {
                                        user_phone1.setText(response.getData().get(1).getUserName());
                                        user_desc1.setText(response.getData().get(1).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(1).getUserHead(), user_photo1);
                                    }
                                    if (response.getData().get(0) != null) {
                                        user_phone2.setText(response.getData().get(0).getUserName());
                                        user_desc2.setText(response.getData().get(0).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(0).getUserHead(), user_photo2);
                                    }
                                    break;
                                case 3:

                                    if (response.getData().get(1) != null) {
                                        user_phone1.setText(response.getData().get(1).getUserName());
                                        user_desc1.setText(response.getData().get(1).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(1).getUserHead(), user_photo1);
                                    }
                                    if (response.getData().get(0) != null) {
                                        user_phone2.setText(response.getData().get(0).getUserName());
                                        user_desc2.setText(response.getData().get(0).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(0).getUserHead(), user_photo2);
                                    }
                                    if (response.getData().get(2) != null) {
                                        user_phone3.setText(response.getData().get(2).getUserName());
                                        user_desc3.setText(response.getData().get(2).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(2).getUserHead(), user_photo3);
                                    }
                                    break;
                                default:
                                    if (response.getData().get(1) != null) {
                                        user_phone1.setText(response.getData().get(1).getUserName());
                                        user_desc1.setText(response.getData().get(1).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(1).getUserHead(), user_photo1);
                                    }
                                    if (response.getData().get(0) != null) {
                                        user_phone2.setText(response.getData().get(0).getUserName());
                                        user_desc2.setText(response.getData().get(0).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(0).getUserHead(), user_photo2);
                                    }
                                    if (response.getData().get(2) != null) {
                                        user_phone3.setText(response.getData().get(2).getUserName());
                                        user_desc3.setText(response.getData().get(2).getQuestionsNumber() + "道题");
                                        ImageLoaderUtils.loadCircleCropUrl(mContext, response.getData().get(2).getUserHead(), user_photo3);
                                    }

                                    for (int i = 3; i < response.getData().size(); i++) {
                                        response.getData().get(i).setNumber((i + 1));
                                        dataBeanArrayList.add(response.getData().get(i));
                                    }
                                    raanking_list_rv.setVisibility(View.VISIBLE);
                                    linearLayoutManager = new LinearLayoutManager(RankingListActivity.this);
                                    raanking_list_rv.setLayoutManager(linearLayoutManager);
                                    rankingListAdapter = new RankingListAdapter(R.layout.ranking_list_item, dataBeanArrayList, RankingListActivity.this);
                                    raanking_list_rv.setAdapter(rankingListAdapter);
                                    break;
                            }



                        }
                    }
                });

    }



}