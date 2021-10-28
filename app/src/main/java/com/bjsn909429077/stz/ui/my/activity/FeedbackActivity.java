package com.bjsn909429077.stz.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bjsn909429077.stz.R;
import com.bjsn909429077.stz.adapter.SelectPictureAdapter;
import com.bjsn909429077.stz.api.BaseUrl;
import com.bjsn909429077.stz.bean.VPBean;
import com.bjsn909429077.stz.utils.OSSUpDataUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jiangjun.library.api.NovateUtils;
import com.jiangjun.library.ui.base.BaseActivity;
import com.jiangjun.library.utils.ButtonUtils;
import com.jiangjun.library.utils.LoginCheck;
import com.jiangjun.library.utils.StringUtil;
import com.jiangjun.library.utils.ToastUtils;
import com.jiangjun.library.widget.LoadingDialog;
import com.jiangjun.library.widget.PromptDialog;
import com.jiangjun.library.widget.TakingPicturesPopupWindow;
import com.ruffian.library.widget.REditText;
import com.tamic.novate.Throwable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;

/**
 * @author XuZhuChao
 * @create 2021/10/21
 * @Describe 意见反馈
 */
public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.edit_content)
    REditText editContent;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private TakingPicturesPopupWindow picPopupWindow;
    private SelectPictureAdapter mSelectPictureAdapter;
    private List<VPBean> listPhoto = new ArrayList<>();
    public LoadingDialog progress;


    @Override
    protected int setLayoutRes() {
        return R.layout.activity_feedback;
    }

    @Override
    protected boolean isShowTitleView() {
        commonTitleView.setTitle("意见反馈");
        return true;
    }


    @Override
    protected void findViews(@Nullable Bundle savedInstanceState) {
        LoadingDialog.Builder loadBuilder = new LoadingDialog.Builder(mContext)
                .setMessage("数据提交中...")
                .setCancelable(false)
                .setCancelOutside(false);
        progress = loadBuilder.create();

        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvNumber.setText(editContent.getText().toString().length() + "/200");
            }
        });

        initPicSelectPopup();
    }


    @Override
    protected void initData() {

    }

    @OnClick(R.id.preservation)
    public void onClick() {

        if (ButtonUtils.isFastDoubleClick())
            return;
        String content = editContent.getText().toString().trim();
        if (StringUtil.isBlank(content)) {
            ToastUtils.Toast(mContext, "请提出您宝贵的意见");
            return;
        }
        progress.show();
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
                save_user_suggest(sb.toString());
            }
        });

    }




    /**
     * @author XuZhuChao
     * @create 2021/9/27
     * @Describe 初始化图片选择器
     */
    private void initPicSelectPopup(){

        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mSelectPictureAdapter = new SelectPictureAdapter();
        recyclerView.setAdapter(mSelectPictureAdapter);


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
                LoginCheck.getInstance().getInputMethodManager(mContext, editContent);
                if (item.getType() == 0) {
                    picPopupWindow.show(view_head, false);
                }
            }
        });

        mSelectPictureAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                LoginCheck.getInstance().getInputMethodManager(mContext, editContent);
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


    /**
     * 意见反馈提交
     */
    private void save_user_suggest(String pics) {

        String content = editContent.getText().toString().trim();

        HashMap<String, String> map = new HashMap<>();

        map.put("content", content);

        if (!TextUtils.isEmpty(pics)) {
            map.put("pics", pics);
        }
        NovateUtils.getInstance().Post(mContext, BaseUrl.feedback_add, map, false,
                new NovateUtils.setRequestReturn<Object>() {
                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.Toast(mContext, throwable.getMessage());
                        progress.dismiss();
                    }

                    @Override
                    public void onSuccee(Object response) {
                        ToastUtils.Toast(mContext, "反馈成功");
                        progress.dismiss();
                        finish();
                    }
                });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, FeedbackActivity.class));
    }
}