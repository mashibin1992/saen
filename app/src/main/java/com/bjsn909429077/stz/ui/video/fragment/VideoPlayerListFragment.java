package com.bjsn909429077.stz.ui.video.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ArrangeListAdapter2;
import com.bjsn909429077.stz.adapter.ArrangeStudyListAdapter;
import com.bjsn909429077.stz.adapter.ArrangeStudyListAdapter2;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.CoursePackageInfoBean;
import com.bjsn909429077.stz.bean.CoursePackageList;
import com.bjsn909429077.stz.bean.NoteListBean;
import com.bjsn909429077.stz.ui.course.contract.CourseArrangeContract;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.jiangjun.library.utils.ToastUtils;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class VideoPlayerListFragment extends BaseLazyLoadFragment implements CourseArrangeContract<CoursePackageList.DataBean.HoursBean>
        , ArrangeStudyListAdapter2.OnItemDeleteClickListener, ArrangeStudyListAdapter.OnItemDeleteAllClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private String isShowType = "0";
    private int courseId;
    private
    ArrayList<CoursePackageList.DataBean> arrayList = new ArrayList<>();
    private ArrangeStudyListAdapter arrangeListAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_video_player_list;
    }

    @Override
    protected void init() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            courseId = arguments.getInt("courseId", 0);
        }
        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        initData();
        arrangeListAdapter = new ArrangeStudyListAdapter(R.layout.item_two_list, arrayList, this);
        recycler_view.setAdapter(arrangeListAdapter);
        arrangeListAdapter.setIsShowDownload(true);
        if (arguments != null) {
            switch (arguments.getString("isShowType")) {
                case "0":
                    arrangeListAdapter.isShowDelete(false);
                    break;
                case "1":
                    arrangeListAdapter.isShowDelete(true);
                    break;
            }
        }
        initListener();
    }

    private void initListener() {
        arrangeListAdapter.setOnItemDeleteAllClickListener(this);
    }

    private void initData() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("courseId", courseId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.chaptersList, map, true,
                new NovateUtils.setRequestReturn<CoursePackageList>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(CoursePackageList response) {
                        if (response != null && response.data != null) {
                            arrayList.addAll(response.data);
                            arrangeListAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void courseArrangeItemCallback(String data, int position) {
        ToastUtils.Toast(mContext, position);
    }

    @Override
    public void itemPlay(CoursePackageList.DataBean.HoursBean data, int position) {
        ToastUtils.Toast(mContext, "??????");
    }

    @Override
    public void itemSpend(CoursePackageList.DataBean.HoursBean data, int position) {
        ToastUtils.Toast(mContext, "??????");
    }

    @Override
    public void itemDownload(CoursePackageList.DataBean.HoursBean data, TextView view, ProgressBar progressBar, int position) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    int finalI = i;
                    getActivity().runOnUiThread(() -> progressBar.setProgress(finalI + 1));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @param s              ????????????????????????
     * @param layoutPosition
     */
    @Override
    public void itemLock(CoursePackageList.DataBean.HoursBean s, int layoutPosition) {
        ToastUtils.Toast(mContext, "??????????????????");
    }


    /**
     * ??????
     *
     * @param data
     * @param position
     */
    @Override
    public void itemDelete(CoursePackageList.DataBean.HoursBean data, int position) {
        ToastUtils.Toast(mContext, "??????");
    }

    /**
     * ????????????
     *
     * @param data
     * @param position
     */
    @Override
    public void itemDeleteAll(CoursePackageList.DataBean data, int position) {
        ToastUtils.Toast(mContext, "????????????");
    }

    /**
     * @param bundle
     * @return
     */
    public static VideoPlayerListFragment getInstance(Bundle bundle) {
        VideoPlayerListFragment videoPlayerListFragment = new VideoPlayerListFragment();
        videoPlayerListFragment.setArguments(bundle);
        return videoPlayerListFragment;
    }

}
