package com.bjsn909429077.stz.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.SelectCourseListAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CoursePackageBean;
import com.bjsn909429077.stz.ui.course.contract.SelectCourseListContract;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 选课列表
 */

public class SelectCourseListFragment extends BaseLazyLoadFragment implements SelectCourseListContract {
    @BindView(R.id.course_list)
    RecyclerView course_list;
    @BindView(R.id.sml)
    SmartRefreshLayout sml;
    private SelectCourseListAdapter selectCourseListAdapter;
    private ArrayList<CoursePackageBean.DataDTO> dataDTOS = new ArrayList<>();
    private int firstTypeId;
    private int type;
    private int pageNo = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_course_list;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            firstTypeId = arguments.getInt("firstTypeId", 0);
            type = arguments.getInt("type", 0);
            if (type == 2) {
                type += 1;
            }
        }
        initRecyclerView();
        initListener();
        initData();
    }

    private void initData() {
        Map<String, Integer> map = new HashMap<>();
        CourseSelectionFragment parentFragment = (CourseSelectionFragment) getParentFragment();
        map.put("firstTypeId", parentFragment.firstTypeId);
        map.put("page", pageNo);
        map.put("type", type);
        Log.d("参数", "loadData: " + map.toString());
        NovateUtils.getInstance().Post(mContext, BaseUrl.coursePackage, map, true,
                new NovateUtils.setRequestReturn<CoursePackageBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(CoursePackageBean response) {
                        sml.finishRefresh();
                        sml.finishLoadMore();
                        if (response != null && response.data != null) {
                            dataDTOS.addAll(response.data);
                            selectCourseListAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }


    private void initRecyclerView() {
        course_list.setLayoutManager(new LinearLayoutManager(mContext));
        selectCourseListAdapter = new SelectCourseListAdapter(dataDTOS, this);
        course_list.setAdapter(selectCourseListAdapter);
    }
    //列表点击回调
    @Override
    public void listItemOnClick(int position) {
        Intent intent = new Intent(mContext, SelectCourseActivity.class);
        intent.putExtra("coursePackageId", dataDTOS.get(position).coursePackageId);
        startActivity(intent);
    }

    private void initListener() {
        sml.setOnRefreshListener(refreshLayout -> {
            dataDTOS.clear();
            pageNo = 0;
            initData();
        });
        sml.setOnLoadMoreListener(refreshLayout -> {
            pageNo++;
            initData();
        });
    }

    @Override
    protected void loadData() {

    }

    public void loadData2() {
        if (dataDTOS != null) {
            dataDTOS.clear();
        }
        initData();
    }

    public void refresh() {
        if (sml != null) {
            sml.autoRefresh();
        }
    }

    public static SelectCourseListFragment getInstance(Bundle bundle) {
        SelectCourseListFragment selectCourseListFragment = new SelectCourseListFragment();
        selectCourseListFragment.setArguments(bundle);
        return selectCourseListFragment;
    }
}
