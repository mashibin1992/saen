package com.bjsn909429077.stz.ui.video.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.NoteAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.NoteListBean;
import com.bjsn909429077.stz.ui.study.activity.EditNoteActivity;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseLazyLoadFragment;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class NoteFragment extends BaseLazyLoadFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.tv_add_note)
    TextView tv_add_note;
    private NoteAdapter mNoteAdapter;
    private int recentlyStudyClassHourId;
    private int courseId;
    private final ArrayList<NoteListBean.DataBean> dataBeans = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_note;
    }

    @Override
    protected void init() {

        if (getArguments() != null) {
            recentlyStudyClassHourId = getArguments().getInt("recentlyStudyClassHourId", 0);
            courseId = getArguments().getInt("id", 0);
        }

        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        mNoteAdapter = new NoteAdapter(R.layout.item_note, dataBeans, this);
        recycler_view.setAdapter(mNoteAdapter);
        initListener();
    }

    private void initListener() {
        tv_add_note.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditNoteActivity.class);
            intent.putExtra("content", "");
            intent.putExtra("isEdit", 0);
            intent.putExtra("recentlyStudyClassHourId", recentlyStudyClassHourId);
            intent.putExtra("id", courseId);
            startActivity(intent);
        });
        //编辑按钮点击事件
        mNoteAdapter.setOnEditListener((data, position) -> {
            Intent intent = new Intent(mContext, EditNoteActivity.class);
            intent.putExtra("content", data.content);
            intent.putExtra("isEdit", 1);
            intent.putExtra("classHourId", data.classHourId);
            intent.putExtra("recentlyStudyClassHourId", recentlyStudyClassHourId);
            intent.putExtra("id", courseId);
            startActivity(intent);
        });
    }

    @Override
    protected void loadData() {
        dataBeans.clear();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("classHourId", recentlyStudyClassHourId);
        map.put("courseId", courseId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.classhour, map, true,
                new NovateUtils.setRequestReturn<NoteListBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("SortHomeModel", "onError: " + throwable);
                    }

                    @Override
                    public void onSuccee(NoteListBean response) {
                        if (response != null && response.data != null) {
                            dataBeans.addAll(response.data);
                            mNoteAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    public static NoteFragment getInstance(Bundle bundle) {
        NoteFragment noteFragment = new NoteFragment();
        noteFragment.setArguments(bundle);
        return noteFragment;
    }
}
