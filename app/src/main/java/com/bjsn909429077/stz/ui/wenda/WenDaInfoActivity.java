package com.bjsn909429077.stz.ui.wenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bjsn909429077.stz.Constant;
import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.SelectPictureAdapter;
import com.bjsn909429077.stz.adapter.WdInfoAdapter;
import com.bjsn909429077.stz.adapter.WenDaTypeAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.EmptyBean;
import com.bjsn909429077.stz.bean.VPBean;
import com.bjsn909429077.stz.bean.WdInfoBean;
import com.bjsn909429077.stz.bean.WdZhuiWenBean;
import com.bjsn909429077.stz.bean.WenDaTypeBean;
import com.bjsn909429077.stz.utils.OSSUpDataUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;

public class WenDaInfoActivity extends BaseActivity implements TakingPicturesPopupWindow.onCameraClickListener{
    @BindView(R.id.iv_collect)
    ImageView iv_collect;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    private int isCollect;
    private String wdId;
    private List<MediaBean> list;
    private RecyclerView recyclerImage;
    private PopupWindow mPopWindow;
    /*

    @BindView(R.id.et_question1)
    EditText et_question1;
    @BindView(R.id.tv_num1)
    TextView tv_num1;
    @BindView(R.id.et_question2)
    TextView et_question2;
    private List<WenDaTypeBean.DataBean> data;
    private int id;
    private List<MediaBean> list;*/
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;
    @Override
    protected int setLayoutRes() {
        return R.layout.activity_wd_info;
    }

    @Override
    protected boolean isShowTitleView() {
        return false;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
      //  initPopupWindow();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(manager);

        tool_bar.setNavigationOnClickListener(v -> {
            finish();
        });
    }

    private void getWdInfo(String wdId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("wdId",wdId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.wdInfo, map, true,
                new NovateUtils.setRequestReturn<WdInfoBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(WdInfoBean wdInfoBean) {
                        if (wdInfoBean!=null&&wdInfoBean.getData()!=null){
                            WdInfoBean.DataBean data = wdInfoBean.getData();
                            List<WdInfoBean.DataBean.WdQuestionAnswerDetailListBean> list = data.getWdQuestionAnswerDetailList();
                            int readCount = data.getReadCount();
                            int isAnswer = data.getIsAnswer();//是否解答：0、否 1、是
                            WdInfoAdapter adapter = new WdInfoAdapter(WenDaInfoActivity.this,R.layout.item_wd,list,readCount,isAnswer);
                            recycler_view.setAdapter(adapter);
                            isCollect = data.getIsCollect();
                            if (isCollect==1){//收藏
                                iv_collect.setBackgroundResource(R.drawable.icon_in1);
                            }else {
                                iv_collect.setBackgroundResource(R.drawable.icon_in);
                            }
                            adapter.setListener(new WdInfoAdapter.onBottonClickListener() {
                                @Override
                                public void onClick(String wdId) {
                                    //追问
                                    showPopWindow();
                                }
                            });
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        wdId = intent.getStringExtra("wdId");

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWdInfo(wdId);
    }

    @OnClick({R.id.iv_collect})
    public void onClick(View view) {
       switch (view.getId()) {
            case R.id.iv_collect://添加收藏
                //添加收藏
                addCollect();
                break;


            default:

        }
    }

    private void addCollect() {
        HashMap<String, String> map = new HashMap<>();
        map.put("wdId",wdId);
        NovateUtils.getInstance().Post(mContext, BaseUrl.wdCollect, map, true,
                new NovateUtils.setRequestReturn<EmptyBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(EmptyBean wdInfoBean) {
                        if (wdInfoBean!=null){//1为收藏，0为取消收藏
                            getWdInfo(wdId);
                        }
                    }
                });
    }

    private void showPopWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_wd_zhuiwen, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //获取实例，设置各个控件的点击响应
        //注意：PopupWindow中各个控件的所在的布局是contentView，而不是在Activity中，所以，要在findViewById(R.id.tv)前指定根布局
        TextView tv_button = contentView.findViewById(R.id.tv_button);
        TextView tv_num = contentView.findViewById(R.id.tv_num);
        recyclerImage = contentView.findViewById(R.id.recyclerImage);
        EditText et = contentView.findViewById(R.id.et);
        final WindowManager.LayoutParams lp = WenDaInfoActivity.this.getWindow().getAttributes();
        lp.alpha = 0.7f;//代表透明程度，范围为0 - 1.0f
        WenDaInfoActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WenDaInfoActivity.this.getWindow().setAttributes(lp);
        initPicSelectPopup();
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1.0f;
                WenDaInfoActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WenDaInfoActivity.this.getWindow().setAttributes(lp);
            }
        });
        et.addTextChangedListener(new TextWatcher() {
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
                if (temp.length() > 300) {
                    ToastUtils.Toast(WenDaInfoActivity.this, "最多200字");
                }
            }
        });


        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //追问
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
                        zwData(et.getText().toString(),sb.toString());
                    }
                });

            }
        });
        //解决5.0以下版本点击外部不消失问题
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 初始化图片选择器
     */
    private TakingPicturesPopupWindow picPopupWindow;
    private SelectPictureAdapter mSelectPictureAdapter;
    private List<VPBean> listPhoto = new ArrayList<>();
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
               // LoginCheck.getInstance().getInputMethodManager(mContext, et_question);
                if (item.getType() == 0) {
                    picPopupWindow.show(view_head, false);
                }
            }
        });

        mSelectPictureAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
           //     LoginCheck.getInstance().getInputMethodManager(mContext, et_question);
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
    //追问
    private void zwData(String data,String listimg) {

        HashMap<String, String> map = new HashMap<>();
        map.put("wdIds",wdId+"");
        map.put("questionDescribe",data);//问题描述
        map.put("questionPics",listimg);//问题图片
        NovateUtils.getInstance().Post(mContext, BaseUrl.zwWd, map, true,
                new NovateUtils.setRequestReturn<WdZhuiWenBean>() {
                    @Override
                    public void onError(Throwable throwable) {
                        mPopWindow.dismiss();
                        ToastUtils.Toast(mContext, throwable.getMessage());
                    }

                    @Override
                    public void onSuccee(WdZhuiWenBean wdZhuiWenBean) {
                        if (wdZhuiWenBean != null) {
                            mPopWindow.dismiss();
                            getWdInfo(wdId);
                        }
                    }
                });
    }

    @Override
    public void onReturnPicture(List<MediaBean> list) {
        if (list!=null){
            this.list = list;
        }
    }

    @Override
    public void onReturnPicture(String realFilePath) {
        if (realFilePath!=null){

        }
    }
}