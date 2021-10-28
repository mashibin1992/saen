package com.bjsn909429077.stz.ui.study.activity;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.BaseQuestionBean;
import com.bjsn909429077.stz.bean.CoursePackageListBean;
import com.bjsn909429077.stz.bean.EventBean;
import com.bjsn909429077.stz.ui.study.contract.EditNoteContract;
import com.bjsn909429077.stz.ui.study.model.EditNoteModel;
import com.bjsn909429077.stz.utils.EditChangedListener;
import com.blankj.utilcode.util.StringUtils;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.download.utils.ToastUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.tamic.novate.Throwable;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.rxbus.RxBus;

public class EditNoteActivity extends BaseActivity implements EditNoteContract {
    @BindView(R.id.et_happened)
    EditText et_happened;
    @BindView(R.id.tv_inputnum)
    TextView tv_inputnum;

    private static final int maxCount = 1000;
    private int recentlyStudyClassHourId;
    private int courseId;//课程id
    private int isEdit; //编辑还是添加
    private int classHourId;//课时id
    private EditNoteModel editNoteModel;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_edit_note;
    }

    @Override
    protected boolean isShowTitleView() {
        return true;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        commonTitleView.setTitle("新增笔记/编辑笔记");

        et_happened.addTextChangedListener(new EditChangedListener(mContext, et_happened, tv_inputnum, maxCount));
    }

    @Override
    protected void initData() {
        editNoteModel = new EditNoteModel(mContext, this);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //原始数据
                String history = bundle.getString("content");
                if (TextUtils.isEmpty(history)) {
                    et_happened.setText("");
                } else {
                    et_happened.setText(history);
                }

                tv_inputnum.setText(et_happened.getText().toString().length() + "/" + maxCount);
                recentlyStudyClassHourId = bundle.getInt("recentlyStudyClassHourId", 0);
                courseId = bundle.getInt("id", 0);
                isEdit = bundle.getInt("isEdit", 0);
                classHourId = bundle.getInt("classHourId", 0);
            }
        }
    }

    @OnClick({R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit://确认
                String happened = et_happened.getText().toString();
                if (StringUtils.isEmpty(happened)) {
                    ToastUtils.showToast(mContext, "没有新内容");
                    return;
                }

                if (isEdit == 0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("classHourId", recentlyStudyClassHourId);
                    map.put("content", et_happened.getText().toString());
                    map.put("id", courseId);
                    editNoteModel.addNote(map);
                } else if (isEdit == 1) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("content", et_happened.getText().toString());
                    map.put("id", courseId);
                    editNoteModel.editNote(map);
                }
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(String success) {
        RxBus.getDefault().post(new EventBean(EventBean.REFRESH,null));
        ToastUtils.showToast(mContext, success);
    }

    @Override
    public void onError(String error) {

    }
}