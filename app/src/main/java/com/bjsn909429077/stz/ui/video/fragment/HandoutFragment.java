package com.bjsn909429077.stz.ui.video.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.HandoutAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CourseWareBean;
import com.bjsn909429077.stz.bean.NoteListBean;
import com.bjsn909429077.stz.utils.NoteTakeInfo;
import com.bjsn909429077.stz.utils.TakePhotoPopWin;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class HandoutFragment extends BaseLazyLoadFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private int courseId;
    private ArrayList<CourseWareBean.DataBean> arrayList = new ArrayList<>();
    private HandoutAdapter handoutAdapter;
    private NoteTakeInfo noteTakeInfo;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_handout;
    }

    @Override
    protected void init() {

        Bundle arguments = getArguments();
        if (arguments != null) {
            courseId = arguments.getInt("courseId", 0);
        }
        noteTakeInfo = new NoteTakeInfo(mContext);

        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        handoutAdapter = new HandoutAdapter(arrayList, this);
        recycler_view.setAdapter(handoutAdapter);
        //列表点击事件
        handoutAdapter.setOnItemClickListener(dataBean -> {
//            noteTakeInfo.setContent(dataBean.);
            Log.d("jiangyi", "init: "+dataBean.filePath);
        });
    }

    @Override
    protected void loadData() {
        arrayList.clear();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("courseId", courseId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.courseware, map, true,
                new NovateUtils.setRequestReturn<CourseWareBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(CourseWareBean response) {
                        if (response != null && response.data != null) {
                            arrayList.addAll(response.data);
                            handoutAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public static HandoutFragment getInstance(Bundle bundle) {
        HandoutFragment handoutFragment = new HandoutFragment();
        handoutFragment.setArguments(bundle);
        return handoutFragment;
    }
}
