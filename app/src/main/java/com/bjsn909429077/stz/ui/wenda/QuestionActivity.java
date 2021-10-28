package com.bjsn909429077.stz.ui.wenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bjsn909429077.stz.Constant;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.HomePagerAdapter;
import com.bjsn909429077.stz.adapter.SelectPictureAdapter;
import com.bjsn909429077.stz.adapter.WenDaTypeAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.VPBean;
import com.bjsn909429077.stz.bean.WDQbean;
import com.bjsn909429077.stz.bean.WdZhuiWenBean;
import com.bjsn909429077.stz.bean.WenDaTypeBean;
import com.bjsn909429077.stz.ui.MyApplication;
import com.bjsn909429077.stz.utils.OSSUpDataUtils;
import com.bjsn909429077.stz.widget.WrapContentHeightViewPager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ImageUtil;
import com.jiangjun.library.utils.LoginCheck;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.PromptDialog;
import com.jiangjun.library.widget.TakingPicturesPopupWindow;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import permissions.dispatcher.RuntimePermissions;

public class QuestionActivity extends BaseActivity {
    @BindView(R.id.et_question)
    EditText et_question;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.et_question1)
    EditText et_question1;
    @BindView(R.id.tv_num1)
    TextView tv_num1;
    @BindView(R.id.et_question2)
    TextView et_question2;
    @BindView(R.id.rely_type)
    RelativeLayout rely_type;
    @BindView(R.id.recyclerImage)
    RecyclerView recyclerImage;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    private TakingPicturesPopupWindow picPopupWindow;
    private SelectPictureAdapter mSelectPictureAdapter;
    private List<WenDaTypeBean.DataBean> data;
    private int typeId;
    private List<VPBean> listPhoto = new ArrayList<>();
    private String from;
    private String wdId;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_question1;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from.equals("tiwen")){
            //提问
            getWdType();
            rely_type.setVisibility(View.VISIBLE);
        }

        initPicSelectPopup();

        tool_bar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void getWdType() {
        HashMap<String, String> map = new HashMap<>();
        NovateUtils.getInstance().Post(mContext, BaseUrl.wdType, map, true,
                new NovateUtils.setRequestReturn<WenDaTypeBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(WenDaTypeBean wenDaTypeBean) {
                        if (wenDaTypeBean != null) {
                            data = wenDaTypeBean.getData();
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        et_question.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = s.length();
                tv_num.setText(number + "");
                if (temp.length() > 200) {
                    ToastUtils.Toast(QuestionActivity.this, "最多200字");
                }
            }
        });
        et_question1.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = s.length();
                tv_num1.setText(number + "");
                if (temp.length() > 200) {
                    ToastUtils.Toast(QuestionActivity.this, "最多200字");
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //筛选
        if (requestCode == Constant.REQUEST_CODE && resultCode == Activity.RESULT_OK) {

        }
    }


    private void showPopWindow() {

        //设置contentView
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_wenda, null);
        PopupWindow  mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //获取实例，设置各个控件的点击响应
        //注意：PopupWindow中各个控件的所在的布局是contentView，而不是在Activity中，所以，要在findViewById(R.id.tv)前指定根布局
        RecyclerView recycler_pop = contentView.findViewById(R.id.recycler_pop);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler_pop.setLayoutManager(gridLayoutManager);

        TextView tv_button = contentView.findViewById(R.id.tv_button);
        if (data!=null&&data.size()>0){
            WenDaTypeAdapter wenDaTypeAdapter = new WenDaTypeAdapter(R.layout.item_wenda_type,data);
            recycler_pop.setAdapter(wenDaTypeAdapter);
            wenDaTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    WenDaTypeBean.DataBean dataBean = data.get(position);
                    String name = dataBean.getName();
                    typeId = dataBean.getId();
                    et_question2.setText(name);
                    mPopWindow.dismiss();
                }
            });
        }

        //解决5.0以下版本点击外部不消失问题
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    @OnClick({R.id.et_question2,R.id.tv_show,R.id.tv_botton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_question2:
                showPopWindow();

                break;
            case R.id.tv_show:

                Intent intent = new Intent(QuestionActivity.this,QuestionInfoActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_botton:
                //提交问题
                if (TextUtils.isEmpty(et_question.getText().toString())){
                    ToastUtils.Toast(QuestionActivity.this,"请填写您的问题");
                    return;
                }
                if (TextUtils.isEmpty(et_question1.getText().toString())){
                    ToastUtils.Toast(QuestionActivity.this,"请填写您的问题描述");
                    return;
                }
                if (from.equals("tiwen")){
                    if (et_question2.getText().toString().equals("选择一级分类")){
                        ToastUtils.Toast(QuestionActivity.this,"请选择问题分类");
                        return;
                    }

                    new OSSUpDataUtils().upData(listPhoto, 0, new OSSUpDataUtils.OssUpCallback() {
                        @Override
                        public void onCallback(List<String> picList) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < picList.size(); i++) {
                                sb.append(picList.get(i));
                                if (i != picList.size() - 1) {
                                    sb.append(",");
                                }
                            }
                            upData(sb.toString());
                        }
                    });

                }

                break;
            default:

        }
    }


    //提问
    private void upData(String pics) {
        HashMap<String, String> map = new HashMap<>();
        map.put("questionName",et_question.getText().toString());
        map.put("typeId",typeId+"");//问题分类
        map.put("questionDescribe",et_question1.getText().toString());//问题描述

        map.put("questionPics",pics);//问题图片
        NovateUtils.getInstance().Post(mContext, BaseUrl.upWd, map, true,
                new NovateUtils.setRequestReturn<WDQbean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(WDQbean wDQbean) {
                        if (wDQbean != null) {
                            Intent intent = new Intent(QuestionActivity.this, QuestSuccessActivity.class);
                            intent.putExtra("wdId",wDQbean.getData().getWdId());
                            intent.putExtra("qrCode",wDQbean.getData().getQrCodeUrl());
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 初始化图片选择器
     */
    private void initPicSelectPopup(){

        recyclerImage.setLayoutManager(new GridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mSelectPictureAdapter = new SelectPictureAdapter();
        recyclerImage.setAdapter(mSelectPictureAdapter);


        picPopupWindow = new TakingPicturesPopupWindow(mContext);
        picPopupWindow.setMax(9);
        picPopupWindow.setList(true);
        picPopupWindow.setOnCameraClickListener(new TakingPicturesPopupWindow.onCameraClickListener() {
            @Override
            public void onReturnPicture(List<MediaBean> list) {
                listPhoto.clear();
                for (MediaBean mediaBean : list) {
                    listPhoto.add(new VPBean(1, mediaBean.getOriginalPath()));
                }
                if (listPhoto.size() != picPopupWindow.getMax()) {
                    listPhoto.add(new VPBean(0, ""));
                }

                mSelectPictureAdapter.setList(listPhoto);

            }

            @Override
            public void onReturnPicture(String realFilePath) {

            }
        });


        mSelectPictureAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                VPBean item = mSelectPictureAdapter.getItem(position);
                LoginCheck.getInstance().getInputMethodManager(mContext, et_question);
                if (item.getType() == 0) {
                    picPopupWindow.show(view_head, false);
                }
            }
        });

        mSelectPictureAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                LoginCheck.getInstance().getInputMethodManager(mContext, et_question);
                PromptDialog dialog = new PromptDialog(mContext, "确定移除该张图片", new PromptDialog.OnDialogClickListener() {
                    @Override
                    public void clickListener(String password, int whichBtn) {
                        if (whichBtn == 1) {
                            listPhoto.remove(position);
                            picPopupWindow.onListRemove(position);
                            if (listPhoto.size() != picPopupWindow.getMax()) {
                                boolean isAdd = true;
                                for (VPBean vpBean : listPhoto) {
                                    if (StringUtil.isBlank(vpBean.getUrl())) {
                                        isAdd = false;
                                    }
                                }
                                if (isAdd) {
                                    listPhoto.add(new VPBean(0, ""));
                                }
                            }
                        }

                        mSelectPictureAdapter.setList(listPhoto);

                    }
                });
                dialog.showDialog();
            }
        });

        listPhoto.add(new VPBean(0, ""));
        mSelectPictureAdapter.setList(listPhoto);
    }


}