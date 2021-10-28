package com.bjsn909429077.stz.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.SortCourseAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.StudyDirectionBean;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.DensityUtil;
import com.jiangjun.library.utils.SharedPreferencesUtil;
import com.tamic.novate.Throwable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class SortActivity2 extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.sort_recyclerView)
    RecyclerView sort_recyclerView;
    @BindView(R.id.tv_sort_btn)
    TextView tv_sort_btn;
    private SortCourseAdapter sortCourseAdapter;

    private List<StudyDirectionBean.DataBean> courseList = new ArrayList<>();
    private final int ITEM_EDGE_PADDING = 15;
    private final int SPAN_SIZE = 3;

    private int width;
    private Intent intent;
    private Integer mFirstTypeId;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_sort;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        commonTitleView.setRightIcon(R.drawable.icon_sort_close, view -> finish());
        commonTitleView.setTitle(R.string.sort_title);
        commonTitleView.setLeftGone();
        mFirstTypeId = (int) SharedPreferencesUtil.getData(mContext, "firstTypeId", -1);
        tv_sort_btn.setOnClickListener(this);
        initList();
        initClick();
    }

    private void initList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, SPAN_SIZE);
        sort_recyclerView.setLayoutManager(gridLayoutManager);
        sortCourseAdapter = new SortCourseAdapter(R.layout.sort_course_item, courseList, this);
        sort_recyclerView.setAdapter(sortCourseAdapter);
        sort_recyclerView.addItemDecoration(getItemDecoration());
        sortCourseAdapter.notifyDataSetChanged();
    }

    @NotNull
    private RecyclerView.ItemDecoration getItemDecoration() {
        final int itemWidth = DensityUtil.dip2px(mContext, 114);//子条目宽度
        final int space = width - itemWidth * SPAN_SIZE;
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int eachWidth = space / SPAN_SIZE;
                int mode = parent.getChildLayoutPosition(view) % 3;
                outRect.top = 0;
                outRect.bottom = 0;
                if (mode == 0) {
                    outRect.left = DensityUtil.dip2px(mContext, ITEM_EDGE_PADDING);
                    outRect.right = eachWidth / 4;
                } else if (mode == SPAN_SIZE - 1) {
                    outRect.right = DensityUtil.dip2px(mContext, ITEM_EDGE_PADDING);
                    outRect.left = eachWidth / 4;
                } else {
                    outRect.left = eachWidth / 4;
                    outRect.right = eachWidth / 4;
                }
            }
        };
    }

    private void initClick() {
        //列表点击事件
        sortCourseAdapter.setOnSoftItemClickListener(position -> {
            intent = new Intent();
            intent.putExtra("title", courseList.get(position).name);
            intent.putExtra("id", courseList.get(position).id);
        });
    }

    @Override
    protected void initData() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("typeId", 0);
        NovateUtils.getInstance().Post(mContext, BaseUrl.studyDirection, map, true,
                new NovateUtils.setRequestReturn<StudyDirectionBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(StudyDirectionBean response) {
                        if (response != null && response.data != null) {
                            courseList.addAll(response.data);
                            for (int i = 0; i < courseList.size(); i++) {
                                if (courseList.get(i).id == mFirstTypeId) {
                                    courseList.get(i).isSelect = true;
                                }
                            }
                            sortCourseAdapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sort_btn:
                if (intent != null) {
                    SharedPreferencesUtil.saveData(mContext, "firstTypeId", intent.getIntExtra("id", -1));
                    SharedPreferencesUtil.saveData(mContext, "title", intent.getStringExtra("title"));
                    //请选择辅导
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
        }
    }
}