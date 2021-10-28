package com.bjsn909429077.stz.ui.study.activity;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ContinueStudyListAdapter;
import com.bjsn909429077.stz.adapter.TwoLevelAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CoursePackageListBean;
import com.bjsn909429077.stz.ui.video.VideoPlaybackActivity;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.DensityUtil;
import com.tamic.novate.Throwable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class ContinueStudyActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rv_list_top)
    RecyclerView rv_list_top;
    private ContinueStudyListAdapter continueStudyListAdapter;

    private ArrayList<CoursePackageListBean.DataBean.CourseListBean> courseList = new ArrayList<>();
    private ArrayList<CoursePackageListBean.DataBean.CourseListBean2> types = new ArrayList<>();

    private final int ITEM_EDGE_PADDING = 10;
    private final int SPAN_SIZE = 4;

    private int width;
    private TwoLevelAdapter twoLevelAdapter;
    private Integer secondTypeId = 0;
    private int coursePackageId;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_continue_study;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        commonTitleView.setTitle("套餐包含课包列表");
        Intent intent = getIntent();
        if (intent != null) {
            coursePackageId = intent.getIntExtra("coursePackageId", 0);
            secondTypeId = intent.getIntExtra("secondTypeId", 0);
        }
        initRecycler();
        initList();
        initListener();

    }

    private void initListener() {
        continueStudyListAdapter.setOnContinueClickListener(new ContinueStudyListAdapter.OnContinueClickListener() {
            @Override
            public void itemClick(CoursePackageListBean.DataBean.CourseListBean s, int position) {
                Intent intent = new Intent();
                intent.putExtra("recentlyStudyClassHourId", s.recentlyStudyClassHourId);
                intent.putExtra("id", s.id);
                switch (s.type) {
                    case 0:
                        intent.setClass(ContinueStudyActivity.this, VideoPlaybackActivity.class);
                        //录播
                        startActivity(intent);
                        break;
                    case 1:
                        //直播
                        intent.setClass(ContinueStudyActivity.this, LiveListActivity.class);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public void endTimeClick(CoursePackageListBean.DataBean.CourseListBean s, int position) {

            }
        });

        twoLevelAdapter.setOnLevelClickListener((data, position) -> {
            secondTypeId = data.id;
            courseList.clear();
            types.clear();
            initData();
        });
    }

    private void initRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        continueStudyListAdapter = new ContinueStudyListAdapter(courseList, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(continueStudyListAdapter);

    }

    private void initList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, SPAN_SIZE);
        rv_list_top.setLayoutManager(gridLayoutManager);
        twoLevelAdapter = new TwoLevelAdapter(types);
        rv_list_top.setAdapter(twoLevelAdapter);
        rv_list_top.addItemDecoration(getItemDecoration());
        twoLevelAdapter.notifyDataSetChanged();
    }

    @NotNull
    private RecyclerView.ItemDecoration getItemDecoration() {
        final int itemWidth = DensityUtil.dip2px(mContext, 78);//子条目宽度
        final int space = width - itemWidth * SPAN_SIZE;
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int eachWidth = space / SPAN_SIZE;
                int mode = parent.getChildLayoutPosition(view) % 4;
                outRect.top = 0;
                outRect.bottom = 0;
                if (mode == 0) {
                    outRect.left = DensityUtil.dip2px(mContext, ITEM_EDGE_PADDING);
                    outRect.right = eachWidth / 2;
                } else if (mode == SPAN_SIZE - 1) {
                    outRect.right = DensityUtil.dip2px(mContext, ITEM_EDGE_PADDING);
                    outRect.left = eachWidth / 2;
                } else if (mode == SPAN_SIZE - 2) {
                    outRect.left = eachWidth / 2;
                    outRect.right = eachWidth / 2;
                } else {
                    outRect.left = eachWidth / 2;
                    outRect.right = eachWidth / 2;
                }
            }
        };
    }

    @Override
    protected void initData() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("coursePackageId", coursePackageId);
        map.put("secondTypeId", secondTypeId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.coursePackageList, map, true,
                new NovateUtils.setRequestReturn<CoursePackageListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(CoursePackageListBean response) {
                        if (response != null && response.data != null) {
                            courseList.addAll(response.data.courseList);
                            types.addAll(response.data.types);
                            twoLevelAdapter.notifyDataSetChanged();
                            continueStudyListAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

}