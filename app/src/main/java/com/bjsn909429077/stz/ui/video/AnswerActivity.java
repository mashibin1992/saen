package com.bjsn909429077.stz.ui.video;

import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.ImageAdapter;
import com.jiangjun.library.ui.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class AnswerActivity extends BaseActivity implements ImageAdapter.OnItemClickListener {

    @BindView(R.id.image_list)
    RecyclerView image_list;
    @BindView(R.id.btn_add)
    ImageView btn_add;
    @BindView(R.id.tv_commit)
    TextView tv_commit;
    @BindView(R.id.tv_title_asw)
    TextView tv_title_asw;
    @BindView(R.id.et_happened)
    EditText et_happened;

    private ArrayList<String> filePath = new ArrayList<>();
    private ImageAdapter imageAdapter;

    @Override
    protected int setLayoutRes() {
        return R.layout.activity_answer2;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }

    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        tv_title_asw.setText(Html.fromHtml(getString(R.string.anwser_tv_title)));

        image_list.setLayoutManager(new GridLayoutManager(this, 3));
        imageAdapter = new ImageAdapter(filePath);
        image_list.setAdapter(imageAdapter);
        initListener();
    }

    private void initListener() {

        imageAdapter.setOnItemClickListener(this);
        //添加照片
        btn_add.setOnClickListener(v -> {

        });
        //提交
        tv_commit.setOnClickListener(v -> {

        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 预览图片
     *
     * @param path
     */
    @Override
    public void imageLook(String path) {

    }

    /**
     * 删除图片
     *
     * @param position
     */
    @Override
    public void imageDelete(int position) {

    }
}